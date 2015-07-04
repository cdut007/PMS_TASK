package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.Log;
import com.jameschen.framework.base.BasePageListFragment;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.Department;
import com.thirdpart.model.entity.DepartmentInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.DeliveryPlanAdapter;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.IndicatorView;
import com.thirdpart.widget.TouchImage;


public class DeliveryPlanFragment extends BasePageListFragment<RollingPlan, RollingPlanList> implements OnItemClickListener{

	enum DeliveryStatus{
		Deliveried,UnDeliveried,ReDeliveried
	}
	private  void executePlanClassDepartmentNetWorkRequest(final boolean showMenu) {
		// TODO Auto-generated method stub
			 Type sToken = new TypeToken<List<DepartmentInfo>>() {
			}.getType();
			
			UINetworkHandler<List<DepartmentInfo>> hanlder = new UINetworkHandler<List<DepartmentInfo>>(getActivity(),sToken) {

				@Override
				public void start() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void finish() {
					// TODO Auto-generated method stub
					setListShown(true);
					checkIsNeedShowEmptyView();
				}

				@Override
				public void callbackFailure(int statusCode, Header[] headers,
						String response) {
					// TODO Auto-generated method stub
					cancelLoading();
					showToast(response);
				}

				@Override
				public void callbackSuccess(int statusCode, Header[] headers,
						List<DepartmentInfo> response) {
					// TODO Auto-generated method stub
					mDepartmentInfos = response;
					if (showMenu) {
						showClassMenu();
					}
				}
			};
			
			if (getLogInController().matchUrls("/construction/endman")) {
				getPMSManager().teamGroupList(hanlder);
			}else {
				getPMSManager().teamWorkList(hanlder);
			}
	        
		
	}
	
	List<DepartmentInfo> mDepartmentInfos;
	DeliveryStatus status = DeliveryStatus.UnDeliveried;
	
	String title,teamId;
	boolean scanMode ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.delivery_plan_ui, container, false);
		bindListView(view,new DeliveryPlanAdapter(getBaseActivity()));
		mListView.setOnItemClickListener(this);
		title = getArguments().getString(Item.PLAN);
		teamId = getArguments().getString("teamId");
		time = (Button) view.findViewById(R.id.plan_date);
		classBtn = (ChooseItemView) view.findViewById(R.id.plan_class);
		time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				go2ChooseTime();
			}
		});
		classBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mDepartmentInfos == null) {
				   executePlanClassDepartmentNetWorkRequest(true);	
				}else {
					showClassMenu();
				}
			}
		});

		DeliveryPlanAdapter deliveryPlanAdapter = (DeliveryPlanAdapter) mAdapter;
		 scanMode = getArguments().getBoolean("scan");
		if (scanMode) {
			if (getLogInController().matchUrls("/construction/team")||
					getLogInController().matchUrls("/construction/endman")
					) {
				
				if (getActivity() instanceof MineActivity) {
					((MineActivity)getActivity()).setCommitBtnLayout(true);
				}
				status = DeliveryStatus.ReDeliveried;
				TouchImage.buttonEffect(time);
				time.setVisibility(View.VISIBLE);
				TouchImage.buttonEffect(classBtn);
				classBtn.setVisibility(View.VISIBLE);
				executePlanClassDepartmentNetWorkRequest(false);
			}else {
				status = DeliveryStatus.Deliveried;
				deliveryPlanAdapter.setScanMode(true);
			}
		}else {
			TouchImage.buttonEffect(time);
			time.setVisibility(View.VISIBLE);
			status = DeliveryStatus.UnDeliveried;
		}
		
		IndicatorView indicatorView = (IndicatorView) view.findViewById(R.id.plan_delivery_indicator);
		indicatorView.setScanMode(status == DeliveryStatus.Deliveried);
		callNextPage(pageSize,getCurrentPage());
		return view;
	}
	
	protected void showClassMenu() {
		// TODO Auto-generated method stub
		if (mDepartmentInfos!=null&&mDepartmentInfos.size()>0) {
			List<String> names = new ArrayList<String>();
			
			for (DepartmentInfo departmentInfo : mDepartmentInfos) {
				Department d = departmentInfo.getDepartment();
				if (d!=null) {
					names.add(d.getName());
				}
				
			}
			classBtn.showMenuItem(mDepartmentInfos,names,new ChooseItemView.onDismissListener<DepartmentInfo>(){

				@Override
				public void onDismiss(
						DepartmentInfo item) {
					// TODO Auto-generated method stub
					 updateClass(item);
				}
				
			});	
		}
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
	    	
	    	if (getLogInController().matchUrls("/construction/endman")) {
	    		if (scanMode) {//see my group plan
	    			List<Department> departments = getLogInController().getInfo().departments;
	    			if (departments== null||departments.size() == 0) {
						Log.i(TAG, "department is empty...");
	    				return;
					}
					teamId = departments.get(0).getId();
				}
	    		
	    		 getPMSManager().endmanList(pagesize+"", pagenum+"",scanMode?"equal":"notequal",pageUINetworkHandler);
		    				
			} else if(getLogInController().matchUrls("/construction/team")){//计划员
				
				 getPMSManager().teamList(pagesize+"", pagenum+"",scanMode?"equal":"notequal",teamId,pageUINetworkHandler);
		    		
			}else {
				Log.i(TAG, "other roles!!!!!!");
				getPMSManager().myTaskList(pagesize+"", pagenum+"","notequal",pageUINetworkHandler);
				   
			} 
	     
	}

	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		// TODO Auto-generated method stub
		executeNextPageNetWorkRequest(pagesize, pageNum);
		
	}

	void go2ChooseTime() {
		Intent intent = new Intent(getActivity(), TimeActivity.class);
		boolean oneday = !getLogInController().matchUrls("/construction/endman");
		intent.putExtra("oneday", oneday);
		startActivityForResult(intent, TimeActivity.REQUEST_PICK_DATE);
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
	
	String format0, format1;
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);

	switch (requestCode) {
	case TimeActivity.REQUEST_PICK_DATE: {
		if (resultCode == Activity.RESULT_OK) {
			int monthVal = data.getIntExtra("month", 1);
			int dayVal = data.getIntExtra("day", 1);
			int monthEndVal = data.getIntExtra("monthEnd", 1);
			int dayEndVal = data.getIntExtra("dayEnd", 1);
			 format0 = data.getStringExtra("format");
			 format1 = data.getStringExtra("format1");
			updateTime(data.getStringExtra("time"));
		}
	}
		break;

	default:
		break;
	}
}
Button time;
ChooseItemView classBtn;
	private void updateTime(String timeStr) {
	// TODO Auto-generated method stub
	 time.setText(timeStr);
}
	DepartmentInfo departmentInfo;
	private void updateClass(DepartmentInfo className) {
		// TODO Auto-generated method stub
		this.departmentInfo = className;
		classBtn.setContent(className.getDepartment().getName());
	}

	public void commit() {
		// TODO Auto-generated method stub
		DeliveryPlanAdapter deliveryPlanAdapter = (DeliveryPlanAdapter) mAdapter;
		int selectCount = deliveryPlanAdapter.getAllCheckOptionsCount();
		if (selectCount<=0) {
			showToast("没有选择计划分配");
			getBaseActivity().cancelProgressDialog();
			return;
		}
		
		if (format0 ==null) {
			showToast("请选择时间");
			getBaseActivity().cancelProgressDialog();
			return;
		}
		if (status == DeliveryStatus.ReDeliveried ) {
			if (departmentInfo == null) {
				showToast("请选择该派班组");
				return;
			}else {
				teamId = departmentInfo.getDepartment().getId();
			}
		}
		List<RollingPlan> mSeletedItems = deliveryPlanAdapter.getAllCheckOptions();
		executeCommitPlanNetWorkRequest(mSeletedItems);
	}

	private void executeCommitPlanNetWorkRequest(
			List<RollingPlan> mSeletedItems) {
		// TODO Auto-generated method stub
		List<String> ids = new ArrayList<String>();
		
		for (RollingPlan plan : mSeletedItems) {
			ids.add(plan.getId());
			
		}
		if (getLogInController().matchUrls("/construction/endman")) {
			getPMSManager().deliveryPlanToHeadMan( ids,teamId, format0, format1,new UINetworkHandler<Object>(getActivity()) {

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
						Object response) {
					showToast("分配计划成功");
					mListView.setRefreshing(true);
					callNextPage(pageSize,defaultBeginPageNum);
					PlanFragment.CallSucc(PlanFragment.callsucc);
					
				}
			});
		}else {

			getPMSManager().deliveryPlanToTeam(teamId, format0,ids, new UINetworkHandler<Object>(getActivity()) {

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
						Object response) {
					showToast("分配计划成功");
					mListView.setRefreshing(true);
					callNextPage(pageSize,defaultBeginPageNum);
					PlanFragment.CallSucc(PlanFragment.callsucc);
					 
				}
			});
		
		}
		

		
	}
	
	
}
