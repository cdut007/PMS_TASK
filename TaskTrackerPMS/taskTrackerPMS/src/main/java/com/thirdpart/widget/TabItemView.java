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

public class TabItemView extends FrameLayout {
	public TabItemView(Context context){
		this(context ,null);
	}
	public TabItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View view = LayoutInflater.from(context).inflate(
				R.layout.tab_container, this, true);
		InitView(view, attrs);
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
		if ("task".equals(barmode)) {//
			List<WidgetItemInfo> mInfos = new ArrayList<WidgetItemInfo>();
			mInfos.add(new WidgetItemInfo(null, null, "焊口", 0, false));
			mInfos.add(new WidgetItemInfo(null, null, "支架", 1, false));
			attachContent(mInfos);
		} else if ("witness".equals(barmode)) {//1,7 序号，见证地点
			
		}else {//default
			
		}
	}
	private void attachContent(List<WidgetItemInfo> conteList) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.tab_item_container);
		
		int count=conteList.size();
		
		for (int i = 0; i <count; i++) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			TextView view = (TextView) inflater.inflate(R.layout.tab_item_text, viewGroup, false);	
			
			view.setText(conteList.get(i).content);
			view.setTag(conteList.get(i));
			final int index = i;
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mLastViewSelect!=null) {
						mLastViewSelect.setSelected(false);
					}
					if (oItemSelectedLisnter!=null) {
						WidgetItemInfo widgetItemInfo = (WidgetItemInfo) v.getTag();
						oItemSelectedLisnter.onTabSelected(index, widgetItemInfo);
					}
					v.setSelected(true);
					mLastViewSelect = v;
				}
			});
			viewGroup.addView(view);
		}
	  viewGroup.getChildAt(0).performClick();
	}
private View  mLastViewSelect;
private  onItemSelectedLisnter oItemSelectedLisnter;

public void setItemSelectedLisnter(onItemSelectedLisnter oItemSelectedLisnter) {
	this.oItemSelectedLisnter = oItemSelectedLisnter;
}
public static interface  onItemSelectedLisnter{
	void onTabSelected(int pos,WidgetItemInfo tag);
}
}
