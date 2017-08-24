package com.example.administrator.addemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.adapter.MainViewPageAdapter;
import com.example.administrator.addemo.adapter.MyRecyclerAdapter;
import com.example.administrator.addemo.utils.SectionedSpanSizeLookup;

//import butterknife.BindView;

/**
 * Created by LYF on 2017/8/20.
 */

public class NewsFragment extends BaseFragment implements MyRecyclerAdapter.MyItemClickListener{

    private static final String MY_FRIST_TYPE ="MY_FRIST_TYPE";

    private int mType;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;

    public NewsFragment(){

    }

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

    @Override
    public void initData(){

    }

    protected void initView(View view,Bundle savedInstanceState){
        //TODO init my fragment  mType
        mRecyclerView =(RecyclerView)view.findViewById(R.id.my_rev);
        myRecyclerAdapter = new MyRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        //设置item的动画，可以不设置
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(myRecyclerAdapter, layoutManager);
        layoutManager.setSpanSizeLookup(lookup);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void ItemClick(View view, int section, int position) {
        Toast.makeText(getActivity(),"click item" + section + position, Toast.LENGTH_LONG).show();
    }
}
