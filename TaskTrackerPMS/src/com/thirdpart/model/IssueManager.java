package com.thirdpart.model;


import java.io.File;
import java.util.List;

import org.apache.http.Header;

import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.entity.IssueResult;

import android.content.Context;

public class IssueManager extends ManagerService {
	
	
	public static String ACTION_ISSUE_CONFIRM = "com.jameschen.issue.confirm";
	public static String ACTION_ISSUE_CREATE = "com.jameschen.issue.create";
	public static String ACTION_ISSUE_STATUS = "com.jameschen.issue.status";
	public static String ACTION_ISSUE_DETAIL = "com.jameschen.issue.detail";
	public static String ACTION_ISSUE_HANDLE = "com.jameschen.issue.handle";
	
 private IssueManager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * @param userId
	 * @param qustionId
	 * @param iswork
	 */
	public void confirmIssue(String userId, String qustionId, boolean iswork) {
		PMSManagerAPI.getInstance(context).confirmIssue(userId, qustionId,
				iswork, new UINetworkHandler<IssueResult>(context) {

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
						notifyFailedResult(ACTION_ISSUE_CONFIRM, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, IssueResult response) {
						
						notifySuccResult(ACTION_ISSUE_CONFIRM, statusCode);
					}
				});

	}

	/**
	 * @param issueResult
	 * @param iswork
	 */
	public void createIssue(IssueResult issueResult, boolean iswork) {
		PMSManagerAPI.getInstance(context).createIssue(issueResult, new UINetworkHandler<IssueResult>(context) {

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
						notifyFailedResult(ACTION_ISSUE_CREATE, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, IssueResult response) {
						// TODO Auto-generated method stub
						notifySuccResult(ACTION_ISSUE_CREATE, statusCode);
					}
				});

	}
	
	/**
	 * @param userId
	 * @param status
	 */
	public void IssueStatus(String userId, String status) {
		PMSManagerAPI.getInstance(context).IssueStatus(userId, status, new UINetworkHandler<IssueResult>(context) {

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
						notifyFailedResult(ACTION_ISSUE_STATUS, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, IssueResult response) {
						// TODO Auto-generated method stub
						notifySuccResult(ACTION_ISSUE_STATUS, statusCode);
					}
				});

	}

	
	/**
	 * @param userId
	 */
	public void IssueDetail(String userId) {
		PMSManagerAPI.getInstance(context).IssueDetail(userId, new UINetworkHandler<IssueResult>(context) {

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
						notifyFailedResult(ACTION_ISSUE_DETAIL, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, IssueResult response) {
						// TODO Auto-generated method stub
						notifySuccResult(ACTION_ISSUE_DETAIL, statusCode);
					}
				});
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
		PMSManagerAPI.getInstance(context).handleIssue(userid, problemid, autoid, solvedman, isSolve, solverid, new UINetworkHandler<IssueResult>(context) {

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
						notifyFailedResult(ACTION_ISSUE_DETAIL, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, IssueResult response) {
						// TODO Auto-generated method stub
						notifySuccResult(ACTION_ISSUE_DETAIL, statusCode);
					}
				});
	}
	
	
	
	/**
	 * @param problemId
	 * @param files
	 */
	public void uploadIssueFiles(String problemId, List<File> files) {
		PMSManagerAPI.getInstance(context).uploadIssueFiles(problemId, files,new UINetworkHandler<IssueResult>(context) {

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
						notifyFailedResult(ACTION_ISSUE_DETAIL, statusCode,
								response);
					}

					@Override
					public void callbackSuccess(int statusCode,
							Header[] headers, IssueResult response) {
						// TODO Auto-generated method stub
						notifySuccResult(ACTION_ISSUE_DETAIL, statusCode);
					}
				});
	}
}
