package com.stackroute.gupshup.circleservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.stackroute.gupshup.circleservice.consumer.ConsumerThread;
import com.stackroute.gupshup.circleservice.service.CircleService;


@Service
public class CircleServiceConsumer {
	
	//------------------------------------circle service auto wired------------------------------------
	@Autowired
	CircleService circleService;
	
	//------------------------------------Environment auto wired -------------------------------------------
	@Autowired
	Environment environment;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void consumeActivity(String topic) 
    {
	    ConsumerThread consumerRunnable = new ConsumerThread(topic,topic,circleService,environment);
        consumerRunnable.start();
        consumerRunnable.getKafkaConsumer().wakeup();
        logger.debug(environment.getProperty("circleconsumer.consumer-stopmessage"));
        try {
			consumerRunnable.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
    }
}
