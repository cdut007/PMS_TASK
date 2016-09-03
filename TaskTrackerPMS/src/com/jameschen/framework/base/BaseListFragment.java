package com.jameschen.framework.base;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jameschen.comm.utils.Log;
import com.jameschen.widget.MyListView;
import com.thirdpart.tasktrackerpms.R;

public abstract class BaseListFragment<T> extends BaseFragment {

	public static final String LIST = "list";
	
	protected MyListView mListView;
	protected MyBaseAdapter<T> mAdapter;
	
	private List<T> mDataList,mSearchList;


	TextView		mStandardEmptyView;
	View			mProgressContainer;

	CharSequence	mEmptyText;
	boolean			mListShown;

	private View	mListContainer;

	public BaseListFragment() {
	
	}
    
	public static interface onSearchListener{
		void beginSearch(String keyword);
		void backToNormal();
	}
	onSearchListener searchListener;
	
	private void startSearch(String keyword){
		Log.i(TAG, "keyword:"+keyword);
		switchToSearchMode(keyword);
		getPMSManager().setKeyword(keyword);
		if (searchListener!=null) {
			searchListener.beginSearch(keyword);	
		}
		
	}
	
	private void backToNormal(){
		switchToSearchMode(null);
		getPMSManager().setKeyword("");
		if (searchListener!=null) {
			searchListener.backToNormal();
		}
	}
	

	private void switchToSearchMode(String keyword) {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(keyword)) {
			if (!mAdapter.isSearchMode()) {
				  mDataList = mAdapter.getObjectInfos();	
			}
		 
		 resetSearchData();
		 mAdapter.setObjectList(mSearchList,true);
		mAdapter.setSearchStr(keyword);
		mAdapter.notifyDataSetChanged();
		showNoResult(false, "");
		}else {
			if (!mAdapter.isSearchMode()) {
				mDataList = mAdapter.getObjectInfos();
			}
			if (mDataList == null) {
				Log.i(TAG, "orignal data is null");
				mDataList = new ArrayList<T>();
			}
		 mAdapter.setObjectList(mDataList,false);
		 mAdapter.setSearchStr(keyword);
		mAdapter.notifyDataSetChanged();
		checkIsNeedShowEmptyView();
			
		}
		
	}
	
	protected void resetSearchData() {
		// TODO Auto-generated method stub
		if (mSearchList == null) {
			mSearchList = new ArrayList<T>();
		}else {
			mSearchList.clear();
		}
	}

	EditText mSearchText;
	OnClickListener searchOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.search_btn) {

				if (TextUtils.isEmpty(mSearchText.getText())) {
					showToast("请输入搜索关键字.");
					return;
				}
				// TODO Auto-generated method stub
				startSearch(mSearchText.getText().toString());
			
			}else {//cancel btn
				closeInputMethod();
				backToNormal();
			}
		}
	};
	
	protected void bindSearchController(View rootView, onSearchListener onSearchListener){
		mSearchText = getBaseActivity().bindSearchViews(searchOnClickListener);
		this.searchListener = onSearchListener;
	}
	
	protected ListView bindListView(View root,MyBaseAdapter<T> adapter) {
		mStandardEmptyView = (TextView) root.findViewById(R.id.listEmpty);
		mStandardEmptyView.setVisibility(View.GONE);
		mProgressContainer = root.findViewById(R.id.progressContainer);
		mListContainer = root.findViewById(R.id.listContainer);
		mListView = (MyListView) root.findViewById(R.id.common_list);
		mListView.getRefreshableView().setSelector(R.drawable.transparent);
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
 
	protected boolean canSearch = true;

	 private LayoutAnimationController getAppearAnimationController() {  
	        int duration=300;  
	        AnimationSet set = new AnimationSet(true);  
	  
	        Animation animation = new AlphaAnimation(0.0f, 1.0f);  
	        animation.setDuration(duration);  
	        set.addAnimation(animation);  
	  
//	        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,  
//	                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,  
//	                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);  
//	        animation.setDuration(duration);  
//	        set.addAnimation(animation);  
	  
	        LayoutAnimationController controller = new LayoutAnimationController(set, 0.15f);  
	        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);  
	        return controller;  
	    }
	 private LayoutAnimationController getAnimationController() {  
	        int duration=300;  
	        AnimationSet set = new AnimationSet(true);  
	  
	        Animation animation = new AlphaAnimation(0.0f, 1.0f);  
	        animation.setDuration(duration);  
	        set.addAnimation(animation);  
	  
	        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,  
	                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,  
	                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);  
	        animation.setDuration(duration);  
	        set.addAnimation(animation);  
	  
	        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);  
	        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);  
	        return controller;  
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
			mStandardEmptyView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.appear_in));
		
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
			
		try {
			if (animate) {
				mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
				mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
			} else {
				mProgressContainer.clearAnimation();
				mListContainer.clearAnimation();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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


	public void addDataToListAndRefresh(boolean clearData,List<T> datas) {

		// page ,
		if (mListView == null) {
			// some error...
			Log.i(TAG, "mListView is null issue");
			return;
		} else {

		}

		//isFromTop.
		if (clearData) {
			clearAdapter();
		}
		
		mAdapter.addObjectList(datas);
		
		checkIsNeedShowEmptyView();

		if (isVisible() || isResumed()) {
			mAdapter.notifyDataSetChanged();
		}

		Log.i(TAG, "load finish");
		loadAnimate();
		cancelLoading();

	}
	

	public static final int APPEAR = 2;
	protected void loadAnimate() {
		loadAnimate(-1);
	}
	protected void loadAnimate(int animate) {
		// TODO Auto-generated method stub
		LayoutAnimationController animateController=null;
		switch (animate) {
		case -1:
			 animateController = getAnimationController();
			break;
		case APPEAR:
			 animateController = getAppearAnimationController();
			break;
		default:
			break;
		}
		((ListView)mListView.getRefreshableView()).setLayoutAnimation(animateController);
		
	}
	
	 protected void cancelLoading() {
		// TODO Auto-generated method stub
		 if (mListView != null) {
			 mListView.onRefreshComplete();

				mListView.setMode(Mode.PULL_FROM_START);
		 }
	}

	
		
	private void clearAdapter() {
		// TODO Auto-generated method stub
		if (mAdapter != null) {
			mAdapter.clear(true);
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
