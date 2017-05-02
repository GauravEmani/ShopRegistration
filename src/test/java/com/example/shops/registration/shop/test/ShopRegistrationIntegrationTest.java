package com.example.shops.registration.shop.test;

import com.example.shops.registration.shop.repository.ShopsRepository;
import com.example.shops.registration.shop.util.ShopDetailsTransferObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("test")
public class ShopRegistrationIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ShopsRepository shopRepository;

    @Before
    public void setUp() {
    	shopRepository.deleteAll();
    }

    @After
    public void tearDown() {
    	shopRepository.deleteAll();
    }

    @Test
    public void shouldReturnObjectWithOkStatus(){

    	ShopDetailsTransferObject mockTransferObject = new ShopDetailsTransferObject();
    	mockTransferObject.setShopName("test");
    	mockTransferObject.setPostcode("411061");
    	mockTransferObject.setShopNumber("1");
        ResponseEntity<ShopDetailsTransferObject> response =  testRestTemplate.postForEntity("/shops", mockTransferObject, ShopDetailsTransferObject.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
     }

    @Test
    public void shouldReturnTransferObjectWithAddresses(){
    	
    	// Add shops 
    	createMockShopEntries();
    	
    	String lat = "18.5882884";
    	String lng = "73.8169099";
    	
    	String url = "/location/shops?lattitude="+lat+"&longitude="+lng;
    	
    	ShopDetailsTransferObject[] response =  testRestTemplate.getForObject(url, ShopDetailsTransferObject[].class);
    	
        assertNotNull(response[0].getPostcode());
        assertNotNull(response[0].getDetailedAddress());
        assertNotNull(response[1].getDetailedAddress());
        assertNotNull(response[1].getPostcode());
        
    }
    
    public void createMockShopEntries() {
    	ShopDetailsTransferObject mockTransferObject = new ShopDetailsTransferObject();
    	mockTransferObject.setShopName("mockShop");
    	mockTransferObject.setPostcode("411061");
    	mockTransferObject.setShopNumber("1");
        ResponseEntity<ShopDetailsTransferObject> response =  testRestTemplate.postForEntity("/shops", mockTransferObject, ShopDetailsTransferObject.class);
        
        ShopDetailsTransferObject mockTransferObject1 = new ShopDetailsTransferObject();
    	mockTransferObject.setShopName("mockShop1");
    	mockTransferObject.setPostcode("411062");
    	mockTransferObject.setShopNumber("2");
        ResponseEntity<ShopDetailsTransferObject> response1 =  testRestTemplate.postForEntity("/shops", mockTransferObject, ShopDetailsTransferObject.class);


    }
}
