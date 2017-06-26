package com.stackroute.gupshup.activityproducer.producer;

public interface ActivityProducer {

	public void publishMessage(String topicName,String activity);
}
