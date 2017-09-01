package com.example.administrator.addemo.adloadhelper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;

/**
 * Created by fotoable on 2016/7/4.
 */
public class AdViewBaseLayout extends LinearLayout {

    private NativeAdBase nativeAdBase;

    public AdViewBaseLayout(Context context) {
        super(context);
    }

    public void updateLayout(NativeAdBase nativeAdBase) {

        this.nativeAdBase = nativeAdBase;
        if (getParent() != null) {
            ((View) getParent()).setVisibility(VISIBLE);
        }
    }

    public Drawable getDrawable() {
        return null;
    }

    public NativeAdBase getNativeAdBase() {
        return nativeAdBase;
    }

    /**
     * 广告的宽(因为admob 加载 Native Express 加载前需要制定宽高)
     * 注意: 设置的宽高 必须不能大于该view的父view的宽高, 否则广告显示不出来 !!!!
     */
    private int adWidth;
    /**
     * 广告的高(因为admob 加载 Native Express 加载前需要制定宽高)
     * 注意: 设置的宽高 必须不能大于该view的父view的宽高, 否则广告显示不出来 !!!
     */
    private int adHeight;

    private View titleView;

    private View bodyView;
    private View actionView;

    private ImageView logoView;

    private ImageView imageView;

    private ViewGroup mediaView;

    private ImageView bigImageView;

    private View registerView;

    private View advertiserView;

    public void setNativeAdBase(NativeAdBase nativeAdBase) {
        this.nativeAdBase = nativeAdBase;
    }

    public View getTitleView() {
        return titleView;
    }

    public void setTitleView(View titleView) {
        this.titleView = titleView;
    }

    public View getBodyView() {
        return bodyView;
    }

    public void setBodyView(View bodyView) {
        this.bodyView = bodyView;
    }

    public ImageView getLogoView() {
        return logoView;
    }

    public void setLogoView(ImageView logoView) {
        this.logoView = logoView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ViewGroup getMediaView() {
        return mediaView;
    }

    public void setMediaView(ViewGroup mediaView) {
        this.mediaView = mediaView;
    }

    public ImageView getBigImageView() {
        return bigImageView;
    }

    public void setBigImageView(ImageView bigImageView) {
        this.bigImageView = bigImageView;
    }

    public View getRegisterView() {
        return registerView;
    }

    public void setRegisterView(View registerView) {
        this.registerView = registerView;
    }

    public View getAdvertiserView() {
        return advertiserView;
    }

    public void setAdvertiserView(View advertiserView) {
        this.advertiserView = advertiserView;
    }

    public View getActionView() {
        return actionView;
    }

    public void setActionView(View actionView) {
        this.actionView = actionView;
    }

    public int getAdWidth() {
        return adWidth;
    }

    public void setAdWidth(int adWidth) {
        this.adWidth = adWidth;
    }

    public int getAdHeight() {
        return adHeight;
    }

    public void setAdHeight(int adHeight) {
        this.adHeight = adHeight;
    }
}