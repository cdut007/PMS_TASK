package com.thirdpart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jameschen.comm.utils.Util;
import com.jameschen.comm.utils.UtilsUI;
import com.thirdpart.tasktrackerpms.R;

public class SearchView extends FrameLayout {
	public SearchView(Context context) {
		this(context, null);
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

	private View cancelBtn, searchBtn;

	private void InitView(View view, AttributeSet attrs) {
		// TODO Auto-generated method stub
		String name = null;
		String content = null;
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.DisplayViewStyle);
			name = a.getString(R.styleable.DisplayViewStyle_customName);
			content = a.getString(R.styleable.DisplayViewStyle_customContent);

			a.recycle();
		}

		mEditText = (EditText) view.findViewById(R.id.search_edit);

		mEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND
								|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
							// do something;
							searchBtn.performClick();

							// hide input method and emotion mothod
							InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
							// switch input status
							if (inputMethodManager.isActive()) {
								try {
									inputMethodManager.hideSoftInputFromWindow(mEditText
											.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
								} catch (Exception e) {
									inputMethodManager.toggleSoftInput(
											InputMethodManager.SHOW_IMPLICIT,
											InputMethodManager.HIDE_NOT_ALWAYS);
								}
							}
						
							return true;
						}
						return false;
					}
				});

		cancelBtn = view.findViewById(R.id.cancel_btn);
		searchBtn = view.findViewById(R.id.search_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText.setText("");
				if (onClickListener != null) {
					onClickListener.onClick(v);
				}
				setVisibility(View.GONE);
			}

		});

		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onClickListener != null) {
					onClickListener.onClick(v);
				}
			}
		});
	}

	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (changedView == this && visibility == VISIBLE) {
			mEditText.setFocusable(true);
			mEditText.setFocusableInTouchMode(true);
			mEditText.requestFocus();
			InputMethodManager inputManager = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(mEditText, 0);
		
			
		}
	};

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
