package com.thirdpart.model.entity;

import java.util.List;

public class Team {
	
	
	private String id;
	
	public String type;
	
	
	public List<UserInfo> users;
	private String name;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}