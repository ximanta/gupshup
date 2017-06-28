package com.stackroute.gupshup.userservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.userservice.service.UserService;

@Service
public class UserConsumer {
	
	@Autowired
	UserService service;
	
	@Autowired
	private Environment environment;
		
	/* subscribing an activity */
	public void subscribeUserActivity(String topic) {

		UserConsumerThread userConsumerRunnable = new UserConsumerThread(environment.getProperty("userconsumer.user-topic"), environment.getProperty("userconsumer.user-topic"), service, environment);
		userConsumerRunnable.start();

		userConsumerRunnable.getUserConsumer().wakeup();

		try {
			userConsumerRunnable.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
