package com.example.shops.registration.shop.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Id;

@Embeddable
public class ShopAddress {
	
	int shopNumber;
	
	@Column(unique=true)
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
