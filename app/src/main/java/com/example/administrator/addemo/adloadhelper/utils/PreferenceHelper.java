package com.example.administrator.addemo.adloadhelper.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.addemo.adloadhelper.AdCacheNode;

public class PreferenceHelper {

    public static final String SHARE_INTERSTITIAL_AD_SHOW_TIME = "foto_ads_share_interstitial_show_time";
//    public static final String SHARE_INTERSTITIAL_INTERVAL = "foto_ads_share_interstitial_interval";
    public static final String REPLACE_FACEBOOK_ID = "foto_ads_replace_facebook_id";
    private static final String TAG = "PreferenceHelper";
    private static final String PREFERENCE_FILE = "foto_ads";
    private static final String KEY_INSTALLED_TIME = "key_installed_time";//应用安装的时间,默认值为0

    //shared存储的文件名
    private final static String CACHETIME_SHAREPRE = "sharePreferences_cachetime";
    //ad配置文件存储的key
    private final static String CACHETIME_KEY = "allads_cachetimes";
    //ad配置文件缓存时间间隔的key 单位:毫秒
    private final static String CACHETIME_UPDATETIME_FROMSERVICE = "update_time_from_service";

    public static String getAdConfig(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CACHETIME_SHAREPRE, Context.MODE_PRIVATE);
        return preferences.getString(CACHETIME_KEY, null);
    }

    public static void setAdConfig(Context context, String config) {
        SharedPreferences preferences = context.getSharedPreferences(CACHETIME_SHAREPRE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CACHETIME_KEY, config);
        editor.apply();
    }
    public static long getAdConfigInterval(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CACHETIME_SHAREPRE, Context.MODE_PRIVATE);
        return preferences.getLong(CACHETIME_UPDATETIME_FROMSERVICE, AdCacheNode.UPDATE_AD_CACHE_TIME_DEFAULT);
    }

    public static void setAdConfigInterval(Context context, long showTime) {
        SharedPreferences preferences = context.getSharedPreferences(CACHETIME_SHAREPRE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(CACHETIME_UPDATETIME_FROMSERVICE, showTime);
        editor.apply();
    }
    public static long getInterstitialShowTime(Context context, String facebookId) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        return preferences.getLong(SHARE_INTERSTITIAL_AD_SHOW_TIME + facebookId, 0);
    }

    public static void setInterstitialShowTime(Context context, String facebookId, long showTime) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SHARE_INTERSTITIAL_AD_SHOW_TIME + facebookId, showTime);
        editor.apply();
    }

    public static long getInstalledTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        return preferences.getLong(KEY_INSTALLED_TIME, 0);
    }

    public static void setInstalledTime(Context context, long installedTime) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(KEY_INSTALLED_TIME, installedTime);
        editor.apply();
    }

//    public static long getInterstitialInterval(Context context, String facebookId) {
//        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
//        return preferences.getLong(SHARE_INTERSTITIAL_INTERVAL + facebookId, 0);
//    }
//
//    public static void setInterstitialInterval(Context context, String facebookId, long interval) {
//        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putLong(SHARE_INTERSTITIAL_INTERVAL + facebookId, interval);
//        editor.commit();
//    }

//    public static String getReplaceFacebookId(Context context, String facebookId) {
//        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
//        return preferences.getString(REPLACE_FACEBOOK_ID + facebookId, "-1");
//    }
//
//    public static void setReplaceFacebookId(Context context, String facebookId, String rFacebookId) {
//        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(REPLACE_FACEBOOK_ID + facebookId, rFacebookId);
//        editor.apply();
//    }

    public static void checkSetInstalledTime(Context context) {
        if (context == null) {
            return;
        }
        long installedTime = getInstalledTime(context);
        if (installedTime == 0) {
            setInstalledTime(context, System.currentTimeMillis());
        }
    }

    public static boolean isInstalledXDays(Context context,int flagDays) {
        if (context == null) {
            Log.d(TAG, "isInstalled5Days, context==null");
            return false;
        }
        long interval = System.currentTimeMillis() - getInstalledTime(context);
        int intervalDays = (int) (interval / 1000 / 60 / 60 / 24);
        Log.d(TAG, "isInstalled5Days, interval-->>" + interval + ", intervalDays -->>" + intervalDays+", flagDays-->>"+flagDays);
//        return days > -1;//测试
        return intervalDays > flagDays;//正式
    }
}
