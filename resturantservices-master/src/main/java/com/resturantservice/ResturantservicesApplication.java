package com.resturantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.resturantservice.api.repos")
public class ResturantservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResturantservicesApplication.class, args);
	}
	
}
