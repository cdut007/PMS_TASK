package com.jameschen.framework.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.http.Header;

import android.R.integer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.NetworkUtil;
import com.jameschen.comm.utils.Util;
import com.jameschen.framework.base.ConvertResponseResultAdapter.ReqType;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thirdpart.model.WebResponseContent;
import com.thirdpart.model.entity.UserInfo;
import com.thirdpart.tasktrackerpms.R;

abstract class MyAsyncHttpResponseHandler<T> extends
		AsyncHttpResponseHandler  {
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
	
	
	protected final void debugHeaders(String TAG, Header[] headers) {
        if (headers != null) {
            Log.d(TAG, "Return Headers:");
            StringBuilder builder = new StringBuilder();
            for (Header h : headers) {
                String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
                Log.d(TAG, _h);
                builder.append(_h);
                builder.append("\n");
            }
           Log.i(TAG, "http=="+builder.toString());
        }
    }

	
	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, Throwable error) {
		// TODO Auto-generated method stub
		String response = "";
		if (responseBody != null) {
			response = new String(responseBody);
		}
		debugHeaders(TAG, headers);
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
		}else {
			debugHeaders(TAG, headers);
		}

		try {
			WebResponseContent mResponseContent = WebResponseContent.parseJson(response);

			if ("1000".equals(mResponseContent.getCode())) {
			
				T responseJsonClass = gson.fromJson(
						mResponseContent.getResponseResult(),
						Util.whatsMyGenericType(this));
				onSucc(statusCode, headers, responseJsonClass);

			} else {

				Log.i(TAG, "statusCode from server=" + mResponseContent.getCode() + ";Message response=" + mResponseContent.getMessage());
				onFail(Integer.parseInt(mResponseContent.getCode()), headers,
						"" + mResponseContent.getMessage());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			debugHeaders(TAG, headers);
			Log.i(TAG,
					"response=" + response + ";JsonException="
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
