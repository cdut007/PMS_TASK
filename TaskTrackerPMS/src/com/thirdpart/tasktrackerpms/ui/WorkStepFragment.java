package com.thirdpart.tasktrackerpms.ui;

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

import com.jameschen.framework.base.BasePageListFragment;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.model.entity.WorkStepList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.WorkStepAdapter;


public class WorkStepFragment extends BasePageListFragment<WorkStep, WorkStepList> implements OnItemClickListener{

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.workstep_ui, container, false);
		bindListView(view,new WorkStepAdapter(getBaseActivity()));
		mListView.setOnItemClickListener(this);
		
		id = getArguments().getLong(ConstValues.ID);
		Log.i(TAG, "id = "+id);
		
		callNextPage(getCurrentPage(), pageNum);
		return view;
	}
	
	long id;
	
	private  void executeNextPageNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
			
	        getPMSManager().workStepWitnessList(id+"",pagesize+"", pagenum+"",new PageUINetworkHandler<WorkStepList>(getBaseActivity()){

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
	    				Header[] headers, WorkStepList response) {
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
//		Intent intent = new Intent(getActivity(), CoachDetailActivity.class);
//		Object object = parent.getAdapter().getItem(position);
//		if (object == null) {
//			return;
//		}
//		Parcelable p = (Coach) (object);
//		intent.putExtra(COACH, p);
//		startActivity(intent);
	}
	
	
}
