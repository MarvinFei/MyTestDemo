package com.example.administrator.addemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.addemo.config.Constants;
import com.example.administrator.addemo.fragment.NewsFragment;

/**
 * Created by Administrator on 2017/8/20.
 */

public class MainViewPageAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;
    private Context mContext;

    public MainViewPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        int type ;
        switch (position){
            case 0:
                type = Constants.MY_FRIST_TYPE;
                break;
            case 1:
                type = Constants.MY_SECOND_TYPE;
                break;
            case 2:
                type = Constants.MY_THIRD_TYPE;
                break;
            default:
                type = Constants.MY_FRIST_TYPE;
                break;
        }
        return NewsFragment.newInstance(type);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "一个";
            case 1:
                return "两个";
            case 2:
                return "三个";
            default:
                return null;
        }
    }

}
