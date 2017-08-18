package com.example.administrator.addemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButton;
    private Button nativeAd;
    private Button admobBannerButton;
    private Button admobInterstitialButton;
    private Button cloudMobiButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MobileAds.initialize(this, ADMOB_ADDEMO_ID);
        initView();

    }

    private void initView(){
        mButton = (Button)findViewById(R.id.show_button);
        nativeAd = (Button)findViewById(R.id.my_native_ad);
        admobBannerButton = (Button)findViewById(R.id.admob_banner_button);
        admobInterstitialButton = (Button)findViewById(R.id.admob_interstitial_button);
        cloudMobiButton = (Button)findViewById(R.id.cloud_mobi_button);
        mButton.setOnClickListener(this);
        nativeAd.setOnClickListener(this);
        admobBannerButton.setOnClickListener(this);
        admobInterstitialButton.setOnClickListener(this);
        cloudMobiButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, AdmobBannerActivity.class);
        switch (v.getId()){
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
