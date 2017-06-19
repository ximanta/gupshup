package com.stackroute.gupshup.userservice.producer;

public interface UserProducer {
	
	public void publishUserActivity(String topicName, String message);

}
