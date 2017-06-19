package com.stackroute.gupshup.activitystreamservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Person implements ASObject{
	
	private final String context;
	private final String type;
	private final String name;
	
	@JsonCreator
	public Person(@JsonProperty("@context") String context, @JsonProperty("type") String type, @JsonProperty("name") String name) {
		this.context = context;
		this.type = type;
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getContext() {
		return context;
	}

	public String getName() {
		return name;
	}
}
