package com.stackroute.gupshup.userservice.domain;
<<<<<<< HEAD

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Person implements ASObject {
=======
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Person implements ASObject{
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
	
	private final String context;
	private final String type;
	private final String name;
	
	@JsonCreator
<<<<<<< HEAD
	public Person(
			@JsonProperty("@context") String context,
			@JsonProperty("type") String type,
			@JsonProperty("name") String name) {
		
=======
	public Person(@JsonProperty("@context") String context, @JsonProperty("type") String type, @JsonProperty("name") String name) {
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
		this.context = context;
		this.type = type;
		this.name = name;
	}

	@Override
<<<<<<< HEAD
	public String getContext() {
		return context;
	}

	@Override
	public String getType() {
		return type;
=======
	public String getType() {
		return type;
	}

	@Override
	public String getContext() {
		return context;
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
	}

	public String getName() {
		return name;
	}
<<<<<<< HEAD
	

=======
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
}
