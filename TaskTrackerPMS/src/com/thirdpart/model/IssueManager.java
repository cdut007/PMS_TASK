package com.thirdpart.model;


import java.io.File;
import java.util.List;

import android.graphics.Color;

import com.thirdpart.model.entity.IssueResult;

public class IssueManager extends ManagerService {
	
	public IssueManager(){
		super();
	}
	private IssueManager(OnUploadReqHttpCallbackListener reqHttpCallbackListener) {
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
	public void confirmIssue(String qustionId, String iswork) {
		PMSManagerAPI.getInstance(context).confirmIssue(qustionId,
				iswork, new ManagerNetworkHandler<IssueResult>(context,ACTION_ISSUE_CONFIRM){});

	}

	/**
	 * @param issueResult
	 * @param mFiles 
	 * @param iswork
	 */
	public void createIssue(IssueResult issueResult, List<File> mFiles ) {
		PMSManagerAPI.getInstance(context).createIssue(issueResult, mFiles,new ManagerNetworkHandler<IssueResult>(context,ACTION_ISSUE_CREATE){});

	}
	
	

	
	/**
	 * @param userId
	 */
	public void IssueDetail(String issueId) {
		PMSManagerAPI.getInstance(context).IssueDetail(issueId,  new ManagerNetworkHandler<IssueResult>(context,ACTION_ISSUE_DETAIL){});
	}
	

	
	/**
	 * @param userid
	 * @param problemid
	 * @param solvemethod 
	 * @param autoid
	 * @param solvedman
	 * @param isSolve
	 * @param solverid
	 */
	public void handleIssue(String problemid, String solvemethod, String autoid, String solvedman, String isSolve, String solverid) {
		PMSManagerAPI.getInstance(context).handleIssue(problemid,solvemethod, autoid, solvedman, isSolve, solverid,new ManagerNetworkHandler<IssueResult>(context,ACTION_ISSUE_HANDLE){});
	}
	
	public static int getIssueStatusColor(String isOk) {
		return "0".equals(isOk)?Color.RED:0xFF00a529;
	}
	public static String getIssueStatus(String isOk) {
		return "0".equals(isOk)?"未解决":"解决";
	}
}
