package com.example.administrator.addemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import butterknife.ButterKnife;

/**
 * Created by LYF on 2017/8/20.
 */

public abstract class BaseFragment extends Fragment{

    private View baseView;
    private ViewGroup parent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (baseView == null){
            baseView = inflater.inflate(getLayoutResId(), container, false);
            initView(baseView, savedInstanceState);
        } else {
            parent = (ViewGroup) baseView.getParent();
        }
        if (parent != null) {
            parent.removeView(baseView);
        }
//        ButterKnife.bind(this, view);
        return baseView;
    }

    public abstract int getLayoutResId();

    public abstract void initData();

    protected void initView(View view, Bundle savedInstanceState){}
}
