package com.example.shops.registration.shop.model;

import javax.persistence.Embeddable;

@Embeddable
public class ShopAddress {
	
	int shopNumber;
	
	long postcode;
	
	String description;

	public int getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(int shopNumber) {
		this.shopNumber = shopNumber;
	}

	public long getPostcode() {
		return postcode;
	}

	public void setPostcode(long postcode) {
		this.postcode = postcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
