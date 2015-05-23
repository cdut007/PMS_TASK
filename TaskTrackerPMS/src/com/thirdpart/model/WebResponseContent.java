package com.thirdpart.model;

public class WebResponseContent {
	
	private boolean success;
	private String responseResult;
	private String message;
	private   String  code;
	
	private  String url;//for test
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
