package com.jameschen.comm.utils;

import com.thirdprt.tasktrackerpms.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class NetworkUtil {

    /*
    * 检验网络连接 并toast提示
    *
    * @return
    */
    public static boolean isInternetAvailable(Context context) {

        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkinfo = con.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            // 当前网络不可用
            return false;
        }

        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        if (!wifi) {
        	
        }
        return true;

    }
}