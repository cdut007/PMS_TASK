package com.jameschen.comm.utils;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class UtilsUI {
	private static int sreenWidth = 0;
	private static int sreenHeight = 0;
	public static int getWidth(Application application) {
		if (sreenWidth<=0) {
			readSreenInfo(application);
		}
		return sreenWidth;
	}
	
	
	public static int getHeight(Application application) {
		if (sreenHeight<= 0) {
			readSreenInfo(application);
		}
		return sreenHeight;
	}


	public static void readSreenInfo(Application context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		

		DisplayMetrics outMetrics = new DisplayMetrics();
		 wm.getDefaultDisplay().getMetrics(outMetrics );
		 sreenHeight =outMetrics.heightPixels;// 屏幕高度		
		sreenWidth = outMetrics.widthPixels;// 屏幕宽度
	}
	public static void showDialogAlert(Context ctx, String title, String body) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setMessage(body);
		Dialog dialogDetails = builder.create();
		dialogDetails.setCanceledOnTouchOutside(true);
		dialogDetails.show();
	}

	public static int getPixByDPI(Context context, int value) {
		int mPix = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, context.getResources()
						.getDisplayMetrics());
		return mPix;
	}
	
	public static int getFixMargin(int srceenWidth,int singleHeight){
		
		int fixMargin=0;
		
		if(srceenWidth>(singleHeight*3)&&singleHeight>0){
			
			
			fixMargin=(srceenWidth-(singleHeight*3))/4;
			
			
		}
       return fixMargin;	
	}
}
