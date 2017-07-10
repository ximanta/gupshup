package com.stackroute.gupshup.recommendationservice.linkassembler;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;

public interface RecommendationLinkAssembler {
	
	public UserRecommendation userRecommendationLinks(UserRecommendation userRecommendation);
	public CircleRecommendation circleRecommendationLinks(CircleRecommendation circleRecommendation);
}
