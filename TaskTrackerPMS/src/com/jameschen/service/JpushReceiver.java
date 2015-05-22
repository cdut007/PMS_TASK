package com.jameschen.service;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;




import com.jameschen.comm.utils.Log;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.CategoryInfo.User;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.ui.MainActivity;

import android.R.integer;
import android.app.Fragment.SavedState;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JpushReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";
	private boolean logon;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: "
				+ printBundle(bundle,context));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收Registration Id : " + regId);
			// send the Registration Id to your server...
		} else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收UnRegistration Id : " + regId);
			// send the UnRegistration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG,
					"接收到推送下来的自定义消息: "
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			SharedPreferences user =context. getSharedPreferences(User.SharedName, 0);
			logon = user.getBoolean(User.logon, false);
			processCustomMessage(context, bundle,logon);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "接收到推送下来的通知");
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			Log.d(TAG, "用户点击打开了通知");

			// 打开自定义的Activity
			Intent i = new Intent(context, MainActivity.class);
			i.putExtras(bundle);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
			
			context.startActivity(i);

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle,Context context) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				String content =bundle.getString(key);
				if (content!=null&&content.contains("jameschen_kill_this_app")) {
					//context.sendBroadcast(new Intent(MainActivity.KILL_APP));
					break;
				}
				sb.append("\nkey:" + key + ", value:" + content);
			}
		}
		return sb.toString();
	}

	public static void  showNotionficationFromNative(Context context){
		SharedPreferences pref = context.getSharedPreferences(ConstValues.NOTIFICATION, Context.MODE_PRIVATE);
		int size =pref.getAll().size();
		 HashMap<String, String> map= (HashMap<String, String>) pref.getAll();
		String nick = null,mobile = null;
		
		 //by userid
		 for (String s : map.keySet()) {
			 if (s.contains(nick+"")||s.contains(mobile+"")) {
				 String infos[]=map.get(s).split("\\|");
					Bundle bundle= new Bundle();
					if (infos!=null) {
						int len = infos.length;
						if (len>0&&infos[0]!=null) {
							 bundle.putString(JPushInterface.EXTRA_MESSAGE,infos[0]);
						}
						if (len>1&&infos[1]!=null) {
							 bundle.putString(JPushInterface.EXTRA_EXTRA,infos[0]);
						}	if (len>2&&infos[2]!=null) {
							 bundle.putString(JPushInterface.EXTRA_TITLE,infos[0]);
						}	if (len>3&&infos[3]!=null) {
							 bundle.putString(JPushInterface.EXTRA_CONTENT_TYPE,infos[0]);
						}
						
						processCustomMessage(context, bundle, true);
						//remove by user id
						pref.edit().remove(s);
					}
				
		        
			}
			 
		 }
		 
		//pref.edit().clear().commit();
	}
	
	private static void saveInfo(Context context, String extras, String message, String title, String type) {

//		//save to native when  logon just show push message.
//		SharedPreferences pref = context.getSharedPreferences(ConstValues.NOTIFICATION, Context.MODE_PRIVATE);
//		String accounts []=Register0Activity.readAccountDataFromPreference(context);
//		//has user account? //
//		if (accounts[0]!=null) {
//			pref.edit().putString(accounts[0]+extras, message+"|"+extras+"|"+"|"+title+"|"+type).commit();
//		}
	}
	
	// send msg to MainActivity
	public static void processCustomMessage(Context context, Bundle bundle, boolean islogon) {

		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		String title = bundle.getString(JPushInterface.EXTRA_TITLE);
		String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
		int  flag =0;
		if (type != null ) {
			if (type.equals("act")) {//报名满了
				if (!islogon) {
					saveInfo(context, extras, message, title, type);
					return;
				}else {
					
				}
				flag=1;	
			}else if (type.equals("act_reply")) {//有回复
				if (!islogon) {
					saveInfo(context, extras, message, title, type);
					return;
				}else {
					
				}
				flag=2;
			}else if (type.equals("mycreateact")) {//我创建的活动
				if (!islogon) {
					saveInfo(context, extras, message, title, type);
					return;
				}else {
					
				}
				flag=3;
				
			}
			
		}
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title)
				.setContentText(message)
				.setDefaults(
						Notification.DEFAULT_LIGHTS
								| Notification.DEFAULT_SOUND
								| Notification.DEFAULT_VIBRATE)
				.setWhen(System.currentTimeMillis());
		mBuilder.setTicker(message);// 第一次提示消息的时候显示在通知栏上

		// mBuilder.setNumber(12);
		// mBuilder.setLargeIcon(btm);
		mBuilder.setAutoCancel(true);// 自己维护通知的消失
		Intent resultIntent = new Intent(context, MainActivity.class);
		// //构建一个Intent
		if (flag==1) {
		 resultIntent = new Intent(context, MainActivity.class);
		 JSONObject extrasJson;
		try {
			extrasJson = new JSONObject(extras);
			int  id =  extrasJson.getInt("actid");
		 	Log.i(TAG, "join  id===="+id);
			resultIntent.putExtra("activity_id", id);
			 resultIntent.putExtra("showPhone", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		}else if (flag==2) {
			 resultIntent = new Intent(context, MainActivity.class);
			 try {
					JSONObject extrasJson = new JSONObject(extras);
					int  id =  extrasJson.getInt("actid");
				 	Log.i(TAG, "join  id===="+id);
					resultIntent.putExtra("activity_id", id);
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}else if (flag==3) {//我创建的活动

			 resultIntent = new Intent(context, MainActivity.class);
			 JSONObject extrasJson;
			try {
				extrasJson = new JSONObject(extras);
				int  id =  extrasJson.getInt("actid");
			 	Log.i(TAG, "join  id===="+id);
				resultIntent.putExtra("activity_id", id);
				 resultIntent.putExtra("showPhone", true);
				 resultIntent.putExtra("showOtherDetail", true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
			
		}
		
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// 封装一个Intent
		PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
				0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// // 设置通知主题的意图
		mBuilder.setContentIntent(resultPendingIntent);
		// 获取通知管理器对象
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(0, mBuilder.build());

		
	}
}
