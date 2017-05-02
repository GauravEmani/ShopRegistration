package com.example.shops.registration.shop;

import java.util.Collection;

import com.example.shops.registration.shop.util.ShopDetailsTransferObject;

public interface ShopServiceIF {
	
	// method declaration for adding a shop
	public ShopDetailsTransferObject addNewShop(ShopDetailsTransferObject transferObject);
	
	// method declaration to fetch nearby shop locations
	public Collection<ShopDetailsTransferObject> getAllNearbyShops(ShopDetailsTransferObject transferObject);
	
	// method declaration to fetch location data from google location api
	public  String getLocationData();

}
