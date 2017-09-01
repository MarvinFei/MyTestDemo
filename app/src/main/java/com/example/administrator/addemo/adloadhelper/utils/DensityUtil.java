package com.example.administrator.addemo.adloadhelper.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by Nianing on 2016/2/1.
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int[] getViewWH(View view) {
        int widthM = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightM = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthM, heightM);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        Log.d("measureWH","width-->>"+width+", height-->>"+height);
        return new int[]{width, height};
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null || context.getResources() == null) {
            return 720;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null || context.getResources() == null) {
            return 1280;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 华氏度 转换为摄氏度
     *
     * @param f 华氏度数
     * @return
     */
    public static Number fToc(float f) {
        try {
            DecimalFormat floatFormat = new DecimalFormat("#.0");
            DecimalFormatSymbols custom = new DecimalFormatSymbols();
            custom.setDecimalSeparator('.');
            floatFormat.setDecimalFormatSymbols(custom);
            return floatFormat.parse(floatFormat.format((f - 32) / 1.8f));
        } catch (ParseException e) {

        }
        return 0;
    }

    /**
     * 摄氏度转换为华氏度
     *
     * @param c 摄氏度数
     * @return
     */
    public static Number cTof(float c) {
        try {
            DecimalFormat floatFormat = new DecimalFormat("#.0");
            DecimalFormatSymbols custom = new DecimalFormatSymbols();
            custom.setDecimalSeparator('.');
            floatFormat.setDecimalFormatSymbols(custom);
            return floatFormat.parse(floatFormat.format(c * 1.8f + 32));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    public static int getDensityDpi(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

}
