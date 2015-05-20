package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Intent;
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
import com.jameschen.widget.BadgeView;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdprt.tasktrackerpms.R;

public class MainActivity extends BaseActivity {

	SparseArray<MenuListener> mTabMenus = new SparseArray<MenuListener>();
	List<View> menuViews = new ArrayList<View>();
	static final int PLAN = 0, TASK = 1, ISSUE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initNavListener();

		String pushTag = getIntent().getStringExtra("pushTag");
		if (pushTag != null) {
			checkFlag(pushTag);
		} else {
			if (savedInstanceState != null) {
				item = savedInstanceState.getInt("nav_menu");
			} else {
				item = PLAN;// defualt is PLAN
			}
			onNavigateItemSelected(item);
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		checkFlag(intent.getStringExtra("tag"));// pushTag
	}

	private void checkFlag(String flag) {

		if (Item.ISSUE.equals(flag)) {
			onNavigateItemSelected(item = ISSUE);
		} else if (Item.TASK.equals(flag)) {
			onNavigateItemSelected(item = TASK);
		} else if (Item.PLAN.equals(flag)) {
			onNavigateItemSelected(item = PLAN);
		}

	}

	private int item = PLAN;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("nav_menu", item);
	}

	private void initNavListener() {

		menuViews.add(findViewById(R.id.btn_menu1));
		menuViews.add(findViewById(R.id.btn_menu2));
		menuViews.add(findViewById(R.id.btn_menu3));

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

	private void setUnreadMsgNum(int unreadMessgeCount,int tab) {
		if (numView == null) {
			numView = new BadgeView(this);
			numView.setGravity(Gravity.END | Gravity.TOP);
			numView.setTargetView(menuViews.get(tab));

		}
		numView.setBadgeCount(unreadMessgeCount);

	}

	

	/**
	 * @param index
	 *            for selected the tab item
	 */
	public void onNavigateItemSelected(int index) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		MenuListener selectedMenu = mTabMenus.get(index);
		if (selectedMenu == null) {
			switch (index) {
			case PLAN: {// plan
				selectedMenu = new MenuListener<PlanFragment>(this, Item.PLAN,
						PlanFragment.class, null);
			}
				break;
			case TASK: {// task
				selectedMenu = new MenuListener<TaskFragment>(this, Item.TASK,
						TaskFragment.class, null);
			}
				break;
			case ISSUE: {// issue
				selectedMenu = new MenuListener<IssueFragment>(this,
						Item.ISSUE, IssueFragment.class, null);
			}
				break;
			default:
				break;
			}
			mTabMenus.put(index, selectedMenu);
		}

		int tab_size = menuViews.size();

		for (int i = 0; i < tab_size; i++) {

			if (index == i) {// Selected
				selectedMenu.onMenuSelected(null, ft);
				setBottomItemSeleted(i, true);
			} else {
				mTabMenus.get(i).onMenuUnselected(null, ft);
				setBottomItemSeleted(i, false);
			}

		}

		ft.commit();
		this.item = index;
	}

	private void setBottomItemSeleted(int index, boolean foucsStatus) {
		int num = menuViews.size();
		if (index >= num) {
			Log.i(TAG, "current index out of boundary = " + index);
			return;
		}
		if (foucsStatus) {
			menuViews.get(index).setSelected(true);
		} else {
			menuViews.get(index).setSelected(false);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		releaseResource();
	}

	private void releaseResource() {

		for (int i = 0; i < mTabMenus.size(); i++) {
			MenuListener tabMenu = mTabMenus.get(i);
			if (tabMenu != null) {
				tabMenu.onTabRelease();
				tabMenu = null;
			}
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
					Log.i(mTag,
							"sel ********show!!!!!!!!!!!!!!!!"
									+ (mFragment.getView() == null));

					ft.show(mFragment);

				}
			}
		}

		public void onTabUnselected(FragmentTransaction ft) {
			if (mFragment != null) {
				if (mFragment.isDetached() || mFragment.isRemoving()) {
					Log.e(mTag,
							"onTabUnselected ********removew!!!!!!!!!!!!!!!!");
					ft.detach(mFragment);
					// mFragment = null;
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
