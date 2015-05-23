package com.jameschen.framework.base;

import java.util.List;

import com.thirdpart.tasktrackerpms.R;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseDetailActivity extends BaseActivity{

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	protected <T> void createItemListToUI(List<T> infos,int listId,CreateItemViewListener<T> createItemViewListener,boolean itemLine) {

		ViewGroup viewGroup = (ViewGroup) findViewById(listId);
		int size =infos.size();
		int len=0;
		if (size == 0) {
	
			return;//no more
		}
		viewGroup.removeViews(1, viewGroup.getChildCount()-1);

		len =size ;
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for (int i = 0; i < len; i++) {
			View vGroup = createItemViewListener.oncreateItem(i, infos.get(i),inflater);
			viewGroup.addView(vGroup);
			
			 if (itemLine && i<len-1) {
				 
				 View line =(View) inflater.inflate(R.layout.item_line, viewGroup, false);
					viewGroup.addView(line);
			 }
		}
	
	}

static interface CreateItemViewListener<T>{
	View oncreateItem(int index, T item ,LayoutInflater layoutInflater);
}	
	
}
