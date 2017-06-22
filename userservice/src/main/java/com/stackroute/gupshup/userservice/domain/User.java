package com.stackroute.gupshup.userservice.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@Document
public class User extends ResourceSupport {
	
	@Id
	private ObjectId _id;

	@NotNull(message = "user name can not be null")
	private String userName;
	
	@NotNull(message = "first name can not be null")
	private String firstName;
	
	@NotNull(message = "Last name can not be null")
	private String lastName;
	
	@NotNull(message = "password can not be null")
	@Size(min=8, message = "password length can not be less than 8")
	private String password;
	
	@NotNull(message = "gender can not be null")
	private String gender;
	
	@NotNull(message = "DOB can not be null")
	private String dob;
	
	@NotNull(message = "email can not be null")
	private String emailId;
	
	@NotNull(message = "contact no. can not be null")
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
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	@Override
	public String toString() {
		return "User [_id=" + _id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", gender=" + gender + ", dob=" + dob + ", emailId=" + emailId
				+ ", contactNo=" + contactNo + ", profilePhoto=" + profilePhoto + ", followingCount=" + followingCount
				+ ", following=" + following + "]";
	}
}
