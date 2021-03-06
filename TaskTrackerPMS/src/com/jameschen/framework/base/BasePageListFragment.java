package com.jameschen.framework.base;

import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jameschen.comm.utils.Log;
import com.jameschen.framework.base.BaseListFragment.onSearchListener;
import com.jameschen.widget.MyListView;
import com.thirdpart.model.entity.base.PageList;
import com.thirdpart.tasktrackerpms.R;

public abstract class BasePageListFragment<T, PageListType extends PageList<T>> extends
		BaseListFragment<T> {

	@Override
	public void showToast(String content) {
		cancelLoading(false);
		super.showToast(content);

	}

	public void clearResource() {
		if (mAdapter != null) {
			mAdapter.recycle();
			mAdapter = null;
		}
	}

	public void releaseResource() {
		// TODO Auto-generated method stub
		clearResource();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mAdapter != null && isVisible()) {
			mAdapter.onResume();
		}

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			if (mAdapter != null) {
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (mAdapter != null) {
			mAdapter.onPause();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

		// clearResource();
	}

	@Override
	public void onDestroy() {

		// if (banner != null) {
		// banner.onDestroy();
		// }
		// banner = null;
		if (clear) {
			releaseResource();
		}

		super.onDestroy();
	}

	protected boolean clear = true;

	public void setErrorPage(int errorCode) {
	}

	@Override
	protected void resetSearchData() {
		// TODO Auto-generated method stub
		super.resetSearchData();
		if (searchPageListInfo!=null) {
			if (searchPageListInfo.getDatas()!=null) {
				searchPageListInfo.getDatas().clear();
			}
			searchPageListInfo = null;
		}
	}
	
	protected void clearAdapter() {
		// TODO Auto-generated method stub
		if (mAdapter != null) {
			mAdapter.clear(true);
			pageListInfo=null;
			searchPageListInfo=null;
			checkIsNeedShowEmptyView();
		}
	}

	private PageListType pageListInfo,searchPageListInfo;
	
	protected  int pageSize = 10,defaultBeginPageNum=1;
	
	protected int getCurrentPage() {
		// TODO Auto-generated method stub
		PageListType	mListInfo = getCurrPageListInfo();
		
		if (mListInfo == null) {
			return defaultBeginPageNum;
		}
		return mListInfo.getCurrentPage();
	}
	
	protected boolean isStartPage() {
		
		PageListType	mListInfo = getCurrPageListInfo();
		
		
		if (mListInfo == null) {
			return true;
		}
		return mListInfo.getCurrentPage() == mListInfo.getStartPage();
	}
	
	private PageListType getCurrPageListInfo() {
		PageListType	mListInfo = pageListInfo;
		if (mAdapter.isSearchMode()) {		
			mListInfo = searchPageListInfo;
		}
		return mListInfo;
	}

	protected boolean isEndPage() {
		PageListType	mListInfo = getCurrPageListInfo();
		if (mListInfo == null) {
			return false;
		}
		return mListInfo.getCurrentPage() == mListInfo.getEndPage()||
				mListInfo.getCurrentPage() == mListInfo.totalpage
				;
	}
	
	@Override
	protected ListView bindListView(View root, MyBaseAdapter<T> adapter) {
		// TODO Auto-generated method stub
		ListView listview = super.bindListView(root, adapter);
		mListView.setMode(Mode.BOTH);//for page
		
		//add search
		//bind search view..
				if (canSearch) {
					bindSearchController(root,new onSearchListener(){

						@Override
						public void beginSearch(String keyword) {
							// TODO Auto-generated method stub
							callNextPage(pageSize, getCurrentPage());
						}

						@Override
						public void backToNormal() {
							// TODO Auto-generated method stub
							
						}
						
					});
				}
		return listview;
	}
	
	@Override
	protected void doFreshFromBottom(MyListView mListView) {
		// TODO Auto-generated method stub
		super.doFreshFromBottom(mListView);
		if (isEndPage()) {
			Log.i(TAG, "last page, no need refresh");
			cancelLoading(true);
			return;
		}
		callNextPage(pageSize,getCurrentPage()+1);
	}
	@Override
	protected void doFreshFromTop(MyListView mListView) {
		// TODO Auto-generated method stub
		super.doFreshFromTop(mListView);
		mListView.setMode(Mode.BOTH);//for page
		callNextPage(pageSize,defaultBeginPageNum);
		
	}
	
	protected  void callNextPage(int pagesize,int pageNum){
		if (mAdapter.isSearchMode()) {
			getPMSManager().setKeyword(mAdapter.searchStr);
		}
	};
	
	public void addDataToListAndRefresh(PageListType mPageList) {

		// page ,
		if (mListView == null) {
			// some error...
			Log.i(TAG, "mListView is null issue");
			return;
		} else {

		}

		List<T> datas = mPageList.getDatas();
		//isFromTop.
		boolean isFromTop = mPageList.getCurrentPage()==defaultBeginPageNum;
		if (isFromTop) {
			clearAdapter();
		}
		
		mAdapter.addObjectList(datas);
		PageListType currentpageListInfo = mPageList;
		if (mAdapter.isSearchMode()) {
			searchPageListInfo = currentpageListInfo;
		}else {
			pageListInfo = currentpageListInfo;
		}
		
		
		checkIsNeedShowEmptyView();

		if (isVisible() || isResumed()) {
			mAdapter.notifyDataSetChanged();
		}

		if (mPageList.getCurrentPage() == mPageList.getEndPage()||
				currentpageListInfo.getCurrentPage() == currentpageListInfo.totalpage	
				) {
			Log.i(TAG, "load finish");
			cancelLoading(true);
		} else {
			cancelLoading(mAdapter.getCount() == 0);
		}
		
		if (isFromTop) {
			loadAnimate();	
		}
	
	}

	protected void startLoading() {
		// TODO Auto-generated method stub
		if (mListView != null) {
		}
	}
	
	protected void cancelLoading(boolean noMoreData) {
		// TODO Auto-generated method stub
		if (mListView != null) {
			if (noMoreData) {
				mListView.onRefreshComplete();

				mListView.setMode(Mode.PULL_FROM_START);
			}else {
				mListView.onRefreshComplete();	

				mListView.setMode(Mode.BOTH);
			}
			
		}else {
			Log.i(TAG, "listView is null:"+noMoreData);
		}
	}


	
	protected abstract class PageUINetworkHandler<ListType extends PageListType> extends UINetworkHandler<PageListType>{

		public PageUINetworkHandler(BaseActivity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		public abstract void startPage();

		public abstract void finishPage();
		
		public abstract void callbackPageFailure(int statusCode, Header[] headers,
				String response);

		public abstract void callbackPageSuccess(int statusCode, Header[] headers, PageListType response);
		
        
		@Override
		public void start() {
			if (isStartPage()) {
				Log.i(TAG, "start page is refreshing~~"+mListView.isRefreshing());
				if (!mListView.isRefreshing()) {
					if (mAdapter!=null&&mAdapter.getCount() <=0) {
						setListShown(false);
					}
					
				}
			}
			
			startPage();
		}
		
		
		@Override
		public void callbackFailure(int statusCode, Header[] headers,
				String response) {
			// TODO Auto-generated method stub
				showToast(response);
				cancelLoading(false);
				callbackPageFailure(statusCode, headers, response);
		}

		@Override
		public void callbackSuccess(int statusCode, Header[] headers,
				PageListType response) {
			// TODO Auto-generated method stub
			callbackPageSuccess(statusCode, headers, response);
			addDataToListAndRefresh(response);
		}


		@Override
		public void finish() {
			setListShown(true);
			finishPage();
		}

    	
    
	}
	
}
