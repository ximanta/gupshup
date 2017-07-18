package com.stackroute.gupshup.recommendationservice.exception;

public class RecommendationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/*---------exception class to throw exception when username or circle ID id incorrect------------*/
	public RecommendationException(String message){
		super(message);
	}

}

