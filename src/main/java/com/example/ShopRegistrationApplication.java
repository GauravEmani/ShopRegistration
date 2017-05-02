package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
/*@EnableAutoConfiguration*/
public class ShopRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopRegistrationApplication.class, args);
	}
}
