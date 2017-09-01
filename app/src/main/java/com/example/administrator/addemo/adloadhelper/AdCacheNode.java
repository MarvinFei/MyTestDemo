package com.example.administrator.addemo.adloadhelper;

import org.json.JSONObject;

/**
 * Created by damon on 21/03/2017.
 */

public class AdCacheNode {

    ///////////////////////////////////////////////////////////////////////////
    // 广告配置文件 key
    ///////////////////////////////////////////////////////////////////////////
    // 配置文件的更新时间  (默认为0: 不能省略)
    public final static String KEY_UPDATE_CACHE_TIME = "updateCacheTime";
    // 广告data数组根节点key (默认为null: 不能省略)
    public final static String KEY_ADS_DATA = "AdViewId";
    // 广告加载类型(默认为fb_admob_baidu: 可以省略)
    public final static String KEY_AD_LOAD_TYPE = "ad_load_type";
    // FB的广告ID (默认为null: 不能省略)
    public final static String KEY_FB_ID = "fb_id";
    // 百度的广告ID (默认为null: 可以省略, 当判断为空时不加载它)
    public final static String KEY_BAIDU_ID = "du_id";
    // altamob广告ID (默认为null: 可以省略, 当判断为空时不加载它)
    public final static String KEY_ALTAMOB_ID = "altamob_id";
    // admob广告ID (默认为null: 可以省略, 当判断为空时不加载它)
    public final static String KEY_ADMOB_ID = "admob_id";
    //admob广告类型(默认为native: 可以省略)
    public final static String KEY_ADMOB_AD_TYPE = "admob_ad_type";
    //facebook广告类型(默认为native: 可以省略)
    public final static String KEY_FB_AD_TYPE = "fb_ad_type";
    //baidu广告类型(默认为native: 可以省略)
    public final static String KEY_BAIDU_AD_TYPE = "baidu_ad_type";
    //altamob广告类型(默认为native: 可以省略)
    public final static String KEY_ALTAMOB_AD_TYPE = "altamob_ad_type";
    // 该广告位的所有广告的缓存时间 单位:秒 (默认为1200: 可以省略)
    public final static String KEY_CACHE_TIME = "cache_time";
    // 该广告位的所有广告是否在点击之后立即重新加载新广告 (默认为true: 可以省略)
    public final static String KEY_CLICK_TO_RELOAD = "click_to_reload";
    //fb 是否全部缓存, (默认为false: 可以省略)
    public final static String KEY_FB_MEDIA_ALL = "ad_media_all";
    //是否多少天后全部转给他其它 (默认为false: 可以省略)
    public final static String KEY_IS_X_DAYS_LATER_TO_OTHER = "flag_5days_later_to_bd";
    //多少天后全部转给他其它 (默认为3: 可以省略)
    public final static String KEY_X_DAYS_LATER_TO_OTHER = "flag_to_bd_days";
    //该广告位的描述内容 (默认为null: 可以省略)
    public final static String KEY_DESCRIBE = "describe";

    ///////////////////////////////////////////////////////////////////////////
    // 常量
    ///////////////////////////////////////////////////////////////////////////

    //广告加载类型 关闭该广告
    public final static String LOAD_TYPE_NONE = "close";
    //广告加载类型 只加载fb
    public final static String LOAD_TYPE_FB = "only_fb";
    //广告加载类型 只加载baidu
    public final static String LOAD_TYPE_BAIDU = "only_baidu";
    //广告加载类型 只加载admob
    public final static String LOAD_TYPE_ADMOB = "only_admob";
    //广告加载类型 fb admob baidu 竞速 (默认)
    public final static String LOAD_TYPE_FB_ADMOB_BAIDU = "fb_admob_baidu";
    //广告加载类型 fb admob altamob 竞速
    public final static String LOAD_TYPE_FB_ADMOB_ALTAMOB = "fb_admob_altamob";
    //广告加载类型 fb bd altamob 竞速(V3 版本)
    public final static String LOAD_TYPE_FB_BAIDU_ALTAMOB = "fb_baidu_altamob";
    //广告加载类型 fb bd  竞速 (V3 版本)
    public final static String LOAD_TYPE_FB_BAIDU = "fb_baidu";
    //广告加载类型 admob altamob 竞速
    public final static String LOAD_TYPE_ADMOB_ALTAMOB = "admob_altamob";





    public final static String LOAD_TYPE_INTERSTITIAL = "interstitial";


    //通用广告类型 banner
    public final static String AD_TYPE_BANNER = "banner";
    //通用广告类型 native
    public final static String AD_TYPE_NATIVE = "native";
    //通用广告类型 插屏
    public final static String AD_TYPE_INTERSTITIAL = "interstitial";
    //admob 广告类型 native express
    public final static String ADMOB_AD_TYPE_NATIVE_EXPRESS = "nativeE";
    //admob 广告类型 native advanced
    public final static String ADMOB_AD_TYPE_NATIVE_ADVANCED = "nativeA";

    //默认多少天后只加载XXX广告(前提是必须开启:KEY_IS_X_DAYS_LATER_TO_OTHER)
    public final static int DEFAULT_DAYS_TO_OTHER = 3;
    //默认更新配置文件的时间间隔
    public static final long UPDATE_AD_CACHE_TIME_DEFAULT = 1000L * 60 * 60 * 2;
    //插屏广告默认缓存间隔,单位毫秒
    public static final long DEFAULT_INTERSTITIAL_CACHE_INTERVAL = 1000L * 60 * 60 * 24;
    //默认error的再次请求时间间隔
    public static final long AD_ERROR_OUTIME = 1000L * 10;
    //默认loading的超时时间
    public static final long AD_LOADING_TIME_OUT = 1000L * 20;
    //默认缓存的广告时间为20分钟
    public static final long DEFAULT_AD_CACHE_TIME = 1000L * 60 * 20;
    //默认为fb_admob_baidu
    public String adLoadType = LOAD_TYPE_FB_ADMOB_BAIDU;
    //默认为null
    public String fbId = null;
    //默认为null
    public String baiduID = null;
    //默认为null
    public String altamobId = null;
    //默认为null
    public String admobId = null;
    //默认为native
    public String fbAdType = AD_TYPE_NATIVE;
    //默认为native
    public String baiduAdType = AD_TYPE_NATIVE;
    //默认为native
    public String altamobAdType = AD_TYPE_NATIVE;
    //默认为nativeE
    public String admobAdType = ADMOB_AD_TYPE_NATIVE_EXPRESS;

    //默认缓存时间为20分钟 单位:毫秒
    public long cacheTime = DEFAULT_AD_CACHE_TIME;
    //默认为true
    public boolean clickToReload = true;
    //默认为false
    public boolean adMediaAll = false;

    //默认false ,x天后是否转其他模式
    public boolean xDays2Other = false;
    //默认为3天
    public int xDays2OtherDays = DEFAULT_DAYS_TO_OTHER;
    //默认为null
    public String describe = null;

    public AdCacheNode(JSONObject jsonObject) {

        if (jsonObject.has(KEY_AD_LOAD_TYPE)) {
            adLoadType = jsonObject.optString(KEY_AD_LOAD_TYPE);
        }
        //fbId 必须存在
        fbId = jsonObject.optString(KEY_FB_ID);

        if (jsonObject.has(KEY_BAIDU_ID)) {
            baiduID = jsonObject.optString(KEY_BAIDU_ID);
        }
        if (jsonObject.has(KEY_ADMOB_ID)) {
            admobId = jsonObject.optString(KEY_ADMOB_ID);
        }
        if (jsonObject.has(KEY_ALTAMOB_ID)) {
            altamobId = jsonObject.optString(KEY_ALTAMOB_ID);
        }

        if (jsonObject.has(KEY_FB_AD_TYPE)) {
            fbAdType = jsonObject.optString(KEY_FB_AD_TYPE);
        }
        if (jsonObject.has(KEY_BAIDU_AD_TYPE)) {
            baiduAdType = jsonObject.optString(KEY_BAIDU_AD_TYPE);
        }
        if (jsonObject.has(KEY_ADMOB_AD_TYPE)) {
            admobAdType = jsonObject.optString(KEY_ADMOB_AD_TYPE);
        }
        if (jsonObject.has(KEY_ALTAMOB_AD_TYPE)) {
            altamobAdType = jsonObject.optString(KEY_ALTAMOB_AD_TYPE);
        }

        if (jsonObject.has(KEY_CACHE_TIME)) {
            long t = jsonObject.optLong(KEY_CACHE_TIME, 0);
            if (t > 0) {
                cacheTime = t * 1000L;
            }
        }
        if (jsonObject.has(KEY_CLICK_TO_RELOAD)) {
            clickToReload = jsonObject.optBoolean(KEY_CLICK_TO_RELOAD, true);
        }
        if (jsonObject.has(KEY_FB_MEDIA_ALL)) {
            adMediaAll = jsonObject.optBoolean(KEY_FB_MEDIA_ALL, false);
        }
        if (jsonObject.has(KEY_IS_X_DAYS_LATER_TO_OTHER)) {
            xDays2Other = jsonObject.optBoolean(KEY_IS_X_DAYS_LATER_TO_OTHER, false);
        }
        if (jsonObject.has(KEY_X_DAYS_LATER_TO_OTHER)) {
            xDays2OtherDays = jsonObject.optInt(KEY_X_DAYS_LATER_TO_OTHER, DEFAULT_DAYS_TO_OTHER);
        }
        if (jsonObject.has(KEY_DESCRIBE)) {
            describe = jsonObject.optString(KEY_DESCRIBE);
        }
    }

    @Override
    public String toString() {
        return "AdCacheNode{" +
                "adLoadType='" + adLoadType + '\'' +
                ", fbId='" + fbId + '\'' +
                ", baiduID='" + baiduID + '\'' +
                ", altamobId='" + altamobId + '\'' +
                ", admobId='" + admobId + '\'' +
                ", fbAdType='" + fbAdType + '\'' +
                ", baiduAdType='" + baiduAdType + '\'' +
                ", altamobAdType='" + altamobAdType + '\'' +
                ", admobAdType='" + admobAdType + '\'' +
                ", cacheTime=" + cacheTime +
                ", clickToReload=" + clickToReload +
                ", adMediaAll=" + adMediaAll +
                ", xDays2Other=" + xDays2Other +
                ", xDays2OtherDays=" + xDays2OtherDays +
                '}';
    }
}