package com.thirdpart.tasktrackerpms.ui;

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

import com.jameschen.framework.base.BasePageListFragment;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.DeliveryPlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;


public class DeliveryPlanFragment extends BasePageListFragment<RollingPlan, RollingPlanList> implements OnItemClickListener{

	
	String title;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.delivery_plan_ui, container, false);
		bindListView(view,new DeliveryPlanAdapter(getBaseActivity()));
		mListView.setOnItemClickListener(this);
		title = getArguments().getString(Item.PLAN);
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
			
	        getPMSManager().planList(pagesize+"", pagenum+"",new PageUINetworkHandler<RollingPlanList>(getBaseActivity()){

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
	    	});
		
	}

	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		// TODO Auto-generated method stub
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
		p.setClassName(title);
		intent.putExtra(Item.PLAN, p);
		startActivity(intent);
	}

	public void commit() {
		// TODO Auto-generated method stub
		DeliveryPlanAdapter deliveryPlanAdapter = (DeliveryPlanAdapter) mAdapter;
		int selectCount = deliveryPlanAdapter.getAllCheckOptionsCount();
		if (selectCount<=0) {
			showToast("没有选择计划分配");
			return;
		}
		List<RollingPlan> mSeletedItems = deliveryPlanAdapter.getAllCheckOptions();
		executeCommitPlanNetWorkRequest(mSeletedItems);
	}

	private void executeCommitPlanNetWorkRequest(
			List<RollingPlan> mSeletedItems) {
		// TODO Auto-generated method stub
	//	getPMSManager().planDetail(planId,getManagerNetWorkHandler(ACTION_PLAN_DETAIL) );

		
	}
	
	
}