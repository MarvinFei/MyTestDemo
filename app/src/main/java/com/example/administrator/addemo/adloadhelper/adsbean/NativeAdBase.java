package com.example.administrator.addemo.adloadhelper.adsbean;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.addemo.adloadhelper.AdCacheNode;
import com.example.administrator.addemo.adloadhelper.AdViewBaseLayout;
import com.example.administrator.addemo.adloadhelper.IAdViewCallBackListener;
import com.example.administrator.addemo.adloadhelper.utils.PreferenceHelper;

import java.util.Calendar;


/**
 * Created by ivorange on 16/5/24.
 */
public class NativeAdBase {

    //该广告是否加载成功
    protected boolean mIsLoadSuccess = false;
    //发起加载广告的时间
    protected long mLoadStartTime;
    //上次加载成功的时间
    protected long mLastLoadTime = 0;
    //界面从哪个广告中读取数据
    @DataFrom
    protected int dataFrom = DataFrom.FROM_NULL;

    protected IAdViewCallBackListener listener = null;
    protected AdCacheNode adCacheNode;
    protected AdViewBaseLayout adContainer;
    protected Context adViewContext;

    public NativeAdBase(Context adViewContext, AdCacheNode adCacheNode,
                        AdViewBaseLayout adContainer,
                        IAdViewCallBackListener listener) {
        this.adViewContext = adViewContext;
        this.adCacheNode = adCacheNode;
        this.adContainer = adContainer;
        this.listener = listener;
    }

    public static NativeAdBase createAds(Context adviewContext, AdCacheNode cacheNode,
                                         AdViewBaseLayout container,
                                         IAdViewCallBackListener listener) {

        if (cacheNode == null) {
            return null;
        }
        if (adviewContext == null) {
            return null;
        }
        if (cacheNode.xDays2Other) {
            boolean is2Other = PreferenceHelper.isInstalledXDays(adviewContext, cacheNode.xDays2OtherDays);
            if (is2Other) {
                cacheNode.fbId = null;
                cacheNode.baiduID = null;
                return new RacingInstance(adviewContext, cacheNode, container, listener);
//                return new AdmobAltamobAdInstance(adviewContext, cacheNode, container, listener);
            }
        }

        NativeAdBase instance = new RacingInstance(adviewContext, cacheNode, container, listener);
        return instance;

    }

    public IAdViewCallBackListener getCurrentListener() {
        return listener;
    }

    public void setCurrentListener(IAdViewCallBackListener listener) {
        this.listener = listener;
    }

    public AdCacheNode getAdCacheNode() {
        return adCacheNode;
    }


    public boolean isExpire() {
        boolean isExpire = false;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - mLastLoadTime > adCacheNode.cacheTime) {
            isExpire = true;

            mLastLoadTime = currentTime;
        }

        return isExpire;
    }


    public void destoryAdView() {
    }

    public void loadAds() {
        mLoadStartTime = System.currentTimeMillis();
    }

    public boolean isLoadSuccess() {
        return mIsLoadSuccess;
    }

    public String getTitle() {

        return "";
    }

    public String getSubTitle() {

        return "";
    }

    public double getRatingScale() {
        return 0.0;
    }

    public double getRatingValue() {
        return 0.0;
    }


    public String getButton() {

        return "";
    }

    public String getTextBody() {

        return "";
    }

    public void displayImageIcon(ImageView imageView) {


    }

    public void displayBigImage(ImageView imageView) {


    }

    public void setMediaView(LinearLayout content) {


    }

    public void registerViewGroup(View view) {

    }


    public void displayAdChoicesIcon(RelativeLayout adChoicesLayout) {

    }


    public long getmLoadStartTime() {
        return mLoadStartTime;
    }

    public @interface AdLoadState {
        int LOAD_INIT = 0;
        int LOAD_ING = 1;
        int LOAD_SUCCESS = 2;
        int LOAD_FAILED = 3;
        /**
         * null,表示不可用状态
         */
        int LOAD_NULL = 4;
    }

    public String loadState2String(@AdLoadState int loadState) {
        String result;
        switch (loadState) {
            case AdLoadState.LOAD_INIT:
                result = "LOAD_INIT";
                break;
            case AdLoadState.LOAD_ING:
                result = "LOAD_ING";
                break;
            case AdLoadState.LOAD_SUCCESS:
                result = "LOAD_SUCCESS";
                break;
            case AdLoadState.LOAD_FAILED:
                result = "LOAD_FAILED";
                break;
            case AdLoadState.LOAD_NULL:
                result = "LOAD_NULL";
                break;

            default:
                //don't forget default
                result = "ERROR_ERROR";
                break;
        }
        return result;
    }

    /**
     * @return 当前正在显示的广告是哪个广告
     */
    @DataFrom
    public int getDataFrom() {
        return dataFrom;
    }

    /**
     * 处理广告点击后,是否刷新逻辑
     *
     * @return true: 点击后立即刷新, false: 点击后不需要刷新
     */
    public boolean isNeedReload() {
        //该方法需要重写
        return false;
    }


    public void resetLastTime() {
        // 延后10秒
        //
        long currentTime = Calendar.getInstance().getTimeInMillis();
        mLastLoadTime = currentTime - (adCacheNode.cacheTime - AdCacheNode.AD_ERROR_OUTIME);
    }

    /**
     * 初始状态, null:不可用
     */
    protected int loadStateAdmob = AdLoadState.LOAD_NULL;

    public @interface DataFrom {
        /**
         * fb和bd数据都没有
         */
        int FROM_NULL = 0;
        /**
         * 从admob中读数据 native express
         */
        int FROM_ADMOB_NATIVE_E = 4;
        /**
         * 从admob中读数据 native advanced content
         */
        int FROM_ADMOB_NATIVE_A_CONTENT = 5;
        /**
         * 从admob中读数据 native advanced install
         */
        int FROM_ADMOB_NATIVE_A_INSTALL = 6;
        /**
         * 从admob中读数据 banner
         */
        int FROM_ADMOB_BANNER = 7;
    }

    public String dataFrom2String(@DataFrom int dataFrom) {
        String result;
        switch (dataFrom) {
            case DataFrom.FROM_NULL:
                result = "FROM_NULL";
                break;

            case DataFrom.FROM_ADMOB_NATIVE_E:
                result = "FROM_ADMOB_NATIVE_E";
                break;
            case DataFrom.FROM_ADMOB_BANNER:
                result = "FROM_ADMOB_BANNER";
                break;
            case DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                result = "FROM_ADMOB_NATIVE_A_CONTENT";
                break;
            case DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                result = "FROM_ADMOB_NATIVE_A_INSTALL";
                break;

            default:
                //don't forget default
                result = "ERROR_ERROR";
                break;
        }
        return result;
    }


    /**
     * @return admob的加载状态
     */
    @AdLoadState
    public int getLoadStateAdmob() {
        return loadStateAdmob;
    }
}