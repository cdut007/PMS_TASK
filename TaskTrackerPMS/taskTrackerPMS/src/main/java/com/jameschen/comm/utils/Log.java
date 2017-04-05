package com.jameschen.comm.utils;

import org.apache.log4j.Logger;

import android.content.Context;


public class Log {
	private static boolean isDebug = false;

	private  static Logger logger=null;
	
	public static void init(Context context) {
		// TODO Auto-generated method stub

	}
	
	private static void recordLog(String tag,String msg) {
		// TODO Auto-generated method stub
		if (logger == null) {
			
			logger = Logger.getLogger(Log.class);
		}
		logger.info("["+tag+"]:"+msg);
		
	}
	
	public static void i(String tag, String info) {
		if (isDebug) {
			android.util.Log.i(tag, info);
		}
		recordLog(tag, info);
	}

	public static void w(String tag, String info) {
		if (isDebug) {
			android.util.Log.w(tag, info);
		}
		recordLog(tag, info);
	}

	public static void e(String tag, String info) {
		if (isDebug) {
			android.util.Log.e(tag, info);
		}
		recordLog(tag, info);
	}

	public static void d(String tag, String info) {
		if (isDebug) {
			android.util.Log.d(tag, info);
		}
		recordLog(tag, info);
	}

	public static void v(String tag, String info) {
		if (isDebug) {
			android.util.Log.v(tag, info);
		}
		recordLog(tag, info);
	}

}
