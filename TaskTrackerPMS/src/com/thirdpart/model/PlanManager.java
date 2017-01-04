package com.thirdpart.model;

import java.util.List;

import com.thirdpart.model.entity.RollingPlan;

import android.R.integer;

public class PlanManager  extends ManagerService{
	
	
	public PlanManager(){
		super();
	}
	
	private PlanManager(OnReqHttpCallbackListener reqHttpCallbackListener) {
		super(reqHttpCallbackListener);
		// TODO Auto-generated constructor stub
	}


	
	public static String ACTION_PLAN_DETAIL = "com.jameschen.plan.detail";
	
	public static String ACTION_PLAN_WORKSTEP_BATCH_COMMIT = "com.jameschen.plan.workstep.batch.commit";
	


	
	 protected  ManagerNetworkHandler getManagerNetWorkHandler(String action){
		 
		 return new ManagerNetworkHandler<RollingPlan>(context,action){};
	 }

	
	 
	public void commitBatch(String oprater,String operatedesc,
				String opraterDate,int qcasign,String qcman,List<String> planIds) {
			
		PMSManagerAPI.getInstance(context).createPlanWorkStepBatchCommit( oprater,operatedesc, opraterDate,qcasign,qcman, planIds, getManagerNetWorkHandler(ACTION_PLAN_WORKSTEP_BATCH_COMMIT) );


		}
	 
	public void planDetail(String planId) {
		PMSManagerAPI.getInstance(context).planDetail(planId,getManagerNetWorkHandler(ACTION_PLAN_DETAIL) );

	}

	public static boolean isHankou(String speciality) {
		// TODO Auto-generated method stub
		return "GDHK".equals(speciality);
	}
	
	
}
