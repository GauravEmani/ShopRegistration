package com.example.shops.registration.shop.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.shops.registration.shop.model.ShopDetailsEntity;

@Repository
public interface ShopsRepository extends CrudRepository<ShopDetailsEntity, String>{

	List<ShopDetailsEntity> findByShopId(@Param("shopId") int shopId);
	
	ShopDetailsEntity findByShopAddress_Postcode(@Param("postcode") long postcode);
}
