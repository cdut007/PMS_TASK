package com.jameschen.framework.base;

import org.apache.http.Header;

import android.R.integer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.NetworkUtil;
import com.jameschen.framework.base.ConvertResponseResultAdapter.ReqType;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thirdpart.model.WebResponseContent;
import com.thirdpart.tasktrackerpms.R;

public abstract class MyAsyncHttpResponseHandler<T> extends
		AsyncHttpResponseHandler implements OnResponseListener<T> {
	private static final String TAG = "AsyncHttpResponseHandler";
	Gson gson = new Gson();

	private  ReqType  ThirdPartReqType = ReqType.NULL;
	
	public MyAsyncHttpResponseHandler() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public MyAsyncHttpResponseHandler(ReqType reqType) {
		// TODO Auto-generated constructor stub
		this();
		this.ThirdPartReqType = reqType;
	}
	
	
	
	private  ReqType getCurrentReqType(){
		return ThirdPartReqType;
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, Throwable error) {
		// TODO Auto-generated method stub
		String response = "";
		if (responseBody != null) {
			response = new String(responseBody);
		}

		Log.i(TAG, "statusCode=" + statusCode + ";response=" + response);
		onFail(statusCode, headers, response);
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		// TODO Auto-generated method stub
		String response = "";
		if (responseBody != null) {
			response = new String(responseBody);
			//Covert response to custom
			response = ConvertResponseResultAdapter.ReqType(response,getCurrentReqType());
		}

		try {
			WebResponseContent mResponseContent = gson.fromJson(response,
					new TypeToken<WebResponseContent>() {
					}.getType());

			if ("1000".equals(mResponseContent.getCode())) {

				T responseJsonClass = gson.fromJson(
						mResponseContent.getResponseResult(),
						new TypeToken<T>() {
						}.getType());
				onSucc(statusCode, headers, responseJsonClass);

			} else {

				onFail(Integer.parseInt(mResponseContent.getCode()), headers,
						"" + mResponseContent.getMessage());
			}

		} catch (JsonSyntaxException e) {
			// TODO: handle exception
			Log.i(TAG,
					"response=" + response + ";JsonSyntaxException="
							+ e.getLocalizedMessage());
			onFail(statusCode, headers, "" + e.getLocalizedMessage());
		}

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

	protected void onFail(int statusCode, Header[] headers, String response) {
	};

	protected void onSucc(int statusCode, Header[] headers, T response) {
	};

}
