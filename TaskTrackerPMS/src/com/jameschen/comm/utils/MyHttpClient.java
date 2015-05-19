package com.jameschen.comm.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thirdpart.model.Config;

public class MyHttpClient  {
	
	  private static final String BASE_URL = Config.ReqHttpMethodPath.HTTP_BASE_URL;

	  private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.put(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.delete(getAbsoluteUrl(url), responseHandler);
	  }
	  
	  public static void upload(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      post(url, params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	     // return BASE_URL + relativeUrl;
		  return relativeUrl;
	  }
	  
	 
	  
	}
