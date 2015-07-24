package com.thirdpart.model.entity;

import java.io.Serializable;
import java.util.List;

public class WorkStep implements Serializable{
//	id": 62,
//    "stepno": 2,
//    "stepflag": "PREPARE",
//    "stepname": "内部清洁度检查",
//    "operater": "sdfs",
//    "operatedate": 1432979982000,
//    "operatedesc": "dfsdfsd",
//    "noticeainfo": null,
//    "noticeresult": null,
//    "noticeresultdesc": null,
//    "noticeaqc1": "W",
//    "witnesseraqc1": null,
//    "witnessdateaqc1": null,
//    "noticeaqc2": "W",
//    "witnesseraqc2": null,
//    "witnessdateaqc2": null,
//    "noticeaqa": "W",
//    "witnesseraqa": null,
//    "witnessdateaqa": null,
//    "noticeb": null,
//    "witnesserb": null,
//    "witnessdateb": null,
//    "noticec": null,
//    "witnesserc": null,
//    "witnessdatec": null,
//    "noticed": null,
//    "witnesserd": null,
//    "witnessdated": null,
//    "createdOn": 1432957324000,
//    "createdBy": "admin",
//    "updatedOn": 1432979991000,
//    "updatedBy": "zhangxu",
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 631028160921504581L;

	private String updatedBy,updatedOn,noticeaqa,noticeaqc2,witnesserb,witnesserc,witnesserd;
	
	public String operater,operatedesc,witnesseraqa,witnesseraqc2,witnesseraqc1,noticeresult,noticeresultdesc;
	public List<WitnessInfo> witnessInfo,witnessesAssign;
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	
	public String getNoticeaqa() {
		return noticeaqa;
	}

	public void setNoticeaqa(String noticeaqa) {
		this.noticeaqa = noticeaqa;
	}

	public String getNoticeaqc2() {
		return noticeaqc2;
	}

	public void setNoticeaqc2(String noticeaqc2) {
		this.noticeaqc2 = noticeaqc2;
	}

	public String getWitnesserb() {
		return witnesserb;
	}

	public void setWitnesserb(String witnesserb) {
		this.witnesserb = witnesserb;
	}

	public String getWitnesserc() {
		return witnesserc;
	}

	public void setWitnesserc(String witnesserc) {
		this.witnesserc = witnesserc;
	}

	public String getWitnesserd() {
		return witnesserd;
	}

	public void setWitnesserd(String witnesserd) {
		this.witnesserd = witnesserd;
	}

	private String id;

	private String stepno;

	private String stepflag;

	private String stepname;

	private String noticeaqc1;

	private String createdOn;

	private String createdBy;

	private RollingPlan rollingPlan;

	public String noticec;

	public String noticed;

	public String noticeb;


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