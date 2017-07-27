package com.stackroute.gupshup.recommendationservice.consumer;

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
import com.stackroute.gupshup.recommendationservice.service.CircleRecommendationService;
import com.stackroute.gupshup.recommendationservice.service.UserRecommendationService;

//------class to create a consumer thread for kafka--------
public class RecommendationConsumerThread extends Thread{
	
    private String topicName;
    private String groupId;
    private KafkaConsumer<String,String> kafkaConsumer;
    private UserRecommendationService userRecommendationService;
    private CircleRecommendationService circleRecommendationService;
    
    public RecommendationConsumerThread(String topicName, String groupId, 
    		UserRecommendationService userRecommendationService, 
    		CircleRecommendationService circleRecommendationService){
    	
    	this.topicName = topicName;
    	this.groupId = groupId;
    	this.userRecommendationService = userRecommendationService;
    	this.circleRecommendationService = circleRecommendationService;
    }
    
   // ---------------method to run kafka consumer thread----------------
    public void run() {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.238.219:9092,172.23.238.219:9093,172.23.238.219:9094");
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
						System.out.println(record.value());
						circleRecommendationService.getActiviType(node);
						userRecommendationService.getActivityType(node);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
            }
    }
    
    public KafkaConsumer<String, String> getUserConsumer() {
		return this.kafkaConsumer;
	}

}

