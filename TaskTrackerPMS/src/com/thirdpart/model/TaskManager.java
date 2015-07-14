package com.thirdpart.model;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ManagerService.ManagerNetworkHandler;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.TaskCategoryInfo;
import com.thirdpart.model.entity.TaskCategoryItem;
import com.thirdpart.model.entity.Team;
import com.thirdpart.model.entity.WitnesserList;

import android.R.integer;
import android.content.Context;

public class TaskManager  extends ManagerService{

	
	public TaskManager(){
		super();
	}
	public static String ACTION_TASK_COMMIT = "com.jameschen.plan.task.commit";
	public static String ACTION_ZHIJIA_COMMIT = "com.jameschen.plan.zhijia.commit";

	private TaskManager(OnReqHttpCallbackListener reqHttpCallbackListener) {
		super(reqHttpCallbackListener);
		// TODO Auto-generated constructor stub
	}

	
//	
//	public static String ACTION_ISSUE_HANDLE = "com.jameschen.issue.handle";
//	
//	/**
//	 * @param pagesize
//	 * @param pagenum
//	 */
//	public void planList(String pagesize, String pagenum) {
//		PMSManagerAPI.getInstance(context).planList(pagesize, pagenum, new UINetworkHandler<RollingPlanList>(context) {
//
//					@Override
//					public void start() {
//						// TODO Auto-generated method stub
//					}
//
//					@Override
//					public void finish() {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void callbackFailure(int statusCode,
//							Header[] headers, String response) {
//						// TODO Auto-generated method stub
//						notifyFailedResult(ACTION_PLAN_LIST, statusCode,
//								response);
//					}
//
//					@Override
//					public void callbackSuccess(int statusCode,
//							Header[] headers, RollingPlanList response) {
//						
//						notifySuccResult(ACTION_PLAN_LIST, statusCode);
//					}
//				});
//
//	}
	
	public static final int TYPE_ZHIJIA=0,TYPE_HANKOU=1;
	public static final String ACTION_WITNESS_CHOOSE_TEAM = "com.jameschen.plan.witness.chooseTeam";
	public int getCount(List<TaskCategoryItem> mCategoryItems,String type,String name){
		    TaskCategoryItem mTaskCategoryItem=null;
		    for (TaskCategoryItem findTaskCategoryItem : mCategoryItems) {
				if (type.equals(findTaskCategoryItem.type)) {
					mTaskCategoryItem = findTaskCategoryItem;
					break;
				}
			}
		    
		    if (mTaskCategoryItem == null) {
				return 0;
			}
		  List<TaskCategoryInfo> result = mTaskCategoryItem.result;
	
		  if (result == null || result.size() == 0) {
			return 0;
		}
		  
		  
		  for (TaskCategoryInfo taskCategoryInfo : result) {
			if (name.equals( taskCategoryInfo.getStatus())) {
				return Integer.parseInt(taskCategoryInfo.getResult());
			}
		}
		  return 0;
	}

	public static String getTaskType(String type) {
		// TODO Auto-generated method stub
		return type.equals("hk")?"焊口":"支架";
	}
	

	 protected  ManagerNetworkHandler getManagerNetWorkHandler(String action){
		 
		if (action.equals(ACTION_WITNESS_CHOOSE_TEAM)) {
			ManagerNetworkHandler<List<Team>> hander = new ManagerNetworkHandler<List<Team>>(context,action){};
			Type sToken = new TypeToken<List<Team>>() {
			}.getType();
			hander.setType(sToken);
			return hander;
		} else {

			return new ManagerNetworkHandler<JsonObject>(context,action){};
		}
		
	 }

	 
	 public void confirmMyPlanFinish(String planid, String welder, String enddate, 
				String qcSign,String qcman) {
			PMSManagerAPI.getInstance(context).confirmMyPlanFinish(planid, welder,  enddate , 
					qcSign,qcman,getManagerNetWorkHandler(ACTION_ZHIJIA_COMMIT) );

		}
	public void commit(String workStepId, String witness, String witnessdes, String witnesseaddress, String witnessdate, String operater, String operatedate, String operatedesc,
			int qcSign,String qcman) {
		PMSManagerAPI.getInstance(context).createWitness(workStepId, witness, witnessdes, witnesseaddress, witnessdate, operater, operatedate, operatedesc, 
				qcSign,qcman,getManagerNetWorkHandler(ACTION_TASK_COMMIT) );

	}
	public void chooseWitnessHeadList() {

		PMSManagerAPI.getInstance(context).witnessTeamList(getManagerNetWorkHandler(ACTION_WITNESS_CHOOSE_TEAM) );

	}
	
}
