package com.stackroute.gupshup.activityproducer.domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Group implements ASObject{
	
	private final String context;
	private final String type;
	private final String name;
	
	@JsonCreator
	public Group(@JsonProperty("@context") String context, @JsonProperty("type") String type, @JsonProperty("name") String name) {
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
