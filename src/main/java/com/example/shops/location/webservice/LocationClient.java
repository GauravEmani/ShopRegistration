package com.example.shops.location.webservice;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("https://maps.googleapis.com/maps/api/geocode")
public interface LocationClient {
	// ?key= AIzaSyB9zUApoXMiCSw7fM9PMV7GOGDSpoo5ICU
	@LoadBalanced
	@RequestMapping(value = "/json", method = RequestMethod.GET)
    String getLocationDetails(@RequestParam("address") String zipcode, @RequestParam("key") String key);
	
}
