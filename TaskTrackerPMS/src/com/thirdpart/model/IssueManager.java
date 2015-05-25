package com.thirdpart.model;


import java.io.File;
import java.util.List;

import org.apache.http.Header;

import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.entity.IssueResult;

import android.content.Context;

public class IssueManager extends ManagerService {
	
	
	private IssueManager(OnReqHttpCallbackListener reqHttpCallbackListener) {
		super(reqHttpCallbackListener);
		// TODO Auto-generated constructor stub
	}



	public static String ACTION_ISSUE_CONFIRM = "com.jameschen.issue.confirm";
	public static String ACTION_ISSUE_CREATE = "com.jameschen.issue.create";
	public static String ACTION_ISSUE_STATUS = "com.jameschen.issue.status";
	public static String ACTION_ISSUE_DETAIL = "com.jameschen.issue.detail";
	public static String ACTION_ISSUE_HANDLE = "com.jameschen.issue.handle";
	
 

	
	
	/**
	 * @param userId
	 * @param qustionId
	 * @param iswork
	 */
	public void confirmIssue(String userId, String qustionId, boolean iswork) {
		PMSManagerAPI.getInstance(context).confirmIssue(userId, qustionId,
				iswork, getUiNetworkHandler(ACTION_ISSUE_CONFIRM,new IssueResult()));

	}

	/**
	 * @param issueResult
	 * @param iswork
	 */
	public void createIssue(IssueResult issueResult, boolean iswork) {
		PMSManagerAPI.getInstance(context).createIssue(issueResult, getUiNetworkHandler(ACTION_ISSUE_CREATE,new IssueResult()));

	}
	
	/**
	 * @param userId
	 * @param status
	 */
	public void IssueStatus(String userId, String status) {
		PMSManagerAPI.getInstance(context).IssueStatus(userId, status, getUiNetworkHandler(ACTION_ISSUE_STATUS,new IssueResult()));

	}

	
	/**
	 * @param userId
	 */
	public void IssueDetail(String userId) {
		PMSManagerAPI.getInstance(context).IssueDetail(userId,  getUiNetworkHandler(ACTION_ISSUE_DETAIL,new IssueResult()));
	}
	

	
	/**
	 * @param userid
	 * @param problemid
	 * @param autoid
	 * @param solvedman
	 * @param isSolve
	 * @param solverid
	 */
	public void handleIssue(String userid, String problemid, String autoid, String solvedman, String isSolve, String solverid) {
		PMSManagerAPI.getInstance(context).handleIssue(userid, problemid, autoid, solvedman, isSolve, solverid,  getUiNetworkHandler(ACTION_ISSUE_HANDLE,new IssueResult()));
	}
	
	
	
	/**
	 * @param problemId
	 * @param files
	 */
	public void uploadIssueFiles(String problemId, List<File> files) {
		PMSManagerAPI.getInstance(context).uploadIssueFiles(problemId, files, getUiNetworkHandler(ACTION_ISSUE_DETAIL,new IssueResult()));
	}
}
