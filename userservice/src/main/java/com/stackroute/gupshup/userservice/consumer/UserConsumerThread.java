package com.stackroute.gupshup.userservice.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.service.UserService;

public class UserConsumerThread extends Thread {
	
	private String topicName;
	private String consumerGroupId;
	private KafkaConsumer<String, String> userConsumer;
	private UserService userService;
<<<<<<< HEAD
		
	public UserConsumerThread(String topicName, String consumerGroupId,UserService userService) {
=======
	private UserProducer userProducer;
	
	@Autowired
	private Environment environment;
	
	public UserConsumerThread(String topicName, String consumerGroupId,UserService userService, UserProducer userProducer) {
>>>>>>> 5a0a0455d3c4a2c72901561988a71b12257b539b
		this.topicName = topicName;
		this.consumerGroupId = consumerGroupId;
		this.userService = userService;
	}
	
	@Override
	public void run() {

		Properties configProperties = new Properties();
		/* setting all configurations for a consumer */
<<<<<<< HEAD
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.202:9092");
		configProperties.put("acks", "all");
		configProperties.put("retries", "3");
		configProperties.put("linger.ms", 5);
=======
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("userconsumer.bootstrap-servers"));
>>>>>>> 5a0a0455d3c4a2c72901561988a71b12257b539b
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("userconsumer.consumer-groupid"));
		configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, environment.getProperty("userconsumer.clientid"));
		
		userConsumer = new KafkaConsumer<String, String>(configProperties);
		/* subscribing a topic */
		userConsumer.subscribe(Arrays.asList(environment.getProperty("userconsumer.user-topic")));
		
		while(true) {
			ConsumerRecords<String, String> records = userConsumer.poll(100);
			
			for(ConsumerRecord<String, String> record: records ) {
				String value = record.value();
<<<<<<< HEAD
							
=======
				
				/* publishing activity to Recommendation and Mailbox1 topic */
				userProducer.publishUserActivity(environment.getProperty("userproducer.mailbox-topic"), value);
				userProducer.publishUserActivity(environment.getProperty("userproducer.recommendation-topic"), value);
>>>>>>> 5a0a0455d3c4a2c72901561988a71b12257b539b
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					JsonNode newNode = mapper.readTree(value);
					userService.checkActivityType(newNode);
					
				} catch (JsonProcessingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
		
	public KafkaConsumer<String, String> getUserConsumer() {
		return this.userConsumer;
	}
}
