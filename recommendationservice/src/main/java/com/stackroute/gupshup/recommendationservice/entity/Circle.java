package com.stackroute.gupshup.recommendationservice.entity;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity(label="circle")
public class Circle {
	
	@GraphId Long id;
	
	String keyword;
	String type;
	
	public Circle(){ };
	
	public Circle(String keyword, String type){
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

	public void setName(String keyword) {
		this.keyword = keyword;
	}

}
