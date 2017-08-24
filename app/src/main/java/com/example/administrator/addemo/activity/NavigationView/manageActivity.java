package com.example.administrator.addemo.activity.NavigationView;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.addemo.BaseActivity;
import com.example.administrator.addemo.R;
import com.example.administrator.addemo.activity.adActivity.AdmobBannerActivity;
import com.example.administrator.addemo.activity.adActivity.AdmobInterstitialActivity;
import com.example.administrator.addemo.activity.adActivity.CloudMobiActivity;
import com.example.administrator.addemo.activity.adActivity.MyNativeAdActivity;

/**
 * Created by Administrator on 2017/8/23.
 */

public class manageActivity extends BaseActivity implements View.OnClickListener{

    private final static String TAG = "manageActivity";
    private static int layoutId = R.layout.main_layout_one;

    private Button mButton;
    private Button nativeAd;
    private Button admobBannerButton;
    private Button admobInterstitialButton;
    private Button cloudMobiButton;


    @Override
    protected int setLayoutId(){
        return this.layoutId;
    }

    @Override
    protected void initView() {
        mButton = (Button)findViewById(R.id.show_button);
        nativeAd = (Button)findViewById(R.id.my_native_ad);
        admobBannerButton = (Button)findViewById(R.id.admob_banner_button);
        admobInterstitialButton = (Button)findViewById(R.id.admob_interstitial_button);
        cloudMobiButton = (Button)findViewById(R.id.cloud_mobi_button);
    }

    @Override
    protected void initData(){
    }

    @Override
    protected void setEvent() {
        mButton.setOnClickListener(this);
        nativeAd.setOnClickListener(this);
        admobBannerButton.setOnClickListener(this);
        admobInterstitialButton.setOnClickListener(this);
        cloudMobiButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AdmobBannerActivity.class);
        switch (v.getId()) {
            case R.id.show_button:
//                loadInterstitialAd();
                Toast.makeText(this, "不加载插页广告", Toast.LENGTH_SHORT);
                break;
            case R.id.my_native_ad:
                //facebook展示原生广告
                intent.setClass(this, MyNativeAdActivity.class);
                startActivity(intent);
                break;
            case R.id.admob_banner_button:
                intent.setClass(this, AdmobBannerActivity.class);
                startActivity(intent);
                break;
            case R.id.admob_interstitial_button:
                intent.setClass(this, AdmobInterstitialActivity.class);
                startActivity(intent);
                break;
            case R.id.cloud_mobi_button:
                intent.setClass(this, CloudMobiActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
