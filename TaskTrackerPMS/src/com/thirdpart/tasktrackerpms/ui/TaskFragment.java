package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.JsonObject;
import com.jameschen.comm.utils.Log;
import com.jameschen.framework.base.BasePageListFragment;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.Department;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.DeliveryPlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;
import com.thirdpart.widget.IndicatorView;


public class TaskFragment extends BasePageListFragment<RollingPlan, RollingPlanList> implements OnItemClickListener{

	
	boolean  scanMode =true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.delivery_plan_ui, container, false);
		bindListView(view,new DeliveryPlanAdapter(getBaseActivity()));
		mListView.setOnItemClickListener(this);
	
		DeliveryPlanAdapter deliveryPlanAdapter = (DeliveryPlanAdapter) mAdapter;
		deliveryPlanAdapter.setScanMode(scanMode);
		IndicatorView indicatorView = (IndicatorView) view.findViewById(R.id.plan_delivery_indicator);
		indicatorView.setScanMode(scanMode);
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private  void executeNextPageNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
		BasePageListFragment<RollingPlan, RollingPlanList>.PageUINetworkHandler<RollingPlanList> pageUINetworkHandler = new PageUINetworkHandler<RollingPlanList>(getBaseActivity()){

	    		@Override
	    		public void startPage() {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void finishPage() {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void callbackPageFailure(int statusCode,
	    				Header[] headers, String response) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void callbackPageSuccess(int statusCode,
	    				Header[] headers, RollingPlanList response) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	    	};
	    	
			getPMSManager().myTaskList(pagesize+"", pagenum+"","notequal",pageUINetworkHandler);
		   
	     
	}

	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		// TODO Auto-generated method stub
		super.callNextPage(pagesize, pageNum);
		executeNextPageNetWorkRequest(pagesize, pageNum);
		
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), PlanDetailActivity.class);
		Object object = parent.getAdapter().getItem(position);
		if (object == null) {
			return;
		}
		RollingPlan p = (RollingPlan) (object);
		//p.setClassName(title);
		intent.putExtra("scan", false);
		intent.putExtra(Item.TASK, p);
		startActivity(intent);
	}

	private void executeCommitPlanNetWorkRequest(
			List<RollingPlan> mSeletedItems) {}
	
	
}
