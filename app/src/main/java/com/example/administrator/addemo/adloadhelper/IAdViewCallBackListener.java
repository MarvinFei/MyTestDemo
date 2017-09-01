package com.example.administrator.addemo.adloadhelper;


import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;

/**
 * Created by ivorange on 16/5/24.
 */
public interface IAdViewCallBackListener {

    void adviewLoad(NativeAdBase nativeAdBase);

    void adviewClick(NativeAdBase nativeAdBase);

    void adviewLoadError(NativeAdBase nativeAdBase);
}
