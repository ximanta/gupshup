package com.stackroute.gupshup.userservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.userservice.service.UserService;

@Service
public class UserConsumer {
	
	@Autowired
	UserService service;
	
	public void subscribeUserActivity(String topic) {

		UserConsumerThread userConsumerRunnable = new UserConsumerThread(topic, topic, service);
		userConsumerRunnable.start();

		userConsumerRunnable.getUserConsumer().wakeup();

		System.out.println("stopping user consumer");

		try {
			userConsumerRunnable.join();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
