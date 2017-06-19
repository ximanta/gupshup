package com.stackroute.gupshup.userservice.domain;

public interface Activity extends ASObject{
	public String getSummary();
	public ASObject getActor();
	public ASObject getObject();
}
