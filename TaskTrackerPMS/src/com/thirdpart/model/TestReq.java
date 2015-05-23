package com.thirdpart.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.StringUtil;
import com.jameschen.framework.base.UINetworkHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;

public class TestReq {

	public static boolean debug = true;

	
	public static void init(final Context context) {
		// TODO Auto-generated method stub
		if (debug) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub

					InputStream inputStram = null;
					try {
						inputStram = context.getAssets().open("content.json");
						String  content = StringUtil.inputStream2String(inputStram); 
						results = content.ma;
						Log.i("test", "results=="+results.length);
						
					for (int i = 0; i < results.length; i++) {
						results[i] = StringUtil.replaceBlank(results[i]);
					}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if (inputStram!=null) {
							try {
								inputStram.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					
					
				}
			}).start();
		}
	}
	
	
	
	public static <T> void simultor(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler, int type) {
		// TODO Auto-generated method stub
		UINetworkHandler<T> mNetworkHandler = (UINetworkHandler<T>) responseHandler;
		
		query(url, params, mNetworkHandler,type);
		
	}
	
	
	//test data.
//			RollingPlanList mPageList = new RollingPlanList();
//			
//			 List<RollingPlan> data = new ArrayList<RollingPlan>();
//			 
//			for (int i = 0; i < pageNum; i++) {
//				data.add(new RollingPlan());
//			}
//			mPageList.setDatas(data);
//			addDataToListAndRefresh(mPageList);	
	
static Handler handler = new Handler();
	private static void query(final String url, RequestParams params,
			final UINetworkHandler mNetworkHandler,final int type)

	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			final byte[] result = queryData(url,type);
			if (result == null) {
				Log.i("test", "parse url="+url);
				return;
			}
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub

					mNetworkHandler.onSuccess(200, null, result);			
				}
			});
			}
		}).start();
		// test data.
	}
	
	
	
	static String[] results = null;
	
	protected static byte[] queryData(String url,int type) {
		// TODO Auto-generated method stub
		String matherUrl = url;
		if (url.endsWith("/")) {
			matherUrl = url+type;
		}else {
			matherUrl = url+"/"+type;
		}
		
		Gson  gson = new Gson();
		for (int i = 0; i < results.length; i++) {
			if (results[i].isEmpty()) {
				continue;
			}

			Log.i("urlJson", "content = "+ results[i]);
			
			WebResponseContent mResponseContent = gson.fromJson(results[i],
					new TypeToken<WebResponseContent>() {
					}.getType());	
			if (mResponseContent.getUrl()!=null ) {
				if ((matherUrl).contains(mResponseContent.getUrl())) {
					return mResponseContent.getResponseResult().getBytes();
				}
			}
		}
		Log.i("urlJson", "not find");
		return null;
	}

	String login = "";



	
	
}
