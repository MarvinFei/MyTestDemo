package com.example.administrator.addemo.adloadhelper;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;
import com.example.administrator.addemo.adloadhelper.adsbean.RacingInstance;
import com.example.administrator.addemo.adloadhelper.utils.NetWorkUtils;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAdView;


/**
 * Created by ivorange on 16/5/24.
 */
public class NativeAdViewLayout extends LinearLayout {

    // 默认缓存时间

    private static final String TAG = "Fotoable_Ads";
    protected int mCurrentType;
    protected long mCacheTime = 0;

    protected String mAdId = null;
    protected boolean mNeedCache = false;
    protected NativeAdBase mCurrentNativeAdBase = null;
    protected AdViewBaseLayout mCurrentAdViewBaseLayout = null;
    protected IAdViewCallBackListener mIAdViewCallBackListener = null;

    protected boolean isContainsCloseBtn = false;
    protected AdCloseListener adCloseListener;
    //是否显示左上角的ad标示
    protected  boolean isNotShowAdFlag;
    /**
     * 可能有多个广告数据返回, -1表示:使用第一个广告数据
     */
    protected int mAdIndex = -1;

    protected Context mContext;

    public NativeAdViewLayout(Context context) {
        super(context);
    }

    /**
     * 创建并获得一个完整的广告布局
     *
     * @param context   activity的上下文
     * @param adviewID  当前广告位ID
     * @param adIndex   默认-1 表示创建并获取一个单独的广告，大于-1 的情况表示获取多个广告集合中的某一个
     * @param needCache 是否需要缓存
     */
    public NativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, int adIndex, boolean needCache) {
        super(context);
        mContext = context;
        this.mCurrentAdViewBaseLayout = currentAdViewBaseLayout;
        initData(context, -1, adviewID, adIndex, needCache, AdCacheNode.DEFAULT_AD_CACHE_TIME);
    }

    public NativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, int adIndex) {
        super(context);
        mContext = context;
        this.mCurrentAdViewBaseLayout = currentAdViewBaseLayout;
        initData(context, -1, adviewID, adIndex, true, AdCacheNode.DEFAULT_AD_CACHE_TIME);
    }

    public NativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, boolean needCache) {
        super(context);
        mContext = context;
        this.mCurrentAdViewBaseLayout = currentAdViewBaseLayout;
        initData(context, -1, adviewID, -1, needCache, AdCacheNode.DEFAULT_AD_CACHE_TIME);
    }

    public NativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, IAdViewCallBackListener iAdViewCallBackListener) {
        super(context);
        mContext = context;
        this.mCurrentAdViewBaseLayout = currentAdViewBaseLayout;
        mIAdViewCallBackListener = iAdViewCallBackListener;
        initData(context, -1, adviewID, -1, false, AdCacheNode.DEFAULT_AD_CACHE_TIME);
    }


    public void setIAdViewCallBackListener(IAdViewCallBackListener iAdViewCallBackListener) {
        this.mIAdViewCallBackListener = iAdViewCallBackListener;
    }

    protected void initData(Context context, int type, String adId, int adIndex, boolean needCache, long cacheTime) {

        this.mCurrentType = type;
        this.mCacheTime = cacheTime;
        this.mAdId = adId;

        this.mNeedCache = needCache;
        this.mAdIndex = adIndex;

        this.setVisibility(VISIBLE);
        if (NetWorkUtils.isNetWorkAvailable(getContext())) {
            loadAndBindView(mAdIndex);
        } else {
            Log.e(TAG, "NativeAdViewLayout initData newWork error");
        }
    }

    public int getAdIndex() {
        return mAdIndex;
    }

    /**
     * 重新加载当前布局中的广告内容
     */

    public void updateNativeAd() {
        loadAndBindView(mAdIndex);
    }


    /**
     * 使用场景：1，不存在缓存时间的情况(SingleNativeBase)，外部调用使用; 2, 广告点击后需要立即拉取新的广告的情况
     */
    public void reloadAd() {
        Log.w(TAG, "reloadAd");
        NativeAdViewManager.getInstance().reloadAd(mContext, mCurrentAdViewBaseLayout, mAdId, mAdIndex, new IAdViewCallBackListener() {

            @Override
            public void adviewLoad(NativeAdBase nativeAdBase) {

                handleAddView(nativeAdBase);
                if (mCurrentAdViewBaseLayout != null && nativeAdBase != null) {
                    mCurrentAdViewBaseLayout.updateLayout(nativeAdBase);
                }

                mCurrentNativeAdBase = nativeAdBase;
                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewLoad(nativeAdBase);
                }
            }

            @Override
            public void adviewClick(NativeAdBase nativeAdBase) {
                Log.i(TAG, "loadAndBindView: adviewClick-->>" + nativeAdBase.isNeedReload());
                if (nativeAdBase.isNeedReload()) {
                    reloadAd();
                }

                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewClick(nativeAdBase);
                }
            }

            @Override
            public void adviewLoadError(NativeAdBase nativeAdBase) {

                Log.d(TAG, "reload adviewLoadError: ");

                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewLoadError(nativeAdBase);
                }

            }
        }, mCacheTime, mNeedCache);
    }

    protected void loadAndBindView(int index) {

        NativeAdBase tempNativeAd = NativeAdViewManager.getInstance().getOrCreateNativeAdViewByID(mContext, mCurrentAdViewBaseLayout, mAdId, index, new IAdViewCallBackListener() {

            @Override
            public void adviewLoad(NativeAdBase nativeAdBase) {
                Log.i(TAG, "loadAndBindView: adviewLoad");

                mCurrentNativeAdBase = nativeAdBase;

                handleAddView(nativeAdBase);
                if (mCurrentAdViewBaseLayout != null && nativeAdBase != null) {
                    mCurrentAdViewBaseLayout.updateLayout(nativeAdBase);
                }

                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewLoad(nativeAdBase);
                }
            }

            @Override
            public void adviewClick(NativeAdBase nativeAdBase) {
                Log.i(TAG, "loadAndBindView: adviewClick-->>" + nativeAdBase.isNeedReload());
                // 如果广告被点击，则需要立即更新
                if (nativeAdBase.isNeedReload()) {
                    reloadAd();
                }
                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewClick(nativeAdBase);
                }
            }

            @Override
            public void adviewLoadError(NativeAdBase nativeAdBase) {


                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewLoadError(nativeAdBase);
                }
            }
        }, mCacheTime, mNeedCache);

        if (mCurrentNativeAdBase != null && mCurrentNativeAdBase != tempNativeAd) {
            //不为空时,表示已经返回了,要么成功,要么失败
            Log.w(TAG, "arcade-->>execute-->>logic0");
            if (mCurrentNativeAdBase.isLoadSuccess()) {
                handleAddView(tempNativeAd);
                if (mCurrentAdViewBaseLayout != null) {
                    mCurrentAdViewBaseLayout.updateLayout(tempNativeAd);
                }
            }
        } else {
            Log.w(TAG, "arcade-->>execute-->>logic1");
            // layout创建时, 广告已提前被加载, 执行这里的逻辑
            if (mCurrentNativeAdBase == null && tempNativeAd != null) {
                mCurrentNativeAdBase = tempNativeAd;
                Log.w(TAG, "issuccess-->>" + (tempNativeAd.isLoadSuccess()));
                if (tempNativeAd.isLoadSuccess()) {
                    handleAddView(tempNativeAd);

                    if (mCurrentAdViewBaseLayout != null) {
                        mCurrentAdViewBaseLayout.updateLayout(tempNativeAd);
                    }
                }
            }
        }

    }

    public boolean getIsLoadSuccessed() {

        if (mCurrentNativeAdBase != null) {

            return mCurrentNativeAdBase.isLoadSuccess();
        }

        return false;
    }

    public NativeAdBase getNativeAdBase() {
        if (mCurrentAdViewBaseLayout != null) {
            return mCurrentAdViewBaseLayout.getNativeAdBase();
        }
        return null;
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

    protected void populateAdmobAdView(NativeAdBase nativeAdBase) {
        if (nativeAdBase == null) {
            return;
        }
        if (mCurrentNativeAdBase == null) {
            return;
        }
        if (nativeAdBase.getDataFrom() == NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT) {
            NativeContentAdView nativeContentAdView = new NativeContentAdView(getContext());
            nativeContentAdView.setHeadlineView(mCurrentAdViewBaseLayout.getTitleView());
            nativeContentAdView.setImageView(mCurrentAdViewBaseLayout.getImageView());
            nativeContentAdView.setBodyView(mCurrentAdViewBaseLayout.getBodyView());
            nativeContentAdView.setCallToActionView(mCurrentAdViewBaseLayout.getActionView());
            nativeContentAdView.setLogoView(mCurrentAdViewBaseLayout.getLogoView());
            nativeContentAdView.setAdvertiserView(mCurrentAdViewBaseLayout.getAdvertiserView());
            // Assign native ad object to the native view.
            setAdmobNativeAd(nativeAdBase, nativeContentAdView);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            removeParent(mCurrentAdViewBaseLayout);
            nativeContentAdView.addView(mCurrentAdViewBaseLayout, params);

            LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params1.gravity = Gravity.CENTER;
            addAdView(nativeContentAdView, nativeAdBase, params1);
        } else if (nativeAdBase.getDataFrom() == NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL) {
            NativeAppInstallAdView nativeAppInstallAdView = new NativeAppInstallAdView(getContext());
            nativeAppInstallAdView.setHeadlineView(mCurrentAdViewBaseLayout.getTitleView());
            nativeAppInstallAdView.setImageView(mCurrentAdViewBaseLayout.getImageView());
            nativeAppInstallAdView.setBodyView(mCurrentAdViewBaseLayout.getBodyView());
            nativeAppInstallAdView.setCallToActionView(mCurrentAdViewBaseLayout.getActionView());
            nativeAppInstallAdView.setIconView(mCurrentAdViewBaseLayout.getLogoView());
            // Assign native ad object to the native view.
            setAdmobNativeAd(nativeAdBase, nativeAppInstallAdView);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            removeParent(mCurrentAdViewBaseLayout);
            nativeAppInstallAdView.addView(mCurrentAdViewBaseLayout, params);


            LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params1.gravity = Gravity.CENTER;
            addAdView(nativeAppInstallAdView, nativeAdBase, params1);
        }
    }

    private void setAdmobNativeAd(NativeAdBase nativeAdBase, NativeAdView nativeAdView) {
        if (nativeAdBase instanceof RacingInstance) {
            RacingInstance racingInstance = (RacingInstance) nativeAdBase;
            nativeAdView.setNativeAd(racingInstance.getAdmobNativeAd());

        }

    }

    public void handleAddView(final NativeAdBase nativeAdBase) {
        if (nativeAdBase == null) {
            Log.e(TAG, "handleAddView nativeAdBase==null");
            return;
        }

        removeAllViews();
        @NativeAdBase.DataFrom int dataFrom = nativeAdBase.getDataFrom();
        String admobAdType = nativeAdBase.getAdCacheNode().admobAdType;
        boolean isDataFromAdmob = isDataFromAdmob(dataFrom);

        if (nativeAdBase instanceof RacingInstance && isDataFromAdmob) {
            RacingInstance racingInstance = (RacingInstance) nativeAdBase;
            Log.w(TAG, "RacingInstance, addView-->>admob, dataFrom-->>" + nativeAdBase.dataFrom2String(nativeAdBase.getDataFrom()));
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            if (admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_EXPRESS)) {
                removeParent(racingInstance.getAdmobNativeExpressAdView());
                addAdView(racingInstance.getAdmobNativeExpressAdView(), nativeAdBase, params);
            } else if (admobAdType.equals(AdCacheNode.AD_TYPE_BANNER)) {
                removeParent(racingInstance.getAdmobBanner());
                addAdView(racingInstance.getAdmobBanner(), nativeAdBase, params);
            } else if (admobAdType.equals(AdCacheNode.ADMOB_AD_TYPE_NATIVE_ADVANCED)) {
                populateAdmobAdView(nativeAdBase);
            }
        } else {
            if (mCurrentAdViewBaseLayout != null) {
                Log.w(TAG, "addView-->> dataFrom-->>" + nativeAdBase.dataFrom2String(nativeAdBase.getDataFrom()));
                removeParent(mCurrentAdViewBaseLayout);
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER;
                addAdView(mCurrentAdViewBaseLayout, nativeAdBase, params);
            } else {
                Log.e(TAG, "mCurrentAdViewBaseLayout ==null");
            }
        }
    }

    private void addAdView(View view, NativeAdBase nativeAdBase, LayoutParams params) {
        AdviewContainer adviewContainer = new AdviewContainer(getContext());

        // 全局判断是否显示关闭广告的按钮
        if(NativeAdViewManager.isShowCloseAdBtn){
            isContainsCloseBtn = NativeAdViewManager.isShowCloseAdBtn;
        }


        adviewContainer.addAdView(view, nativeAdBase, isContainsCloseBtn, adCloseListener,isNotShowAdFlag);


        Log.e("zcw","测试addView，广告库adCloseListener"+adCloseListener);

        Log.e("zcw","测试addView，广告库isContainsCloseBtn"+isContainsCloseBtn);

        addView(adviewContainer, params);
    }

    private boolean isDataFromAdmob(@NativeAdBase.DataFrom int dataFrom) {
        return (dataFrom == NativeAdBase.DataFrom.FROM_ADMOB_BANNER || dataFrom == NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_E || dataFrom == NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL || dataFrom == NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT);
    }

    public boolean isContainsCloseBtn() {
        return isContainsCloseBtn;
    }

    public void setContainsCloseBtn(boolean containsCloseBtn) {
        isContainsCloseBtn = containsCloseBtn;
    }

    public AdCloseListener getAdCloseListener() {
        return adCloseListener;
    }

    public void setAdCloseListener(AdCloseListener adCloseListener) {
        this.adCloseListener = adCloseListener;
    }

    public boolean isNotShowAdFlag() {
        return isNotShowAdFlag;
    }

    public void setNotShowAdFlag(boolean notShowAdFlag) {
        isNotShowAdFlag = notShowAdFlag;
    }
}
