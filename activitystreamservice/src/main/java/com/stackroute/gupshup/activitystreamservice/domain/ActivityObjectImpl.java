package com.stackroute.gupshup.activitystreamservice.domain;

public final class ActivityObjectImpl implements ActivityObject {

	private final String type;
	private final String name;
	private final String content;

	public ActivityObjectImpl(final String type, final String name,final String cotent) {
		this.name = name;
		this.type = type;
		this.content = cotent;
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
	public String getContent() {
		
		return content;
	}

	@Override
	public String toString() {
		return "ActivityObjectImpl [type=" + type + ", name=" + name + ", content=" + content + "]";
	}
	
	

	

}
