package com.thirdpart.model.entity;

import java.io.Serializable;
import java.util.List;

public class IssueResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String worstepid;

	private String stepno;

	private String stepname;

	private String describe;

	private String questionname;

	private String isOk;

	private String level;

	private String solvemethod;

	private String methodmanid;

	private String solvedate;

	private String concerman;

	private String createdBy;
	
	private String userId;

	private String solverid;
	
	private String issolve;
	
	private String currentsolver;
	
	public String getSolverid() {
		return solverid;
	}

	public void setSolverid(String solverid) {
		this.solverid = solverid;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private List<IssuePhoto> file;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setWorstepid(String worstepid) {
		this.worstepid = worstepid;
	}

	public String getWorstepid() {
		return this.worstepid;
	}

	public void setStepno(String stepno) {
		this.stepno = stepno;
	}

	public String getStepno() {
		return this.stepno;
	}

	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	public String getStepname() {
		return this.stepname;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}

	public String getQuestionname() {
		return this.questionname;
	}

	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}

	public String getIsOk() {
		return this.isOk;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return this.level;
	}

	public void setSolvemethod(String solvemethod) {
		this.solvemethod = solvemethod;
	}

	public String getSolvemethod() {
		return this.solvemethod;
	}

	public void setMethodmanid(String methodmanid) {
		this.methodmanid = methodmanid;
	}

	public String getMethodmanid() {
		return this.methodmanid;
	}

	public void setSolvedate(String solvedate) {
		this.solvedate = solvedate;
	}

	public String getSolvedate() {
		return this.solvedate;
	}

	public void setConcerman(String concerman) {
		this.concerman = concerman;
	}

	public String getConcerman() {
		return this.concerman;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setFile(List<IssuePhoto> file) {
		this.file = file;
	}

	public List<IssuePhoto> getFile() {
		return this.file;
	}

	@Override
	public String toString() {
		return "IssueResult [id=" + id + ", worstepid=" + worstepid
				+ ", stepno=" + stepno + ", stepname=" + stepname
				+ ", describe=" + describe + ", questionname=" + questionname
				+ ", isOk=" + isOk + ", level=" + level + ", solvemethod="
				+ solvemethod + ", methodmanid=" + methodmanid + ", solvedate="
				+ solvedate + ", concerman=" + concerman + ", createdBy="
				+ ", solverid=" + solverid 
				+ ", userId=" + userId + createdBy + ", file=" + file + "]";
	}

	public String getIssolve() {
		return issolve;
	}

	public void setIssolve(String issolve) {
		this.issolve = issolve;
	}

	public String getCurrentsolver() {
		return currentsolver;
	}

	public void setCurrentsolver(String currentsolver) {
		this.currentsolver = currentsolver;
	}

}