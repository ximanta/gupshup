package com.stackroute.gupshup.recommendationservice.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@NodeEntity(label="circle")
public class CircleRecommendation {
	
	@GraphId Long id;

	@NotNull(message="circle id cannot be null")
	@Size(min=1, message="circle id cannot be empty")
	String circleId;
	
	@NotNull(message="ciecle name cannot be null")
	@Size(min=1, message="circle name cannot be empty")
	String circleName;
	
	@NotNull(message="circle keywords cannot be null")
	@Size(min=1, message="circle keyword cannot be empty")
	String keyword;
	
	public CircleRecommendation(){ };
	
	@JsonCreator
	public CircleRecommendation(
			@JsonProperty("circleId") String circleId,
			@JsonProperty("circleName") String circleName,
			@JsonProperty("keyword") String keyword
			){
		this.circleId = circleId;
		this.circleName = circleName;
		this.keyword = keyword;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getCircleName() {
		return circleName;
	}

	public void setType(String circleName) {
		this.circleName = circleName;
	}

	@Override
	public String toString() {
		return "CircleRecommendation [id=" + id + ", circleId=" + circleId + ", circleName=" + circleName + ", keyword="
				+ keyword + "]";
	}

}
