package com.stackroute.gupshup.recommendationservice.entity;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label="person")
public class User {
	
	@GraphId Long id;
	
	private String name;
	private String firstname;
	private String lastname;
	private String gender;
	private String intrest;
	private int DOB;
	
	public User(){};
	
	public User(String name, String firstname, String lastname, String gender, String intrest, int DOB){
		this.name = name;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.intrest = intrest;
		this.DOB = DOB;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIntrest() {
		return intrest;
	}

	public void setIntrest(String intrest) {
		this.intrest = intrest;
	}

	public int getDOB() {
		return DOB;
	}

	public void setDOB(int dOB) {
		DOB = dOB;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstname=" + firstname + ", lastname=" + lastname + ", gender="
				+ gender + ", intrest=" + intrest + ", DOB=" + DOB + "]";
	}
	
	

}
