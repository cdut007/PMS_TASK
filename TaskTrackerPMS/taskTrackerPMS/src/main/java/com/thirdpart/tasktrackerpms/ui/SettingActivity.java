package com.thirdpart.tasktrackerpms.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jameschen.comm.utils.LogUtils;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.TouchImage;

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
		version =findViewById(R.id.version);
		((TextView)version).setText("Version:"+getVersion(getApplicationContext()));
		TouchImage.buttonEffect(logout);
		findViewById(R.id.feedback_log).setOnClickListener(this);
		
	}
	public  String getVersion(Context context)//获取版本号
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "null";
		}
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
