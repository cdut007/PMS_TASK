package com.thirdpart.model;

import org.apache.http.Header;

import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlanList;

import android.content.Context;

public class PlanManager  extends ManagerService{
	
	public static String ACTION_PLAN_LIST = "com.jameschen.plan.list";
	
	public static String ACTION_PLAN_DETAIL = "com.jameschen.plan.detail";
	
	private PlanManager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @param pagesize
	 * @param pagenum
	 */
	public void planList(String pagesize, String pagenum) {
		PMSManagerAPI.getInstance(context).planList(pagesize, pagenum, new UINetworkHandler<RollingPlanList>(context) {

					@Override
					public void start() {
						// TODO Auto-generated method stub
					}

					@Override
					public void finish() {
						// TODO Auto-generated method stub

					}

					@Override
					public void callbackFailure(int statusCode,
							Header[] headers, String response) {
						// TODO Auto-generated method stub
						notifyFailedResult(ACTION_PLAN_LIST, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, RollingPlanList response) {
						
						notifySuccResult(ACTION_PLAN_LIST, statusCode);
					}
				});

	}
	
	
	public void planDetail(String planId) {
		
		PMSManagerAPI.getInstance(context).planDetail(planId, new UINetworkHandler<RollingPlanList>(context) {

					@Override
					public void start() {
						// TODO Auto-generated method stub
					}

					@Override
					public void finish() {
						// TODO Auto-generated method stub

					}

					@Override
					public void callbackFailure(int statusCode,
							Header[] headers, String response) {
						// TODO Auto-generated method stub
						notifyFailedResult(ACTION_PLAN_DETAIL, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, RollingPlanList response) {
						
						notifySuccResult(ACTION_PLAN_DETAIL, statusCode);
					}
				});

	}
	
	
}
