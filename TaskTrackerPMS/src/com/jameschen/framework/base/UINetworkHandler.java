package com.jameschen.framework.base;

import java.lang.reflect.Type;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.jameschen.comm.utils.NetworkUtil;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.R.bool;

public abstract class UINetworkHandler<T> extends MyAsyncHttpResponseHandler<T> {

	private Context context;

    		
	public UINetworkHandler(Context context) {
		this(context, null);
	}

	public UINetworkHandler(Context context, Type sToken) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.type = sToken;
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
		if (isFinishing()) {
			return;
		}
		callbackFailure(statusCode, headers, response);
		

	}

	private boolean isFinishing() {
		// TODO Auto-generated method stub
		if ( context instanceof Activity) {
			if (((Activity)context).isFinishing()) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onSucc(int statusCode, Header[] headers, T response) {
		if (isFinishing()) {
			return;
		}
		// do something
		callbackSuccess(statusCode, headers, response);
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
		if (isFinishing()) {
			return;
		}
		finish();
	}



	private void executeNetWorkRequest() {

		start();

	}

}
