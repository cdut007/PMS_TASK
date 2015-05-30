package com.thirdpart.model;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ManagerService.ManagerNetworkHandler;
import com.thirdpart.model.entity.DepartmentInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.WitnesserList;

import android.content.Context;

public class WitnessManager extends ManagerService{

	public WitnessManager(){
		super();
	}
	

	public static String ACTION_WITNESS_CHOOSE_WITNESSER = "com.jameschen.witnesser";
	public static String ACTION_WITNESS_CHOOSE_COMMIT = "com.jameschen.plan.witness.commit";
	
	private WitnessManager(OnReqHttpCallbackListener reqHttpCallbackListener) {
		super(reqHttpCallbackListener);
		// TODO Auto-generated constructor stub
	}


	 protected  ManagerNetworkHandler getManagerNetWorkHandler(String action){
		 
		if (action.equals(ACTION_WITNESS_CHOOSE_WITNESSER)) {
			ManagerNetworkHandler<List<WitnesserList>> hander = new ManagerNetworkHandler<List<WitnesserList>>(context,action){};
			Type sToken = new TypeToken<List<WitnesserList>>() {
			}.getType();
			hander.setType(sToken);
			return hander;
		} else {

			return new ManagerNetworkHandler<JsonObject>(context,action){};
		}
		
	 }

	
	public void commit(String witnessid, String witnesseraqa, String witnesseraqc2, String witnesseraqc1, String witnesserb, String witnesserc, String witnesserd) {
		PMSManagerAPI.getInstance(context).modifyWitness(witnessid, witnesseraqa, witnesseraqc2, witnesseraqc1, witnesserb, witnesserc, witnesserd,getManagerNetWorkHandler(ACTION_WITNESS_CHOOSE_COMMIT) );

	}
	public void chooseWitnessList(String witnessId) {
		PMSManagerAPI.getInstance(context).witnesserList(witnessId, getManagerNetWorkHandler(ACTION_WITNESS_CHOOSE_WITNESSER) );

	}
}
