package com.stackroute.gupshup.gupshupMBS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.stackroute.gupshup.gupshupMBS.consumer.MailboxConsumer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
public class GupshupMbsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(GupshupMbsApplication.class, args);
        MailboxConsumer mailBoxConsumer=applicationContext.getBean(MailboxConsumer.class);
        mailBoxConsumer.subscribeUserActivity("mailbox");
	}
}
