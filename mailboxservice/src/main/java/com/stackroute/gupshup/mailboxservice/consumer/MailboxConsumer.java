package com.stackroute.gupshup.mailboxservice.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.mailboxservice.service.InboxService;

@Service
public class MailboxConsumer {

	@Autowired
	InboxService inboxService;

	public void subscribeUserActivity(String topic) {

		MailboxConsumerThread mailboxConsumerRunnable = new MailboxConsumerThread(topic, topic, inboxService);
		mailboxConsumerRunnable.start();
		mailboxConsumerRunnable.getUserConsumer().wakeup();
		System.out.println("stopping user consumer");

		try {
			mailboxConsumerRunnable.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class MailboxConsumerThread extends Thread 
{

	private String topicName;
	private String consumerGroupId;
	private KafkaConsumer<String, String> mailboxConsumer;
	private InboxService inboxService;

	public MailboxConsumerThread(String topicName, String consumerGroupId,InboxService inboxService) {
		this.topicName = topicName;
		this.consumerGroupId = consumerGroupId;
		this.inboxService = inboxService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Properties configProperties = new Properties();
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
		configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

		mailboxConsumer = new KafkaConsumer<String, String>(configProperties);
		mailboxConsumer.subscribe(Arrays.asList(topicName));

		while(true) 
		{
			ConsumerRecords<String, String> records = mailboxConsumer.poll(100);
			for(ConsumerRecord<String, String> record: records ) 
			{
				System.out.println(record.value());
				String value = record.value();
				ObjectMapper mapper = new ObjectMapper();

				try {
					JsonNode newNode = null;
					if(!value.equals("") && value != null && value.length()>0){
						newNode = mapper.readTree(value);
						inboxService.checkActivityType(newNode);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					Thread currentThread = Thread.currentThread();
					try {
						currentThread.join(0);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public KafkaConsumer<String, String> getUserConsumer() {
		return this.mailboxConsumer;
	}
}