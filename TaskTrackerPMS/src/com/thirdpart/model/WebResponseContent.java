package com.thirdpart.model;

public class WebResponseContent {
	
	private boolean success;
	private String responseResult;
	private String message;
	private   int  code;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getResponseResult() {
		return responseResult;
	}
	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setSuccess(boolean sucess) {
		this.success = sucess;
	}
	
}
