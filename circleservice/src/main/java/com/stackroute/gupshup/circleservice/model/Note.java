package com.stackroute.gupshup.circleservice.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class Note implements ASObject {

	private final String context;
	
	@NotNull(message="type is required")
	private final String type;
	
	private final String name;
	
	@NotNull(message="content can not be null")
	private final String content;

	@JsonCreator
	public Note(
			@JsonProperty("context") String context, 
			@JsonProperty("type") String type, 
			@JsonProperty("name") String name, 
			@JsonProperty("content") String content) {
		this.context = context;
		this.type = type;
		this.name = name;
		this.content = content;
	}

	public String getContext() {
		return context;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}
	
}
