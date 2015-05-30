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
import com.thirdpart.model.ConstValues.Item;
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


public class WitnessListFragment extends BasePageListFragment<WitnessDistributed, WitnessDistributedList> implements OnItemClickListener{

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.witness_list_ui, container, false);
		bindListView(view,new WitnesserAdapter(getBaseActivity()));
		mListView.setOnItemClickListener(this);
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private  void executeNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
	        getPMSManager().deliveryWitnessList(pagesize+"", pagenum+"","equal",new PageUINetworkHandler<WitnessDistributedList>(getBaseActivity()){

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
	    	});
		
	}
	
	@Override
	protected void callNextPage(int pagesize, int pageNum) {
	executeNetWorkRequest( pagesize, pageNum);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Object object = parent.getAdapter().getItem(position);
		if (object == null) {
			return;
		}
		Intent intent= new Intent(getActivity(),WitnessUpdateActivity.class);
		
		WitnessDistributed workStep = (WitnessDistributed)object;
		intent.putExtra(Item.WITNESS, workStep);
		startActivity(intent);
	}




	
}
