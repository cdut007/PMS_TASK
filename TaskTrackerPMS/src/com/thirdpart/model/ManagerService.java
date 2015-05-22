package com.thirdpart.model;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Fragment;
import android.app.Fragment.InstantiationException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.ArrayMap;

public abstract class ManagerService {

public final static String ISSUE_SERVICE = "issue_serive";


private static ConcurrentHashMap<String, Class<?>> managerServiceMap = new ConcurrentHashMap<String, Class<?>>();	

public static final String CODE = "code",REASON="reanson",SUCC="succ",DATA="data";

protected Context context;

ManagerService(Context context){
	this.context = context;
}



public static  ManagerService getNewManagerService(Context context,Class<?> mClass){
    String serviceName = mClass.getName();
	try {
        Class<?> clazz = managerServiceMap.get(serviceName);
        if (clazz == null) {
            // Class not found in the cache, see if it's real, and try to add it
            clazz = context.getClassLoader().loadClass(serviceName);
            if (!ManagerService.class.isAssignableFrom(clazz)) {
                throw new InstantiationException("Trying to instantiate a class " + serviceName
                        + " that is not a ManagerService", new ClassCastException());
            }
            managerServiceMap.put(serviceName, clazz);
        }
        ManagerService managerService = (ManagerService)clazz.newInstance();
        managerService.context = context;
        return managerService;
    } catch (ClassNotFoundException e) {
        throw new InstantiationException("Unable to instantiate managerService " + serviceName
                + ": make sure class name exists, is public, and has an"
                + " empty constructor that is public", e);
    } catch (java.lang.InstantiationException e) {
        throw new InstantiationException("Unable to instantiate managerService " + serviceName
                + ": make sure class name exists, is public, and has an"
                + " empty constructor that is public", e);
    } catch (IllegalAccessException e) {
        throw new InstantiationException("Unable to instantiate managerService " + serviceName
                + ": make sure class name exists, is public, and has an"
                + " empty constructor that is public", e);
    }
}



void  notifyFailedResult(String action,int statusCode,String response){
	  
    Intent mIntent = new Intent(action);
    mIntent.putExtra(CODE, statusCode);
    mIntent.putExtra(REASON, response);
	LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);

}

 void  notifySuccResult(String action,int statusCode){
	  
    Intent mIntent = new Intent(action);
    mIntent.putExtra(SUCC, true);
	LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);

}

}
