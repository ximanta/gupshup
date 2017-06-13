package com.stackroute.gupshup.inboxservice.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;
@Service
public class ActivityConsumer 
{
	public void publishMessage(String topic) 
	    {
		    ConsumerThread consumerRunnable = new ConsumerThread(topic,topic);
	        consumerRunnable.start();
//	        String line = "";
//	        while (!line.equals("exit")) {
//	            line = in.next();
//	        }
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

        public ConsumerThread(String topicName, String groupId){
            this.topicName = topicName;
            this.groupId = groupId;
        }
        public void run() {
            Properties configProperties = new Properties();
            configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
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
                    for (ConsumerRecord<String, String> record : records)
                        System.out.println(record.value());
                }
           
        }
        public KafkaConsumer<String,String> getKafkaConsumer()
        {
           return this.kafkaConsumer;
        }
   }
