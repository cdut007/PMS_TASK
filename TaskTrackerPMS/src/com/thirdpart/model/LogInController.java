package com.thirdpart.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.AES4all;
import com.jameschen.comm.utils.CrypToCfg;
import com.thirdpart.model.ConstValues.CategoryInfo.User;
import com.thirdpart.model.entity.UserInfo;
import com.thirdpart.tasktrackerpms.ui.LoginActivity;

public class LogInController {

	private static LogInController mController;
	private Context context;

	public boolean IsLogOn() {
		boolean hasLoginInfo = false;
		SharedPreferences user = context.getSharedPreferences(User.SharedName,
				0);
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

	public void quit(Context context) {
		SharedPreferences user = context.getSharedPreferences(User.SharedName,
				0);
		user.edit().putBoolean(User.logon, false).commit();
		user.edit().remove(User.password).commit();
		//go to login page
		Intent i = new Intent(context, LoginActivity.class);
		i.setFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		
	}

	private UserInfo myInfo;
	
	public UserInfo  getInfo(){
		if (myInfo == null) {
			readAccountDataFromPreference();
		}
		if (myInfo == null) {//maybe data broken ,just quit			
		 quit(context);
		 myInfo = new UserInfo();//create an empty
		}
		return myInfo;
	}
	
	public void saveUserToPreference(Context context, String account,
			String pswd, UserInfo userInfo) {
		// TODO Auto-generated method stub
		SharedPreferences user = context.getSharedPreferences(User.SharedName,
				0);
		Gson gson = new Gson();
		String info = gson.toJson(userInfo);
		try {
			user.edit()
					.putString(
							User.account,
							 CrypToCfg.encrypt(account))
					.commit();
			if (!TextUtils.isEmpty(pswd)) {
				user.edit()
						.putString(
								User.password, CrypToCfg.encrypt(pswd))
						.putBoolean(User.logon, true).commit();
			}
			user.edit()
			.putString(
					User.userinfo, CrypToCfg.encrypt(info))
			.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String[] readAccountDataFromPreference() {
		// TODO Auto-generated method stub
		// 如果文件不存在，则进行创建
		SharedPreferences userPref = context.getSharedPreferences(
				User.SharedName, 0);
		// 取出保存的NAME，取出改字段名的值，不存在则创建默认为空
		String name = userPref.getString(User.account, null); // 取出保存的 NAME
		String password = userPref.getString(User.password, null); // 取出保存的 uid
		String Info = userPref.getString(User.userinfo, null);
		String[] accounts = new String[2];
		if (name == null) {
			return accounts;
		}
		try {
			accounts[0] =  CrypToCfg.decrypt(name);
			if (password != null) {
				accounts[1] =  CrypToCfg.decrypt(password);
			}
			String decryptInfo =  CrypToCfg.decrypt(Info);
			Gson gson = new Gson();
			myInfo = gson.fromJson(decryptInfo,new TypeToken<UserInfo>() {
			}.getType() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

}