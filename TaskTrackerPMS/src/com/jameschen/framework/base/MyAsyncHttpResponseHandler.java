package com.jameschen.framework.base;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

public abstract class MyAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler implements OnResponseListener<T>{
	  Gson gson =new Gson();
	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
		// TODO Auto-generated method stub
		onFail(statusCode, headers, new String(responseBody));
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		// TODO Auto-generated method stub
		String response = new String(responseBody);
		
		onSucc(statusCode, headers, );
	}
	
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	
	public  abstract void onFail(int statusCode, Header[] headers, String response);

	
	public abstract  void onSucc(int statusCode, Header[] headers,T response);
	


}
