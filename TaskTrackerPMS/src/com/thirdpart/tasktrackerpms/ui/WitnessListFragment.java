package com.thirdpart.tasktrackerpms.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.JsonObject;
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
import com.thirdpart.tasktrackerpms.adapter.DeliveryPlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.WitnesserAdapter;
import com.thirdpart.tasktrackerpms.ui.DeliveryPlanFragment.DeliveryStatus;


public class WitnessListFragment extends BasePageListFragment<WitnessDistributed, WitnessDistributedList>  {

	
	public String tag;
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
		tag = getArguments().getString(ConstValues.Tag);
		isMyevent = getLogInController().matchUrls("/witness/myevent");
		Log.i(TAG, "witness menu id = "+menuid);
		WitnesserAdapter adapter = new WitnesserAdapter(getBaseActivity(),scanMode(),isMyevent&&menuid==0);
		adapter.setEditMode(menuid==0);
		bindListView(view,adapter);
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
    	
    	if (mAdapter.isSearchMode()) {
			
		}
    	
    	
		if (menuid==0) {//my revice witness
			if (isMyevent) {
				 getPMSManager().receiveMyWitnessList(pagesize+"", pagenum+"",networkhanler,null,tag);
						
			}else {
				 getPMSManager().receiveWitnessList(pagesize+"", pagenum+"","equal",tag,networkhanler);
						
			}
					
		}else if(menuid==1){//my 
			 getPMSManager().myTaskWitnessList(pagesize+"", pagenum+"","equal",networkhanler);
				
		}else if(menuid==2){//my 
			 getPMSManager().receiveWitnessList(pagesize+"", pagenum+"","assigned",null,networkhanler);
				
		}else if(menuid==3){//finished ..
			 getPMSManager().receiveMyWitnessList(pagesize+"", pagenum+"",networkhanler,"complete",tag);
				
		}
	       
	       
	}
	
	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		super.callNextPage(pagesize, pageNum);
	executeNetWorkRequest( pagesize, pageNum);
	}


	public void commit() {

		// TODO Auto-generated method stub
		WitnesserAdapter witnesserAdapter = (WitnesserAdapter) mAdapter;
		int selectCount = witnesserAdapter.getAllCheckOptionsCount();
		if (selectCount<=0) {
			showToast("请先选择见证");
			getBaseActivity().cancelProgressDialog();
			return;
		}
		
		
		List<WitnessDistributed> mSeletedItems = witnesserAdapter.getAllCheckOptions();
		executeCommitWitnessResultNetWorkRequest(mSeletedItems);
	
		
	}
	 int count = 0;
	 
	private void executeCommitWitnessResultNetWorkRequest(
			List<WitnessDistributed> mSeletedItems) {
		// TODO Auto-generated method stub
		List<String> ids = new ArrayList<String>();
		 String content="合格";
		 String okType ="3";
		 count = 0;
		 WitnesserAdapter witnesserAdapter = (WitnesserAdapter) mAdapter;
		final int size =  witnesserAdapter.getObjectInfos().size();
		 
		for (WitnessDistributed item : mSeletedItems) {
			ids.add(item.getId());

			  UINetworkHandler<JsonObject> handler=new UINetworkHandler<JsonObject>(getBaseActivity()) {

					@Override
					public void start() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void finish() {
						// TODO Auto-generated method stub
						getBaseActivity().cancelProgressDialog();
					}

					@Override
					public void callbackFailure(int statusCode, Header[] headers,
							String response) {
						// TODO Auto-generated method stub
						showToast(response);
					}

					@Override
					public void callbackSuccess(int statusCode, Header[] headers,
							JsonObject response) {
						// TODO Auto-generated method stub
						count++;
						if (count == size) {
							showToast("提交成功");
						}
						WitnessListFragment.CallSucc(WitnessListFragment.callsucc);
						
					}
				};
			if (isMyevent) {
				getPMSManager().wirteMyeventWitnessResult(content,okType, item.getId(), handler);
				
			}else {
				getPMSManager().wirteWitnessResult(content,okType, item.getWorkStep().getId(), handler);
					
			}
		}
		
		
		 String failedContent="不合格";
		 String failedType ="1";
		 witnesserAdapter = (WitnesserAdapter) mAdapter;
		List<WitnessDistributed> mUnSeletedItems = witnesserAdapter.getAllUnCheckOptions();
		List<String> unSelectids = new ArrayList<String>();
		for (WitnessDistributed item : mUnSeletedItems) {
			unSelectids.add(item.getId());
			  UINetworkHandler<JsonObject> handler=new UINetworkHandler<JsonObject>(getBaseActivity()) {

					@Override
					public void start() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void finish() {
						// TODO Auto-generated method stub
						getBaseActivity().cancelProgressDialog();
					}

					@Override
					public void callbackFailure(int statusCode, Header[] headers,
							String response) {
						// TODO Auto-generated method stub
						showToast(response);
					}

					@Override
					public void callbackSuccess(int statusCode, Header[] headers,
							JsonObject response) {
						// TODO Auto-generated method stub
						count++;
						if (count == size) {
							showToast("提交成功");
						}
						WitnessListFragment.CallSucc(WitnessListFragment.callsucc);
						
					}
				};
			if (isMyevent) {
				getPMSManager().wirteMyeventWitnessResult(failedContent,failedType, item.getId(), handler);
				
			}else {
				getPMSManager().wirteWitnessResult(failedContent,failedType, item.getWorkStep().getId(), handler);
					
			}
		}
		
		
	   
		
		
		
	}
	
}
