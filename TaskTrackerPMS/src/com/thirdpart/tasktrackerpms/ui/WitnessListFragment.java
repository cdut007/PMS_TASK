package com.thirdpart.tasktrackerpms.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jameschen.framework.base.BasePageListFragment;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.EventCallbackListener;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.entity.DepartmentInfo;
import com.thirdpart.model.entity.IssueList;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.WitnessDistributed;
import com.thirdpart.model.entity.WitnessDistributedList;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.model.entity.WitnesserList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.WitnesserAdapter;


public class WitnessListFragment extends BasePageListFragment<WitnessDistributed, WitnessDistributedList>  {

	
	
	private long menuid;
	boolean isMyevent = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    registerCallBack( new EventCallbackListener()  {
			
			@Override
			public void commitSucc() {
				// TODO Auto-generated method stub
				Log.i(TAG, "call back commit succ");
				
				if (mListView!=null) {
					mListView.setRefreshing(true);
				}
				callNextPage(pageSize,getCurrentPage());
	
			}

			@Override
			public String getTag() {
				// TODO Auto-generated method stub
				return callsucc;
			}
		});
	}
	public static String callsucc="witness";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.witness_list_ui, container, false);
		menuid = getArguments().getLong(ConstValues.ID);
		isMyevent = getLogInController().matchUrls("/witness/myevent");
		Log.i(TAG, "witness menu id = "+menuid);
		bindListView(view,new WitnesserAdapter(getBaseActivity(),scanMode(),isMyevent&&menuid==0));
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	private boolean scanMode() {
		// TODO Auto-generated method stub
		return menuid!=0;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private  void executeNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
		BasePageListFragment<WitnessDistributed, WitnessDistributedList>.PageUINetworkHandler<WitnessDistributedList> networkhanler = new PageUINetworkHandler<WitnessDistributedList>(getBaseActivity()){

    		@Override
    		public void startPage() {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void finishPage() {
    			// TODO Auto-generated method stub
    			//test data.
    		}

    		@Override
    		public void callbackPageFailure(int statusCode,
    				Header[] headers, String response) {
    			// TODO Auto-generated method stub
    		}

    		@Override
    		public void callbackPageSuccess(int statusCode,
    				Header[] headers, WitnessDistributedList response) {
    			// TODO Auto-generated method stub
    			
    		}
    	};
		if (menuid==0) {//my revice witness
			if (isMyevent) {
				 getPMSManager().receiveMyWitnessList(pagesize+"", pagenum+"",networkhanler);
						
			}else {
				 getPMSManager().receiveWitnessList(pagesize+"", pagenum+"","equal",networkhanler);
						
			}
					
		}else if(menuid==1){//my 
			 getPMSManager().myTaskWitnessList(pagesize+"", pagenum+"","equal",networkhanler);
				
		}else if(menuid==2){//my 
			 getPMSManager().myTaskWitnessList(pagesize+"", pagenum+"","assigned",networkhanler);
				
		}
	       
	}
	
	@Override
	protected void callNextPage(int pagesize, int pageNum) {
	executeNetWorkRequest( pagesize, pageNum);
	}




	
}
