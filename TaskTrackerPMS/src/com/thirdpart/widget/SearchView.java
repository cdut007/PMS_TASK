package com.thirdpart.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.internal.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.ListPopupWindowAdapter;
import com.thirdpart.tasktrackerpms.R;

public class SearchView extends FrameLayout {
	public SearchView(Context context){
		this(context ,null);
	}
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View view = LayoutInflater.from(context).inflate(
				R.layout.common_search_bar_ui, this, true);
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
	
	public void performCancelClick() {
		// TODO Auto-generated method stub
		cancelBtn.performClick();
	}
	
	private View cancelBtn,searchBtn;

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
		
		mEditText =  (EditText) view.findViewById(R.id.search_edit);
		
		
		cancelBtn =  view.findViewById(R.id.cancel_btn);
		searchBtn = view
				.findViewById(R.id.search_btn);
	   cancelBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mEditText.setText("");
			if (onClickListener!=null) {
				onClickListener.onClick(v);
			}
			setVisibility(View.GONE);
		}
		
	});
	   
	   searchBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (onClickListener!=null) {
				onClickListener.onClick(v);
			}
		}
	});
	}

	
	EditText mEditText;
	
	public EditText getSearchEdit() {
		// TODO Auto-generated method stub
		return mEditText;
	}
	OnClickListener onClickListener;
	public void setOnSearchBtnClickListener(
			OnClickListener searchOnClickListener) {
		// TODO Auto-generated method stub
		onClickListener = searchOnClickListener;
	}
	public void resetData() {
		// TODO Auto-generated method stub
		mEditText.setText("");
	}
	
}
