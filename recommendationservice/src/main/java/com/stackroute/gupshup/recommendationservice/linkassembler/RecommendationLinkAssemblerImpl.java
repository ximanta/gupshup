package com.stackroute.gupshup.recommendationservice.linkassembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.stackroute.gupshup.recommendationservice.controller.RecommendationController;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;

@Component
public class RecommendationLinkAssemblerImpl implements RecommendationLinkAssembler {

	
	/*-------add, delete and update hateous links for circle-------*/
	@Override
	public CircleRecommendation circleRecommendationLinks(CircleRecommendation circleRecommendation) {
		
		Link selfLink = linkTo(RecommendationController.class).slash(circleRecommendation.getCircleId()).withSelfRel();
		circleRecommendation.add(selfLink);
		
		Link updateLink = linkTo(RecommendationController.class).slash(circleRecommendation.getCircleId()).withRel("updateUser");
		circleRecommendation.add(updateLink);
		
		Link deleteLink = linkTo(RecommendationController.class).slash(circleRecommendation.getCircleId()).withRel("deleteUser");
		circleRecommendation.add(deleteLink);
		
		return circleRecommendation;
	}

	/*-------add, delete and update hateous links for user-------*/
	@Override
	public UserRecommendation userRecommendationLinks(UserRecommendation userRecommendation) {
		
		Link selfLink = linkTo(RecommendationController.class).slash(userRecommendation.getName()).withSelfRel();
		userRecommendation.add(selfLink);
		
		Link updateLink = linkTo(RecommendationController.class).slash(userRecommendation.getName()).withRel("updateUser");
		userRecommendation.add(updateLink);
		
		Link deleteLink = linkTo(RecommendationController.class).slash(userRecommendation.getName()).withRel("deleteUser");
		userRecommendation.add(deleteLink);
		
		return userRecommendation;
	}
	
	
	

}

