package com.example.administrator.addemo.adloadhelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;
import com.example.administrator.addemo.adloadhelper.utils.CommonUtils;
import com.example.administrator.addemo.adloadhelper.utils.NetWorkUtils;
import com.example.administrator.addemo.adloadhelper.utils.PreferenceHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cz.msebera.android.httpclient.Header;

import static com.example.administrator.addemo.adloadhelper.AdCacheNode.AD_LOADING_TIME_OUT;
import static com.example.administrator.addemo.adloadhelper.AdCacheNode.KEY_UPDATE_CACHE_TIME;

/**
 * Created by ivorange on 16/5/24.
 */
public class NativeAdViewManager {

    //4个选项: fb  baidu  admob altamob 默认: null
    public static String DEBUG_ONLY_ONE = null;
    private static final String TAG = NativeAdViewManager.class.getName();


    private static NativeAdViewManager mNativeManagerInstance = null;
    private static ConcurrentHashMap<String, AdCacheNode> mAllCacheTimes = new ConcurrentHashMap<>();
    private Context applicationContext;

    /**
     * 已经加载成功的 广告实例集合
     */
    private ConcurrentHashMap<String, NativeAdBase> mAllNativeAdBaseMaps = new ConcurrentHashMap<>();
    // 防止正在加载的时候又重新发起请求
    private ConcurrentHashMap<String, NativeAdBase> mLoadingMaps = new ConcurrentHashMap<>();
    private UpdateIntervalTimeReceiver mUpdateIntervalTimeReceiver;

    //服务器配置文件url
    private String mCacheTimeUrl = null;
    //本地配置文件的名字
    private String localJson = "";

    private long mLastUpdateTime = 0l;
    private com.google.android.gms.ads.InterstitialAd admobInterstitialAd = null;

    private Handler handler = new Handler();

    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public static boolean isShowCloseAdBtn = false;//全局判断是否关闭

    /*
    广告系统需要注意的几个事项：

    1. 广告第一次请求失败后，不要立即重新请求，应该间隔至少15秒，不然会被FB拒绝而导致本次请求失败。
    2. 广告第一次请求成功，后续请求可能会失败，所以需要做重连处理。
    3. 广告第一次请求还没有返回结果，又重新发送请求，从而导致高请求量和无效展示，收益变低。有些频繁打开的页面会出现这种问题，所以广告请求这块需要做同步锁。
    4. 不要重复利用广告ID，如果广告位发生调整，一定重新换一个ID，不然新老数据混在一起导致收益变低。
     */
    private String admobInterstitialId = null;

    /**
     * 使用场景说明：
     * 1，同一个广告ID，一次拉取多个广告的情况，此情况需要预加载（cacheCompositeAds），创建（new NativeAdViewLayout）,
     * 刷新（updateNativeAd）
     * 2, 单个广告ID, 加载一个广告view的情况，创建（new NativeAdViewLayout），有缓存时间的（刷新updateNativeAd）
     * 无缓存时间的（刷新reloadAd）
     */
    private NativeAdViewManager() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .build();
    }

    public static NativeAdViewManager getInstance() {
        if (mNativeManagerInstance == null) {
            mNativeManagerInstance = new NativeAdViewManager();
        }
        return mNativeManagerInstance;
    }

    /**
     * 初始化Manager 类
     *
     * @param appContext 上下文
     * @param url        当前配置文件下载URL
     */
    public void init(Context appContext, String url, String fileName) {

        this.applicationContext = appContext;
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        PreferenceHelper.checkSetInstalledTime(applicationContext);
        //初始化ImageLoader
        initImageLoader(appContext);

        mUpdateIntervalTimeReceiver = new UpdateIntervalTimeReceiver();

        mCacheTimeUrl = url;
        this.localJson = fileName;
//        facebookInterstitialAds.clear();
        //初始化配置文件
        initAndUpdateCacheTime();
        registerReceiver();
    }


    /**
     * 初始化Manager 类
     *
     * @param appContext     上下文
     * @param url            当前配置文件下载URL
     * @param fileName       本地配置文件
     * @param isCloseBtnShow 是否显示广告关闭的按钮
     */
    public void init(Context appContext, String url, String fileName, boolean isCloseBtnShow) {

        this.applicationContext = appContext;
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        PreferenceHelper.checkSetInstalledTime(applicationContext);
        //初始化ImageLoader
        initImageLoader(appContext);

        mUpdateIntervalTimeReceiver = new UpdateIntervalTimeReceiver();

        mCacheTimeUrl = url;
        this.localJson = fileName;
//        facebookInterstitialAds.clear();
        //初始化配置文件
        initAndUpdateCacheTime();
        registerReceiver();

        this.isShowCloseAdBtn = isCloseBtnShow;
    }

    public AdCacheNode getAdCacheNode(String strAdsId) {
        return mAllCacheTimes.get(strAdsId);
    }


    public void updateAllAdsCacheTime() {

        if (NetWorkUtils.isNetWorkAvailable(applicationContext)) {
            if (mCacheTimeUrl == null || mCacheTimeUrl.length() == 0) {
                return;
            }
            AdsRestClient.get(mCacheTimeUrl, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    if (statusCode == 200) {
                        try {
                            json2Node(response);
                            if (response.has(KEY_UPDATE_CACHE_TIME)) {
                                if (applicationContext != null) {
                                    //保存配置文件的更新间隔
                                    long temp = response.optLong(KEY_UPDATE_CACHE_TIME, 0);
                                    PreferenceHelper.setAdConfigInterval(applicationContext, temp * 1000L);
                                }
                            }
                            if (applicationContext != null) {
                                //保存配置文件
                                PreferenceHelper.setAdConfig(applicationContext, response.toString());
                            }

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        } else {
            Log.e(TAG, "updateAllAdsCacheTime newWork error");
        }
    }

    private void loadAdmobInterstitial(final Context context, final String fbId, String admobId) {
        if (TextUtils.isEmpty(admobId)) {
            Log.e(TAG, "admobId kong");
            return;
        }
        if (context == null) {
            Log.e(TAG, "Interstitial context =null ");
            return;
        }
        admobInterstitialAd = new com.google.android.gms.ads.InterstitialAd(context);
        admobInterstitialAd.setAdUnitId(admobId);
        admobInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d(TAG, "admobInterstitialAd onAdClosed");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "admobInterstitialAd onAdLoaded");

                if (admobInterstitialAd != null) {
                    admobInterstitialAd.show();
                    PreferenceHelper.setInterstitialShowTime(context.getApplicationContext(), fbId, System.currentTimeMillis());
                }
            }
        });

        if (admobInterstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                    .build();
            admobInterstitialAd.loadAd(adRequest);
        }
    }


    public boolean isCacheAds(final String adId) {
        NativeAdBase tempNativeAdBase = mAllNativeAdBaseMaps.get(adId);
        if (tempNativeAdBase != null) {
            return true;
        }

        NativeAdBase loadBean = mLoadingMaps.get(adId);
        if (loadBean != null) {
            return true;
        }

        return false;
    }

    public boolean isLoadingAds(final String adId) {
        NativeAdBase loadBean = mLoadingMaps.get(adId);
        if (loadBean != null) {
            return true;
        }

        return false;
    }

    /**
     * 预加载一个新的 NativeAdBase对象
     *
     * @param context         activity的context
     * @param adId            adId 当前广告位ID
     * @param currentListener
     */
    public void cacheNativeAd(Context context, AdViewBaseLayout container, final String adId, final IAdViewCallBackListener currentListener) {
        if (!AdsRestClient.isNetWorkAvailable(context)) {
            Log.e(TAG, "netWork error ,do not cacheNativeAd");
            return;
        }

        NativeAdBase tempNativeAdBase = mAllNativeAdBaseMaps.get(adId);
        if (tempNativeAdBase != null) {
            Log.e(TAG, "cacheNativeAd mAllNativeAdBaseMaps contains");
            return;
        }

        Log.d(TAG, "cacheNativeAd: " + adId);

        NativeAdBase loadBean = mLoadingMaps.get(adId);
        if (loadBean != null) {
            Log.e(TAG, "cacheNativeAd mLoadingMaps contains");
            return;
        }

        NativeAdBase itemNative = NativeAdBase.createAds(context, getAdCacheNode(adId), container, new IAdViewCallBackListener() {

            @Override
            public void adviewLoad(NativeAdBase nativeAdBase) {
                Log.d(TAG, "cacheNativeAd: adviewLoad " + adId);
                NativeAdBase nativeAdBase1 = mAllNativeAdBaseMaps.get(adId);
                if (nativeAdBase1 != null) {
                    nativeAdBase1.destoryAdView();
                    mAllNativeAdBaseMaps.remove(adId);
                }
                mAllNativeAdBaseMaps.put(adId, nativeAdBase);

                mLoadingMaps.remove(adId);

                if (currentListener != null) {
                    currentListener.adviewLoad(nativeAdBase);
                }
            }

            @Override
            public void adviewLoadError(NativeAdBase nativeAdBase) {
                Log.d(TAG, "cacheNativeAd: adviewLoad " + adId);
                mLoadingMaps.remove(adId);

                if (currentListener != null) {
                    currentListener.adviewLoadError(nativeAdBase);
                }
            }

            @Override
            public void adviewClick(NativeAdBase nativeAdBase) {
                Log.d(TAG, "cacheNativeAd: adviewClick " + adId);
                if (currentListener != null) {
                    currentListener.adviewClick(nativeAdBase);
                }
            }
        });

        if (itemNative != null) {
            mLoadingMaps.put(adId, itemNative);
            itemNative.loadAds();
        }

    }


    public NativeAdBase getOrCreateNativeAdViewByID(Context context, AdViewBaseLayout container,
                                                    String adId,
                                                    int adIndex,
                                                    IAdViewCallBackListener listener,
                                                    long cacheTime,
                                                    boolean needCache) {

        if (adIndex != -1) {
            Log.e(TAG, " 参数异常-->>" + adIndex);
            return null;
        }


        NativeAdBase tempNativeAdBase = mAllNativeAdBaseMaps.get(adId);

        if (tempNativeAdBase != null) {

            if (tempNativeAdBase.isExpire()) {
                Log.e(TAG, " arcade-->>createSingleNativeAdBase contains but timeout,id-->>" + adId);
                createSingleNativeAdBase(context, container, adId, needCache, cacheTime, listener);
            } else {
                Log.e(TAG, " arcade-->>createSingleNativeAdBase contains no timeout,update listener,id-->>" + adId);
                tempNativeAdBase.setCurrentListener(listener);
            }

        } else {
            Log.e(TAG, " arcade-->>mAllNativeAdBaseMaps not contains,id-->>" + adId);
            tempNativeAdBase = createSingleNativeAdBase(context, container, adId, needCache, cacheTime, listener);
        }


        return tempNativeAdBase;
    }

    /**
     * 使用场景：1，不存在缓存时间的情况(SingleNativeBase)，外部调用使用; 2, 广告点击后需要立即拉取新的广告的情况
     *
     * @param adID
     * @param adIndex
     * @param listener
     * @param cacheTime
     * @param needCache
     */
    public void reloadAd(Context context, AdViewBaseLayout container, String adID, int adIndex, IAdViewCallBackListener listener, long cacheTime, boolean needCache) {


        // 对于reload的话，强制更新，不管正在请求的是否返回
        //
        NativeAdBase loadBean = mLoadingMaps.get(adID);
        if (loadBean != null) {

            mLoadingMaps.remove(adID);
            loadBean.destoryAdView();
        }

        if (adIndex == -1) {
            createSingleNativeAdBase(context, container, adID, needCache, cacheTime, listener);
        } else {
            Log.e(TAG, "reload adIndex != -1");
        }
    }

    private NativeAdBase createSingleNativeAdBase(Context context, AdViewBaseLayout container, final String adId, final boolean needCache, long cachetime, IAdViewCallBackListener currentListener) {

        final IAdViewCallBackListener listener = currentListener;

        boolean bAllMedia = false;

        if (mAllCacheTimes.get(adId) != null) {

            AdCacheNode aNode = mAllCacheTimes.get(adId);

            cachetime = aNode.cacheTime;

            bAllMedia = aNode.adMediaAll;
        }

        NativeAdBase loadBean = mLoadingMaps.get(adId);
        if (loadBean != null) {
            long interval = System.currentTimeMillis() - loadBean.getmLoadStartTime();
            if (interval < AD_LOADING_TIME_OUT) {
                loadBean.setCurrentListener(listener);
                //没有超时,loading中
                Log.d(TAG, "arcade-->>createSingleNativeAdBase,from mLoadingMaps: " + adId);
                return loadBean;
            } else {
                //已经超时了
                Log.d(TAG, "arcade-->>createSingleNativeAdBase,new timeout: " + adId);

                mLoadingMaps.remove(adId);

                loadBean.destoryAdView();
            }
        } else {
            Log.d(TAG, "arcade-->>createSingleNativeAdBase: form new filter " + adId);
        }

        NativeAdBase tempNativeAdBean = NativeAdBase.createAds(context, getAdCacheNode(adId), container, new IAdViewCallBackListener() {

            @Override
            public void adviewLoad(NativeAdBase nativeAdBase) {
                Log.i(TAG, "arcade-->>nativeAdViewManager adviewLoad");
                if (needCache) {
                    NativeAdBase nativeAdBaseItem = mAllNativeAdBaseMaps.get(adId);
                    if (nativeAdBaseItem != null && nativeAdBaseItem != nativeAdBase) {
                        nativeAdBaseItem.destoryAdView();
                        mAllNativeAdBaseMaps.remove(adId);
                    }
                    mAllNativeAdBaseMaps.put(adId, nativeAdBase);
                }
                mLoadingMaps.remove(adId);

                if (listener != null) {
                    listener.adviewLoad(nativeAdBase);
                } else {
                    Log.e(TAG, "arcade-->>nativeAdViewManager adviewLoad listener==null");
                }
            }

            @Override
            public void adviewLoadError(NativeAdBase nativeAdBase) {
                Log.i(TAG, "arcade-->>nativeAdViewManager adviewLoadError");
                // 广告加载失败后，需要到下一个更新周期才开始重试
                NativeAdBase nativeAdBaseItem = mAllNativeAdBaseMaps.get(adId);
                if (nativeAdBaseItem != null) {
                    nativeAdBaseItem.resetLastTime();
                }

                Log.d(TAG, "adviewLoadError: " + adId);
                mLoadingMaps.remove(adId);

                if (listener != null) {
                    listener.adviewLoadError(nativeAdBase);
                } else {
                    Log.e(TAG, "nativeAdViewManager adviewLoadError listener==null");
                }
            }

            @Override
            public void adviewClick(NativeAdBase nativeAdBase) {

                if (listener != null) {
                    listener.adviewClick(nativeAdBase);
                } else {
                    Log.e(TAG, "nativeAdViewManager adviewClick listener==null");
                }
            }
        });

        if (tempNativeAdBean != null) {
            mLoadingMaps.put(adId, tempNativeAdBean);
            tempNativeAdBean.loadAds();
        } else {
            Log.e(TAG, "error-->>tempNativeAdBean==null");
        }
        return tempNativeAdBean;
    }


    private String idToDU() {
        String strInit = "0";
        if (mAllCacheTimes.size() == 0)
            return strInit;

        try {
            JSONObject rootNode = new JSONObject();
            JSONArray nativeNode = new JSONArray();
            Iterator iterator = mAllCacheTimes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                AdCacheNode adNode = (AdCacheNode) entry.getValue();
                JSONObject jsItem = new JSONObject();
                jsItem.put("pid", (adNode.baiduID == null ? "" : adNode.baiduID));
                JSONArray fbNode = new JSONArray();

                //fbNode.put(adNode.fbId);
                fbNode.put("000000000_000000000");
                jsItem.put("fbids", fbNode);
                nativeNode.put(jsItem);
            }

            rootNode.put("native", nativeNode);
            strInit = rootNode.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return strInit;
    }


    private String getCacheMaps() {
        String cacheS = PreferenceHelper.getAdConfig(applicationContext);
        if (!TextUtils.isEmpty(cacheS)) {
            return cacheS;
        }
        return CommonUtils.getFromAssets(applicationContext, localJson);
    }

    public class UpdateIntervalTimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long expireTime = PreferenceHelper.getAdConfigInterval(applicationContext);
            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastUpdateTime > expireTime) {
                //Log.i(TAG, "updateAllAdsCacheTime()");
                updateAllAdsCacheTime();
                mLastUpdateTime = currentTime;
            }
        }
    }
    /*
        用于后台缓存用的广告过期时间数据缓存
     */
    public Context getApplicationContext() {
        return applicationContext;
    }


    private void initAndUpdateCacheTime() {

        try {
            String strNode = getCacheMaps();

            Log.d(TAG, "initAndUpdateCacheTime: " + strNode);
            if (strNode != null) {
                JSONObject rootNode = new JSONObject(strNode);
                json2Node(rootNode);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void json2Node(JSONObject rootNode) throws JSONException {
        if (rootNode.has(AdCacheNode.KEY_ADS_DATA)) {

            mAllCacheTimes.clear();

            JSONArray jsonArray = rootNode.optJSONArray(AdCacheNode.KEY_ADS_DATA);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);


                AdCacheNode adNode = new AdCacheNode(jsonObject);

                mAllCacheTimes.put(adNode.fbId, adNode);

                //因为配置文件从服务器获取后还会执行一次的
                NativeAdBase nativeAdBase = mAllNativeAdBaseMaps.get(adNode.fbId);
                if (nativeAdBase != null) {
                    Log.i(TAG, "adNode.cacheTime1=" + adNode.cacheTime);
                    nativeAdBase.getAdCacheNode().cacheTime = adNode.cacheTime;
                }
            }

            Log.d(TAG, "initAndUpdateCacheTime: " + mAllCacheTimes.size());
        }
    }

    private void initImageLoader(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator()).memoryCacheSize(10 *
                            1024 * 1024)
                    .diskCacheSize(20 * 1024 * 1024) //
                    .diskCacheFileCount(600) // 缓存的文件数量
                    .tasksProcessingOrder(QueueProcessingType.LIFO).build();
            ImageLoader.getInstance().init(config);
        }
    }

    private void registerReceiver() {

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        mIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        mIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        applicationContext.registerReceiver(mUpdateIntervalTimeReceiver, mIntentFilter);
    }

    public static void destroyAd(String fbId) {
        if (fbId == null) {
            Log.e(TAG, "destroyAd, fbId==null");
            return;
        }
        NativeAdViewManager nativeAdViewManager = NativeAdViewManager.getInstance();
        if (nativeAdViewManager == null) {
            Log.e(TAG, "NativeAdViewManager==null");
            return;
        }
        NativeAdBase adSuccess = nativeAdViewManager.mAllNativeAdBaseMaps.get(fbId);
        if (adSuccess != null) {
            adSuccess.destoryAdView();
            nativeAdViewManager.mAllNativeAdBaseMaps.remove(fbId);
        }
        NativeAdBase adLoading = nativeAdViewManager.mLoadingMaps.get(fbId);
        if (adLoading != null) {
            adLoading.destoryAdView();
            nativeAdViewManager.mLoadingMaps.remove(fbId);
        }

    }

    public DisplayImageOptions getOptions() {
        return options;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
