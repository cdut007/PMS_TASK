package com.thirdpart.model;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public abstract class ManagerService {
public final static String ISSUE_SERVICE = "issue_serive";
private static ConcurrentHashMap<String,ManagerService> managerServiceMap = new ConcurrentHashMap<String, ManagerService>();	
protected Context context;

ManagerService(Context context){
	this.context = context;
}

public static  ManagerService getManagerService(Context context,String ServiceName){
		
	ManagerService managerService = managerServiceMap.get(ServiceName);
			
		if (managerService !=null ) {
			return  managerService;
		}
		
	if (ServiceName.equals(ISSUE_SERVICE)) {
		return managerServiceMap.put(ServiceName, new IssueManager(context));//use class name for create.
	}else {
		throw new RuntimeException("could not find the serive ,which is called:"+ServiceName);
	}
	
}

protected static void removeSerive(String name){
	managerServiceMap.remove(name);
}

void  notifyFailedResult(String action,int statusCode,String response){
	  
    Intent mIntent = new Intent(action);
    mIntent.putExtra("code", statusCode);
    mIntent.putExtra("reason", response);
	LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);

}

 void  notifySuccResult(String action,int statusCode){
	  
    Intent mIntent = new Intent(action);
    mIntent.putExtra("succ", true);
	LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);

}

 public interface  OnCallbackResponseListener<T>{

		void onSucc(T data);
	}

}
