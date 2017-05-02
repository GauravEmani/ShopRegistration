package com.example.shops.registration.shop.util;

import com.example.shops.registration.shop.model.ShopDetailsEntity;

public class EntityToTransferObjectMapper {
	
	public void EntityEntityToTransferObjectMapper() {
		
	}
	
	public ShopDetailsTransferObject updateTransferObjectfromEntity(ShopDetailsEntity entity, ShopDetailsTransferObject transferObject) {
		
		transferObject.setDetailedAddress(entity.getAddress().getDescription());
		transferObject.setLatitude(""+entity.getLattitude());
		transferObject.setLongitude(""+entity.getLongitude());
		transferObject.setPostcode(""+entity.getAddress().getPostcode());
		transferObject.setShopName(entity.getShopName());
		transferObject.setShopNumber(""+entity.getAddress().getShopNumber());
		
		return transferObject;
	}

}
