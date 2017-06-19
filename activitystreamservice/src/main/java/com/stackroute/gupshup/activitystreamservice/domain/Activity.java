package com.stackroute.gupshup.activitystreamservice.domain;

public interface Activity extends ASObject{
	public String getSummary();
	public ASObject getActor();
	public ASObject getObject();
}
