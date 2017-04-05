package com.jameschen.comm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

public class UtilsTime {

	/**
	 * 一分钟的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MINUTE = 60 * 1000;

	/**
	 * 一小时的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;

	/**
	 * 一天的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_DAY = 24 * ONE_HOUR;

	/**
	 * 一月的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MONTH = 30 * ONE_DAY;

	/**
	 * 一年的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_YEAR = 12 * ONE_MONTH;

	public static String convertTimeFormartByMillsec(long timePassed) {
		String updateAtValue = null;
		long timeIntoFormat;
		if (timePassed < 0) {
			updateAtValue = "1分钟前";
		} else if (timePassed < ONE_MINUTE) {
			updateAtValue = "刚刚更新";
		} else if (timePassed < ONE_HOUR) {
			timeIntoFormat = timePassed / ONE_MINUTE;
			updateAtValue = timeIntoFormat + "分钟前";
		} else if (timePassed < ONE_DAY) {
			timeIntoFormat = timePassed / ONE_HOUR;
			updateAtValue = timeIntoFormat + "小时前";
		} else if (timePassed < ONE_MONTH) {
			timeIntoFormat = timePassed / ONE_DAY;
			updateAtValue = timeIntoFormat + "天前";
		} else if (timePassed < ONE_YEAR) {
			timeIntoFormat = timePassed / ONE_MONTH;
			updateAtValue = timeIntoFormat + "个月前";
		} else {
			timeIntoFormat = timePassed / ONE_YEAR;
			updateAtValue = timeIntoFormat + "年前";
		}
		return updateAtValue;
	}

	public static long getLastRefreshTime(Context context,
			String string) {
		SharedPreferences dePreferences = context.getSharedPreferences("refresh_time", 0);
		return dePreferences.getLong(string, 0);
	}

	public static void saveRefreshTime(Context context,
			long currentTimeMillis, String string) {
		SharedPreferences dePreferences = context.getSharedPreferences("refresh_time", 0);
		dePreferences.edit().putLong(string, currentTimeMillis).commit();
	}
}
