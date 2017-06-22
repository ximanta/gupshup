package com.stackroute.gupshup.recommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication
@EnableDiscoveryClient
@EnableNeo4jRepositories(basePackages={"com.stackroute.gupshup.recommendationservice.repository"})
public class RecommendationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RecommendationApplication.class, args);
	}
}
