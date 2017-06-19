package com.stackroute.gupshup.userservice.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.producer.UserProducer;
import com.stackroute.gupshup.userservice.service.UserService;

public class UserConsumerThread extends Thread {
	
	private String topicName;
	private String consumerGroupId;
	private KafkaConsumer<String, String> userConsumer;
	private UserService userService;
	private UserProducer userProducer;
	
	public UserConsumerThread(String topicName, String consumerGroupId,UserService userService, UserProducer userProducer) {
		this.topicName = topicName;
		this.consumerGroupId = consumerGroupId;
		this.userService = userService;
		this.userProducer = userProducer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Properties configProperties = new Properties();
		/* setting all configurations for a consumer */
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.160:9092");
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
		configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");
		
		userConsumer = new KafkaConsumer<String, String>(configProperties);
		/* subscribing a topic */
		userConsumer.subscribe(Arrays.asList(topicName));
		
		while(true) {
			ConsumerRecords<String, String> records = userConsumer.poll(100);
			
			for(ConsumerRecord<String, String> record: records ) {
				System.out.println(record.value());
				String value = record.value();
				/* publishing activity to Recommendation and Mailbox1 topic */
				userProducer.publishUserActivity("Recommendation", value);
				userProducer.publishUserActivity("Mailbox1", value);
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					JsonNode newNode = mapper.readTree(value);
					userService.checkActivityType(newNode);
					
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
		
	public KafkaConsumer<String, String> getUserConsumer() {
		return this.userConsumer;
	}
}
