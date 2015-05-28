package com.thirdpart.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseDetailActivity.CreateItemViewListener;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.tasktrackerpms.R;

public class AddItemView extends FrameLayout {
	public AddItemView(Context context){
		this(context ,null);
	}
	public AddItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View view = LayoutInflater.from(context).inflate(
				R.layout.common_choose_item, this, true);
		InitView(view, attrs);
	}
	
	
	
	
	public <T extends WidgetItemInfo > void showMenuItem(final List<T> items,final OnDismissListener onDismissListener) {}
	
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
		String content = null;
		boolean tinySepc = false;
		if (attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.DisplayViewStyle);
			 name = a.getString(R.styleable.DisplayViewStyle_customName);
			 content = a
						.getString(R.styleable.DisplayViewStyle_customContent);
			 tinySepc = a
						.getBoolean(R.styleable.DisplayViewStyle_tinySpec,false);

			a.recycle();
		}
		
		nameView = (TextView) view.findViewById(R.id.common_choose_item_title);
		contentView = (Button) view
				.findViewById(R.id.common_choose_item_content);
		if (name != null) {
			nameView.setText(name);
		}
		if (content != null) {
			contentView.setText(content);
		}
		
		if (tinySepc) {
			LinearLayout.LayoutParams param = (android.widget.LinearLayout.LayoutParams) contentView.getLayoutParams();
			param.rightMargin = UtilsUI.getPixByDPI(getContext(), 40);
			contentView.setLayoutParams(param);
		}

	}

	
	public void setNameAndContent(String name,String content) {
		// TODO Auto-generated method stub
			nameView.setText(name);
			contentView.setText(content);
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
	
	public static final class AddItem{
		
	}
	List<AddItem> mItems = new ArrayList<AddItemView.AddItem>();
	
	
	protected void createItemListToUI(AddItem info,CreateItemViewListener createItemViewListener) {

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.common_add_item_container);
		int size =mItems.size();
		int len=0;
		if (size == 0) {
	
			return;//no more
		}

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
			
			
		}
	
	}

public static interface CreateItemViewListener{
	View oncreateItem(int index,View convertView ,ViewGroup viewGroup);
	void deleteItem(int index);
}	
}
