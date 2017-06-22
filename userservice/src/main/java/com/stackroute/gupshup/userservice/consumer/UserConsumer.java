package com.stackroute.gupshup.userservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.userservice.producer.UserProducer;
import com.stackroute.gupshup.userservice.service.UserService;

@Service
public class UserConsumer {
	
	@Autowired
	UserService service;
	
	@Autowired
	UserProducer userProducer;
	
	/* subscribing an activity */
	public void subscribeUserActivity(String topic) {

		UserConsumerThread userConsumerRunnable = new UserConsumerThread(topic, topic, service, userProducer);
		userConsumerRunnable.start();

		userConsumerRunnable.getUserConsumer().wakeup();

		System.out.println("stopping user consumer");

		try {
			userConsumerRunnable.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
