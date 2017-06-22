package com.stackroute.gupshup.recommendationservice.entity;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@NodeEntity(label="circle")
public class CircleRecommendation {
	
	@GraphId Long id;
	
	String keyword;
	String type;
	
	public CircleRecommendation(){ };
	
	@JsonCreator
	public CircleRecommendation(
			@JsonProperty("keyword") String keyword,
			@JsonProperty("type") String type
			){
		this.keyword = keyword;
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
