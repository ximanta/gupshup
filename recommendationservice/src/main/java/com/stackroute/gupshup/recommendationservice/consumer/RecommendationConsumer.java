package com.stackroute.gupshup.recommendationservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.recommendationservice.service.CircleRecommendationService;
import com.stackroute.gupshup.recommendationservice.service.UserRecommendationService;

//-----kafka consumer class-------
@Service
public class RecommendationConsumer {
	
	@Autowired
	UserRecommendationService userRecommendationService;
	
	@Autowired
	CircleRecommendationService circleRecommendationService;
	
	//--------method to consume messages from recommendation topic--------
	public void consumeActivity(String topic){
		
		RecommendationConsumerThread recommendationConsumerRunnable = new RecommendationConsumerThread(topic, topic, userRecommendationService, circleRecommendationService);
		recommendationConsumerRunnable.start();
		recommendationConsumerRunnable.getUserConsumer().wakeup();
		System.out.println("stopping user consumer");
		try {
			recommendationConsumerRunnable.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

