package com.stackroute.gupshup.circleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
public class CircleserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircleserviceApplication.class, args);
	}
}
