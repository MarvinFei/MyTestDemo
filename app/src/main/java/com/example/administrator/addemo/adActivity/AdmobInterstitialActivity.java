package com.example.administrator.addemo.adActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.addemo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Administrator on 2017/8/9.
 */

public class AdmobInterstitialActivity extends AppCompatActivity {

    private static String TAG = "InterstitialActivity";

    private InterstitialAd admobInterstitial;
    private CountDownTimer mCountDownTimer;
    private Button mRetryButton;
    private boolean mGameIsInProgress;
    private long mTimerMillisconds;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admob_interstitial_activity);

        //create Interstitial and set unit_id
        admobInterstitial = new InterstitialAd(this);
        admobInterstitial.setAdUnitId(getString(R.string.interstitial_unit_id));
        Log.e(TAG, "onCreate: admobInterstitial" + admobInterstitial );
//        admobInterstitial.loadAd(new AdRequest.Builder().build());

        admobInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("Ads", "onAdClosed");
            }
        });

        mRetryButton = (Button)findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admobInterstitial.loadAd(new AdRequest.Builder().build());
                if (admobInterstitial.isLoaded()){
                    admobInterstitial.show();
                    Log.e(TAG, "onClick: showing Interstitial " + admobInterstitial );
                } else {
                    Log.e(TAG, "onClick: load unsuccess " + admobInterstitial );
                }
            }
        });

    }









//        admobInterstitial.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed(){
//                startGame();
//            }
//        });
//
//        mRetryButton = (Button)findViewById(R.id.retry_button);
//        mRetryButton.setVisibility(View.VISIBLE);
//        mRetryButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                showInterstitial();
//            }
//        });
//
//        startGame();
//    }
//
//    public void createTimer(final long millisconds){
//        if (mCountDownTimer != null) {
//            mCountDownTimer.cancel();
//        }
//
//        final TextView textView = (TextView)findViewById(R.id.timer);
//
//        mCountDownTimer = new CountDownTimer(millisconds, 50) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mTimerMillisconds = millisUntilFinished;
//                textView.setText("seconds remaining:" + ((millisUntilFinished/1000)+1));
//            }
//
//            @Override
//            public void onFinish() {
//                mGameIsInProgress = false;
//                textView.setText("done!");
//                mRetryButton.setVisibility(View.VISIBLE);
//            }
//        };
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (mGameIsInProgress) {
//            resumeGame(mTimerMillisconds);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        if (mCountDownTimer != null) {
//            mCountDownTimer.cancel();
//        }
//        super.onPause();
//    }
//
//    private void showInterstitial(){
//        if (admobInterstitial != null && admobInterstitial.isLoaded()){
//            admobInterstitial.show();
//        } else {
//            Toast.makeText(this, "Ad did not load",Toast.LENGTH_SHORT).show();
//            startGame();
//        }
//    }
//
//    private void startGame(){
//        if (!admobInterstitial.isLoading() && admobInterstitial.isLoaded()) {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            admobInterstitial.loadAd(adRequest);
//        }
//    }
//
//    private void resumeGame(long milliseconds){
//        mGameIsInProgress = true;
//        mTimerMillisconds = milliseconds;
//        createTimer(milliseconds);
//        mCountDownTimer.start();
//    }

}
