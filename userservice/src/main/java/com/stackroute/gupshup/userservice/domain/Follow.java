package com.stackroute.gupshup.userservice.domain;
<<<<<<< HEAD

=======
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

<<<<<<< HEAD
public class Follow implements Activity {
=======
public class Follow implements Activity{
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf

	private final String context;
	private final String type;
	private final String summary;
	private final ASObject actor;
	private final ASObject object;
	
	@JsonCreator
	public Follow(
<<<<<<< HEAD
			
=======
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
			@JsonProperty("@context") String context,
			@JsonProperty("type") String type,
			@JsonProperty("summary") String summary,
			@JsonDeserialize(as=Person.class) @JsonProperty("actor") ASObject actor,
<<<<<<< HEAD
			@JsonDeserialize(as=Person.class) @JsonProperty("object") ASObject object
			) {
		
=======
			@JsonDeserialize(as=Person.class) @JsonProperty("object") ASObject object) {
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
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
	
<<<<<<< HEAD
=======
	
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
}
