package com.example.shops.registration.shop.util;

import com.example.shops.registration.shop.model.ShopAddress;
import com.example.shops.registration.shop.model.ShopDetailsEntity;

public class ShopObjectToEntityMapper {
	
	public void ShopDetailsTransferObject() {
		
	}
	
	/*
	 *  Description: This method will primarily set all the data from the transferObject into the entity
	 */
	public ShopDetailsEntity updateEntityValues(ShopDetailsTransferObject transferObject, ShopDetailsEntity entity) {
	
		// create a shop address object
		ShopAddress address = new ShopAddress();
		address.setDescription(transferObject.getDetailedAddress());
		address.setPostcode(Integer.parseInt(transferObject.getPostcode()));
		address.setShopNumber(Integer.parseInt(transferObject.getShopNumber()));
		
		// set all the data from transfer to entity
		entity.setAddress(address);
		entity.setLattitude(Double.parseDouble(transferObject.getLatitude()));
		entity.setLongitude(Double.parseDouble(transferObject.getLongitude()));
		entity.setShopName(transferObject.getShopName());
		
		return entity;
	}
}
