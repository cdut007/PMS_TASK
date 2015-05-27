package com.jameschen.framework.base;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Fragment.InstantiationException;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jameschen.comm.utils.Log;
import com.jameschen.widget.MyListView;
import com.thirdpart.model.ManagerService;
import com.thirdpart.tasktrackerpms.R;

public abstract class BaseListFragment<T> extends BaseFragment {

	public static final String LIST = "list";
	
	protected MyListView mListView;
	protected MyBaseAdapter<T> mAdapter;
	


	TextView		mStandardEmptyView;
	View			mProgressContainer;

	CharSequence	mEmptyText;
	boolean			mListShown;

	private View	mListContainer;

	public BaseListFragment() {
	}

	
	protected ListView bindListView(View root,MyBaseAdapter<T> adapter) {
		mStandardEmptyView = (TextView) root.findViewById(R.id.listEmpty);
		mStandardEmptyView.setVisibility(View.GONE);
		mProgressContainer = root.findViewById(R.id.progressContainer);
		mListContainer = root.findViewById(R.id.listContainer);
		mListView = (MyListView) root.findViewById(R.id.common_list);
		mStandardEmptyView.setText(mEmptyText);
		mAdapter  = adapter;
		mListView.setAdapter(mAdapter);
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				doFreshFromTop(mListView);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				doFreshFromBottom(mListView);
			}

	
		});
		
		return mListView.getRefreshableView();

	}

	
	
	
	protected void doFreshFromBottom(MyListView mListView) {
		// TODO Auto-generated method stub
		Log.i(TAG, "fresh from bottom");
		
	}

	protected void doFreshFromTop(MyListView mListView) {
		// TODO Auto-generated method stub
		Log.i(TAG, "fresh from top");
		showNoResult(false, "");
		
	}

	protected void showNoResult(boolean noResult, String noResultStr) {
		if (noResult) {
			mStandardEmptyView.setText(noResultStr);
			mStandardEmptyView.setVisibility(View.VISIBLE);
		} else {
			mStandardEmptyView.setVisibility(View.GONE);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return null;
	}

	/**
	 * Attach to list view once the view hierarchy has been created.
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	}

	/**
	 * Detach from list view.
	 */
	@Override
	public void onDestroyView() {
		mListShown = false;
		super.onDestroyView();
	}

	/**
	 * The default content for a ListFragment has a TextView that can
	 * be shown when the list is empty.  If you would like to have it
	 * shown, call this method to supply the text it should use.
	 * @param mList 
	 */
	public void setEmptyText(CharSequence text, AdapterView<ListAdapter> mList) {

		if (mStandardEmptyView == null) {
			throw new IllegalStateException("Can't be used with a custom content view");
		}
		mStandardEmptyView.setText(text);
		if (mEmptyText == null) {
			mList.setEmptyView(mStandardEmptyView);
		}
		mEmptyText = text;
	}

	/**
	 * Control whether the list is being displayed.  You can make it not
	 * displayed if you are waiting for the initial data to show in it.  During
	 * this time an indeterminant progress indicator will be shown instead.
	 * 
	 * <p>Applications do not normally need to use this themselves.  The default
	 * behavior of ListFragment is to start with the list not being shown, only
	 * showing it once an adapter is given with {@link #setListAdapter(ListAdapter)}.
	 * If the list at that point had not been shown, when it does get shown
	 * it will be do without the user ever seeing the hidden state.
	 * 
	 * @param shown If true, the list view is shown; if false, the progress
	 * indicator.  The initial value is true.
	 */
	public void setListShown(boolean shown) {
		setListShown(shown, true);
	}

	/**
	 * Like {@link #setListShown(boolean)}, but no animation is used when
	 * transitioning from the previous state.
	 */
	public void setListShownNoAnimation(boolean shown) {
		setListShown(shown, false);
	}

	/**
	 * Control whether the list is being displayed.  You can make it not
	 * displayed if you are waiting for the initial data to show in it.  During
	 * this time an indeterminant progress indicator will be shown instead.
	 * 
	 * @param shown If true, the list view is shown; if false, the progress
	 * indicator.  The initial value is true.
	 * @param animate If true, an animation will be used to transition to the
	 * new state.
	 */
	private void setListShown(boolean shown, boolean animate) {

		if (shown) {
			if (mListContainer.getVisibility() == View.VISIBLE) {
				return;
			}
			if (animate) {
				mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
				mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
			} else {
				mProgressContainer.clearAnimation();
				mListContainer.clearAnimation();
			}
			mProgressContainer.setVisibility(View.GONE);
			mListContainer.setVisibility(View.VISIBLE);
		} else {
			if (animate) {
				mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
				mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
			} else {
				mProgressContainer.clearAnimation();
				mListContainer.clearAnimation();
			}
			mProgressContainer.setVisibility(View.VISIBLE);
			mListContainer.setVisibility(View.GONE);
		}
	}


	
	
	protected void setEmptyView(View emptyView) {
		this.mStandardEmptyView = (TextView) emptyView;
	}

	public View getEmptyView() {
		return mStandardEmptyView;
	}
	
	public void checkIsNeedShowEmptyView(){
		if (mAdapter.getCount() == 0) {
			showNoResult(true, getString(R.string.no_value));
		} else {
			showNoResult(false, "");
		}
	}
}
