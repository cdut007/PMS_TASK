package com.thirdpart.model;

import java.util.HashMap;
import java.util.Map;

import com.jameschen.comm.utils.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thirdpart.model.Config.ReqHttpMethodPath;

import android.content.Context;

public class PMSManagerAPI {
	
	private static PMSManagerAPI managerAPI;
	
	private Context context;
	
	
	public Map<String, String> getPublicParamRequstMap() {
		Map<String, String> headers = new HashMap<String, String>();
		
		return headers;
	}
	
	private RequestParams getCommonPageParams(String pageSize,String pageNum) {
		// TODO Auto-generated method stub
		RequestParams requestParams = getPublicParams();
		requestParams.put("pagesize", pageSize);
		requestParams.put("pagenum", pageNum);
     return  requestParams;
	}
	
	private RequestParams getPublicParams() {
		// TODO Auto-generated method stub
		
     return  new RequestParams(getPublicParamRequstMap());
	}
	
	private PMSManagerAPI(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public static PMSManagerAPI getInstance(Context context) {
		// TODO Auto-generated method stub
		synchronized (PMSManagerAPI.class) {
		if (managerAPI == null) {
			managerAPI = new PMSManagerAPI(context);
		}	
		}
		return managerAPI;
	}

	/**
	 * @param loginId 
	 * @param password
	 * @param responseHandler  userInfo
	 */
	public void login(String loginId,String password, AsyncHttpResponseHandler responseHandler){
		RequestParams params = getPublicParams();
		params.put("Login_ID", loginId);
		params.put("Password", password);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_LOGIN_URL, params, responseHandler);
	
	}
	
	/**
	 * @param loginId
	 * @param password
	 * @param responseHandler ConstructionTeamList
	 */
	public void teamList(String pagesize,String pagenum, 
			String condition,String consteam, 
			AsyncHttpResponseHandler responseHandler){
		RequestParams params = getCommonPageParams(pagesize, pagenum);
		params.put("condition", condition);
		params.put("consteam", consteam);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_CONSTRUCTION_TEAM_LIST_URL, params, responseHandler);
		
	}
	
	/**
	 * @param pagesize
	 * @param pagenum
	 * @param responseHandler
	 */
	public void planList(String pagesize,String pagenum, 
			AsyncHttpResponseHandler responseHandler){
		RequestParams params = getCommonPageParams(pagesize, pagenum);
	
		MyHttpClient.get(ReqHttpMethodPath.REQUST_ROLLINGPLAN_LIST_URL, params, responseHandler);
		
	}
	
	
	/**
	 * @param witnessId
	 * @param responseHandler WitnesserList
	 */
	public void witnesserList(String witnessId,  
			AsyncHttpResponseHandler responseHandler){
		RequestParams params = getPublicParams();
		params.put("witness", witnessId);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESSER_LIST_URL, params, responseHandler);
		
	}
	
	/**
	 * @param userId
	 * @param qustionId
	 * @param iswork
	 * @param responseHandler
	 */
	public void confirmIssue(String userId,String qustionId,boolean iswork, AsyncHttpResponseHandler responseHandler){
		RequestParams params = getPublicParams();
		params.put("userid", userId);
		params.put("id", qustionId); 
		params.put("iswork", iswork);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_CONFIRM_ISSUE_URL, params, responseHandler);
	
	}
	
}
