package com.example.administrator.addemo.adloadhelper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by danhantao on 15/1/15.
 */
public class NetWorkUtils {
    /**
     * 网络是否可用
     *
     * @param ctx
     * @return
     */
    public static boolean isNetWorkAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * @return 0: 无网络， 1:WIFI， 2:其他（流量）
     */
    public static int getNetType(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {
            return 0;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else {
            return 2;
        }
    }

}
