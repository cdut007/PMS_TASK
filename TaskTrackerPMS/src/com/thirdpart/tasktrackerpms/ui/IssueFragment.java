package com.thirdpart.tasktrackerpms.ui;


import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jameschen.framework.base.BasePageListFragment;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.UINetworkHandler;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.thirdpart.model.EventCallbackListener;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.IssueCategoryItem;
import com.thirdpart.model.entity.IssueCategoryStatistic;
import com.thirdpart.model.entity.IssueList;
import com.thirdpart.model.entity.IssueMenu;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.TaskCategory;
import com.thirdpart.model.entity.TaskCategoryItem;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;

public class IssueFragment extends BasePageListFragment{

	
	private IssueMenuAdapter itemAdapter;

	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerCallBack( new EventCallbackListener()  {
			
			@Override
			public void commitSucc() {
				// TODO Auto-generated method stub
				Log.i(TAG, "call back commit succ");
				queryDate();
	
			}

			@Override
			public String getTag() {
				// TODO Auto-generated method stub
				return callsucc;
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		queryDate();
	}
	
	public static String callsucc="issueFragment";

	private void queryDate() {
		// TODO Auto-generated method stub
		getPMSManager().getIssueCategoryCount(new UINetworkHandler<IssueCategoryStatistic>(getActivity()) {

			@Override
			public void start() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void callbackFailure(int statusCode, Header[] headers,
					String response) {
				// TODO Auto-generated method stub
				//showToast(response);
			}

			@Override
			public void callbackSuccess(int statusCode, Header[] headers,
					IssueCategoryStatistic response) {
				// TODO Auto-generated method stub
				updateInfo(IssueCategoryStatistic.getCategoryItem(response));
			}
		});
	}
	
	private void updateInfo(List<IssueCategoryItem> response) {
		// TODO Auto-generated method stub
		List<IssueMenu> mList = itemAdapter.getObjectInfos();
		for (IssueCategoryItem issueCategoryItem : response) {
			for (IssueMenu issueMenu : mList) {
				if (issueMenu.getId().equals(issueCategoryItem.key)) {
					issueMenu.count = issueCategoryItem.count;
					continue;
				}
			}
		}
		itemAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_issue_ui, container, false);
		bindListView(view,itemAdapter = new IssueMenuAdapter(getBaseActivity()));
		mListView.setMode(Mode.DISABLED);
		loadAnimate(APPEAR);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), IssueActivity.class);
				Object object = parent.getAdapter().getItem(position);
				if (object == null) {
					return;
				}
				IssueMenu p = (IssueMenu) (object);
				intent.putExtra(Item.ISSUE, p);
				startActivity(intent);
			}
		});
		
		queryDate();
		
		return view;
	}
	

	static class IssueMenuAdapter extends MyBaseAdapter<IssueMenu> {
		private Context context;

		public IssueMenuAdapter(Context context) {
			super(context,R.layout.common_menu_item);
			this.context = context;
			setObjectList(IssueMenu.getMenus());
		}

		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			convertView = super.getView(position, convertView, parent);
			
			return convertView;

		}

		@Override
		public void recycle() {
		
			super.recycle();

		}

		@Override
		protected HoldView<IssueMenu> createHoldView() {
			// TODO Auto-generated method stub
			return new IssueMenuHoldView();
		}

		private final static class IssueMenuHoldView extends HoldView<IssueMenu> {
			
			TextView mContent,mCount;
			@Override
			protected void initChildView(View convertView,
					MyBaseAdapter<IssueMenu> myBaseAdapter) {
				// TODO Auto-generated method stub
				mContent = (TextView) convertView.findViewById(R.id.common_menu_item_content);
				mCount = (TextView) convertView.findViewById(R.id.common_menu_item_count);
			}

			@Override
			protected void setInfo(IssueMenu object) {
				// TODO Auto-generated method stub
				mContent.setText(object.getContent());
				if (object.count==0) {
					mCount.setText("");
				}else {
					mCount.setText(""+object.count);
				}
				
			}
			
		}
		

	}


	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		// TODO Auto-generated method stub
		
	}


}
