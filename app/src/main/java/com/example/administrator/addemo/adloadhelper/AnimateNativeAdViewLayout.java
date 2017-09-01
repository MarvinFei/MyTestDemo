package com.example.administrator.addemo.adloadhelper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;
import com.example.administrator.addemo.adloadhelper.utils.NetWorkUtils;


public class AnimateNativeAdViewLayout extends NativeAdViewLayout implements View.OnClickListener {

    private static final String TAG = AnimateNativeAdViewLayout.class.getName();
    //damontodo 这个类的成员变量莫名会被清空掉, 不知道为什么.暂找不到原因, 所以这里默认值设置为 true
    private boolean mAnimateIsStopFlag = true;
    private View mAnimateView;
    private RelativeLayout mAnimateRunView;
    private RelativeLayout mAdsLoadFailedView;
    private TextView mTxtLoad;
    private TextView mTxtError;

    private boolean mCurrentAdsLoadSuccessFlag = false;

    public AnimateNativeAdViewLayout(Context context) {
        super(context);
    }


    public AnimateNativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, int adIndex, boolean needCache) {
        super(context, currentAdViewBaseLayout, adviewID, adIndex, needCache);
    }

    public AnimateNativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, int adIndex) {
        super(context, currentAdViewBaseLayout, adviewID, adIndex);
    }

    public AnimateNativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, boolean needCache) {
        super(context, currentAdViewBaseLayout, adviewID, needCache);
    }

    public AnimateNativeAdViewLayout(Context context, AdViewBaseLayout currentAdViewBaseLayout, String adviewID, IAdViewCallBackListener iAdViewCallBackListener) {
        super(context, currentAdViewBaseLayout, adviewID, iAdViewCallBackListener);
    }

    public void setBackgroundAndFont() {

        if (mTxtLoad != null) {
            mTxtLoad.setTextColor(getResources().getColor(R.color.white));
        }

        if (mTxtError != null) {
            mTxtError.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void setBackgroundTransparent() {
        try {
            if (mAnimateView != null) {
                int animateBackgroundColor = Color.parseColor("#32ffffff");
                mAnimateView.setBackgroundColor(animateBackgroundColor);
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void initData(Context context, int type, String adId, int adIndex, boolean needCache, long cacheTime) {
        this.mCurrentType = type;
        this.mCacheTime = cacheTime;
        this.mAdId = adId;
        this.mNeedCache = needCache;
        this.mAdIndex = adIndex;

        this.setVisibility(VISIBLE);
        if (NetWorkUtils.isNetWorkAvailable(getContext())) {
            updateNativeAd();
        }
    }

    @Override
    public void updateNativeAd() {

//        Log.d(TAG,"updateNativeAd-->>"+NativeAdViewManager.getInstance().isCacheAds(mAdId));
        // 先开始加载动画
        if (!NativeAdViewManager.getInstance().isCacheAds(mAdId)) {
            startAnimateBeforeAdLoad();
        } else {
            if (NativeAdViewManager.getInstance().isLoadingAds(mAdId) && mAnimateView == null) {
                startAnimateBeforeAdLoad();
            }
        }
        loadAndBindView(mAdIndex);
    }

    @Override
    protected void loadAndBindView(int index) {
        NativeAdBase tempNativeAd = NativeAdViewManager.getInstance().getOrCreateNativeAdViewByID(
                mContext, mCurrentAdViewBaseLayout, mAdId, index, new IAdViewCallBackListener() {

                    @Override
                    public void adviewLoad(NativeAdBase nativeAdBase) {
                        Log.d(TAG, "arcade-->>loadAndBindView adviewLoad");
                        mCurrentAdsLoadSuccessFlag = true;
                        mCurrentNativeAdBase = nativeAdBase;

                        stopAnimate();
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
                        Log.i(TAG, "loadAndBindView: adviewClick-->>"+nativeAdBase.isNeedReload());
                        if (nativeAdBase.isNeedReload()) {
                            reloadAd();
                        }
                        if (mIAdViewCallBackListener != null) {
                            mIAdViewCallBackListener.adviewClick(nativeAdBase);
                        }
                    }

                    @Override
                    public void adviewLoadError(NativeAdBase nativeAdBase) {
                        mCurrentAdsLoadSuccessFlag = false;
                        stopAnimate();
                        Log.d(TAG, "arcade-->>adviewLoadError: stopAnimate");

                        if (mIAdViewCallBackListener != null) {
                            mIAdViewCallBackListener.adviewLoadError(nativeAdBase);
                        }

                    }
                }, mCacheTime, mNeedCache);
        if (mCurrentNativeAdBase != null && mCurrentNativeAdBase != tempNativeAd) {
            //不为空时,表示已经返回了,要么成功,要么失败
            Log.w(TAG, "arcade-->>execute-->>logic0");
            if (mCurrentNativeAdBase.isLoadSuccess()) {
                mCurrentAdsLoadSuccessFlag = true;
                stopAnimate();
                handleAddView(tempNativeAd);
                if (mCurrentAdViewBaseLayout != null) {
                    mCurrentAdViewBaseLayout.updateLayout(tempNativeAd);
                }
            }
        } else {
            // layout创建时, 广告已提前被加载, 执行这里的逻辑
//            Log.w(TAG,"test0-->>"+(mCurrentNativeAdBase == null)+", "+(mCurrentAdViewBaseLayout == null));
            //
            Log.w(TAG, "arcade-->>execute-->>logic1");
            if (mCurrentNativeAdBase == null && tempNativeAd != null) {
//                Log.w(TAG,"test1-->>"+tempNativeAd.isLoadSuccess());
                mCurrentNativeAdBase = tempNativeAd;
                Log.w(TAG,"issuccess-->>"+(tempNativeAd.isLoadSuccess()));
                if (tempNativeAd.isLoadSuccess()) {
                    handleAddView(tempNativeAd);
                    if (mCurrentAdViewBaseLayout != null) {
                        mCurrentAdViewBaseLayout.updateLayout(tempNativeAd);
                    }
                }
            }
        }
    }

    private void startAnimateBeforeAdLoad() {
        // 如果广告未加载成功，先显示加载动画
        mAnimateView = LayoutInflater.from(getContext()).inflate(R.layout.animate_before_ad_load_success, null);
        mAnimateRunView = (RelativeLayout) mAnimateView.findViewById(R.id.ad_wait_animate);
        mAnimateRunView.setVisibility(View.VISIBLE);
        //mProgressBarCircularIndeterminate = (ProgressBarCircularIndeterminate) mAnimateView.findViewById(R.id.indeterminate_progress);
        //mProgressBar = (ProgressBar) mAnimateView.findViewById(R.id.pb_adloadhelper_loading_view);
        mAdsLoadFailedView = (RelativeLayout) mAnimateView.findViewById(R.id.animate_for_ad_load_failed);
        mAdsLoadFailedView.setVisibility(View.GONE);
        mTxtLoad = (TextView) mAnimateView.findViewById(R.id.txt_loading);
        mTxtError = (TextView) mAnimateView.findViewById(R.id.txt_error);
        mAdsLoadFailedView.setOnClickListener(this);
        mAnimateView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(mAnimateView);

        //startTimer();
    }

    public void reloadAd() {


        NativeAdViewManager.getInstance().reloadAd(mContext, mCurrentAdViewBaseLayout, mAdId, mAdIndex, new IAdViewCallBackListener() {

            @Override
            public void adviewLoad(NativeAdBase nativeAdBase) {
                Log.i(TAG, "reloadAd adviewLoad");
                mCurrentAdsLoadSuccessFlag = true;
                stopAnimate();
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
                Log.i(TAG, "loadAndBindView: adviewClick-->>"+nativeAdBase.isNeedReload());
                if (nativeAdBase.isNeedReload()) {
                    reloadAd();
                }
                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewClick(nativeAdBase);
                }
            }

            @Override
            public void adviewLoadError(NativeAdBase nativeAdBase) {


                mCurrentAdsLoadSuccessFlag = false;
                stopAnimate();

                if (mIAdViewCallBackListener != null) {
                    mIAdViewCallBackListener.adviewLoadError(nativeAdBase);
                }

            }
        }, mCacheTime, mNeedCache);
    }

    private void stopAnimate() {
        if (mAnimateView != null) {
            mAnimateIsStopFlag = true;
            //mProgressBarCircularIndeterminate.stopAnimate();
            if (mCurrentAdsLoadSuccessFlag) {
                //mProgressBarCircularIndeterminate = null;
                //mProgressBar.setVisibility(View.GONE);
                mAnimateView.setVisibility(View.GONE);
            } else {
                mAnimateRunView.setVisibility(INVISIBLE);
                mAdsLoadFailedView.setVisibility(VISIBLE);
                mTxtError.setText(R.string.load_failed_and_reload);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String tmpString = mTxtError.getText().toString();
        String compareString = getResources().getString(R.string.load_failed_and_reload);
        Log.d(TAG, tmpString.equals(compareString) + ", mAnimateIsStopFlag-->>" + mAnimateIsStopFlag + ", mCurrentAdsLoadSuccessFlag-->>" + mCurrentAdsLoadSuccessFlag);
        if (tmpString.equals(compareString) && mAnimateIsStopFlag && !mCurrentAdsLoadSuccessFlag) {
            mAnimateIsStopFlag = false;
            //startTimer();
            reloadAd();
            //mProgressBarCircularIndeterminate.restartAnimate();
            mAnimateRunView.setVisibility(VISIBLE);
            mAdsLoadFailedView.setVisibility(INVISIBLE);
        }
    }


    public boolean getCurrentAdsLoadSuccessFlag() {
        return mCurrentAdsLoadSuccessFlag;
    }
}