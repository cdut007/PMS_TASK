package com.thirdpart.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.thirdpart.model.ConstValues.CategoryInfo.User;

public class LogInController  {

	
	private static LogInController mController;
	private Context context;
	
	
	public  boolean IsLogOn(){
		boolean hasLoginInfo=false;
		SharedPreferences user = context.getSharedPreferences(User.SharedName, 0);
		hasLoginInfo = user.getBoolean(User.logon, false);
		
		return hasLoginInfo;
	}

	private LogInController(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public static LogInController getInstance(Context context) {
		// TODO Auto-generated method stub
		synchronized (LogInController.class) {
		if (mController == null) {
			mController = new LogInController(context);
		}	
		}
		return mController;
	}

	
}