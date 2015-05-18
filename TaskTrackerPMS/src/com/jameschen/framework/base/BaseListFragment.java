package com.jameschen.framework.base;

import java.util.List;

import android.view.View;
import android.widget.TextView;

import com.jameschen.widget.MyListView;

public abstract class BaseListFragment<T> extends BaseFragment {

	public static final String LIST = "list";
	public List<T> mInfos;
	public final int numPerPage = 10;
	public String sort;
	private int currentPositon = 0;
	private int totalNum = 0;
	protected MyListView mListView;
	private MyBaseAdapter<T> mAdapter;
	private View emptyView;
	protected TextView locationView;
	
	
	
	protected TextView city,weatherTv;
	

}
