package com.example.administrator.addemo.adloadhelper.adsbean;

import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;

/**
 * Created by damon on 06/04/2017.
 */

public class AdInstanceUtils {
    private static final String TAG = "AdInstanceUtils";

    public static boolean hasRacing(String loadType, String adType) {
        if (TextUtils.isEmpty(loadType)) {
            Log.e(TAG, "error, loadType kong");
            return false;
        }
        if (TextUtils.isEmpty(adType)) {
            Log.e(TAG, "error, adType kong");
            return false;
        }
        return loadType.contains(adType);
    }

    public static void destoryAdView(NativeExpressAdView admobNativeExpressAdView, AdView admobBanner, com.google.android.gms.ads.formats.NativeAd admobNativeAd) {
        try {

            if (admobNativeExpressAdView != null) {
                admobNativeExpressAdView.destroy();
            }
            if (admobBanner != null) {
                admobBanner.destroy();
            }
            if (admobNativeAd != null) {
                if (admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    nativeContentAd.destroy();
                } else if (admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    nativeAppInstallAd.destroy();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String getTitle(@NativeAdBase.DataFrom int dataFrom, com.google.android.gms.ads.formats.NativeAd admobNativeAd ) {
        String result = "";
        switch (dataFrom) {
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                if (admobNativeAd != null && admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    if (nativeContentAd.getHeadline() != null) {
                        result = nativeContentAd.getHeadline().toString();
                    }
                }
                break;
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                if (admobNativeAd != null && admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    if (nativeAppInstallAd.getHeadline() != null) {
                        result = nativeAppInstallAd.getHeadline().toString();
                    }
                }
                break;
                default:
                    break;

        }
        return result == null ? "" : result;
    }

    public static String getButton(@NativeAdBase.DataFrom int dataFrom,  com.google.android.gms.ads.formats.NativeAd admobNativeAd) {
        String result = "";
        switch (dataFrom) {
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                if (admobNativeAd != null && admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    if (nativeContentAd.getCallToAction() != null) {
                        result = nativeContentAd.getCallToAction().toString();
                    }
                }
                break;
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                if (admobNativeAd != null && admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    if (nativeAppInstallAd.getCallToAction() != null) {
                        result = nativeAppInstallAd.getCallToAction().toString();
                    }
                }
                break;
                default:
                    break;

        }
        return result == null ? "" : result;
    }

    public static String getTextBody(@NativeAdBase.DataFrom int dataFrom, com.google.android.gms.ads.formats.NativeAd admobNativeAd) {
        String result = "";
        switch (dataFrom) {
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                if (admobNativeAd != null && admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    if (nativeContentAd.getBody() != null) {
                        result = nativeContentAd.getBody().toString();
                    }
                }
                break;
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                if (admobNativeAd != null && admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    if (nativeAppInstallAd.getBody() != null) {
                        result = nativeAppInstallAd.getBody().toString();
                    }
                }
                break;
                default:
                    break;

        }
        return result == null ? "" : result;
    }

    public static void displayImageIcon(ImageView imageView, @NativeAdBase.DataFrom int dataFrom, com.google.android.gms.ads.formats.NativeAd admobNativeAd) {
        if (imageView == null) {
            Log.e(TAG, "displayImageIcon imageView== null");
            return;
        }
        imageView.setImageDrawable(null);
        switch (dataFrom) {
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                if (admobNativeAd != null && admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    if (nativeContentAd.getLogo() != null) {
                        if (nativeContentAd.getLogo().getDrawable() != null) {
                            imageView.setImageDrawable(nativeContentAd.getLogo().getDrawable());
                        } else {
                            Log.e(TAG, "nativeContentAd.getLogo().getDrawable() == null");
                        }
                    } else {
                        Log.e(TAG, "nativeContentAd.getLogo() == null");
                    }
                } else {
                    Log.e(TAG, "admobNativeAd null?-->>" + (admobNativeAd == null));
                }
                break;
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                if (admobNativeAd != null && admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    if (nativeAppInstallAd.getIcon() != null) {
                        if (nativeAppInstallAd.getIcon().getDrawable() != null) {
                            imageView.setImageDrawable(nativeAppInstallAd.getIcon().getDrawable());
                        }
                    }
                }
                break;
                default:
                    break;

        }
    }

    public static void displayBigImage(ImageView imageView, @NativeAdBase.DataFrom int dataFrom, com.google.android.gms.ads.formats.NativeAd admobNativeAd) {
        if (imageView == null) {
            Log.e(TAG, "displayBigImage imageView== null");
            return;
        }
        imageView.setImageDrawable(null);
        switch (dataFrom) {
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                if (admobNativeAd != null && admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    if (nativeContentAd.getImages() != null && nativeContentAd.getImages().size() > 0) {
                        com.google.android.gms.ads.formats.NativeAd.Image image = nativeContentAd.getImages().get(0);
                        if (image.getDrawable() != null) {
                            imageView.setImageDrawable(image.getDrawable());
                        }
                    }
                }
                break;
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                if (admobNativeAd != null && admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    if (nativeAppInstallAd.getImages() != null && nativeAppInstallAd.getImages().size() > 0) {
                        com.google.android.gms.ads.formats.NativeAd.Image image = nativeAppInstallAd.getImages().get(0);
                        if (image.getDrawable() != null) {
                            imageView.setImageDrawable(image.getDrawable());
                        }
                    }
                }
                break;
        }
    }

    public static void setMediaView(LinearLayout content, @NativeAdBase.DataFrom int dataFrom, com.google.android.gms.ads.formats.NativeAd admobNativeAd) {
        if (content == null) {
            Log.e(TAG, " error-->>setMediaView,content==null");
            return;
        }
        switch (dataFrom) {
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_CONTENT:
                if (admobNativeAd != null && admobNativeAd instanceof NativeContentAd) {
                    NativeContentAd nativeContentAd = (NativeContentAd) admobNativeAd;
                    if (nativeContentAd.getImages() != null && nativeContentAd.getImages().size() > 0) {
                        com.google.android.gms.ads.formats.NativeAd.Image image = nativeContentAd.getImages().get(0);
                        if (image.getDrawable() != null) {
                            content.removeAllViews();
                            ImageView mediaView = new ImageView(content.getContext());
                            mediaView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            mediaView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            mediaView.setImageDrawable(image.getDrawable());
                            content.addView(mediaView);
                        }
                    }
                }
                break;
            case NativeAdBase.DataFrom.FROM_ADMOB_NATIVE_A_INSTALL:
                if (admobNativeAd != null && admobNativeAd instanceof NativeAppInstallAd) {
                    NativeAppInstallAd nativeAppInstallAd = (NativeAppInstallAd) admobNativeAd;
                    if (nativeAppInstallAd.getImages() != null && nativeAppInstallAd.getImages().size() > 0) {
                        com.google.android.gms.ads.formats.NativeAd.Image image = nativeAppInstallAd.getImages().get(0);
                        if (image.getDrawable() != null) {
                            content.removeAllViews();
                            ImageView mediaView = new ImageView(content.getContext());
                            mediaView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            mediaView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            mediaView.setImageDrawable(image.getDrawable());
                            content.addView(mediaView);
                        }
                    }
                }
                break;

        }
    }

}
