package com.example.administrator.addemo;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/8/20.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private final String TAG = "baseActivity";

    protected Context ctx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        initView();
        initData();
        setEvent();
        ctx = this;
    }

    /*
    * 初始化界面
    * */
    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setEvent();

    protected abstract int setLayoutId();


}
