package com.jameschen.comm.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thirdpart.model.Config;

public class MyHttpClient  {
	
	  private static final String BASE_URL = Config.ReqHttpMethodPath.HTTP_BASE_URL;

	  private static AsyncHttpClient client = new AsyncHttpClient();

	  static void printReqUrl(String url,RequestParams requestParams){
		  Log.i("reqUrl", "url="+url+"; requestParam="+requestParams.toString());
	  }
	  
	  
	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  printReqUrl(url, params);
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  printReqUrl(url, params);
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  printReqUrl(url, params);
	      client.put(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  printReqUrl(url, params);
	      client.delete(getAbsoluteUrl(url), responseHandler);
	  }
	  

	  private static String getAbsoluteUrl(String relativeUrl) {
	     // return BASE_URL + relativeUrl;
		  return relativeUrl;
	  }
	  
	 
	  
	}
