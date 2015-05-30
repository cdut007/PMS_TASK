package com.jameschen.framework.base;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jameschen.comm.utils.Log;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.tasktrackerpms.R;

public abstract class BaseActivity extends ActionBarActivity implements
		OnClickListener {
	public String TAG = "Task";

	private PMSManagerAPI mPMSManagerAPI;
	
	public PMSManagerAPI getPMSManager() {
		// TODO Auto-generated method stub
      if (mPMSManagerAPI == null) {
		mPMSManagerAPI = PMSManagerAPI.getInstance(this);
      }
      return mPMSManagerAPI;
	}
	
    private LogInController mLogInController;
	
	public LogInController getLogInController() {
		// TODO Auto-generated method stub
      if (mLogInController == null) {
    	  mLogInController = LogInController.getInstance(this);
      }
      return mLogInController;
	}
	
	protected abstract void initView();
	
	Configuration config; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TAG = getLocalClassName();
		Log.i(TAG, "oncreate");
		
		//fix the font scale size
		config=getResources().getConfiguration();
		Resources res = getResources();  
		config.fontScale=1;
		res.updateConfiguration(config,res.getDisplayMetrics() );  
		
		TopBarInit(getSupportActionBar());
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onresume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i(TAG, "onRestart");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "onStop");
	}
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);

		overridePendingTransition(R.anim.enter_right_anim,
				R.anim.exit_right_anim);

	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.enter_right_anim,
				R.anim.exit_right_anim);
	}

	
	
	//*****************************************//
		//*****************top bar init************//
		//*****************************************//
	public void TopBarInit(ActionBar actionBar) {

		actionBar.setBackgroundDrawable(new BitmapDrawable(getResources(),
				BitmapFactory.decodeResource(getResources(),
						R.drawable.top_title_bg)));
		actionBar.setCustomView(R.layout.top_bar_layout);
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		ViewGroup customView = (ViewGroup) actionBar.getCustomView();

		titleTv = (TextView) customView.findViewById(R.id.top_title);
		(backBtn=(ImageView) customView.findViewById(R.id.btn_back)).setOnClickListener(this);
		(nextBtn = (Button) customView.findViewById(R.id.btn_next)).setOnClickListener(this);
		(nextImgBtn = (ImageView) customView.findViewById(R.id.img_btn_next)).setOnClickListener(this);
		//default set right btn not visiable
		setTopBarRightBtnVisiable(View.GONE);
	}

	private TextView titleTv;
	private ImageView backBtn, nextImgBtn;
	private Button nextBtn;

	protected void setTopBarRightBtnListener(
			OnClickListener nextBtnOnClickListener) {
		setTopBarRightBtnListener("下一步", nextBtnOnClickListener);
	}

	protected void setTopBarRightBtnListener(int resourceId,
			OnClickListener nextBtnOnClickListener) {
		setTopBarRightBtnVisiable(View.GONE);
		nextImgBtn.setImageResource(resourceId);
		nextImgBtn.setVisibility(View.VISIBLE);
		nextImgBtn.setOnClickListener(nextBtnOnClickListener);
	}

	protected void setTopButtonRightText(String text) {
		nextBtn.setText(text);
	}

	protected void setTopButtonRightRes(int drawableId) {
		if (drawableId == 0) {
			nextBtn.setCompoundDrawables(null, null, null, null);
		} else {
			Drawable drawable = getResources().getDrawable(drawableId);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
			nextBtn.setCompoundDrawablePadding(0);
			nextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);

			nextBtn.setCompoundDrawables(null, null, drawable, null);
		}

	}

	protected void setTopBarRightBtnListener(String text,
			OnClickListener nextBtnOnClickListener) {
		if (!TextUtils.isEmpty(text)) {
			nextBtn.setText(text);
			if (text.equals("下一步")) {
				setTopBarRightBtnListener(R.drawable.next_step,
						nextBtnOnClickListener);
				return;
			}
		}

		setTopBarRightBtnVisiable(View.VISIBLE);
		nextBtn.setOnClickListener(nextBtnOnClickListener);
	}

	
	protected void setTopBarAllBtnVisiable(int visibility) {
		setTopBarLeftBtnVisiable(visibility);
		setTopBarRightBtnVisiable(visibility);
	}

	
	protected void setTopBarLeftBtnVisiable(int visibility) {
		backBtn.setVisibility(visibility);
	}

	protected void setTopBarRightBtnVisiable(int visibility) {
		nextBtn.setVisibility(visibility);
		if (visibility == View.GONE) {
			nextImgBtn.setVisibility(visibility);
		}
	}

	protected void setTopBarBtnVisiable(int visibility) {
		nextBtn.setVisibility(visibility);
		setTopBarRightBtnVisiable(visibility);
	}

	protected void setTitle(String title) {
		titleTv.setBackgroundDrawable(null);
		titleTv.setText(title);
	}

	public void changeTitle(String content) {
		// TODO Auto-generated method stub
		setTitle(content);
	}
	
	//*****************************************//
		//*****************top bar end************//
		//*****************************************//
	
	protected boolean resultOk = false;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		 case R.id.btn_back: {
		 if (resultOk) {
		 setResult(RESULT_OK);
		 }
		 onBackPressed();
		 }
		 break;

		default:
			break;
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.exit_left_anim, R.anim.enter_left_anim);

	}

	Object loker = new Object();

	protected ProgressDialog mAlertDialog;

	protected ProgressDialog showProgressDialog(int pTitelResID, String pMessage) {
		String title = getResources().getString(pTitelResID);
		return showProgressDialog(title, pMessage, null);
	}

	public synchronized ProgressDialog createProgressDialog(String pTitle,
			String pMessage,
			DialogInterface.OnCancelListener pCancelClickListener) {

		ProgressDialog mDialog = ProgressDialog.show(this, pTitle, pMessage,
				true, true);
		mDialog.setCancelable(true);
		mDialog.setOnCancelListener(pCancelClickListener);

		return (ProgressDialog) mDialog;
	}

	public synchronized ProgressDialog showProgressDialog(String pTitle,
			String pMessage,
			DialogInterface.OnCancelListener pCancelClickListener) {
		if (mAlertDialog != null) {
			mAlertDialog.setTitle(pTitle);
			mAlertDialog.setMessage(pMessage);
			return mAlertDialog;
		}
		mAlertDialog = ProgressDialog.show(this, pTitle, pMessage, true, true);
		mAlertDialog.setCancelable(true);
		mAlertDialog.setOnCancelListener(pCancelClickListener);

		return (ProgressDialog) mAlertDialog;
	}

	public synchronized void cancelProgressDialog() {
		// TODO Auto-generated method stub
		if (mAlertDialog != null) {
			if (mAlertDialog.isShowing()) {
				mAlertDialog.cancel();
			}
			mAlertDialog = null;
		}
	}

	private Toast mWarningToast, mInfoToast;

	public void showToast(String msg) {
		if (msg == null) {
			return;
		}
		if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
					.show();
		} else {
			if (mInfoToast == null) {
				mInfoToast = Toast.makeText(getApplicationContext(), "",
						Toast.LENGTH_SHORT);
			}
			mInfoToast.cancel();
			mInfoToast.setText(msg);
			mInfoToast.show();
		}
	}

	// close input method
	public void closeInputMethod() {
		// hide input method and emotion mothod
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// switch input status
		if (inputMethodManager.isActive()) {
			try {
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {
				inputMethodManager.toggleSoftInput(
						InputMethodManager.SHOW_IMPLICIT,
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
       Log.i(TAG, "onDestroy");
	}

}
