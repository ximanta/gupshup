package com.stackroute.gupshup.circleservice.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.service.CircleService;

class ConsumerThread extends Thread
{
	private String topicName;
	private String groupId;
	private KafkaConsumer<String,String> kafkaConsumer;
	private CircleService circleService;
	private Environment environment;

	//----------------------------------Logger implementation----------------------------------
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ConsumerThread(String topicName, String groupId, CircleService circleService, Environment environment){
		this.topicName = topicName;
		this.groupId = groupId;
		this.circleService = circleService;
		this.environment = environment;
	}

	public void run() {
		Properties configProperties = new Properties();
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,environment.getProperty("circleservice.bootstrap-server"));
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

		//Figure out where to start processing messages from
		kafkaConsumer = new KafkaConsumer<String, String>(configProperties);
		kafkaConsumer.subscribe(Arrays.asList(topicName));

		//Start processing messages
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode node = objectMapper.readTree(record.value());
					logger.debug(record.value());
					System.out.println(record.value());
					circleService.getActivityType(node);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				catch (CircleException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public KafkaConsumer<String,String> getKafkaConsumer()
	{
		return this.kafkaConsumer;
	}
}
