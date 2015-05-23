package com.thirdpart.tasktrackerpms.ui;


import org.apache.http.Header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jameschen.framework.base.BasePageListFragment;
import com.thirdpart.model.entity.IssueList;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;

public class IssueFragment extends BasePageListFragment<IssueResult	, IssueList>{

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.issue_ui, container, false);
		bindListView(view,new IssueAdapter(getBaseActivity()));
		callNextPage(getCurrentPage(), pageNum);
		return view;
	}
	
	
	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		
	}
	

}
