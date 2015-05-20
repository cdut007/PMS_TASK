package com.jameschen.framework.base;

import android.location.Location;

import com.google.gson.Gson;
import com.thirdpart.model.WebLocationResponseContent;
import com.thirdpart.model.WebResponseContent;

public class ConvertResponseResultAdapter {

	public enum ReqType{
		Location ,NULL
	}
	
	
	
	public static String ReqType(String response, ReqType reqType) {
		// TODO Auto-generated method stub
		switch (reqType) {
		case Location:
			
			return  WebLocationResponseContent.ParseLocationJsonResult(response);

		default:
			break;
		}
		return response;
	}

	

}
