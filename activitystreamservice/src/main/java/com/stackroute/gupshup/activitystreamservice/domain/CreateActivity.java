package com.stackroute.gupshup.activitystreamservice.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public final class CreateActivity {
	
	private final ActivityObject activityObject;
	private final ActorObject actorObject;
	
	public CreateActivity(final ActivityObject activityObject,final ActorObject actorObject){
		this.activityObject = activityObject;
		this.actorObject = actorObject;
	}

	public ActivityObject getActivityObject() {
		return new CreateActivity(activityObject, actorObject).getActivityObject();
	}

	public ActorObject getActorObject() {
		return new CreateActivity(activityObject, actorObject).getActorObject();
	}

	@Override
	public String toString() {
		return "CreateActivity [activityObject=" + activityObject + ", actorObject=" + actorObject + "]";
	}
	
}
