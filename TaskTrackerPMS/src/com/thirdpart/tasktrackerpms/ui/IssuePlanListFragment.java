package com.thirdpart.tasktrackerpms.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.data.s;

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
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter.OnStatusItemListener;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;


public class IssuePlanListFragment extends BasePageListFragment<IssueResult, IssueList> implements OnItemClickListener{

	
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.issue_ui, container, false);
		bindListView(view,new IssueAdapter(getBaseActivity(),onStatusItemListener));
		mListView.setOnItemClickListener(this);
		planid = getArguments().getLong(ConstValues.ID);
		Log.i(TAG, "plan id = "+planid);
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	long planid ;
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private  void executeNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
	        getPMSManager().IssuePlanList(planid+"",pagesize+"", pagenum+"",new PageUINetworkHandler<IssueList>(getBaseActivity()){

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


	OnStatusItemListener onStatusItemListener = new OnStatusItemListener() {
		
		@Override
		public void onItemClicked(View convertView, IssueResult issueResult) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(getActivity(), IssueSolveActivity.class);
			Object object =issueResult;
			if (object == null) {
				return;
			}
			
			
			int requestCode=0;
			IssueResult p = (IssueResult) (object);
			intent.putExtra(Item.ISSUE, (Serializable)p);
			//问题详情
			intent.setClass(getBaseActivity(), IssueDetailActivity.class);
			

			startActivityForResult(intent,requestCode);
		
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), PlanDetailActivity.class);
		Object object = parent.getAdapter().getItem(position);
		if (object == null) {
			return;
		}
		
		
		IssueResult p = (IssueResult) (object);
		RollingPlan plan = new RollingPlan();
		plan.setId(p.rollingPlanId);
		intent.putExtra(Item.PLAN, plan);
		intent.putExtra("scan", true);
		
		startActivity(intent);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
}
