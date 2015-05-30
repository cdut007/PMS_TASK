package com.thirdpart.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class SorcllTextView extends TextView{

	public SorcllTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
	}
	
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if(focused)
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		if(hasWindowFocus)
		super.onWindowFocusChanged(hasWindowFocus);
	}
	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}
}
