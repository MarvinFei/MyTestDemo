package com.example.administrator.addemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudtech.ads.core.CTAdvanceNative;
import com.cloudtech.ads.core.CTImageRatioType;
import com.cloudtech.ads.core.CTNative;
import com.cloudtech.ads.core.CTService;
import com.cloudtech.ads.utils.YeLog;
import com.example.administrator.addemo.config.Config;
import com.example.administrator.addemo.listener.MyCTAdEventListener;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2017/8/18.
 */

public class CloudMobiActivity extends AppCompatActivity{
    private ViewGroup container;
    private ViewGroup adLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_process);

        CTService.adSourceType = "ct";

        loadNativeAd();

//        loadInterstitial();

//        loadAppwall();

    }

    private void loadNativeAd() {
        container = (ViewGroup)findViewById(R.id.container);
        adLayout = (ViewGroup) View.inflate(MyApplication.context, R.layout.advance_native_layout, null);

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd();
            }
        });
    }

    public void loadAd() {
        // 获取原生广告，返回一个含有广告的容器
        CTService.getAdvanceNative(Config.slotIdNative, MyApplication.context,
                CTImageRatioType.RATIO_19_TO_10, new MyCTAdEventListener() {
                    @Override
                    public void onAdviewGotAdSucceed(CTNative result) {
                        if (result == null) {
                            return;
                        }
                        YeLog.e("onAdviewGotAdSucceed");
                        CTAdvanceNative ctAdvanceNative = (CTAdvanceNative) result;
                        showAd(ctAdvanceNative);
                        super.onAdviewGotAdSucceed(result);

                    }


                    @Override
                    public void onAdviewGotAdFail(CTNative result) {
                        YeLog.e("onAdviewGotAdFail");
                        super.onAdviewGotAdFail(result);
                    }


                    @Override
                    public void onAdviewClicked(CTNative result) {
                        YeLog.e("onAdviewClicked");
                        super.onAdviewClicked(result);
                    }

                });
    }


    private void showAd(final CTAdvanceNative ctAdvanceNative) {
        SimpleDraweeView img = (SimpleDraweeView) adLayout.findViewById(R.id.iv_img);
        SimpleDraweeView icon = (SimpleDraweeView) adLayout.findViewById(R.id.iv_icon);
        TextView title = (TextView) adLayout.findViewById(R.id.tv_title);
        TextView desc = (TextView) adLayout.findViewById(R.id.tv_desc);
        TextView click = (TextView) adLayout.findViewById(R.id.bt_click);
        SimpleDraweeView ad_choice_icon = (SimpleDraweeView) adLayout.findViewById(
                R.id.ad_choice_icon);

        img.setImageURI(Uri.parse(ctAdvanceNative.getImageUrl()));
        icon.setImageURI(Uri.parse(ctAdvanceNative.getIconUrl()));
        title.setText(ctAdvanceNative.getTitle());
        desc.setText(ctAdvanceNative.getDesc());
        click.setText(ctAdvanceNative.getButtonStr());
        ad_choice_icon.setImageURI(ctAdvanceNative.getAdChoiceIconUrl());

//        ctAdvanceNative.addADLayoutToADContainer(adLayout);
        ctAdvanceNative.registeADClickArea(adLayout);

        container.removeAllViews();
//        container.addView(ctAdvanceNative);
        container.addView(adLayout);
    }


}
