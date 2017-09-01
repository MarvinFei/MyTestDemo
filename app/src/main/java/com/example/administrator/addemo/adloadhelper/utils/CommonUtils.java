package com.example.administrator.addemo.adloadhelper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by sunjinxue on 2016/4/21.
 */
public class CommonUtils {
    public static boolean isFaceBookInstalled(Context context) {
        String packageName = "com.facebook.katana";
        String packageName_alter = "com.facebook.android";
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //check the alternative package name
            try {
                packageInfo = context.getPackageManager().getPackageInfo(packageName_alter, PackageManager.GET_ACTIVITIES);
            } catch (PackageManager.NameNotFoundException e1) {
                packageInfo = null;
                e1.printStackTrace();
            }
        }
        if (packageInfo == null) {
            Log.e("isFaceBookInstalled", " no");
            return false;
        }
            Log.e("isFaceBookInstalled", " yes");
        return true;
    }
    public static String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
