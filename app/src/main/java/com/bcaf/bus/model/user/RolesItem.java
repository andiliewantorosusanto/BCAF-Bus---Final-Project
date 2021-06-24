package com.bcaf.bus.model.user;

import com.google.gson.annotations.SerializedName;

public class RolesItem{

	@SerializedName("role")
	private String role;

	@SerializedName("id")
	private int id;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
