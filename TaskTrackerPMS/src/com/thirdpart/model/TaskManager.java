package com.thirdpart.model;

import java.util.List;

import org.apache.http.Header;

import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.TaskCategoryInfo;
import com.thirdpart.model.entity.TaskCategoryItem;

import android.R.integer;
import android.content.Context;

public class TaskManager  extends ManagerService{

	
	public TaskManager(){
		super();
	}
	public static String ACTION_TASK_COMMIT = "com.jameschen.plan.task.commit";

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

	public void commit(String witnessid, String witnesseraqa, String witnesseraqc2, String witnesseraqc1, String witnesserb, String witnesserc, String witnesserd) {
		PMSManagerAPI.getInstance(context).modifyWitness(witnessid, witnesseraqa, witnesseraqc2, witnesseraqc1, witnesserb, witnesserc, witnesserd,getManagerNetWorkHandler(ACTION_TASK_COMMIT) );

	}
	
	
}
