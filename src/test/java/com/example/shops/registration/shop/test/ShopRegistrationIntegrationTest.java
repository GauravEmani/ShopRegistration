package com.example.shops.registration.shop.test;

import com.example.shops.registration.shop.model.ShopAddress;
import com.example.shops.registration.shop.model.ShopDetailsEntity;
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
    	// ShopDetailsTransferObject mockTransferObject = new ShopDetailsTransferObject();
    	
    	ShopAddress address = new ShopAddress();
    	address.setPostcode(411061);
    	address.setShopNumber(1);
    	address.setDescription("Pimpri-Chinchwad Maharashtra India 411061");
    	
    	ShopDetailsEntity mockEntity = new ShopDetailsEntity();
    	mockEntity.setShopName("mockShop");
    	mockEntity.setAddress(address);
    	mockEntity.setLattitude(18.5882884);
    	mockEntity.setLongitude(73.8169099);
   
    	
    	shopRepository.save(mockEntity);
    	
    	ShopAddress address1 = new ShopAddress();
    	address1.setPostcode(411062);
    	address1.setShopNumber(2);
    	address1.setDescription("Maharashtra India 411062");
    	
    	ShopDetailsEntity mockEntity1 = new ShopDetailsEntity();
    	mockEntity1.setShopName("mockShop1");
    	mockEntity1.setAddress(address1);
    	mockEntity1.setLattitude(18.6962184);
    	mockEntity1.setLongitude(73.78308609999999);
    	
    	shopRepository.save(mockEntity1);

    }
}
