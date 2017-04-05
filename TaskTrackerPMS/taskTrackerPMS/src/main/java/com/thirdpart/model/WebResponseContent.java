package com.thirdpart.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.jameschen.comm.utils.Log;
import com.thirdpart.tasktrackerpms.BuildConfig;


public class WebResponseContent {
	
	private boolean success;
	private String responseResult;
	private String message;
	private String  code;
	
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
		if (BuildConfig.DEBUG) {
			
			Log.i("getResponseResult","responseResult="+ responseResult);
		}
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
	
	
	public static WebResponseContent parseJson(String content) throws JSONException{
		JSONObject jsonObject = new JSONObject(content);
		WebResponseContent webResponseContent = new WebResponseContent();
		webResponseContent.responseResult = jsonObject.optString("responseResult", null);
		webResponseContent.code = jsonObject.optString("code", "0");
		webResponseContent.message = jsonObject.optString("message",null);
		webResponseContent.url = jsonObject.optString("url", null);
		return webResponseContent;
	}
	
}
