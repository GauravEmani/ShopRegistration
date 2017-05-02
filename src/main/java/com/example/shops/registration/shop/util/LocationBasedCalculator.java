package com.example.shops.registration.shop.util;

import org.springframework.stereotype.Component;

@Component
public class LocationBasedCalculator {
	
	public boolean compareDistance(double userlattitude, double userlongitude, double existingLattitude, double existingLongitude) {

		// lat1 and lng1 are the values of a previously stored location
	    if (distance(userlattitude, userlongitude, existingLattitude, existingLongitude) < 10) { // if distance < 10 miles we take locations as equal
	       return true;
	    }
	    return false;
	}

	/** calculates the distance between two locations in MILES */
	private double distance(double lat1, double lng1, double lat2, double lng2) {

	    double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);

	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);

	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	        * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	    double dist = earthRadius * c;

	    return dist; // output distance, in MILES
	}

}
