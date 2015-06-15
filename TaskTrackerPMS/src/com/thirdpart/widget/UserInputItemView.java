package com.thirdpart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thirdpart.tasktrackerpms.R;

public class UserInputItemView extends FrameLayout {
	public UserInputItemView(Context context){
		this(context ,null);
	}
	public UserInputItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View view = LayoutInflater.from(context).inflate(
				R.layout.common_user_input_item, this, true);
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
		boolean scan  = false;
		if (attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.DisplayViewStyle);
			 name = a.getString(R.styleable.DisplayViewStyle_customName);
			 content = a
					.getString(R.styleable.DisplayViewStyle_customContent);
			 scan= a
						.getBoolean(R.styleable.DisplayViewStyle_scan,false);
			a.recycle();
		}
		
		nameView = (TextView) view.findViewById(R.id.common_user_input_item_title);
		contentView = (EditText) view
				.findViewById(R.id.common_user_input_item_content);
		if (name != null) {
			nameView.setText(name);
		}
		if (scan) {
			contentView.setEnabled(false);
			contentView.setHint(content);
		}
		contentView.setText(content);
	}

	public CharSequence getContent() {
		return contentView.getText();
	}
	

	public void setContent(String content,boolean scan) {
		this.contentView.setText(content);
		if (scan) {
			this.contentView.setEnabled(false);	
		}
	}
	public void setNameAndContent(String name,String content) {
		// TODO Auto-generated method stub
			nameView.setText(name);
			contentView.setText(content);
	}
	public void addTextChangedListener(TextWatcher textWatcher) {
		// TODO Auto-generated method stub
		contentView.addTextChangedListener(textWatcher);
	}
	
}
