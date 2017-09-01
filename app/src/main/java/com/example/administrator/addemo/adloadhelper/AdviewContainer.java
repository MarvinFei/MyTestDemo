package com.example.administrator.addemo.adloadhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;


/**
 * Created by damon on 24/04/2017.
 * 广告的容器视图
 */

public class AdviewContainer extends RelativeLayout {
    private static final String TAG = "AdviewContainer";

    //广告关闭按钮
    public ViewGroup ad_close_layout;

    /**
     * true: 包含关闭按钮, 默认:false
     */
    public boolean isContainsCloseBtn = false;

    public RelativeLayout adChoiceLayout = null;

    public AdviewContainer(Context context) {
        super(context);
    }

    public AdviewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdviewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addAdView(View adView, NativeAdBase nativeAdBase, boolean isContainsCloseBtn, final AdCloseListener adCloseListener, boolean isNotShowAdFlag) {
        if (adView == null) {
            Log.e(TAG, "addAdView, adView==null");
            return;
        }
        if (nativeAdBase == null) {
            Log.e(TAG, "addAdView, nativeAdBase==null");
            return;
        }
        @NativeAdBase.DataFrom int dataFrom = nativeAdBase.getDataFrom();
        removeParent(this);//从父视图中移除
        removeAllViews();//移除所有的子视图
        // TODO: 2017/8/16 end
        addView(adView);
        addView(adChoiceLayout);
        nativeAdBase.displayAdChoicesIcon(adChoiceLayout);
        }


    private void removeParent(View view) {
        if (view == null) {
            Log.e(TAG, "removeParent view==null");
            return;
        }
        ViewGroup p = (ViewGroup) view.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
    }
}
