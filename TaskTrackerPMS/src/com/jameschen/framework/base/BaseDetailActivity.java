package com.jameschen.framework.base;

import java.util.List;

import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.tasktrackerpms.R;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseDetailActivity extends BaseActivity{

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	
	
	private View getChildViewByTag(ViewGroup viewGroup,String tag) {
		// TODO Auto-generated method stub
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View childView = viewGroup.getChildAt(i);
			WidgetItemInfo sInfo = (WidgetItemInfo) childView.getTag();
				if (sInfo!=null&&tag.equals(sInfo.tag)) {
					return childView;
				}
			
		}
		return null;
	}
	
	protected <T extends WidgetItemInfo> void createItemListToUI(List<T> infos,int listId,CreateItemViewListener createItemViewListener,boolean itemLine) {

		ViewGroup viewGroup = (ViewGroup) findViewById(listId);
		int size =infos.size();
		int len=0;
		if (size == 0) {
	
			return;//no more
		}
	//	viewGroup.removeViews(1, viewGroup.getChildCount()-1);

		len =size ;
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for (int i = 0; i < len; i++) {
			View convertView = getChildViewByTag(viewGroup, infos.get(i).tag); 
			 boolean  isNotExsit = (convertView == null);
			 convertView = createItemViewListener.oncreateItem(i, convertView,viewGroup);
			if (!isNotExsit) {
				continue;
			}

			viewGroup.addView(convertView);
			
			 if (itemLine && i<len-1) {
				 
				 View line =(View) inflater.inflate(R.layout.item_line, viewGroup, false);
					viewGroup.addView(line);
			 }
		}
	
	}

public static interface CreateItemViewListener{
	View oncreateItem(int index,View convertView ,ViewGroup viewGroup);
}	
	
}
