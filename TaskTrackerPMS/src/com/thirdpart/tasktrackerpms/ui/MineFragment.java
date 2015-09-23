package com.thirdpart.tasktrackerpms.ui;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jameschen.framework.base.BaseActivity;
import com.jameschen.framework.base.BasePageListFragment;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.IssueList;
import com.thirdpart.model.entity.IssueMenu;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.IssueAdapter;

public class MineFragment extends BasePageListFragment{

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		updateTitle();
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			FragmentManager fragmentManager = getFragmentManager() ;
			android.support.v4.app.Fragment fragment = fragmentManager.findFragmentByTag(Item.HOME);
			if (fragment==null||fragment.isHidden()) {
				(getBaseActivity()).changeTitle("");	
			}
			
		}else {
			updateTitle();
		}
	}
		
	private void updateTitle() {
		// TODO Auto-generated method stub		
		(getBaseActivity()).changeTitle(getLogInController().getInfo().getRealname());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mine_ui, container, false);
		canSearch = false;
		bindListView(view,new IssueMenuAdapter(getBaseActivity()));
		mListView.setMode(Mode.DISABLED);
		loadAnimate(APPEAR);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MineActivity.class);
				Object object = parent.getAdapter().getItem(position);
				if (object == null) {
					return;
				}
				IssueMenu p = (IssueMenu) (object);
				intent.putExtra(Item.MINE, p);
				startActivity(intent);
			}
		});
		return view;
	}
	

	static class IssueMenuAdapter extends MyBaseAdapter<IssueMenu> {
		private BaseActivity context;

		public IssueMenuAdapter(BaseActivity context) {
			super(context,R.layout.common_menu_item);
			this.context = context;
			setObjectList(IssueMenu.getMineMenus(!context.getLogInController().matchPlanUrls()));
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
			
			TextView mContent;
			@Override
			protected void initChildView(View convertView,
					MyBaseAdapter<IssueMenu> myBaseAdapter) {
				// TODO Auto-generated method stub
				mContent = (TextView) convertView.findViewById(R.id.common_menu_item_content);
			}

			@Override
			protected void setInfo(IssueMenu object) {
				// TODO Auto-generated method stub
				mContent.setText(object.getContent());
			}
			
		}
		

	}


	@Override
	protected void callNextPage(int pagesize, int pageNum) {
		// TODO Auto-generated method stub
		
	}


}
