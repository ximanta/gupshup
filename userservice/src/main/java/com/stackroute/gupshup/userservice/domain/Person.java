package com.stackroute.gupshup.userservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Person implements ASObject {
	
	private final String context;
	private final String type;
	private final String name;
	
	@JsonCreator
	public Person(
			@JsonProperty("@context") String context,
			@JsonProperty("type") String type,
			@JsonProperty("name") String name) {
		
		this.context = context;
		this.type = type;
		this.name = name;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	

}
