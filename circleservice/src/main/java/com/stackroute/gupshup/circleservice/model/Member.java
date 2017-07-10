package com.stackroute.gupshup.circleservice.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Member {
	
	@Id
	private String id;
	
	@NotNull(message="user name is required")
	private String username;
	
	private String fullname;
	private String profilePicture;
	private Date accessTime;
	private int status;
	private int range;
	private int offset;
	private long readMails;
	private long unreadMails;
	private String circleId;
	private String circleName;

	public Member() {
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public long getReadMails() {
		return readMails;
	}

	public void setReadMails(long readMails) {
		this.readMails = readMails;
	}
	
	public long getUnreadMails() {
		return unreadMails;
	}

	public void setUnreadMails(long unreadMails) {
		this.unreadMails = unreadMails;
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

	public Member(String circleId, String circleName,long unreadMails) {
		this.unreadMails = unreadMails;
		this.circleId = circleId;
		this.circleName = circleName;
	}

}
