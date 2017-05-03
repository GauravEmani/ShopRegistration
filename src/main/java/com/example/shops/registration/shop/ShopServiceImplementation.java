package com.example.shops.registration.shop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.shops.location.webservice.LocationClient;
import com.example.shops.registration.shop.model.ShopDetailsEntity;
import com.example.shops.registration.shop.repository.ShopsRepository;
import com.example.shops.registration.shop.util.EntityToTransferObjectMapper;
import com.example.shops.registration.shop.util.LocationBasedCalculator;
import com.example.shops.registration.shop.util.ShopDetailsTransferObject;
import com.example.shops.registration.shop.util.ShopObjectToEntityMapper;


@Component
public class ShopServiceImplementation implements ShopServiceIF{
	
	String latitude;
	String longitude;
	String address;
	
	final String API_KEY = "AIzaSyD-7mnApTTTvsRNKyGtYDVC93UwxPjvnPY";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	LocationClient locationClient;
	@Autowired
	ShopsRepository shopRepository;
	
	@Autowired
	LocationBasedCalculator locationCalculator;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	public boolean checkForExistingShopRecord(ShopDetailsTransferObject transferObject) {
		return shopRepository.findOne(transferObject.getPostcode()) != null;
	}
	
	public String getLocationFromGoogle(String zipcode) {
	
		// Call the google apis to fetch the geo-location	
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+zipcode+"&key="+API_KEY;
	    return restTemplate().getForObject(url, String.class);
		// return restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address=411061&key=AIzaSyD-7mnApTTTvsRNKyGtYDVC93UwxPjvnPY", String.class);

	}
	
	public ShopDetailsTransferObject updateLatNLongInTransferObj(String googleApiResp, ShopDetailsTransferObject transferObj) {
		JSONObject obj = new JSONObject(googleApiResp);
		JSONArray ja = obj.getJSONArray("results");

		// GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
		JSONObject jo = (JSONObject) ja.getJSONObject(0).get("geometry");

		// Get location object
		JSONObject jo1 = jo.getJSONObject("location");

		// set the values in the transfer object
		transferObj.setLatitude(jo1.get("lat").toString());
		transferObj.setLongitude(jo1.get("lng").toString());
		
		// Get location object
		String formattedAddress = (String) ja.getJSONObject(0).get("formatted_address");

		// set the formatted address in the transfer object
		transferObj.setDetailedAddress(formattedAddress);
		return transferObj;
	}
	

	@Override
	public ShopDetailsTransferObject addNewShop(ShopDetailsTransferObject transferObject) {
		// Create an Entity object
		ShopDetailsEntity shopEntity = new ShopDetailsEntity();

		// Call the Google API to fetch json based on postcode
		String responseBody = getLocationFromGoogle(transferObject.getPostcode());

		// Update transferObject with lattitude and longitude
		ShopDetailsTransferObject updatedTransferObject = updateLatNLongInTransferObj(responseBody, transferObject);

		// Update transferObject with address details
		// ShopDetailsTransferObject updateTransferObjectWithAddress = updateTransferObjectWithAddress()
		
		// Instantiate the objectToEntityMapper class
		ShopObjectToEntityMapper entityMapper = new ShopObjectToEntityMapper();
		shopEntity = entityMapper.updateEntityValues(updatedTransferObject, shopEntity);

		// save the entity
		shopRepository.save(shopEntity);
		
		// create transfer object from saved entity
		EntityToTransferObjectMapper transferObjectMapper = new EntityToTransferObjectMapper();
		ShopDetailsTransferObject finalTransferObject = transferObjectMapper.updateTransferObjectfromEntity(shopEntity,
				transferObject);
		return finalTransferObject;
	}
	
	public boolean fetchNearbyLocations(ShopDetailsTransferObject transferObject, ShopDetailsEntity entity) {
		return locationCalculator.compareDistance(Double.parseDouble(transferObject.getLatitude()), Double.parseDouble(transferObject.getLongitude()), 
				entity.getLattitude(), entity.getLongitude());
	}

	@Override
	public List<ShopDetailsTransferObject> getAllNearbyShops(ShopDetailsTransferObject transferObject) {
		// get all shops from the repository
		List<ShopDetailsEntity> listOfShopEntities = shopRepository.findAll();
		
		List<ShopDetailsEntity> listOfNearbyShopEntities = new ArrayList<ShopDetailsEntity>();
		
		List<ShopDetailsTransferObject> listOfTransferObjects = new ArrayList<ShopDetailsTransferObject>();
		
		EntityToTransferObjectMapper transferObjectMapper = new EntityToTransferObjectMapper();
		
		// iterate over the shops
		for (ShopDetailsEntity shopDetailsEntity : listOfShopEntities) {
			if(fetchNearbyLocations(transferObject, shopDetailsEntity)) {
				listOfNearbyShopEntities.add(shopDetailsEntity);
		
				listOfTransferObjects.add(transferObjectMapper.updateTransferObjectfromEntity(shopDetailsEntity, new ShopDetailsTransferObject()));
			}
		}		
	
		return listOfTransferObjects;
	}

	@Override
	public String getLocationData() {
		// TODO Auto-generated method stub
		return null;
	}
}
