package com.jameschen.comm.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;

import com.google.gson.JsonNull;

public class Util {

	public static <T> Type whatsMyGenericType(Object object) {
		return (Class<T>) ((ParameterizedType) object.getClass() .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public static boolean isActivyFinish(Context context) {
		// TODO Auto-generated method stub
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			if (activity.isFinishing() ) {
				return true;
			}
			if (VERSION.SDK_INT>=VERSION_CODES.KITKAT&&activity.isDestroyed()) {
				return true;
			}
		}
		return false;
	}
	public static boolean isJsonNull(String content){
		if ("null".equals(content)||TextUtils.isEmpty(content)) {
			return true;
		}
		return false;
	}
}
