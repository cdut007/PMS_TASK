package com.thirdpart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thirdpart.tasktrackerpms.R;

public class EditItemView extends FrameLayout {
	public EditItemView(Context context){
		this(context ,null);
	}
	public EditItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View view = LayoutInflater.from(context).inflate(
				R.layout.common_edit_item, this, true);
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
	private TextView contentView;

	private void InitView(View view, AttributeSet attrs) {
		// TODO Auto-generated method stub
		String name = null;
		String content = null;
		if (attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.DisplayViewStyle);
			 name = a.getString(R.styleable.DisplayViewStyle_customName);
			 content = a
					.getString(R.styleable.DisplayViewStyle_customContent);

			a.recycle();
		}
		
		nameView = (TextView) view.findViewById(R.id.common_edit_item_title);
		contentView = (EditText) view
				.findViewById(R.id.common_edit_item_content);
		if (name != null) {
			nameView.setText(name);
		}
		if (content != null) {
			contentView.setText(content);
		}

	}

	
	public void setNameAndContent(String name,String content) {
		// TODO Auto-generated method stub
			nameView.setText(name);
			contentView.setText(content);
	}
	
}