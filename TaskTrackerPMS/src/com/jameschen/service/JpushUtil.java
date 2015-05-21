package com.jameschen.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class JpushUtil {
	//public static final String KEY_APP_KEY = "JPUSH_APPKEY";

	// У��Tag Alias ֻ��������,Ӣ����ĸ������
	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	// ȡ��AppKey
//	public static String getAppKey(Context context) {
//		Bundle metaData = null;
//		String appKey = null;
//		try {
//			ApplicationInfo ai = context.getPackageManager()
//					.getApplicationInfo(context.getPackageName(),
//							PackageManager.GET_META_DATA);
//			if (null != ai)
//				metaData = ai.metaData;
//			if (null != metaData) {
//				appKey = metaData.getString(KEY_APP_KEY);
//				if ((null == appKey) || appKey.length() != 24) {
//					appKey = null;
//				}
//			}
//		} catch (NameNotFoundException e) {
//
//		}
//		return appKey;
//	}

	// ȡ�ð汾��
	public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}

}
