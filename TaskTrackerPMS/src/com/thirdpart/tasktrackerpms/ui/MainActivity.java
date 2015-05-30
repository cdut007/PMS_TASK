package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.thirdpart.tasktrackerpms.R;

public class MainActivity extends BaseActivity {

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		setContentView(R.layout.activity_main);
		setTopBarLeftBtnVisiable(View.GONE);

		setTopBarRightBtnListener(R.drawable.setting_icon,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(MainActivity.this,
								SettingActivity.class));
					}
				});
		initNavListener();

	}

	HashMap<String, MenuListener> mTabMenus = new HashMap<String, MenuListener>();
	HashMap<String, View> menuViews = new HashMap<String, View>();
	static final String PLAN = Item.PLAN, TASK = Item.TASK, MINE = Item.MINE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String pushTag = getIntent().getStringExtra("pushTag");
		if (pushTag != null) {
			checkFlag(pushTag);
		} else {
			if (savedInstanceState != null) {
				item = savedInstanceState.getString("nav_menu");
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

		if (Item.MINE.equals(flag)) {
			onNavigateItemSelected(item = MINE);
		} else if (Item.TASK.equals(flag)) {
			onNavigateItemSelected(item = TASK);
		} else if (Item.PLAN.equals(flag)) {
			onNavigateItemSelected(item = PLAN);
		}

	}

	private String item = PLAN;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("nav_menu", item);
	}

	private void initNavListener() {

		menuViews.put(PLAN, findViewById(R.id.btn_menu1));
		menuViews.put(TASK, findViewById(R.id.btn_menu2));
		menuViews.put(MINE, findViewById(R.id.btn_menu3));

		Set<String> key = menuViews.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			final String menuViewKey = (String) it.next();
			menuViews.get(menuViewKey).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							onNavigateItemSelected(menuViewKey);
						}
					});

		}

	}

	private BadgeView numView;

	private void setUnreadMsgNum(int unreadMessgeCount, int tab) {
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
	public void onNavigateItemSelected(String item) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		MenuListener selectedMenu = mTabMenus.get(item);
		if (selectedMenu == null) {
			switch (item) {
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
			case MINE: {// MINE
				selectedMenu = new MenuListener<MineFragment>(this,
						Item.MINE, MineFragment.class, null);
			}
				break;
			default:
				break;
			}
			mTabMenus.put(item, selectedMenu);
		}

		Set<String> key = mTabMenus.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String menuViewKey = (String) it.next();

			if (item.equals(menuViewKey)) {// Selected
				selectedMenu.onMenuSelected(null, ft);
				setBottomItemSeleted(item, true);
			} else {
				MenuListener unSlectedTab = mTabMenus.get(menuViewKey);
				if (unSlectedTab != null) {
					unSlectedTab.onMenuUnselected(null, ft);
				}
				setBottomItemSeleted(menuViewKey, false);
			}

		}

		ft.commit();
		this.item = item;
	}

	
	
	private void setBottomItemSeleted(String item, boolean foucsStatus) {

		View currentItem = menuViews.get(item);
		if (currentItem == null) {
			Log.i(TAG, "current item is null = " + item);
			return;
		}

		if (foucsStatus) {

			currentItem.setSelected(true);
		} else {
			currentItem.setSelected(false);
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
		public Fragment mFragment;

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
				Log.e(mTag, "create the new tab name:" + mTag);
				mFragment = Fragment.instantiate(mActivity, mClass.getName(),
						mArgs);
				ft.add(R.id.fragment_tab_content, mFragment, mTag);
			} else {

				if (mFragment.isDetached() || mFragment.isRemoving()) {
					ft.attach(mFragment);
				} else {
					Log.i(mTag, "show tab :" + mTag);

					ft.show(mFragment);

				}
			}
		}

		public void onTabUnselected(FragmentTransaction ft) {
			if (mFragment != null) {
				if (mFragment.isDetached() || mFragment.isRemoving()) {
					Log.e(mTag, "tab is detached or removed:" + mTag);
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
