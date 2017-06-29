package com.stackroute.gupshup.recommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import com.stackroute.gupshup.recommendationservice.consumer.RecommendationConsumer;


@SpringBootApplication
@EnableDiscoveryClient
@EnableNeo4jRepositories(basePackages={"com.stackroute.gupshup.recommendationservice.repository"})
public class RecommendationApplication {
	
	public static void main(String[] args) {
		//SpringApplication.run(RecommendationApplication.class, args);
		
		ConfigurableApplicationContext applicationContext = SpringApplication.run(RecommendationApplication.class, args);
	       RecommendationConsumer recommendationConsumer=applicationContext.getBean(RecommendationConsumer.class);
	       recommendationConsumer.consumeActivity("recommendation");
	}
}

