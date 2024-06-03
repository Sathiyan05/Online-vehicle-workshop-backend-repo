package com.ovw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages ="com.ovw")
public class OnlineVehicleWorkshopApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(OnlineVehicleWorkshopApplication.class, args);
	}
	


}
