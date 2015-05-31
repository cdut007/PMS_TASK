package com.thirdpart.tasktrackerpms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.model.LogInController;
import com.thirdpart.tasktrackerpms.R;

public class SlapshActivity extends BaseActivity {


	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		setContentView(R.layout.slapsh_ui);	
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Light_NoTitleBar);
		getSupportActionBar().hide();

		if (IsNeedGoLoginPage()) {

			go2Login();

		} else {

			go2main();
		}
		
		regPush();
	}

	private void regPush() {
		// TODO Auto-generated method stub
		getLogInController().registerPush();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}

	
	
	
	private boolean IsNeedGoLoginPage() {

		return !getLogInController().IsLogOn();
	}

	Handler handler = new Handler();
	long startTime = 0;

	private void go2main() {
		
		Intent intentEx = new Intent();
		intentEx.setClass(this, MainActivity.class);
		overridePendingTransition(R.anim.welcome_begin_anim,
				R.anim.welcome_end_anim);
		startActivity(intentEx);
		finish();
	}

	private void go2Login() {
		
		Intent intentEx = new Intent();
		intentEx.setClass(this, LoginActivity.class);
		overridePendingTransition(R.anim.welcome_begin_anim,
				R.anim.welcome_end_anim);
		startActivity(intentEx);
		finish();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(0, 0);
	}

}
