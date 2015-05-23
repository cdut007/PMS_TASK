package com.jameschen.framework.base;

import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jameschen.comm.utils.Log;
import com.jameschen.widget.MyListView;
import com.thirdpart.model.entity.base.PageList;

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

	protected void clearAdapter() {
		// TODO Auto-generated method stub
		if (mAdapter != null) {
			mAdapter.clear(true);
			pageListInfo=null;
			checkIsNeedShowEmptyView();
		}
	}

	private PageListType  pageListInfo;
	
	protected  int pageNum = 10;
	
	protected int getCurrentPage() {
		// TODO Auto-generated method stub
		if (pageListInfo == null) {
			return 0;
		}
		return pageListInfo.getCurrentPage();
	}
	
	@Override
	protected ListView bindListView(View root, MyBaseAdapter<T> adapter) {
		// TODO Auto-generated method stub
		return super.bindListView(root, adapter);
	}
	
	@Override
	protected void doFreshFromBottom(MyListView mListView) {
		// TODO Auto-generated method stub
		super.doFreshFromBottom(mListView);
		callNextPage(getCurrentPage()+1, pageNum);
	}
	@Override
	protected void doFreshFromTop(MyListView mListView) {
		// TODO Auto-generated method stub
		super.doFreshFromTop(mListView);
		callNextPage(0, pageNum);
		
	}
	
	protected abstract void callNextPage(int pagesize,int pageNum);
	
	public void addDataToListAndRefresh(PageListType mPageList) {

		// page ,
		if (mListView == null) {
			// some error...
			Log.i(TAG, "mListView is null issue");
			return;
		} else {

		}

		List<T> datas = mPageList.getDatas();
		
		//isFromTop 
		if (mPageList.getCurrentPage()==0) {
			clearAdapter();
		}
		
		mAdapter.addObjectList(datas);
		this.pageListInfo = mPageList;
		
		checkIsNeedShowEmptyView();

		if (isVisible() || isResumed()) {
			mAdapter.notifyDataSetChanged();
		}

		if (mPageList.getCurrentPage() == mPageList.getEndPage()) {
			cancelLoading(true);
		} else {
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
			mListView.onRefreshComplete();
		}
	}


	
	protected abstract class PageUINetworkHandler extends UINetworkHandler<PageListType>{

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
			startPage();
		}
		
		
		@Override
		public void callbackFailure(int statusCode, Header[] headers,
				String response) {
			// TODO Auto-generated method stub
				showToast(response);
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
			finishPage();
		}

    	
    
	}
	
}
