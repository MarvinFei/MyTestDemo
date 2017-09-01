package com.example.administrator.addemo;

import android.app.Application;
import android.content.Context;

import com.cloudtech.ads.core.CTService;
import com.example.administrator.addemo.adloadhelper.NativeAdViewManager;
import com.example.administrator.addemo.config.Config;
import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.google.android.gms.ads.MobileAds;


/**
 * Created by Administrator on 2017/8/4.
 */

public class MyApplication extends Application {

    public static Context context;
    public static boolean hasSplash = false;
    private static String ADMOB_ADDEMO_ID = "ca-app-pub-4562829771983924~4830653261";

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            //检查内存泄漏
        }
        context = this.getApplicationContext();
        //init the sdk
        CTService.init(context, Config.slotIdReward);
        //init the newsfeed

        //init the fresco for sample
        Fresco.initialize(context);

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AdSettings.addTestDevice("61d1907283cdfd097af5a35b4eedf6bc");
        initAdSDK();


    }

    private void initAdSDK() {
        //admob 初始化

            NativeAdViewManager.getInstance().init(this, "http://10.0.12.118:8080/ad_emojikeyboard_config_v3_LYF_test.json", "ad_emojikeyboard_config_v3_LYF_test.json");
            MobileAds.initialize(this, ADMOB_ADDEMO_ID);//admob 正式 app id

    }
}
