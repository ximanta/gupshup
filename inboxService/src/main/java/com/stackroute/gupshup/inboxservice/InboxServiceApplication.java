package com.stackroute.gupshup.inboxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.stackroute.gupshup.inboxservice.consumer.ActivityConsumer;

@SpringBootApplication
@EnableDiscoveryClient
public class InboxServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(InboxServiceApplication.class, args);
		ActivityConsumer consumer = applicationContext.getBean(ActivityConsumer.class);
		consumer.publishMessage("notopic");
		
	}
}
