package com.stackroute.gupshup.userservice.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserProducerCallback implements Callback{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
        	logger.debug("Error producing to topic " + recordMetadata.topic());
           e.printStackTrace();
        }
    }
}
