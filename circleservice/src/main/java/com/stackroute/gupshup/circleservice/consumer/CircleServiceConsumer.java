package com.stackroute.gupshup.circleservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.circleservice.service.CircleService;


@Service
public class CircleServiceConsumer {
	
	//------------------------------------Circle service autowired------------------------------------
	@Autowired
	CircleService circleService;
	
	//------------------------------------Environment autowired --------------------------------------
	@Autowired
	Environment environment;
	
	public void consumeActivity(String topic) 
    {
	    ConsumerThread consumerRunnable = new ConsumerThread(topic,topic,circleService,environment);
        consumerRunnable.start();
        consumerRunnable.getKafkaConsumer().wakeup();
        
        try {
			consumerRunnable.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
    }
}
