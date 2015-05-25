//package com.thirdpart.model;
//
//import java.util.List;
//
//import org.apache.http.Header;
//
//import com.jameschen.framework.base.UINetworkHandler;
//import com.thirdpart.model.entity.Team;
//
//import android.content.Context;
//
//public class TeamManager extends ManagerService {
//
//	private TeamManager(OnReqHttpCallbackListener reqHttpCallbackListener) {
//		super(reqHttpCallbackListener);
//		// TODO Auto-generated constructor stub
//	}
//
//	public static String ACTION_TEAM_INFOS = "com.jameschen.issue.teamInfos";
//	public static String ACTION_TEAM_LIST = "com.jameschen.issue.teamList";
//	public static String ACTION_TEAM_DELIVERY_PLAN = "com.jameschen.issue.team.delivery.TASK";
//	public static String ACTION_TEAM_MODIFY_PLAN = "com.jameschen.issue.team.modify.TASK";
//	public static String ACTION_TEAM_REMOVE_PLAN = "com.jameschen.issue.team.remove.TASK";
//	
//	
//	
//
//	public void teamInfos() {
//
//		PMSManagerAPI.getInstance(context).teamInfos(
//				new UINetworkHandler<String>(context) {
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
//						notifyFailedResult(ACTION_TEAM_INFOS, statusCode,
//								response);
//					}
//
//					@Override
//					public void callbackSuccess(int statusCode,
//							Header[] headers, String response) {
//
//						notifySuccResult(ACTION_TEAM_INFOS, statusCode);
//					}
//				});
//
//	}
//
//	
//	
//	/**
//	 * @param pagesize
//	 * @param pagenum
//	 * @param condition
//	 * @param consteam
//	 */
//	public void teamList(String pagesize, String pagenum, String condition,
//			String consteam) {
//
//		PMSManagerAPI.getInstance(context).teamList(pagesize, pagenum,
//				condition, consteam, new UINetworkHandler<List<Team>>(context) {
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
//						notifyFailedResult(ACTION_TEAM_LIST, statusCode,
//								response);
//					}
//
//					@Override
//					public void callbackSuccess(int statusCode,
//							Header[] headers, List<Team> response) {
//
//						notifySuccResult(ACTION_TEAM_LIST, statusCode);
//					}
//				});
//
//	}
//	
//	
//	/**
//	 * @param teamId
//	 * @param rollingPlanIds
//	 */
//	public void deliveryPlanToTeam(String teamId, List<String> rollingPlanIds) {
//
//		PMSManagerAPI.getInstance(context).deliveryPlanToTeam(teamId, rollingPlanIds, new UINetworkHandler<String>(context) {
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
//						notifyFailedResult(ACTION_TEAM_DELIVERY_PLAN, statusCode,
//								response);
//					}
//
//					@Override
//					public void callbackSuccess(int statusCode,
//							Header[] headers, String response) {
//
//						notifySuccResult(ACTION_TEAM_DELIVERY_PLAN, statusCode);
//					}
//				});
//
//	}
//	
//	/**
//	 * @param teamId
//	 * @param rollingPlanIds
//	 */
//	public void modifyPlanToTeam(String teamId, List<String> rollingPlanIds) {
//
//		PMSManagerAPI.getInstance(context).modifyPlanToTeam(teamId, rollingPlanIds, new UINetworkHandler<String>(context) {
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
//						notifyFailedResult(ACTION_TEAM_MODIFY_PLAN, statusCode,
//								response);
//					}
//
//					@Override
//					public void callbackSuccess(int statusCode,
//							Header[] headers, String response) {
//
//						notifySuccResult(ACTION_TEAM_MODIFY_PLAN, statusCode);
//					}
//				});
//
//	}
//	
//	/**
//	 * @param teamId
//	 * @param rollingPlanIds
//	 */
//	public void removePlanToTeam(String teamId, List<String> rollingPlanIds) {
//
//		PMSManagerAPI.getInstance(context).removeTaskToTeam(teamId, rollingPlanIds, new UINetworkHandler<String>(context) {
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
//						notifyFailedResult(ACTION_TEAM_REMOVE_PLAN, statusCode,
//								response);
//					}
//
//					@Override
//					public void callbackSuccess(int statusCode,
//							Header[] headers, String response) {
//
//						notifySuccResult(ACTION_TEAM_REMOVE_PLAN, statusCode);
//					}
//				});
//
//	}
//	
//}
