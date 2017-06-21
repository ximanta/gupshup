package com.stackroute.gupshup.circleservice.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.circleservice.consumer.ConsumerThread;
import com.stackroute.gupshup.circleservice.service.CircleService;
import com.stackroute.gupshup.circleservice.service.CircleServiceImpl;

@Service
public class CircleServiceConsumer {
	
	@Autowired
	CircleService circleService;
	
	public void consumeActivity(String topic) 
    {
	    ConsumerThread consumerRunnable = new ConsumerThread(topic,topic,circleService);
        consumerRunnable.start();
        consumerRunnable.getKafkaConsumer().wakeup();
        System.out.println("Stopping consumer .....");
        try {
			consumerRunnable.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
}

class ConsumerThread extends Thread
{
    private String topicName;
    private String groupId;
    private KafkaConsumer<String,String> kafkaConsumer;
    private CircleService circleService;

    public ConsumerThread(String topicName, String groupId, CircleService circleService){
        this.topicName = topicName;
        this.groupId = groupId;
        this.circleService = circleService;
    }
    public void run() {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.23.239.182:9092");
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
						circleService.getActivityType(node);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} catch (IOException e) {
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
