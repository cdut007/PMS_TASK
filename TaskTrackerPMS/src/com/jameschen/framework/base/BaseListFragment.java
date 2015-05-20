package com.jameschen.framework.base;

import java.util.List;

import android.view.View;
import android.widget.TextView;

import com.jameschen.widget.MyListView;

public abstract class BaseListFragment<T> extends BaseFragment {

	public static final String LIST = "list";
	private List<T> mInfos;
	
	protected MyListView mListView;
	protected MyBaseAdapter<T> mAdapter;
	private View emptyView;
	
	protected void setEmptyView(View emptyView) {
		this.emptyView = emptyView;
	}

	public View getEmptyView() {
		return emptyView;
	}
	
	public void checkIsNeedShowEmptyView(){
		if (mAdapter.getCount() == 0) {
			if (emptyView != null) {
				emptyView.setVisibility(View.VISIBLE);
			}
		} else {
			if (emptyView != null) {
				emptyView.setVisibility(View.GONE);
			}
		}
	}
}
