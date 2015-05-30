package com.thirdpart.model.entity;

import java.io.Serializable;

public class IssuePhoto implements Serializable{
	private String path;

	private String fileName;

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}

}