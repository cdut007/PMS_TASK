package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.LogUtils;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.tasktrackerpms.R;

public class SettingActivity extends BaseActivity implements OnClickListener {

	private static final int UPDATE_PASSWORD = 0x11;
	private View modifyPassword,aboutUs;
	private View version;
	private View logout;
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.system_setting);	

		(logout =findViewById(R.id.btnExit)).setOnClickListener(this);
		findViewById(R.id.feedback_log).setOnClickListener(this);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setTopBarRightBtnVisiable(View.GONE);
		setTitle("设置");
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {


		case R.id.btnExit:
		{
			getLogInController().quit(this);
		}
		break;
		case R.id.feedback_log:
		{
			feedbackLog();
		}
		break;
		
		default:
			break;
		}
	}
	private void feedbackLog() {
		// TODO Auto-generated method stub
		LogUtils.sendLog(this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	switch (requestCode) {
	case UPDATE_PASSWORD:
		if (resultCode== RESULT_OK) {
		
		}
		break;

	default:
		break;
	}
	}
	
	
}
