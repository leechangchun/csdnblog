package com.langchen.xlib.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 2016/8/22.
 */
public class AppUtil {
    /**
     * 判断网络是否可用
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
    判断wifi时候可用
    */
    public static boolean isWifReachable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                return true;
            }
        }
        return false;
    }
}
