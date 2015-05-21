package com.jameschen.framework.base;

import org.apache.http.Header;

import android.content.Context;

import com.jameschen.comm.utils.NetworkUtil;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.R.bool;

public abstract class UINetworkHandler<T> extends MyAsyncHttpResponseHandler<T> {

	private Context context;

    		
	public UINetworkHandler(Context context) {
		super();
		this.context = context;
		
		executeNetWorkRequest();
	}

	public abstract void start();

	
	public abstract void finish();
	
	public abstract void callbackFailure(int statusCode, Header[] headers,
			String response);

	public abstract void callbackSuccess(int statusCode, Header[] headers, T response);
	


	@Override
	protected void onFail(int statusCode, Header[] headers, String response) {
		
		// TODO Auto-generated method stub
		if (statusCode == 0) {
			if (NetworkUtil.isInternetAvailable(context)) {
				response = context.getString(R.string.server_no_respnonse);
			} else {
				response = context.getString(R.string.warning_no_internet);
			}
		} 
		
		callbackFailure(statusCode, headers, response);
		

	}

	@Override
	protected void onSucc(int statusCode, Header[] headers, T response) {
		
		// do something
		callbackSuccess(statusCode, headers, response);
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
		
		finish();
	}



	private void executeNetWorkRequest() {

		start();

	}

}
