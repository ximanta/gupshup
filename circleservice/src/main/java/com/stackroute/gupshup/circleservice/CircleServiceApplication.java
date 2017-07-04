package com.stackroute.gupshup.circleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.stackroute.gupshup.circleservice.consumer.CircleServiceConsumer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
public class CircleServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(CircleServiceApplication.class, args);
		CircleServiceConsumer circleServiceConsumer = applicationContext.getBean(CircleServiceConsumer.class);
		Environment environment = applicationContext.getEnvironment();
		circleServiceConsumer.consumeActivity(environment.getProperty("circleservice.topic.circle"));
	}
	
}
