package com.thirdpart.model.entity.base;

import java.io.Serializable;

import com.thirdpart.model.entity.WorkStep;

public class Data implements Serializable{

	private String id;

	private String witness;

	private String witnessdes;

	private String witnessaddress;

	private String witnessdate;

	private String status;

	private String isok;

	private String triggerName;

	private String createdOn;

	private String createdBy;

	private WorkStep workStep;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setWitness(String witness) {
		this.witness = witness;
	}

	public String getWitness() {
		return this.witness;
	}

	public void setWitnessdes(String witnessdes) {
		this.witnessdes = witnessdes;
	}

	public String getWitnessdes() {
		return this.witnessdes;
	}

	public void setWitnessaddress(String witnessaddress) {
		this.witnessaddress = witnessaddress;
	}

	public String getWitnessaddress() {
		return this.witnessaddress;
	}

	public void setWitnessdate(String witnessdate) {
		this.witnessdate = witnessdate;
	}

	public String getWitnessdate() {
		return this.witnessdate;
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

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerName() {
		return this.triggerName;
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


}
