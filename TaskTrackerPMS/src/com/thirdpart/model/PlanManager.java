package com.thirdpart.model;
import org.apache.http.Header;

import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ManagerService.ManagerNetworkHandler;
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
	


	
	 protected  ManagerNetworkHandler getManagerNetWorkHandler(String action){
		 
		 return new ManagerNetworkHandler<RollingPlanDetail>(context,action){};
	 }

	
	public void planDetail(String planId) {
		PMSManagerAPI.getInstance(context).planDetail(planId,getManagerNetWorkHandler(ACTION_PLAN_DETAIL) );

	}
	
	
}
