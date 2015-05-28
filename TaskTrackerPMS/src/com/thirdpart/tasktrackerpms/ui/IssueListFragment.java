package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

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
import com.thirdpart.model.entity.IssueList;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;


public class IssueListFragment extends BasePageListFragment<IssueResult, IssueList> implements OnItemClickListener{

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.issue_ui, container, false);
		bindListView(view,new IssueAdapter(getBaseActivity()));
		mListView.setOnItemClickListener(this);
		statusid = getArguments().getLong(ConstValues.ID);
		Log.i(TAG, "issue id = "+statusid);
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	long statusid ;
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private  void executeNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
	        getPMSManager().IssueStatus(statusid+"",pagesize+"", pagenum+"",new PageUINetworkHandler<IssueList>(getBaseActivity()){

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
	    				Header[] headers, IssueList response) {
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
		
	}
	
	
}
