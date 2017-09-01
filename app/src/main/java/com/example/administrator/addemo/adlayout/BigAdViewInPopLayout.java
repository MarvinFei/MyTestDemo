package com.example.administrator.addemo.adlayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.adloadhelper.AdViewBaseLayout;
import com.example.administrator.addemo.adloadhelper.adsbean.NativeAdBase;

import com.example.administrator.addemo.config.Constants;


/**
 * Created by wangfei on 16/7/15.
 *
 * luck按钮点击后pop广告布局
 * 主页面右上角gift点击后pop广告布局
 * 键盘顶部gif点击后广告布局
 */
public class BigAdViewInPopLayout extends AdViewBaseLayout{

    private String TAG = "BigAdViewLayout";

    private LinearLayout mNativeAdMediaView;
    private ImageView mNativeAdTitleIcon;
    private TextView mNativeAdTitle;
    private TextView mNativeAdBody;
    private Button mNativeAdBtn;


    private TextView ad_adview_subtitle;

    private LinearLayout ll_ad_rate;
    private ImageView iv_ad_xing_1;
    private ImageView iv_ad_xing_2;
    private ImageView iv_ad_xing_3;
    private ImageView iv_ad_xing_4;
    private ImageView iv_ad_xing_5;
//    private RelativeLayout ad_choice_layout;
    public BigAdViewInPopLayout(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.kb_big_adview_in_pop_layout,this,true);
        mNativeAdMediaView = (LinearLayout)findViewById(R.id.ad_mediaview_content);
        mNativeAdTitleIcon = (ImageView)findViewById(R.id.ad_little_icon_content);
        mNativeAdTitle = (TextView)findViewById(R.id.ad_adview_title);
        mNativeAdBody = (TextView)findViewById(R.id.ad_adview_body);
        mNativeAdBtn = (Button)findViewById(R.id.ad_view_btn);

//        ad_choice_layout = (RelativeLayout) findViewById(R.id.ad_choice_layout);
        ad_adview_subtitle = (TextView)findViewById(R.id.ad_adview_subtitle);
        //rate
        ll_ad_rate = (LinearLayout)findViewById(R.id.ll_ad_rate);
        iv_ad_xing_1 = (ImageView)findViewById(R.id.iv_ad_xing_1);
        iv_ad_xing_2 = (ImageView)findViewById(R.id.iv_ad_xing_2);
        iv_ad_xing_3 = (ImageView)findViewById(R.id.iv_ad_xing_3);
        iv_ad_xing_4 = (ImageView)findViewById(R.id.iv_ad_xing_4);
        iv_ad_xing_5 = (ImageView)findViewById(R.id.iv_ad_xing_5);
        setTitleView(mNativeAdTitle);
        setBodyView(mNativeAdBody);
        setMediaView(mNativeAdMediaView);
        setActionView(mNativeAdBtn);
        setRegisterView(mNativeAdBtn);
        setAdHeight(Constants.ADMOB_HEIGHT);
        setAdWidth(Constants.ADMOB_WIDTH);
    }

    @Override
    public void updateLayout(NativeAdBase nativeAdBase) {
        mNativeAdTitle.setText(nativeAdBase.getTitle());
        mNativeAdBody.setText(nativeAdBase.getTextBody());
        mNativeAdBtn.setText(nativeAdBase.getButton());
        nativeAdBase.setMediaView(mNativeAdMediaView);
        nativeAdBase.displayImageIcon(mNativeAdTitleIcon);
        nativeAdBase.registerViewGroup(mNativeAdBtn);
//        nativeAdBase.displayAdChoicesIcon(ad_choice_layout);
        setRateView(nativeAdBase);
        super.updateLayout(nativeAdBase);
    }


    private void setRateView(NativeAdBase nativeAdBase) {
        String subTitle = nativeAdBase.getSubTitle();
        double ratingScale = nativeAdBase.getRatingScale();
        double ratingValue = nativeAdBase.getRatingValue();
        Log.i(TAG, "subTitle: " + subTitle);
        Log.i(TAG, "ratingScale: " + ratingScale);
        Log.i(TAG, "ratingValue: " + ratingValue);

        if (ratingValue <= 0){
            ll_ad_rate.setVisibility(View.GONE);
            if (TextUtils.isEmpty(subTitle)){
                ad_adview_subtitle.setVisibility(View.GONE);
            }else{
                ad_adview_subtitle.setText(subTitle);
                ad_adview_subtitle.setVisibility(View.VISIBLE);
            }
            return;
        }

        ll_ad_rate.setVisibility(View.VISIBLE);
        ad_adview_subtitle.setVisibility(View.GONE);
        if (ratingValue < 1.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_2);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue == 1.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue > 1.0 && ratingValue < 2.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_2);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue == 2.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue > 2.0 && ratingValue < 3.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_2);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue == 3.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_3);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue > 3.0 && ratingValue < 4.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_2);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue == 4.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_3);
        }else if(ratingValue > 4.0 && ratingValue < 5.0){
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_2);
        }else {
            iv_ad_xing_1.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_2.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_3.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_4.setImageResource(R.drawable.ad_xing_1);
            iv_ad_xing_5.setImageResource(R.drawable.ad_xing_1);
        }
    }
}
