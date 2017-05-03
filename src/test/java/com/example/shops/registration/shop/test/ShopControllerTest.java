package com.example.shops.registration.shop.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.shops.registration.shop.ShopController;
import com.example.shops.registration.shop.ShopServiceIF;
import com.example.shops.registration.shop.util.ShopDetailsTransferObject;

public class ShopControllerTest {

	public ShopControllerTest() {
	}

	private ShopServiceIF mockShopServiceImplementation = null;
	private ShopController controller = null;
	private String validPostCode = null;
	private String invalidLattitude = null;
	private String validLattitude = null;
	private String invalidLongitude = null;
	private String validLongitude = null;
	private String validShopNumber = null;

	@Before
	public void setUp() {
		mockShopServiceImplementation = mock(ShopServiceIF.class);
		controller = new ShopController(mockShopServiceImplementation);
		invalidLattitude = null;
		validLattitude = "18.882884";
		invalidLongitude = null;
    	validLongitude = "73.8169099";
		validPostCode = "411061";
		validShopNumber = "1";
	}

	@After
	public void tearDown() {
		mockShopServiceImplementation = null;
		controller = null;
		validLattitude = null;
		invalidLattitude = null;
		validLongitude = null;
		invalidLongitude = null;
		validPostCode = null;
		validShopNumber = null;
	}

	@Test
	public void testAddNewShop() throws Exception 
	{
		ShopDetailsTransferObject mockTransferObject = createMockTransferObject();

		when(mockShopServiceImplementation.addNewShop(mockTransferObject)).thenReturn(mockTransferObject);

		// check for not found response when transfer object is null
		ResponseEntity<ShopDetailsTransferObject> result = controller.createShop(null);
		verify(mockShopServiceImplementation, never()).addNewShop(null);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

		// check for valid response on addition of valid shop transfer object
		result = controller.createShop(mockTransferObject);
		verify(mockShopServiceImplementation, times(1)).addNewShop(mockTransferObject);
		assertEquals(HttpStatus.OK, result.getStatusCode());

		// check for valid response with updated shop data when a different
		// request for the same shop is posted
		mockTransferObject.setShopName("Same Shop From Different user");
		result = controller.createShop(mockTransferObject);
		verify(mockShopServiceImplementation, times(2)).addNewShop(mockTransferObject);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	@Test
	public void testForFetchingNearbyShops() 
	{	
		List<ShopDetailsTransferObject> shopDetailsArr = new ArrayList<ShopDetailsTransferObject>();
		
		ShopDetailsTransferObject mockTransferObj = new ShopDetailsTransferObject();
		mockTransferObj.setLatitude(validLattitude);
		mockTransferObj.setLongitude(validLongitude);
		
		when(mockShopServiceImplementation.getAllNearbyShops(mockTransferObj)).thenReturn(shopDetailsArr);
		
		// check for not found response when lattitude and longitude are null
		ResponseEntity<ShopDetailsTransferObject[]> result = controller.getShops(invalidLattitude, invalidLongitude);
		verify(mockShopServiceImplementation, never()).getAllNearbyShops(null);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		// check for not found response when lattitude is null
		result = controller.getShops(invalidLattitude, validLongitude);
		verify(mockShopServiceImplementation, never()).getAllNearbyShops(mockTransferObj);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		// check for not found response when longitude is null
		result = controller.getShops(validLattitude, invalidLongitude);
		verify(mockShopServiceImplementation, never()).getAllNearbyShops(mockTransferObj);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		// check for a valid response when lattitude and longitude are valid
		result = controller.getShops(validLattitude, validLongitude);
		verify(mockShopServiceImplementation, times(1)).getAllNearbyShops(mockTransferObj);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	public ShopDetailsTransferObject createMockTransferObject() 
	{
		ShopDetailsTransferObject mockTransferObject = new ShopDetailsTransferObject();
		mockTransferObject.setPostcode(validPostCode);
		mockTransferObject.setShopNumber(validShopNumber);
		
		return mockTransferObject;
	}
}