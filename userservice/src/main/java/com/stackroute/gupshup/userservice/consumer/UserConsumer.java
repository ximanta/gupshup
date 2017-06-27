package com.stackroute.gupshup.userservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.userservice.service.UserService;

@Service
public class UserConsumer {
	
	@Autowired
	UserService service;
		
	/* subscribing an activity */
	public void subscribeUserActivity(String topic) {

		UserConsumerThread userConsumerRunnable = new UserConsumerThread(topic, topic, service);
		userConsumerRunnable.start();

		userConsumerRunnable.getUserConsumer().wakeup();
<<<<<<< HEAD

=======
>>>>>>> 5a0a0455d3c4a2c72901561988a71b12257b539b
		try {
			userConsumerRunnable.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
