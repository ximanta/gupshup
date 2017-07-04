package com.stackroute.gupshup.circleservice.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document
public class Circle extends ResourceSupport{
	
	@Id
	private String circleId;
	private String circleName;
	private String circleDescription;
	private String circleCreatedBy;
	private String circleCreatedDate;
	private Date lastupdated;
	private long totalUsers;
	private long totalmails;
	private List<User> circleMembers;
	private List<String> keywords;
	private List<Mail> mailbox;
	
	public Circle() {
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

	public List<Mail> getMailbox() {
		return mailbox;
	}

	public void setMailbox(List<Mail> mailbox) {
		this.mailbox = mailbox;
	}
	
}
