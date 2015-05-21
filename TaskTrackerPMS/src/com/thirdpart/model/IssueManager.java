package com.thirdpart.model;

import java.io.Serializable;

import org.apache.http.Header;

import com.jameschen.framework.base.OnResponseListener;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.entity.IssueResult;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class IssueManager extends ManagerService{
public static String ACTION_ISSUE_CONFIRM="com.jameschen.issue.confirm";

 IssueManager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

public void confirmIssue(String userId, String qustionId, boolean iswork ,final OnCallbackResponseListener<IssueResult> onResponseListener){
	PMSManagerAPI.getInstance(context).confirmIssue(userId, qustionId, iswork, new UINetworkHandler<IssueResult>(context) {

		@Override
		public void start() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void finish() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void callbackFailure(int statusCode, Header[] headers,
				String response) {
			// TODO Auto-generated method stub
			notifyFailedResult(ACTION_ISSUE_CONFIRM, statusCode, response);
		}

		@Override
		public void callbackSuccess(int statusCode, Header[] headers, IssueResult response) {
			// TODO Auto-generated method stub
			onResponseListener.onSucc(response);
		}
	});
	
}


	
	
}
