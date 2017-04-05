package com.jameschen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends MListView {

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	public interface OnLoadTaskListener{
		void reloadTask();
	}
	
	private OnLoadTaskListener loadTaskListener;
	
	public void setLoadTaskListener(OnLoadTaskListener loadTaskListener){
		this.loadTaskListener = loadTaskListener;
	}
	
	public void reloadTask() {
		// TODO Auto-generated method stub
		if (loadTaskListener!=null) {
			loadTaskListener.reloadTask();
		}
	}

}
