package com.thirdpart.tasktrackerpms.ui;

import org.apache.http.Header;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.jameschen.framework.base.BasePageListFragment;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.EventCallbackListener;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.model.entity.WorkStepList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.WorkStepAdapter;


public class WorkStepFragment extends BasePageListFragment<WorkStep, WorkStepList> implements OnItemClickListener{

	boolean scan = false;
	private RollingPlan mRollingPlan;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.workstep_ui, container, false);
		mRollingPlan = (RollingPlan) getArguments().getSerializable(Item.PLAN);		
		canSearch = false;
		bindListView(view,new WorkStepAdapter(getBaseActivity(),getLogInController().matchPlanUrls(),mRollingPlan));
		mListView.setOnItemClickListener(this);
		id =Long.parseLong(mRollingPlan.getId());
		scan = getArguments().getBoolean("scan");
		WorkStepAdapter workStepAdapter = (WorkStepAdapter) mAdapter;
		workStepAdapter.setScanMode(scan);
		Log.i(TAG, "scanMode="+scan+";id = "+id);
		
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	
	long id;
	
	private  void executeNextPageNetWorkRequest(int pagesize,int pagenum) {
		// TODO Auto-generated method stub
			
	        getPMSManager().getWorkStepList(id+"",pagesize+"", pagenum+"",new PageUINetworkHandler<WorkStepList>(getBaseActivity()){

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

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		registerCallBack( new EventCallbackListener()  {
			
			@Override
			public void commitSucc() {
				// TODO Auto-generated method stub
				Log.i(TAG, "call back commit succ");
				
				if (mListView!=null) {
					mListView.setRefreshing(true);
				}
				callNextPage(pageSize,defaultBeginPageNum);
	
			}

			@Override
			public String getTag() {
				// TODO Auto-generated method stub
				return callsucc;
			}
		});
	}

	public static String callsucc="issuefeedback";
	@Override
	public void onDestroy() {
	
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
