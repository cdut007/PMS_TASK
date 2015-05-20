package com.jameschen.framework.base;

import org.apache.http.Header;

import com.jameschen.comm.utils.NetworkUtil;
import com.thirdpart.tasktrackerpms.R;

public abstract class UINetworkHandler<T> extends MyAsyncHttpResponseHandler<T> {

	private BaseActivity activity;

	public UINetworkHandler(BaseActivity activity) {
		this.activity = activity;
		executeNetWorkRequest();
	}

	public abstract void start();

	
	public abstract void finish();
	
	public abstract void callbackFailure(int statusCode, Header[] headers,
			String response);

	public abstract void callbackSuccess(int statusCode, Header[] headers, T response);
	


	@Override
	protected void onFail(int statusCode, Header[] headers, String response) {
		if (activity.isFinishing()) {
			return;
		}
		// TODO Auto-generated method stub
		if (statusCode == 0) {
			if (NetworkUtil.isInternetAvailable(activity)) {
				response = activity.getString(R.string.server_no_respnonse);
			} else {
				response = activity.getString(R.string.warning_no_internet);
			}
			activity.showToast(response);
		} else {
			callbackFailure(statusCode, headers, response);
		}

	}

	@Override
	protected void onSucc(int statusCode, Header[] headers, T response) {
		if (activity.isFinishing()) {
			return;
		}
		// do something
		callbackSuccess(statusCode, headers, response);
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
		if (activity.isFinishing()) {
			return;
		}
		finish();
	}



	private void executeNetWorkRequest() {
		// TODO Auto-generated method stub
		if (!NetworkUtil.isInternetAvailable(activity)) {
			activity.showToast(activity.getString(R.string.warning_no_internet));
			finish();
			return;

		}

		start();

	}

}
