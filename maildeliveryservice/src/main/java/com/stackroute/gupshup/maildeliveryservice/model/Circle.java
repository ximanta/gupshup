package com.stackroute.gupshup.maildeliveryservice.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class Circle {
	
	private String circleId;
	@NotNull
	private String circleName;
	@NotNull
	private String circleDescription;
	private String circleCreatedBy;
	private String circleCreatedDate;
	private Date lastupdated;
	private long totalUsers;
	private long totalmails;
	@NotNull
	private List<String> keywords;
	
	public Circle() {
	}

	public Circle(String circleId, String circleName,long unreadMessage){
		this.circleId = circleId;
		this.circleName = circleName;
		this.totalmails = unreadMessage;
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

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalmails() {
		return totalmails;
	}

	public void setTotalmails(long totalmails) {
		this.totalmails = totalmails;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
}
