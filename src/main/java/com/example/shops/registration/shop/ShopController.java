package com.example.shops.registration.shop;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shops.registration.shop.model.ShopDetailsEntity;
import com.example.shops.registration.shop.repository.ShopsRepository;
import com.example.shops.registration.shop.util.ResponseHelper;
import com.example.shops.registration.shop.util.ShopDetailsTransferObject;

@RestController
public class ShopController {

	ShopServiceIF shopService;

	public ShopController(@Autowired ShopServiceIF shopService) {
		this.shopService = shopService;
	}

	@RequestMapping(value = "/shops", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<ShopDetailsTransferObject> createShop(@RequestBody ShopDetailsTransferObject transferObject) {
		try 
		{
			if(transferObject == null) 
			{
				return new ResponseEntity<ShopDetailsTransferObject>((ShopDetailsTransferObject)null, 
						HttpStatus.BAD_REQUEST);
			}
			else 
			{
				ShopDetailsTransferObject updatedTransferObject;
				if(shopService.checkForExistingShopRecord(transferObject)) {
					updatedTransferObject = shopService.addNewShop(transferObject);
					MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
					params.add("update-info", "An existing shop record has been updated");
					return new ResponseEntity<ShopDetailsTransferObject>(updatedTransferObject, params, HttpStatus.OK);
				}
				updatedTransferObject = shopService.addNewShop(transferObject);
				return new ResponseEntity<ShopDetailsTransferObject>(updatedTransferObject, HttpStatus.OK);
			}

		} 
		catch (UnexpectedRollbackException rollbackException) 
		{
			return ResponseHelper.error(HttpStatus.CONFLICT, "Shop Creation failed", rollbackException);
		} 
		catch (Exception exception) 
		{
			return ResponseHelper.error(HttpStatus.INTERNAL_SERVER_ERROR,
					"An internal server exception occured.Please check the stack trace.", exception);
		}
	}

	@RequestMapping(value = "/location/shops", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ShopDetailsTransferObject[]> getShops(@RequestParam("lattitude") String lat,
			@RequestParam("longitude") String lng) {
		try 
		{
			ShopDetailsTransferObject tempObj = new ShopDetailsTransferObject();
			tempObj.setLatitude(lat);
			tempObj.setLongitude(lng);
		
			if(tempObj == null || lat == null || lng == null) 
			{
				return new ResponseEntity<ShopDetailsTransferObject[]>((ShopDetailsTransferObject[])null, 
						HttpStatus.BAD_REQUEST);
			}
			else 
			{				
				return new ResponseEntity<ShopDetailsTransferObject[]>(
						convertResultListToArray(shopService.getAllNearbyShops(tempObj)), HttpStatus.OK);
			}
		} 
		catch (UnexpectedRollbackException rollbackException) 
		{
			return ResponseHelper.error(HttpStatus.CONFLICT, "Stop failed", rollbackException);
		}
		catch (Exception exception) 
		{
			return ResponseHelper.error(HttpStatus.INTERNAL_SERVER_ERROR,
					"An internal server exception occured.Please check the stack trace.", exception);
		}
	}

	private ShopDetailsTransferObject[] convertResultListToArray(Collection<ShopDetailsTransferObject> collection) {
		return collection.toArray(new ShopDetailsTransferObject[collection.size()]);
	}

}
