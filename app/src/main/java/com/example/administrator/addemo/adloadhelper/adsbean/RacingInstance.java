package com.example.administrator.addemo.adloadhelper.adsbean;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.administrator.addemo.adloadhelper.AdCacheNode;
import com.example.administrator.addemo.adloadhelper.AdViewBaseLayout;
import com.example.administrator.addemo.adloadhelper.AdsRestClient;
import com.example.administrator.addemo.adloadhelper.IAdViewCallBackListener;
import com.example.administrator.addemo.adloadhelper.NativeAdViewManager;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;

import java.util.Calendar;


/**
 * Created by damon on 11/05/2017.
 */

public class RacingInstance extends NativeAdBase {
    private static final String TAG = "racing_instance";

    //admob
    private com.google.android.gms.ads.formats.NativeAd admobNativeAd;
    private AdLoader admobadLoader;
    private NativeExpressAdView admobNativeExpressAdView;
    private AdView admobBanner;
    private RacingInstance.AdmobListener admobListener;


    public RacingInstance(Context adviewContext, AdCacheNode adCacheNode, AdViewBaseLayout adContainer, IAdViewCallBackListener listener) {
        super(adviewContext, adCacheNode, adContainer, listener);
        initData();
    }

    private void initData() {
        initAdmob();
    }

    private void initAdmob() {
        if (adCacheNode.admobId == null) {
            return;
        }
        if (!AdInstanceUtils.hasRacing(adCacheNode.adLoadType, "admob")) {
            return;
        }
        Log.e(TAG, "LYF initAdmob: ="+adCacheNode.admobId );
        if (adCacheNode.admobAdType.equals(AdCacheNode.AD_TYPE_BANNER)) {
            initAdmobBanner();
        } else if (adCacheNode.admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_EXPRESS)) {
            initAdmobExpress();
        } else if (adCacheNode.admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_ADVANCED)) {
            initAdvanced();
        }

    }

    private void initAdvanced() {

        AdLoader.Builder builder = new AdLoader.Builder(adViewContext, adCacheNode.admobId);
        builder.forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
            @Override
            public void onContentAdLoaded(NativeContentAd ad) {
                Log.i(TAG,   " onAdLoaded NativeContentAd " + adCacheNode.admobAdType + ", admobId-->>" + adCacheNode.admobId);
                admobNativeAd = ad;
                    dataFrom = DataFrom.FROM_ADMOB_NATIVE_A_CONTENT;
                    handleSuccess();

            }
        });
        builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
            @Override
            public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
                admobNativeAd = nativeAppInstallAd;
                    dataFrom = DataFrom.FROM_ADMOB_NATIVE_A_INSTALL;
                    handleSuccess();

            }
        });
        admobListener = new RacingInstance.AdmobListener();
        admobadLoader = builder.withAdListener(admobListener).build();
    }

    private void handleSuccess() {
        loadStateAdmob = AdLoadState.LOAD_SUCCESS;
        mLastLoadTime = Calendar.getInstance().getTimeInMillis();
        Log.e(TAG, "LYF 加载成功状态准备更新为true handleSuccess: ="+ mIsLoadSuccess );
        mIsLoadSuccess = true;
        if (listener != null) {
            listener.adviewLoad(this);
        }
    }

    private void initAdmobExpress() {

//        final Context context = NativeAdViewManager.getInstance().getApplicationContext();
        admobNativeExpressAdView = new NativeExpressAdView(adViewContext);
        admobNativeExpressAdView.setAdSize(new AdSize(adContainer.getAdWidth(), adContainer.getAdHeight()));
        admobNativeExpressAdView.setAdUnitId(adCacheNode.admobId);
        admobListener = new RacingInstance.AdmobListener();
        admobNativeExpressAdView.setAdListener(admobListener);
        loadStateAdmob = AdLoadState.LOAD_INIT;
    }

    private void initAdmobBanner() {
        admobBanner = new AdView(adViewContext);
        admobBanner.setAdSize(AdSize.BANNER);
        admobBanner.setAdUnitId(adCacheNode.admobId);
        admobListener = new RacingInstance.AdmobListener();
        admobBanner.setAdListener(admobListener);
    }


    private void loadError() {
        if (isAllLoadFailed()) {
            mIsLoadSuccess = false;
            if (listener != null) {
                listener.adviewLoadError(RacingInstance.this);
            }
        }
    }

    private class AdmobListener extends com.google.android.gms.ads.AdListener {
        @Override
        public void onAdFailedToLoad(int errorCode) {
            //当广告加载失败时调用
            loadStateAdmob = AdLoadState.LOAD_FAILED;
            loadError();
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            //Called when the user is about to return to the app after clicking on an ad.
            //用户单击该广告时调用
            if (listener != null) {
                listener.adviewClick(RacingInstance.this);
            }
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            //当广告加载完毕时调用
            if (adCacheNode.admobAdType.equals(AdCacheNode.AD_TYPE_BANNER)) {
                    dataFrom = DataFrom.FROM_ADMOB_BANNER;
                    handleSuccess();
            } else if (adCacheNode.admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_EXPRESS)) {
                    dataFrom = DataFrom.FROM_ADMOB_NATIVE_E;
                    handleSuccess();
                }
            }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            //Called when an ad opens an overlay that covers the screen.
            //当广告覆盖在屏幕上时调用
            Log.d(TAG," onAdOpened " + adCacheNode.admobAdType + ", admobId-->>" + adCacheNode.admobId);
            if (listener != null) {
                listener.adviewClick(RacingInstance.this);
            }
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            //当广告离开app时调用, 比如:跳转了浏览器
            Log.d(TAG,  " onAdLeftApplication " + adCacheNode.admobAdType + ", admobId-->>" + adCacheNode.admobId);

        }
    }

    @Override
    public void loadAds() {
        super.loadAds();
        if (!AdsRestClient.isNetWorkAvailable(adViewContext)) {
            Log.e(TAG, "no network not loadAds");
            return;
        }
        if (TextUtils.isEmpty(NativeAdViewManager.DEBUG_ONLY_ONE) || NativeAdViewManager.DEBUG_ONLY_ONE.equals("admob")) {
            loadAdAdmob();
        }
    }

    private void loadAdAdmob() {
        if (adCacheNode.admobId == null) {
            return;
        }
        if (adCacheNode.admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_EXPRESS)) {
            if (admobNativeExpressAdView == null) {
                //这里使用handler.post只是为了让其它代码直接结束再执行onErrorT();
                //原因 详见:AnimateNativeAdViewLayout.mAnimateIsStopFlag
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG,  "admobNativeExpressAdView==null not loadAds");
                        loadError();
                    }
                });
            } else {
                loadStateAdmob = AdLoadState.LOAD_ING;
                AdRequest request = new AdRequest.Builder().build();
                admobNativeExpressAdView.loadAd(request);
            }
        } else if (adCacheNode.admobAdType.equals(AdCacheNode.AD_TYPE_BANNER)) {
            if (admobBanner == null) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadError();
                    }
                });
            } else {
                loadStateAdmob = AdLoadState.LOAD_ING;
                AdRequest request = new AdRequest.Builder().build();
                admobBanner.loadAd(request);
            }
        } else if (adCacheNode.admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_ADVANCED)) {
            if (admobadLoader == null) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadError();
                    }
                });
            } else {
                loadStateAdmob = AdLoadState.LOAD_ING;
                AdRequest request = new AdRequest.Builder().build();
                admobadLoader.loadAd(request);
            }
        }
    }



    /**
     * @return
     */
    private boolean isAllLoadFailed() {
        boolean admobFailed = (loadStateAdmob == AdLoadState.LOAD_FAILED || loadStateAdmob == AdLoadState.LOAD_NULL);
        return admobFailed;
    }

    @Override
    public void destoryAdView() {
        destroyAdmob();
        listener = null;
        dataFrom = DataFrom.FROM_NULL;
    }

    private void destroyAdmob() {
        if (adCacheNode.admobId == null) {
            return;
        }
        if (!AdInstanceUtils.hasRacing(adCacheNode.adLoadType, "admob")) {
            return;
        }
        loadStateAdmob = NativeAdBase.AdLoadState.LOAD_NULL;
        AdInstanceUtils.destoryAdView(admobNativeExpressAdView, admobBanner, admobNativeAd);
    }

    @Override
    public String getTitle() {
        return AdInstanceUtils.getTitle(dataFrom,  admobNativeAd);
    }

    @Override
    public String getButton() {
        return AdInstanceUtils.getButton(dataFrom, admobNativeAd);
    }

    @Override
    public String getTextBody() {
        return AdInstanceUtils.getTextBody(dataFrom,admobNativeAd);
    }

    @Override
    public void displayImageIcon(ImageView imageView) {
        AdInstanceUtils.displayImageIcon(imageView, dataFrom, admobNativeAd);
    }

    @Override
    public void setMediaView(LinearLayout content) {
        AdInstanceUtils.setMediaView(content, dataFrom, admobNativeAd);
    }

    @Override
    public void displayBigImage(ImageView imageView) {
        AdInstanceUtils.displayBigImage(imageView, dataFrom, admobNativeAd);
    }

    @Override
    public boolean isNeedReload() {
        return adCacheNode.clickToReload;
    }

    public NativeExpressAdView getAdmobNativeExpressAdView() {
        return admobNativeExpressAdView;
    }

    public AdView getAdmobBanner() {
        return admobBanner;
    }

    public com.google.android.gms.ads.formats.NativeAd getAdmobNativeAd() {
        return admobNativeAd;
    }



    private String generateLogStateString() {
        StringBuilder builder = new StringBuilder();
        builder.append(", admobId-->>" + adCacheNode.admobId);
        builder.append(", loadStateAdmob-->>" + loadState2String(loadStateAdmob));
        return builder.toString();
    }
}
