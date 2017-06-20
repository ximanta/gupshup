package com.stackroute.gupshup.userservice.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
public class UserProducerImpl implements UserProducer {
	
	/* publishing an activity to a topic */
	public void publishUserActivity(String topicName, String message)
	{
		Properties configProperties = new Properties();
		
		/* setting all the configurations for a producer */
		configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.159:9092");
		configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
		configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer userProducer = new KafkaProducer<>(configProperties);
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, message);
				
	}

}
