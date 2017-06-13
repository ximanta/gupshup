package com.stackroute.gupshup.activitystreamservice.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public final class ActorObjectImpl implements ActorObject{
	
	private final String type;
	private final String name;
	
	public ActorObjectImpl(final String type,final String name){
		this.name = name;
		this.type = type;
	}

	@Override
	public String getType() {
		
		return type;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public String toString() {
		return "ActorObjectimpl [type=" + type + ", name=" + name + "]";
	}

}
