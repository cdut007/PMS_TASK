package com.jameschen.framework.base;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler{

	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
		// TODO Auto-generated method stub
		
	}
    @Override
   public void onFinish() {
	// TODO Auto-generated method stub
	super.onFinish();
   }
    
	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		//1000
	}

}
