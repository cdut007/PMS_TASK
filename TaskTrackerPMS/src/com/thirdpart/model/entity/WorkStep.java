package com.thirdpart.model.entity;

public class WorkStep {
	private String id;

	private String stepno;

	private String stepflag;

	private String stepname;

	private String noticeaqc1;

	private String createdOn;

	private String createdBy;

	private RollingPlan rollingPlan;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setStepno(String stepno) {
		this.stepno = stepno;
	}

	public String getStepno() {
		return this.stepno;
	}

	public void setStepflag(String stepflag) {
		this.stepflag = stepflag;
	}

	public String getStepflag() {
		return this.stepflag;
	}

	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	public String getStepname() {
		return this.stepname;
	}

	public void setNoticeaqc1(String noticeaqc1) {
		this.noticeaqc1 = noticeaqc1;
	}

	public String getNoticeaqc1() {
		return this.noticeaqc1;
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

	public void setRollingPlan(RollingPlan rollingPlan) {
		this.rollingPlan = rollingPlan;
	}

	public RollingPlan getRollingPlan() {
		return this.rollingPlan;
	}

}