package com.stackroute.gupshup.circleservice.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Delete {

private final String context;
	
	@NotNull(message="type is required")
	private final String type;
	
	private final String summary;
	
	@NotNull(message="actor can not be null")
	private final ASObject actor;
	
	@NotNull(message="object can not be null")
	private final ASObject object;

	@JsonCreator
	public Delete (
			@JsonProperty("@context") String context,
			@JsonProperty("type") String type,
			@JsonProperty("summary") String summary,
			@JsonDeserialize(as=Person.class) @JsonProperty("actor") ASObject actor,
			@JsonDeserialize(as=Note.class) @JsonProperty("object") ASObject object) {
		this.context = context;
		this.type = type;
		this.summary = summary;
		this.object = object;
		this.actor = actor;
	}

	public String getContext() {
		return context;
	}

	public String getType() {
		return type;
	}

	public String getSummary() {
		return summary;
	}

	public ASObject getActor() {
		return actor;
	}

	public ASObject getObject() {
		return object;
	}

}
