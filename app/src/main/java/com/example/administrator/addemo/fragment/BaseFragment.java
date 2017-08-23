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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
//        ButterKnife.bind(this, view);
        baseView = view;
        initView(baseView, savedInstanceState);
        return baseView;
    }

    public abstract int getLayoutResId();

    protected void initView(View view, Bundle savedInstanceState){}
}
