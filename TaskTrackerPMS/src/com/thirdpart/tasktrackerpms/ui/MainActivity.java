package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.jameschen.comm.utils.Log;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.model.ConstValues;
import com.thirdprt.tasktrackerpms.R;

public class MainActivity extends BaseActivity {
	MenuListener<PlanFragment> planListener;
	MenuListener<TaskFragment> taskListener;
	MenuListener<IssueFragment> issueListener;
    SparseArray<Fragment> mTabFragments = new SparseArray<Fragment>();
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		initNavListener();
		
		String pushTag = getIntent().getStringExtra("pushTag");
		if (pushTag!=null) {
			checkFlag(pushTag);
		}else {
			if (savedInstanceState != null) {
				item = savedInstanceState.getInt("nav_menu");
			}else {
				item=0;//defualt is 0
			}
			onNavigateItemSelected(item);
		}
		
	
		
	}
	
	

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		checkFlag(intent.getStringExtra("tag"));//pushTag
	}

	private void checkFlag(String flag) {

		if (ConstValues.SPORT_EVENT.equals(flag)) {
			
			SportEventListFragment.sortByCreateTime=true;
			onNavigateItemSelected(item=2);
			//sort by  
			FragmentManager fragmentManager = getSupportFragmentManager();
			SportEventListFragment sportEventListFragment = (SportEventListFragment) fragmentManager
			.findFragmentByTag(ConstValues.SPORT_EVENT);
			Log.i(TAG, "sort by create time.");
			if (sportEventListFragment!=null) {
				sportEventListFragment.sortByCreateTime();
			}
		}else if(ConstValues.STADIUM.equals(flag)) {
			onNavigateItemSelected(item=1);
		}else if(ConstValues.COACH.equals(flag)) {
			onNavigateItemSelected(item=0);
		}
		
	}

	
	private int item = 0;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("nav_menu", item);
	}

	
	private void initNavListener() {
		
		Bundle coachbundle = new Bundle();

		menuViews.add(findViewById(R.id.btn_menu0));
		menuViews.add(findViewById(R.id.btn_menu1));
		menuViews.add(findViewById(R.id.btn_menu2));
		
		
		
		aroundListener = new MenuListener<AroundListFragment>(this,
				ConstValues.AROUND_USER, AroundListFragment.class, coachbundle);
		stadiumListener = new MenuListener<StadiumListFragment>(this,
				ConstValues.STADIUM, StadiumListFragment.class, null);
		accountListener = new MenuListener<AccountFragment>(this,
				ConstValues.ACCOUNT, AccountFragment.class, null);
	
		mTabFragments.put(key, value);
		for (int index = 0; index < menuViews.size(); index++) {
			final int position = index;
			menuViews.get(index).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onNavigateItemSelected(position);
				}
			});
		}

	}


	private BadgeView numView;

	private void setUnreadMsgNum(int unreadMessgeCount) {
		if (numView == null) {
			numView = new BadgeView(this);
			numView.setGravity(Gravity.RIGHT | Gravity.TOP);
			numView.setTargetView(menuViews.get(2));

		}
		numView.setBadgeCount(unreadMessgeCount);

	}

	List<View> menuViews = new ArrayList<View>();
	

	public void onNavigateItemSelected(int index) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (index) {
		
		case 4:// gymnasium
			stadiumListener.onMenuSelected(null, ft);
			aroundListener.onMenuUnselected(null, ft);
			accountListener.onMenuUnselected(null, ft);
			weatherListener.onMenuUnselected(null, ft);
			sportEventListener.onMenuUnselected(null, ft);
			break;
		case 0:// near user
			aroundListener.onMenuSelected(null, ft);
			stadiumListener.onMenuUnselected(null, ft);
			accountListener.onMenuUnselected(null, ft);
			weatherListener.onMenuUnselected(null, ft);
			sportEventListener.onMenuUnselected(null, ft);
			break;
		
		case 2:// event
			sportEventListener.onMenuSelected(null, ft);
			aroundListener.onMenuUnselected(null, ft);
			stadiumListener.onMenuUnselected(null, ft);
			accountListener.onMenuUnselected(null, ft);
			weatherListener.onMenuUnselected(null, ft);
			break;
		case 1:// weather
			accountListener.onMenuUnselected(null, ft);
			aroundListener.onMenuUnselected(null, ft);
			stadiumListener.onMenuUnselected(null, ft);
			weatherListener.onMenuSelected(null, ft);
			sportEventListener.onMenuUnselected(null, ft);
			break;
		case 3:// account
			weatherListener.onMenuUnselected(null, ft);
			aroundListener.onMenuUnselected(null, ft);
			stadiumListener.onMenuUnselected(null, ft);
			accountListener.onMenuSelected(null, ft);
			sportEventListener.onMenuUnselected(null, ft);
			break;

		}
		ft.commit();
		setBottomItemSeleted(index);
		this.item = index;
	}

	private void setBottomItemSeleted(int index) {
		int num = menuViews.size();
		for (int i = 0; i < num; i++) {
			if (i == index) {
				menuViews.get(i).setSelected(true);
			} else {
				menuViews.get(i).setSelected(false);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		releaseResource();
	}

	private void releaseResource() {
		if (sportEventListener != null) {
			sportEventListener.onTabRelease();
			sportEventListener = null;
		}
		if (stadiumListener != null) {
			stadiumListener.onTabRelease();
			stadiumListener = null;
		}
		if (aroundListener != null) {
			aroundListener.onTabRelease();
			aroundListener = null;
		}
		

	}

	/**
	 * Callback interface invoked when a tab is focused, unfocused, added, or
	 * removed.
	 */
	public interface SlidMenuListener {

		/**
		 * Called when a tab enters the selected state.
		 * 
		 * @param tab
		 *            The tab that was selected
		 * @param ft
		 *            A FragmentTransaction for queuing fragment operations to
		 *            execute during a tab switch. The previous tab's unselect
		 *            and this tab's select will be executed in a single
		 *            transaction. This FragmentTransaction does not support
		 *            being added to the back stack.
		 */
		public void onMenuSelected(Object object, FragmentTransaction ft);

		public void onMenuUnselected(Object object, FragmentTransaction ft);

		public void onMenuReselected(Object object, FragmentTransaction ft);
	}

	public static class MenuListener<T extends Fragment> implements
			SlidMenuListener {
		private final FragmentActivity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		private final Bundle mArgs;
		private Fragment mFragment;

		public MenuListener(FragmentActivity activity, String tag, Class<T> clz) {
			this(activity, tag, clz, null);
		}

		public MenuListener(FragmentActivity activity, String tag,
				Class<T> clz, Bundle args) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
			mArgs = args;
			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			mFragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(mTag);
//			if (mFragment != null && !mFragment.isDetached()) {
//				Log.e(mTag, "FFFFFFFFFFFFFFFFFFF");
//				FragmentTransaction ft = mActivity.getSupportFragmentManager()
//						.beginTransaction();
//				ft.detach(mFragment);
//				ft.commit();
//				mFragment = null;
//			}
		}

		public void onTabSelected(FragmentTransaction ft) {
			if (mFragment == null) {
				Log.e(mTag, "sel ********null!!!!!!!!!!!!!!!!");
				mFragment = Fragment.instantiate(mActivity, mClass.getName(),
						mArgs);
				ft.add(R.id.fragment_tab_content, mFragment, mTag);
			} else {

				if (mFragment.isDetached() || mFragment.isRemoving()) {
					ft.attach(mFragment);
				} else {
					Log.i(mTag, "sel ********show!!!!!!!!!!!!!!!!"+(mFragment.getView()==null));
				
						ft.show(mFragment);
					
					
				}
			}
		}

		public void onTabUnselected(FragmentTransaction ft) {
			if (mFragment != null) {
				if (mFragment.isDetached() || mFragment.isRemoving()) {
					Log.e(mTag, "onTabUnselected ********removew!!!!!!!!!!!!!!!!");
					ft.detach(mFragment);
					//mFragment = null;
				} else {
					ft.hide(mFragment);
				}
			}
		}

		public void onTabRelease() {
			if (mFragment != null) {
				// ft.hide(mFragment);
				// ft.remove(mFragment);
				mFragment = null;
			}
		}

		@Override
		public void onMenuReselected(Object object, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMenuSelected(Object object, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			onTabSelected(ft);

		}

		@Override
		public void onMenuUnselected(Object object, FragmentTransaction ft) {
			// TODO Auto-generated method stub

			onTabUnselected(ft);
		}
	}
	
	@Override
	
	protected void onResume() {
		super.onResume();
	
	};
	
	

	public void onBackPressed() {

		super.onBackPressed();

	
	}
	
	
	
}
