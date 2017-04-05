package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;
import org.w3c.dom.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jameschen.framework.base.BaseListFragment;
import com.jameschen.framework.base.UINetworkHandler;
import com.jameschen.framework.base.BaseListFragment.onSearchListener;
import com.jameschen.widget.MyListView;
import com.thirdpart.model.EventCallbackListener;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.DepartmentInfo;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.PlanAdapter;


public class PlanFragment extends BaseListFragment<DepartmentInfo> implements OnItemClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		registerCallBack( new EventCallbackListener()  {
			
			@Override
			public void commitSucc() {
				
				Log.i(TAG, "call back commit succ");
				
				if (mListView!=null) {
					mListView.setRefreshing(true);
				}
				executeNextPageNetWorkRequest();
	
			}

			@Override
			public String getTag() {
				
				return callsucc;
			}
		});
	}
	
	
	public static String callsucc="planDelivery";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.plan_ui, container, false);
		bindListView(view,new PlanAdapter(getBaseActivity()));
		//bind search view..
					bindSearchController(view,new onSearchListener(){

						@Override
						public void beginSearch(String keyword) {
							// TODO Auto-generated method stub
							getPMSManager().setKeyword(keyword);
							executeNextPageNetWorkRequest();
						}

						@Override
						public void backToNormal() {
							// TODO Auto-generated method stub
							
						}
						
					});
				
		mListView.setOnItemClickListener(this);
		mListView.setMode(Mode.PULL_FROM_START);
		setListShown(false);
		executeNextPageNetWorkRequest();
		
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	@Override
	protected void doFreshFromTop(MyListView mListView) {
		
		super.doFreshFromTop(mListView);
		executeNextPageNetWorkRequest();
	}
	
	
	private  void executeNextPageNetWorkRequest() {
		
			 Type sToken = new TypeToken<List<DepartmentInfo>>() {
			}.getType();
			
			UINetworkHandler<List<DepartmentInfo>> hanlder = new UINetworkHandler<List<DepartmentInfo>>(getActivity(),sToken) {

				@Override
				public void start() {
					
				}

				@Override
				public void finish() {
					
					setListShown(true);
					checkIsNeedShowEmptyView();
				}

				@Override
				public void callbackFailure(int statusCode, Header[] headers,
						String response) {
					
					cancelLoading();
					showToast(response);
				}

				@Override
				public void callbackSuccess(int statusCode, Header[] headers,
						List<DepartmentInfo> response) {
					
					addDataToListAndRefresh(true, response);
				}
			};
			
			if (getLogInController().matchUrls("/construction/endman")) {
				getPMSManager().teamGroupList(hanlder);
			}else {
				getPMSManager().teamWorkList(hanlder);
			}
	        
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), DeliveryPlanListActivity.class);
		Object object = parent.getAdapter().getItem(position);
		if (object == null) {
			return;
		}
		DepartmentInfo p = (DepartmentInfo) (object);
		intent.putExtra(Item.PLAN, p);
		startActivity(intent);
	}
	
	
}
