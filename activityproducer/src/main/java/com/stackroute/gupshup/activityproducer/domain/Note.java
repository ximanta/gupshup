package com.stackroute.gupshup.activityproducer.domain;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class Note implements ASObject {

	private final String context;
	
	@NotNull(message="type is required")
	private final String type;
	
	private final String summary;
	
	@NotNull(message="content can not be null")
	private final String content;

	@JsonCreator
	public Note(@JsonProperty("context") String context, @JsonProperty("type") String type, @JsonProperty("summary") String summary, @JsonProperty("content") String content) {
		this.context = context;
		this.type = type;
		this.summary = summary;
		this.content = content;
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

	public String getContent() {
		return content;
	}

	
	
}
