package com.thirdpart.model;

import com.google.gson.Gson;

public class WebLocationResponseContent {
	
	private boolean success;
	private String responseResult;
	private String message;
	private   String  code;
	
	
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
	
	public static String ParseLocationJsonResult(String response) {
//		// TODO Auto-generated method stub
		WebResponseContent webResponseContent = new WebResponseContent();//createWebResponseContent();
//		
//		//webResponseContent.setCode(code);
		Gson gson = new Gson();
		return gson.toJson(webResponseContent);
		
	}
	
}
