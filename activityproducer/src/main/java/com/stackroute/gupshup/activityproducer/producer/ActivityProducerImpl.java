package com.stackroute.gupshup.activityproducer.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ActivityProducerImpl implements ActivityProducer{

	//---------------------------------autowired environment----------------------------
	@Autowired
	Environment environment;

	public void publishMessage(String topicName,String activity){

		Properties configProperties = new Properties();
		configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,environment.getProperty("activityproducer.bootstrap-server"));
		configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.ByteArraySerializer");
		configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(configProperties);

		ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName,activity);
		producer.send(rec);
		producer.close();
	}	
	
}