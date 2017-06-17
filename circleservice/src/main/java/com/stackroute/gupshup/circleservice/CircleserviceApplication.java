package com.stackroute.gupshup.circleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.stackroute.gupshup.circleservice.consumer.CircleServiceConsumer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
public class CircleserviceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(CircleserviceApplication.class, args);
		CircleServiceConsumer circleServiceConsumer = applicationContext.getBean(CircleServiceConsumer.class);
		circleServiceConsumer.consumeActivity("group");
	}
}
