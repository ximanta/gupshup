package com.stackroute.gupshup.activityproducer.domain;

public interface Activity extends ASObject{
	public String getSummary();
	public ASObject getActor();
	public ASObject getObject();
}
