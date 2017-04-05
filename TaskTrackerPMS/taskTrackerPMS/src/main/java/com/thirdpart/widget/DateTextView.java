package com.thirdpart.widget;

import java.sql.Date;
import java.util.Calendar;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thirdpart.tasktrackerpms.R;

public class DateTextView extends TextView {
	public DateTextView(Context context) {
		this(context, null);
	}

	public DateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		InitView(attrs);
	}

	@Override
	public void setBackgroundResource(int resid) {
		// TODO Auto-generated method stub
		super.setBackgroundResource(resid);

	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	private TextView nameView, contentView;

	private void InitView(AttributeSet attrs) {
		// TODO Auto-generated method stub
		String tag = (String) getTag();
		if (tag != null) {
			  Calendar calendar = Calendar.getInstance();
		       
			if (tag.equals(getResources().getString(R.string.yesterday))) {
				 java.util.Date sDate = calendar.getTime();
			        sDate.setTime(sDate.getTime() - 24*3600*1000);
			        calendar.setTime(sDate);

				       int day=  calendar.get(Calendar.DAY_OF_MONTH) ;
				setText((day)+"日");
			} else if (tag.equals(getResources().getString(R.string.today))) {
				setText("今日");
			} else if (tag.equals(getResources().getString(R.string.tommorw))) {
				 java.util.Date sDate = calendar.getTime();
			        sDate.setTime(sDate.getTime() + 24*3600*1000);
			        calendar.setTime(sDate);

				       int day=  calendar.get(Calendar.DAY_OF_MONTH) ;
				setText( day+"日");
			}
		}

	}

	public void setNameAndContent(String name, String content) {
		// TODO Auto-generated method stub
		nameView.setText(name);
		setContent(content);
	}

	public void setContent(String describe) {
		// TODO Auto-generated method stub
		contentView.setText(describe);
	}

}
