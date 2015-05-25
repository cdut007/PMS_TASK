package com.thirdpart.model;

import java.lang.reflect.Type;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.AES4all;
import com.thirdpart.model.ConstValues.CategoryInfo.User;
import com.thirdpart.model.entity.UserInfo;
import com.thirdpart.tasktrackerpms.ui.LoginActivity;
import com.thirdpart.tasktrackerpms.ui.MainActivity;
import com.thirdpart.tasktrackerpms.ui.SlapshActivity;

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

	public void quit() {
		SharedPreferences user = context.getSharedPreferences(User.SharedName,
				0);
		user.edit().putBoolean(User.logon, false).commit();
		user.edit().remove(User.password).commit();
		//go to login page
		Intent i = new Intent(context, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	private UserInfo myInfo;
	
	public UserInfo  getInfo(){
		if (myInfo == null) {
			readAccountDataFromPreference();
		}
		if (myInfo == null) {//maybe data broken ,just quit			
		 quit();
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
							new String(
									AES4all.encryptAESECB(account.getBytes())))
					.commit();
			if (!TextUtils.isEmpty(pswd)) {
				user.edit()
						.putString(
								User.password,
								new String(AES4all.encryptAESECB(pswd
										.getBytes())))
						.putBoolean(User.logon, true).commit();
			}
			user.edit()
			.putString(
					User.userinfo,
					new String(
							AES4all.encryptAESECB(info.getBytes())))
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
			accounts[0] = new String(AES4all.decryptAESECB(name.getBytes()));
			if (password != null) {
				accounts[1] = new String(AES4all.decryptAESECB(password
						.getBytes()));
			}
			String decryptInfo =  new String(AES4all.decryptAESECB(Info
					.getBytes()));
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