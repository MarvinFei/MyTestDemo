package com.example.administrator.addemo.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.addemo.R;

//import butterknife.BindView;

/**
 * Created by LYF on 2017/8/20.
 */

public class NewsFragment extends BaseFragment {

    private static final String MY_FRIST_TYPE ="MY_FRIST_TYPE";

    private int mType;

    public NewsFragment(){}

    public static NewsFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(MY_FRIST_TYPE, type);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getArguments().getInt(MY_FRIST_TYPE);
    }


    @Override
    public int getLayoutResId(){
        return R.layout.fragment_recycler_list_view;
    }

    protected void initView(View view,Bundle savedInstanceState){
        //TODO init my fragment
    }
}
