package com.thirdpart.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.CrypToCfg;
import com.jameschen.comm.utils.Log;
import com.thirdpart.model.ConstValues.CategoryInfo.User;
import com.thirdpart.model.entity.Role;
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

	public boolean matchRoles(String seaerchRole){
		List<Role> roles = getInfo().getRoles();
		for (Role role : roles) {
			if (seaerchRole.equals(role.name)) {
				Log.i("role", "find role:"+seaerchRole);
				return true;
			}
		}
		return false;
	}
	
	
	public void quit(Context context) {
		SharedPreferences user = context.getSharedPreferences(User.SharedName,
				0);
		user.edit().putBoolean(User.logon, false).commit();
		user.edit().remove(User.password).commit();
		user.edit().remove(User.userinfo).commit();
		mController=null;
		JPushInterface.stopPush(context);
		
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

	
	
	/**
	 * @return 设备唯一标识符
	 */
	public static  String getUUID(Context context) {
	/*	 TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	
		 String tmDevice = "", tmSerial = "", tmPhone, androidId;
		// 357784053869432
		 if (tm!=null) {
			tmDevice = "" + tm.getDeviceId(); 
			tmSerial = "" + tm.getSimSerialNumber();	
		}*/
		
		//357784053869432
		String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		//UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		//ffffffff-e2ef-4fa0-fad7-dd880033c587
		String uniqueId = androidId.toString();
		return uniqueId;
		
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
	public void registerPush() {
		registerPush(null);
	}
	public void registerPush(String id) {
		if (id == null) {
			 id =getInfo().getId();	
		}
		
		if (id == null) {
			Log.i("login", "ID is null");//
			return;
		}
		String uuid = getUUID(context);
		Log.i("login", "uuid="+uuid);//
		if (JPushInterface.isPushStopped(context)) {
			JPushInterface.resumePush(context);
		}
	
		JPushInterface.setAliasAndTags(context, ""+uuid+"_"+id, null,new TagAliasCallback() {
			
			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				// TODO Auto-generated method stub
				Log.i("push","code="+arg0);	
				if (arg0!=0) {//try later
					sHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							
						registerPush();	
						}
					}, 60*1000);
				}else {
					sHandler.removeCallbacksAndMessages(null);
				}
			}
		} );
		
		
	}
static Handler sHandler = new Handler();
}