package com.thirdpart.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jameschen.comm.utils.AES4all;
import com.thirdpart.model.ConstValues.CategoryInfo.User;

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
	}

	public void saveUserToPreference(Context context, String account,
			String pswd) {
		// TODO Auto-generated method stub
		SharedPreferences user = context.getSharedPreferences(User.SharedName,
				0);
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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

}