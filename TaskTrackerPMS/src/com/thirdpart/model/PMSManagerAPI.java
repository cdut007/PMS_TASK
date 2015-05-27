package com.thirdpart.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jameschen.comm.utils.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thirdpart.model.Config.ReqHttpMethodPath;
import com.thirdpart.model.entity.IssueResult;

import android.content.Context;

public class PMSManagerAPI {

	private static PMSManagerAPI managerAPI;

	private Context context;

	public Map<String, String> getPublicParamRequstMap() {
		Map<String, String> headers = new HashMap<String, String>();

		return headers;
	}

	private RequestParams getCommonPageParams(String pageSize, String pageNum) {
		// TODO Auto-generated method stub
		RequestParams requestParams = getPublicParams();
		requestParams.put("pagesize", pageSize);
		requestParams.put("pagenum", pageNum);
		return requestParams;
	}

	private RequestParams getPublicParams() {
		// TODO Auto-generated method stub

		return new RequestParams(getPublicParamRequstMap());
	}

	private PMSManagerAPI(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public static PMSManagerAPI getInstance(Context context) {
		// TODO Auto-generated method stub
		synchronized (PMSManagerAPI.class) {
			if (managerAPI == null) {
				managerAPI = new PMSManagerAPI(context);
			}
		}
		return managerAPI;
	}

	/**
	 * @param loginId
	 * @param password
	 * @param responseHandler
	 *            userInfo
	 */
	public void login(String loginId, String password,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("LoginId", loginId);
		params.put("password", password);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_LOGIN_URL, params,
				responseHandler);

	}

	/**
	 * @param loginId
	 * @param password
	 * @param responseHandler
	 *            ConstructionTeamList
	 */
	public void teamList(String pagesize, String pagenum, String condition,
			String consteam, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pagesize, pagenum);
		params.put("condition", condition);
		params.put("consteam", consteam);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_CONSTRUCTION_TEAM_LIST_URL,
				params, responseHandler);

	}

	/**
	 * @param pagesize
	 * @param pagenum
	 * @param responseHandler
	 */
	public void planList(String pagesize, String pagenum,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pagesize, pagenum);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_ROLLINGPLAN_LIST_URL, params,
				responseHandler);

	}

	/**
	 * @param witnessId
	 * @param responseHandler
	 *            WitnesserList
	 */
	public void witnesserList(String witnessId,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("witness", witnessId);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESSER_LIST_URL, params,
				responseHandler);
	}

	/**
	 * @param loginid
	 * @param qustionId
	 * @param iswork
	 * @param responseHandler
	 */
	public void confirmIssue(String loginid, String qustionId, boolean iswork,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginid", loginid);
		params.put("id", qustionId);
		params.put("iswork", iswork);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_CONFIRM_ISSUE_URL, params,
				responseHandler);
	}

	/**
	 * @param responseHandler
	 *            List<Team>[team(id,name)]
	 */
	public void teamInfos(AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		MyHttpClient.get(ReqHttpMethodPath.REQUST_CONSTRUCTION_TEAMS_INFOS_URL,
				params, responseHandler);
	}

	/**
	 * @param issue
	 * @param responseHandler
	 */
	public void createIssue(IssueResult issue,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();

		params.put("loginid", issue.getUserId());
		params.put("workstepid", issue.getWorstepid());
		params.put("workstepno", issue.getStepno());
		params.put("stepname", issue.getStepname());
		params.put("questionname", issue.getQuestionname());
		params.put("describe", issue.getDescribe());
		params.put("solverid", issue.getSolverid());
		params.put("concernman", issue.getConcerman());

		MyHttpClient.post(ReqHttpMethodPath.REQUST_CREATE_ISSUE_URL, params,
				responseHandler);
	}

	/**
	 * @param loginUserId
	 * @param rollingPlanIds
	 * @param endManId
	 * @param startTime
	 * @param endTime
	 *            (format:2015-05-26)
	 * @param responseHandler
	 */
	public void deliveryPlanToHeadMan(String loginUserId,
			List<String> rollingPlanIds, String endManId, String startTime,
			String endTime, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("ids", rollingPlanIds);
		params.put("endManId", endManId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);

		MyHttpClient.post(
				ReqHttpMethodPath.REQUST_DISTRIBUTE_ROLLINGPLAN_TO_HEADMAN_URL,
				params, responseHandler);
	}
	
	public void modifyPlanToHeadMan(String loginUserId,
			List<String> rollingPlanIds, String endManId, String startTime,
			String endTime, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("ids", rollingPlanIds);
		params.put("endManId", endManId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);

		MyHttpClient.put(
				ReqHttpMethodPath.REQUST_MODIFY_ROLLINGPLAN_TO_HEADMAN_URL,
				params, responseHandler);
	}

	/**
	 * @param teamId
	 * @param rollingPlanIds
	 * @param responseHandler
	 */
	public void deliveryPlanToTeam(String teamId, List<String> rollingPlanIds,
			AsyncHttpResponseHandler responseHandler) {

		RequestParams params = getPublicParams();
		params.put("teamId", teamId);
		params.put("ids", rollingPlanIds);

		MyHttpClient.post(ReqHttpMethodPath.REQUST_DISTRIBUTE_TASK_TO_TEAM_URL,
				params, responseHandler);
	}
	
	public void modifyPlanToTeam(String teamId, List<String> rollingPlanIds,
			AsyncHttpResponseHandler responseHandler) {

		RequestParams params = getPublicParams();
		params.put("teamId", teamId);
		params.put("ids", rollingPlanIds);

		MyHttpClient.put(ReqHttpMethodPath.REQUST_MODIFY_TASK_TO_TEAM_URL,
				params, responseHandler);
	}

	/**
	 * Method : POST param:loginUserId,witnessid OPTION
	 * param:witnesseraqa,witnesseraqc1,
	 * witnesseraqc2,witnesserb,witnesserc,witnesserd
	 * 
	 * @param loginUserId
	 * @param witnessid
	 * @param witnesseraqa
	 * @param witnesseraqc2
	 * @param witnesseraqc1
	 * @param witnesserb
	 * @param witnesserc
	 * @param witnesserd
	 * 
	 * @param responseHandler
	 */
	public void deliveryWitness(String loginUserId, String witnessid,
			String witnesseraqa, String witnesseraqc2, String witnesseraqc1,
			String witnesserb, String witnesserc, String witnesserd,
			AsyncHttpResponseHandler responseHandler) {

		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("witnessid", witnessid);

		if (witnesseraqa != null) {
			params.put("witnesseraqa", witnesseraqa);
		}
		if (witnesseraqc1 != null) {
			params.put("witnesseraqc1", witnesseraqc1);
		}
		if (witnesseraqc2 != null) {
			params.put("witnesseraqc2", witnesseraqc2);
		}
		if (witnesserb != null) {
			params.put("witnesserb", witnesserb);
		}
		if (witnesserc != null) {
			params.put("witnesserc", witnesserc);
		}
		if (witnesserd != null) {
			params.put("witnesserd", witnesserd);
		}
		MyHttpClient.post(ReqHttpMethodPath.REQUST_DISTRIBUTED_WITNESSER_URL,
				params, responseHandler);
	}

	/**
	 * @param loginUserId
	 * @param witnessid
	 * @param witnesseraqa
	 * @param witnesseraqc2
	 * @param witnesseraqc1
	 * @param witnesserb
	 * @param witnesserc
	 * @param witnesserd
	 * @param responseHandler
	 */
	public void modifyWitness(String loginUserId, String witnessid,
			String witnesseraqa, String witnesseraqc2, String witnesseraqc1,
			String witnesserb, String witnesserc, String witnesserd,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("witnessid", witnessid);

		if (witnesseraqa != null) {
			params.put("witnesseraqa", witnesseraqa);
		}
		if (witnesseraqc1 != null) {
			params.put("witnesseraqc1", witnesseraqc1);
		}
		if (witnesseraqc2 != null) {
			params.put("witnesseraqc2", witnesseraqc2);
		}
		if (witnesserb != null) {
			params.put("witnesserb", witnesserb);
		}
		if (witnesserc != null) {
			params.put("witnesserc", witnesserc);
		}
		if (witnesserd != null) {
			params.put("witnesserd", witnesserd);
		}
		MyHttpClient.put(
				ReqHttpMethodPath.REQUST_MODIFY_DISTRIBUTED_WITNESSER_URL,
				params, responseHandler);
	}

	/**
	 * @param loginid
	 * @param problemid
	 * @param autoid
	 * @param solvedman
	 * @param isSolve
	 * @param solverid
	 * @param responseHandler
	 */
	public void handleIssue(String loginid, String problemid, String autoid,
			String solvedman, String isSolve, String solverid,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginid", loginid);
		params.put("problemid", problemid);
		params.put("autoid", autoid);
		params.put("solvedman", solvedman);
		params.put("isSolve", isSolve);
		params.put("solverid", solverid);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_HANDLE_ISSUE_URL, params,
				responseHandler);
	}

	/**
	 * @param loginUserId
	 * @param responseHandler
	 */
	public void headmens(String loginUserId,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_HEADMEN_URL, params,
				responseHandler);
	}

	/**
	 * @param issueId
	 * @param responseHandler
	 */
	public void IssueDetail(String issueId,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("id", issueId);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_ISSUE_DETAIL_URL, params,
				responseHandler);
	}

	/**PUT param:noticeresult(1 ok ,3 bad),loginUserId,id 
	 * @param responseHandler
	 * @param noticeresult 
	 * @param loginUserId 
	 * @param id 
	 */
	public void modifyWitnesserWriteResult(String noticeresult, String loginUserId, String id,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("noticeresult", noticeresult);
		params.put("loginUserId", loginUserId);
		params.put("id", id);
		MyHttpClient.put(ReqHttpMethodPath.REQUST_MODIFY_WITNESSER_WIRTE_RESULT_URL,
				params, responseHandler);
	}
	
	
	/**
	 * POST param:loginUserId,id(rollingPlan id),welder(name),enddate(format:2015-05-26) 
	 * @param loginUserId
	 * @param rollingPlanid
	 * @param welder
	 * @param enddate
	 * @param responseHandler
	 */
	public void confirmMyPlanFinish(String loginUserId, String rollingPlanid, String welder,String enddate,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("id", rollingPlanid);
		params.put("welder", welder);
		params.put("enddate", enddate);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_MY_ROLLINGPLAN_FINISH_TO_CONFIRM_URL,
				params, responseHandler);
	}
	
	public void modifyMyPlanFinishResult(String loginUserId, String rollingPlanid, String welder,String enddate,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("id", rollingPlanid);
		params.put("welder", welder);
		params.put("enddate", enddate);
		MyHttpClient.put(ReqHttpMethodPath.REQUST_MY_ROLLINGPLAN_MODIFY_FINISH_TO_CONFIRM_URL,
				params, responseHandler);
	}

	/**PUT param:loginUserId,id(rollingPlan id),operater(name),operatedate(format:2015-05-26 22:22:22) 
	 * @param loginUserId
	 * @param rollingPlanid
	 * @param operater
	 * @param operatedate
	 * @param responseHandler
	 */
	public void modifyMyTaskByWorkStep(String loginUserId, String rollingPlanid, String operater,String operatedate,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("id", rollingPlanid);
		params.put("operater", operater);
		params.put("operatedate", operatedate);
		MyHttpClient.put(ReqHttpMethodPath.REQUST_MY_TASK_MODIFY_BY_WORKSTEP_URL,
				params, responseHandler);
	}
	
	public void finishMyTaskByWorkStep(String loginUserId, String rollingPlanid, String operater,String operatedate,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("loginUserId", loginUserId);
		params.put("id", rollingPlanid);
		params.put("operater", operater);
		params.put("operatedate", operatedate);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_MY_TASK_TO_FINISH_BY_WORKSTEP_URL,
				params, responseHandler);
	}
	
	public void myWitnessList(String loginUserId, String pagesize, String pagenum,AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pagesize, pagenum);
		params.put("loginUserId", loginUserId);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_MY_WITNESS_LIST_URL,
				params, responseHandler);
	}
	
	public void removePlansToHeadman(List<String> rollingPlanIds, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("ids", rollingPlanIds);
		MyHttpClient.delete(ReqHttpMethodPath.REQUST_REMOVE_ROLLINGPLAN_TO_HEADMAN_URL,
				params, responseHandler);
	}
	
	public void removeTaskToTeam(String teamId,List<String> taskIds, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("teamId", teamId);
		params.put("ids", taskIds);
		MyHttpClient.delete(ReqHttpMethodPath.REQUST_REMOVE_TASK_TO_TEAM_URL,
				params, responseHandler);
	}
	
	public void planDetail(String planid, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		MyHttpClient.get(ReqHttpMethodPath.REQUST_ROLLINGPLAN_DETAIL_URL+planid,
				params, responseHandler);
	}
	
	public void IssueStatus(String loginid,String status,String pagesize,String pagenum, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pagesize, pagenum);
		params.put("loginid", loginid);
		params.put("status", status);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_STATUS_ISSUE_URL,
				params, responseHandler);
	}
	
	/**
	 * @param problemId
	 * @param files
	 * @param responseHandler
	 */
	public void uploadIssueFiles(String problemId,List<File> files, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("problemId", problemId);
		params.put("files", files);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_UPLOAD_ISSUE_FILES_URL,
				params, responseHandler);
	}
	//param:loginUserId,pagesize,pagenum,condition (equal) 
	/**
	 * @param loginUserId
	 * @param pageSize
	 * @param pageNum
	 * @param condition
	 * @param responseHandler
	 */
	public void deliveryWitnessList(String loginUserId, 
			String pageSize,String pageNum,String condition,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pageSize, pageNum);
		params.put("loginUserId", loginUserId);
		params.put("condition", condition);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESS_LIST_OF_DISTRIBUTE_URL,
				params, responseHandler);
	}
	
	public void workStepWitnessList(String id, 
			String pageSize,String pageNum, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pageSize, pageNum);
		params.put("Id", id);
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESS_LIST_OF_WORKSTEP_URL,
				params, responseHandler);
	}
	
	public void getWitnessResultType( AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESS_RESULT_TYPE_URL,
				params, responseHandler);
	}
	
	public void witnessTeamList( AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESS_TEAM_LIST_URL,
				params, responseHandler);
	}
	
	public void getWitnessType( AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WITNESS_TYPE_LIST_URL,
				params, responseHandler);
	}
	
	public void getWorkStepList(String id,String pagesize,String pagenum, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getCommonPageParams(pagesize, pagenum);
		
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WORK_STEP_LIST_URL+id,
				params, responseHandler);
	}
	
	//noticeresult(1 ok ,3 bad),loginUserId,id 
	public void wirteWitnessResult(String noticeresult,String loginUserId,String id, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		params.put("noticeresult", noticeresult);
		params.put("loginUserId", loginUserId);
		params.put("id", id);
		MyHttpClient.post(ReqHttpMethodPath.REQUST_WITNESSER_WIRTE_RESULT_URL,
				params, responseHandler);
	}
	
	public void getWorkStepDetail(String id, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = getPublicParams();
		MyHttpClient.get(ReqHttpMethodPath.REQUST_WROKSTEP_DETAIL_URL+id,
				params, responseHandler);
	}
	
	
	
	
}
