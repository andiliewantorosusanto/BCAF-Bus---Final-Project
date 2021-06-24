package com.bcaf.bus.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User{
	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("mobileNumber")
	private String mobileNumber;

	@SerializedName("roles")
	private List<RolesItem> roles;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<RolesItem> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesItem> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}