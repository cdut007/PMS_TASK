package com.thirdpart.model.entity;

import java.io.Serializable;

public class Task implements Serializable{

	private String type;
	private String complate;
	private String surplus;
	private String total;
	private String percent;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComplate() {
		return complate;
	}
	public void setComplate(String complate) {
		this.complate = complate;
	}
	public String getSurplus() {
		return surplus;
	}
	public void setSurplus(String surplus) {
		this.surplus = surplus;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}

}
