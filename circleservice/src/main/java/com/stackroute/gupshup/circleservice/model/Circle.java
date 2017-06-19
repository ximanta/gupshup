package com.stackroute.gupshup.circleservice.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

public class Circle extends ResourceSupport{
	
	@Id
	private String _id;
	private String circleId;
	private String circleName;
	private String circleDescription;
	private String circleCreatedBy;
	private String circleCreatedDate;
	private List<User> circleMembers;
	private List<String> keywords;
	
	public Circle() {
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
	public List<User> getCircleMembers() {
		return circleMembers;
	}
	public void setCircleMembers(List<User> circleMembers) {
		this.circleMembers = circleMembers;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
}
