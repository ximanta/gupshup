package com.stackroute.gupshup.userservice.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@Document
public class User extends ResourceSupport {
	
	@Id
	@NotNull(message = "error.userName.notnull")
	private String userName;
	
	@NotNull(message = "error.firstName.notnull")
	private String firstName;
	
	@NotNull(message = "error.lastName.notnull")
	private String lastName;
	
	@NotNull(message = "error.password.notnull")
	@Size(min=8, message = "error.password.size")
	private String password;
	
	@NotNull(message = "error.gender.notnull")
	private String gender;
	
	@NotNull(message = "error.dob.notnull")
	private String dob;
	
	@NotNull(message = "error.emailId.notnull")
	private String emailId;
	
	@NotNull(message = "error.contactNo.notnull")
	private String contactNo;
	
	private String profilePhoto;
	private long followingCount;
	private List<User> following;
		
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public long getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(long followingCount) {
		this.followingCount = followingCount;
	}
	public List<User> getFollowing() {
		return following;
	}
	public void setFollowing(List<User> following) {
		this.following = following;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", gender=" + gender + ", dob=" + dob + ", emailId=" + emailId + ", contactNo=" + contactNo
				+ ", profilePhoto=" + profilePhoto + ", followingCount=" + followingCount + ", following=" + following
				+ "]";
	}
}
