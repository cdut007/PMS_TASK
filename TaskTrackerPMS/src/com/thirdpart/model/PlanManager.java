package com.thirdpart.model;
import org.apache.http.Header;

import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlanDetail;

public class PlanManager  extends ManagerService{
	
	
	public PlanManager(){
		super();
	}
	
	private PlanManager(OnReqHttpCallbackListener reqHttpCallbackListener) {
		super(reqHttpCallbackListener);
		// TODO Auto-generated constructor stub
	}


	
	public static String ACTION_PLAN_DETAIL = "com.jameschen.plan.detail";
	


	
	
	
	public void planDetail(String planId) {
		
		PMSManagerAPI.getInstance(context).planDetail(planId, new UINetworkHandler<RollingPlanDetail>(context) {

			@Override
			public void start() {
				// TODO Auto-generated method stub
				notifyStart(ACTION_PLAN_DETAIL);
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				notifyFinish(ACTION_PLAN_DETAIL);
			}

			@Override
			public void callbackFailure(int statusCode,
					Header[] headers, String response) {
				// TODO Auto-generated method stub
				notifyFailedResult(ACTION_PLAN_DETAIL, statusCode,headers,
						response);
			}

			@Override
			public void callbackSuccess(int statusCode,
					Header[] headers, RollingPlanDetail response) {
				
				notifySuccResult(ACTION_PLAN_DETAIL, statusCode,headers,response);
			}
		});

	}
	
	
}
