package com.thirdpart.model.entity;

public class MyWitness {
	private String id;

	private String witnesser;

	private String status;

	private String isok;

	private String noticePoint;

	private String createdOn;

	private String createdBy;

	private WorkStep workStep;

	private String parent;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setWitnesser(String witnesser) {
		this.witnesser = witnesser;
	}

	public String getWitnesser() {
		return this.witnesser;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}

	public String getIsok() {
		return this.isok;
	}

	public void setNoticePoint(String noticePoint) {
		this.noticePoint = noticePoint;
	}

	public String getNoticePoint() {
		return this.noticePoint;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setWorkStep(WorkStep workStep) {
		this.workStep = workStep;
	}

	public WorkStep getWorkStep() {
		return this.workStep;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent() {
		return this.parent;
	}

}