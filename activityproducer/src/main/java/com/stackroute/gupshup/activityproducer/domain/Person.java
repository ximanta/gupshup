package com.stackroute.gupshup.activityproducer.domain;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Person implements ASObject{
	
	private final String context;
	
	@NotNull(message="type is required")
	private final String type;
	
	@NotNull(message="id is required")
	private final String id;
	
	@NotNull(message="name is required")
	private final String name;
	
	private final String image ;
	
	@JsonCreator
	public Person(
			@JsonProperty("@context") String context,
			@JsonProperty("id") String id,
			@JsonProperty("type") String type,
			@JsonProperty("name") String name,
			@JsonProperty("image") String image) {
		this.context = context;
		this.type = type;
		this.name = name;
		this.id = id;
		this.image = image;
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

	public String getImage() {
		return image;
	}
	
}
