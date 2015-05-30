package com.thirdpart.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.jameschen.comm.utils.StringUtil;
import com.jameschen.comm.utils.UtilsUI;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.tasktrackerpms.R;

public class IndicatorView extends FrameLayout {
	public IndicatorView(Context context){
		this(context ,null);
	}
	public IndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View view = LayoutInflater.from(context).inflate(
				R.layout.issue_indicator_bar, this, true);
		InitView(view, attrs);
	}
	
	
	
	
	public <T extends WidgetItemInfo > void showMenuItem(final List<T> items,final OnDismissListener onDismissListener) {
		// TODO Auto-generated method stub
		final PopupWindowUtil<T> mPopupWindow = new PopupWindowUtil<T>();
		mPopupWindow.showActionWindow(this, getContext(), items);
		mPopupWindow.setItemOnClickListener(new PopupWindowUtil.OnItemClickListener() {

			@Override
			public void onItemClick(int index) {
					T item = items.get(index);
					setTag(item);
					onDismissListener.onDismiss();
			}
		});
	}
	
	@Override
	public void setBackgroundResource(int resid) {
		// TODO Auto-generated method stub
		super.setBackgroundResource(resid);
		getChildAt(0).setBackgroundResource(resid);
	
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}
	
	private TextView nameView;
	private Button contentView;

	private void InitView(View view, AttributeSet attrs) {
		// TODO Auto-generated method stub
		String name = null;
		if (attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.DisplayViewStyle);
			 name = a.getString(R.styleable.DisplayViewStyle_customName);
			
			a.recycle();
		}
	
		checkMode(name);
		

	}

	private void checkMode(String barmode) {
		// TODO Auto-generated method stub
		if ("delivery".equals(barmode)) {//2,4,1 焊口号／支架号  图纸号 分配
			List<WidgetItemInfo> mInfos = new ArrayList<WidgetItemInfo>();
			mInfos.add(new WidgetItemInfo(null, null, "焊口号", 2, false));
			mInfos.add(new WidgetItemInfo(null, null, "图纸号", 4, false));
			mInfos.add(new WidgetItemInfo(null, null, "分配", 1, false));
			attachContent(mInfos);
			hiddenChild(3);
		} else if ("witness".equals(barmode)) {//1,7 序号，见证地点
			List<WidgetItemInfo> mInfos = new ArrayList<WidgetItemInfo>();
			mInfos.add(new WidgetItemInfo(null, null, "序号", 1, false));
			mInfos.add(new WidgetItemInfo(null, null, "见证地点", 7, false));
			attachContent(mInfos);
			hiddenChild(2);
		}else {//default
			
		}
	}
	private void attachContent(List<WidgetItemInfo> conteList) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.indicator_container);
		
		int count=conteList.size();
		
		for (int i = 0; i <count; i++) {
			TextView view = (TextView) viewGroup.getChildAt(i);
			LinearLayout.LayoutParams param = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
			param.weight=conteList.get(i).type;
			view.setText(conteList.get(i).content);
			view.setLayoutParams(param);
		}
	
	}


	private void hiddenChild(int start) {
		// TODO Auto-generated method stub
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.indicator_container);
		
		int count=viewGroup.getChildCount()-start;

		for (int i = 0; i <count; i++) {
			View view = viewGroup.getChildAt(i+start);
			LinearLayout.LayoutParams param = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
			param.weight=0;
			param.width=0;
			view.setLayoutParams(param);
			view.setVisibility(View.GONE);
		}

	}
}
