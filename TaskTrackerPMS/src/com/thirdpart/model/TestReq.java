package com.thirdpart.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.integer;
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

	public static boolean debug = false;


	static List<String> orList = new ArrayList<String>();
	static {
		
		orList.add("hdxt/api/core/authenticate/{0}");
		orList.add("hdxt/api/baseservice/rollingplan/{0}");
		orList.add("hdxt/api/baseservice/rollingplan/{p}");
		orList.add("hdxt/api/baseservice/workstep/rollingplan/{0}");
		orList.add("hdxt/api/baseservice/construction/team/{0}");
		orList.add("hdxt/api/baseservice/workstep/{0}");
		orList.add("hdxt/api/baseservice/construction/team/teams/{0}");
		orList.add("hdxt/api/baseservice/construction/team/{1}");
		orList.add("hdxt/api/baseservice/construction/team/{2}");
		orList.add("hdxt/api/baseservice/construction/team/{3}");
		
		orList.add("hdxt/api/baseservice/construction/endman/endmen/{0}");		
		orList.add("hdxt/api/baseservice/construction/endman/{1}");
		orList.add("hdxt/api/baseservice/construction/endman/{2}");
		orList.add("hdxt/api/baseservice/construction/endman/{3}");
		orList.add("hdxt/api/baseservice/construction/mytask/workstep/{1}");
		orList.add("hdxt/api/baseservice/construction/mytask/workstep/{2}");
		orList.add("hdxt/api/baseservice/construction/mytask/rollingplan/{1}");
		orList.add("hdxt/api/baseservice/construction/mytask/rollingplan/{2}");
		orList.add("hdxt/api/baseservice/witness/workstep/{0}");
		orList.add("hdxt/api/baseservice/witness/witnessresulttype/{0}");
		
		orList.add("hdxt/api/baseservice/witness/{0}");		
		orList.add("hdxt/api/baseservice/witness/witnessertype/{0}");
		orList.add("hdxt/api/baseservice/witness/team/{0}");
		orList.add("hdxt/api/baseservice/witness/witnesser/{0}");
		orList.add("hdxt/api/baseservice/witness/witnesser/{1}");
		orList.add("hdxt/api/baseservice/witness/witnesser/{2}");
		orList.add("hdxt/api/baseservice/witness/witnesser/result/{1}");
		orList.add("hdxt/api/baseservice/witness/witnesser/result/{2}");
		orList.add("hdxt/api/baseservice/witness/myevent/{0}");
		orList.add("workflow/api/hdxt/problem/add/{1}");
		
		orList.add("workflow/api/hdxt/problem/upload/{1}");		
		orList.add("workflow/api/hdxt/problem/handle/{1}");
		orList.add("workflow/api/hdxt/confrim/{1}");
		orList.add("workflow/hdxt/api/problem/{0}");
		orList.add("workflow/hdxt/api/problem/detail/{0}");
		
		
	}
	
	 private static int judge(String s) { 
		 
	        Stack<Character> stk = new Stack<Character>(); 
	        results.clear();
	        int start=0 ,end =0;
	        int interfaceCount = 0;
	        for(int i = 0;i < s.length();++ i){ 
	            char c = s.charAt(i); 
	            switch(c){ 
	                case '{': 
	                	if (stk.isEmpty()) {
	                		

	                		start = i;		
						}
	                    stk.push(c); 
	                    break; 
	                case '}': 
	                {
	                    
	                    stk.pop(); 
	                    if(stk.isEmpty()){ 
	                    	end = i;
	                    	if (start < end) {
	                    		String content = StringUtil.replaceBlank(s.substring(start, end+1));

	                    		if (!content.equals("{id}")) {
	                				String addString = "{\"url\":\""+(orList.get(interfaceCount))+"\","+StringUtil.replaceBlank(content).substring(1);
//	                				addString = addString.replaceAll("，", ",")
//		    	                			.replaceAll("：", ":").replaceAll("“", "\"")
//		    	                			.replaceAll("”", "\"").replaceAll("［", "[")
//		    	                			.replaceAll("］", "]");
	                				results.add(addString);
		                			interfaceCount++;
								}else {
									
								}
	                			
	                		
							}
	                        break; 
	                    } 
	                    if('{' != stk.lastElement()){ 
	                    	end = i;
	                        break; 
	                    }
	                }
	                    break; 
	                
	            } 
	        } 
	        
	        
	        return 0; 
	    
	    } 
	
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
						judge(content);
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
					mNetworkHandler.onFinish();
				}
			});
			}
		}).start();
		// test data.
	}
	
	
	
	private static List<String> results = new ArrayList<String>();
	
	
	static String matchString (String url,String type){
		String matherUrl = url;
		if (url.endsWith("/")) {
			matherUrl = url+"{"+type+"}";
		}else {
			matherUrl = url+"/"+"{"+type+"}";
		}
		return matherUrl;
	}
	
	
	
	
	protected static byte[] queryData(String url,int type) {
		// TODO Auto-generated method stub
		String matherUrl = matchString(url, type+"");
		
		for (int i = 0; i < results.size(); i++) {
			

			WebResponseContent mResponseContent ;
			try {
				 mResponseContent = WebResponseContent.parseJson(results.get(i));	
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("out", "content="+results.get(i));
				e.printStackTrace();
				return null;
			}
			
			if (mResponseContent.getUrl()!=null ) {
				if ((matherUrl).contains(mResponseContent.getUrl())
						&&
						(!(matherUrl).contains("hdxt/api/baseservice/rollingplan"))
						) {

					return results.get(i).getBytes();
				}else {
					if (url.contains("hdxt/api/baseservice/rollingplan")){
						if (url.endsWith("hdxt/api/baseservice/rollingplan/")) {//for page
							if (matchString(url, "p").contains(mResponseContent.getUrl())) {

								Log.i("urlJson", "content p= "+ results.get(i));
								return  results.get(i).getBytes();
							}
						}else {// for plan id

							Log.i("urlJson", "plan content = "+ results.get(i));
							return  results.get(i).getBytes();
						}
					}
					
				}
			}
		}
		Log.i("urlJson", "not find");
		return null;
	}

	String login = "";



	
	
}
