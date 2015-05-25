package com.jameschen.comm.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.thirdpart.model.LogInController;
import com.thirdpart.model.entity.UserInfo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;


public class LogUtils {

	private final static String TAG = "LogUtils";

	public static void sendLog(Context context) {

		try {

			context.startActivity(CollectLogs.getLogReportIntent("<<<PLEASE ADD THE BUG DESCRIPTION HERE>>>", context));

		} catch (Exception e) {

			Log.e(TAG, "Impossible to send logs..."+ e.getLocalizedMessage());
		}


	}


	
	public static String getSupportEmail(SharedPreferences preferences) {
		return preferences.getString("SUPPORT_MAIL", "");
	}
	public static void  setSupportEmail(String supportMail,SharedPreferences preferences) {
		
		 preferences.edit().putString("SUPPORT_MAIL", supportMail).commit();
		 
	}
	public static void sendHuaweiLogFile3(Context context,String filePath){
		
		String versionName="";
		
		try {
			
			versionName=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			
			
		} catch (NameNotFoundException e) {
	
			e.printStackTrace();
		}
	
		StringBuffer sb=new StringBuffer();
		UserInfo info = LogInController.getInstance(context).getInfo();
		String name = info==null?"":info.getName();
		sb.append("MSISDN : ").append(name).append("\r\n")
	    .append("Please describe problem :").append("\r\n").append("\r\n").append("\r\n").append("\r\n")
	    .append("Attach Screenshots (if any) : ").append("\r\n").append("\r\n").append("\r\n").append("\r\n")
		.append("Running on ").append(android.os.Build.MODEL).append("\r\n")
		.append("OS Version : ").append(android.os.Build.VERSION.RELEASE).append("\r\n")
		.append("Client Version : ").append(versionName);
	
	     File zipFile=new File(filePath);
		
		    List<Intent> targetShareIntents=new ArrayList<Intent>();
		    Intent shareIntent=new Intent();
		    shareIntent.setAction(Intent.ACTION_SEND);
		    shareIntent.setType("message/rfc822");
		    List<ResolveInfo> resInfos=context.getPackageManager().queryIntentActivities(shareIntent, 0);
		    
		    removeDuplicateWithOrder(resInfos);
		    
		    if(!resInfos.isEmpty()){
		       
		        for(ResolveInfo resInfo : resInfos){
		        	
		            String packageName=resInfo.activityInfo.packageName;
		            
		            Log.i(TAG,"packageName = "+ packageName);
		            
		            if(packageName.contains("mail")||"com.google.android.gm".equals(packageName)){
		            	
		                Intent intent=new Intent();
		                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
		                intent.setAction(Intent.ACTION_SEND);		       
		                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"appslog@starhub.com"});
		                intent.putExtra(Intent.EXTRA_SUBJECT, "Message+ - Bug Report");
		                intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
		                intent.setType("application/zip");
		                intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(zipFile));
		                intent.setPackage(packageName);
		                targetShareIntents.add(intent);
		            }
		        }
		        if(!targetShareIntents.isEmpty()){
		        	
		        	Log.i(TAG, "Do not Have Intent size is "+targetShareIntents.size());
		        	
		            Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Choose email client");
		            
		            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
		            
		            context.startActivity(chooserIntent);
		            
		        }else{
		        	
		        	Log.i(TAG, "Do not Have Intent");
		        }
		    }
		
		
		
	}

 public static void  sendFeedback2(Context context){
	
	String versionName="";
	
	try {
		
		versionName=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		
		
	} catch (NameNotFoundException e) {

		e.printStackTrace();
	}
	
	StringBuffer sb=new StringBuffer();
    sb.append("We would like to hear your feedback and improvement ideas. Please feel free to share below :").append("\r\n").append("\r\n").append("\r\n")
    .append("Attach Screenshots (if any) : ").append("\r\n").append("\r\n").append("\r\n")
	.append("Running on ").append(android.os.Build.MODEL).append("\r\n")
	.append("OS Version : ").append(android.os.Build.VERSION.RELEASE).append("\r\n")
	.append("Client Version : ").append(versionName);
	
	 Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","messageplus_feedback@starhub.com", null));
     emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message+ - Feedback");
     emailIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
     context.startActivity(Intent.createChooser(emailIntent, "Choose email client"));
	
}
 public static void sendFeedback(Context context, String to, String subject, String body) {
	    StringBuilder builder = new StringBuilder("mailto:" + Uri.encode(to));
	    if (subject != null) {
	        builder.append("?subject=" + Uri.encode(Uri.encode(subject)));
	        if (body != null) {
	            builder.append("&body=" + Uri.encode(Uri.encode(body)));
	        }
	    }
	    String uri = builder.toString();
	    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
	    context.startActivity(intent);
	}
	public static void removeDuplicateWithOrder(List<ResolveInfo> list) {

		if (list == null) {

			return;

		}

		Set<String> set = new HashSet<String>();

		List<ResolveInfo> newList = new ArrayList<ResolveInfo>();

		for (Iterator<ResolveInfo> iter = list.iterator(); iter.hasNext();) {

			ResolveInfo info = (ResolveInfo) iter.next();

			if (set.add(info.activityInfo.packageName)) {

				newList.add(info);

			}
		}

		list.clear();

		list.addAll(newList);

	}

}
