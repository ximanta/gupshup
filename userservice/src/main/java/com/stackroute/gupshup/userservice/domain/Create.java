package com.stackroute.gupshup.userservice.domain;
<<<<<<< HEAD

=======
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

<<<<<<< HEAD
public final class Create implements Activity {
	
=======
public final class Create implements Activity{

>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
	private final String context;
	private final String type;
	private final String summary;
	private final ASObject actor;
	private final ASObject object;
<<<<<<< HEAD
	
=======

>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
	@JsonCreator
	public Create(
			@JsonProperty("@context") String context,
			@JsonProperty("type") String type,
			@JsonProperty("summary") String summary,
			@JsonDeserialize(as=Person.class) @JsonProperty("actor") ASObject actor,
			@JsonDeserialize(as=Person.class) @JsonProperty("object") ASObject object) {
<<<<<<< HEAD
		
		this.context = context;
		this.type = type;
		this.summary = summary;
		this.actor = actor;
		this.object = object;
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
	
	
=======
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
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf

}
