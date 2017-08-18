package com.example.administrator.addemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cloudtech.ads.core.CTAdEventListener;
import com.cloudtech.ads.core.CTNative;
import com.cloudtech.ads.vo.AdsNativeVO;

import static com.example.administrator.addemo.MyApplication.context;

/**
 * Created by Administrator on 2017/8/18.
 */

public class MyCTAdEventListener implements CTAdEventListener {

    @Override
    public void onAdviewGotAdSucceed(CTNative result) {
        showMsg("onAdviewGotAdSucceed");
    }

    @Override
    public void onAdsVoGotAdSucceed(AdsNativeVO result) {
        showMsg("onAdsVoGotAdSucceed");
    }

    @Override
    public void onInterstitialLoadSucceed(CTNative result) {
        showMsg("onInterstitialLoadSucceed");
    }

    @Override
    public void onAdviewGotAdFail(CTNative result) {
        showMsg(result.getErrorsMsg());
        Log.i("sdksample", "errorMsg:" + result.getErrorsMsg());
    }

    @Override
    public void onAdviewIntoLandpage(CTNative result) {
        showMsg("onAdviewIntoLandpage");
    }

    @Override
    public void onStartLandingPageFail(CTNative result) {
        showMsg("onStartLandingPageFail");
    }

    @Override
    public void onAdviewDismissedLandpage(CTNative result) {
        showMsg("onAdviewDismissedLandpage");
    }

    @Override
    public void onAdviewClicked(CTNative result) {
        showMsg("onAdviewClicked");
    }

    @Override
    public void onAdviewClosed(CTNative result) {
        showMsg("onAdviewClosed");
    }

    @Override
    public void onAdviewDestroyed(CTNative result) {
        showMsg("onAdviewDestroyed");
    }


    private void showMsg(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
