package com.thirdpart.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.Telephony.Sms.Conversations;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
				R.layout.common_add_item, this, true);
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
	private TouchImage contentView;
	String type = null;
	
	private void InitView(View view, AttributeSet attrs) {
		// TODO Auto-generated method stub
		String name = null;
		
		if (attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.DisplayViewStyle);
			 name = a.getString(R.styleable.DisplayViewStyle_customName);
			
			 type = a
						.getString(R.styleable.DisplayViewStyle_customType);

			a.recycle();
		}
		
		nameView = (TextView) view.findViewById(R.id.common_choose_item_title);
		contentView = (TouchImage) view
				.findViewById(R.id.common_add_item_content);
		if (name != null) {
			nameView.setText(name);
		}
		if ("file".equals(type)) {
			
			contentView.setImageResource(R.drawable.tianjia);
		}else {
			contentView.setImageResource(R.drawable.tianjiaqt);
		}
		
		

	}

	
	public void setNameAndContent(String name) {
		// TODO Auto-generated method stub
			nameView.setText(name);
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
		String tag;
	}
	List<AddItem> mItems = new ArrayList<AddItemView.AddItem>();
	
	
	protected void createItemListToUI(AddItem info,CreateItemViewListener createItemViewListener) {

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.common_add_item_container);
		

		View convertView = getChildViewByTag(viewGroup, info.tag); 
		 boolean  isNotExsit = (convertView == null);
		 convertView = oncreateItem(info, convertView,viewGroup);
		if (!isNotExsit) {
			return ;
		}
		mItems.add(info);
		viewGroup.addView(convertView);
		
		
	
	
	}

private View oncreateItem( AddItem addItem, View convertView, ViewGroup viewGroup) {
	
	// TODO Auto-generated method stub
	//if exsit just update , otherwise create it.
	
	if (convertView ==null) {
		//create
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		switch (type) {
		case "file":
			convertView = inflater.inflate(R.layout.issue_choose_file_item, viewGroup, false);	
						
			break;

		default:
			convertView = inflater.inflate(R.layout.issue_choose_foucs_member_item, viewGroup, false);	
			
			break;
		}
		
		convertView.findViewById(R.id.common_choose_item_title).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddItem addItem = (AddItem) v.getTag();
				chooseItem(addItem);
			}
		});
		convertView.findViewById(R.id.common_choose_item_content).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddItem addItem = (AddItem) v.getTag();
				updateItem(addItem);
			}
		});
	}else {
		
	}
	
	//bind tag
	convertView.findViewById(R.id.common_choose_item_title).setTag(addItem);
	convertView.findViewById(R.id.common_choose_item_content).setTag(addItem);
	return convertView;

	}

protected void updateItem(AddItem addItem) {
	// TODO Auto-generated method stub
	
}
protected void chooseItem(AddItem addItem) {
	// TODO Auto-generated method stub
	
}


protected void removeItem(AddItem addItem) {
	// TODO Auto-generated method stub
	mItems.remove(addItem);
}

public static interface CreateItemViewListener{
	View oncreateItem(int index,View convertView ,ViewGroup viewGroup);
	void deleteItem(int index);
}	
}
