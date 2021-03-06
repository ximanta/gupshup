package com.stackroute.gupshup.userservice.domain;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Group implements ASObject{
	
	private final String context;
	
	@NotNull(message="Type is required")
	private final String type;
	
	@NotNull(message="Id is required")
	private final String id;
	
	@NotNull(message="Name is required")
	private final String name;
	
	@JsonCreator
	public Group(
			@JsonProperty("@context") String context,
			@JsonProperty("id") String id,
			@JsonProperty("type") String type,
			@JsonProperty("name") String name) {
		this.context = context;
		this.type = type;
		this.name = name;
		this.id = id;
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

	public String getId() {
		return id;
	}
	
}
