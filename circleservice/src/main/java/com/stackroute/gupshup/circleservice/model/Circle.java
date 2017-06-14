package com.stackroute.gupshup.circleservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

public class Circle extends ResourceSupport{
	@Id
	private String circleId;
	private String personName;
	private String personId;
	private String circleName;
	private String circleDescription;
	private String circleCreatedBy;
	private String circleCreatedDate;
	private String circleMembers[];
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getCircleDescription() {
		return circleDescription;
	}
	public void setCircleDescription(String circleDescription) {
		this.circleDescription = circleDescription;
	}
	public String getCircleCreatedBy() {
		return circleCreatedBy;
	}
	public void setCircleCreatedBy(String circleCreatedBy) {
		this.circleCreatedBy = circleCreatedBy;
	}
	public String getCircleCreatedDate() {
		return circleCreatedDate;
	}
	public void setCircleCreatedDate(String circleCreatedDate) {
		this.circleCreatedDate = circleCreatedDate;
	}
	public String[] getCircleMembers() {
		return circleMembers;
	}
	public void setCircleMembers(String[] circleMembers) {
		this.circleMembers = circleMembers;
	}
	
	
	
}
