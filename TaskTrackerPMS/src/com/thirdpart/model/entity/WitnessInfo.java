package com.thirdpart.model.entity;

import java.io.Serializable;
import java.util.Date;

public class WitnessInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8524055906787183733L;
	public Integer id;
	public String witness;
	public String witnessName;
	public String witnesser;
	public String witnesserName;
	public String witnessdes;
	public String witnessaddress;
	public long witnessdate;
	public Integer status;
	public Integer isok;
	public String remark;
	public String triggerName;
	public String noticeType;
	public String noticePoint;
	public long createdOn;
	public String createdBy;
	public long updatedOn;
	public String updatedBy;
}
