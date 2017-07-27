package com.stackroute.gupshup.maildeliveryservice.model;

public interface Activity extends ASObject{
	public String getSummary();
	public ASObject getActor();
	public ASObject getObject();
}
