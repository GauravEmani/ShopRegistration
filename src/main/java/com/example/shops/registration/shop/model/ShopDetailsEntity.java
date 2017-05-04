package com.example.shops.registration.shop.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class ShopDetailsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	int shopId;
	
	String shopName;
	
	@Embedded
	@org.springframework.data.annotation.Version
	ShopAddress shopAddress;
	
	double lattitude;
	
	double longitude;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ShopAddress getAddress() {
		return shopAddress;
	}

	public void setAddress(ShopAddress address) {
		this.shopAddress = address;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double d) {
		this.lattitude = d;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}	
}
