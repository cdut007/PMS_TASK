package com.jameschen.framework.base;

import java.util.List;

import android.view.View;
import android.widget.TextView;

import com.jameschen.widget.MyListView;

public abstract class BaseListFragment<T> extends BaseFragment {

	public static final String LIST = "list";
	public List<T> mInfos;
	
	protected MyListView mListView;
	private MyBaseAdapter<T> mAdapter;
	private View emptyView;
	

}
