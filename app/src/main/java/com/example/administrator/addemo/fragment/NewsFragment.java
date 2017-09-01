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
import com.example.administrator.addemo.adapter.MyRecyclerAdapter2;
import com.example.administrator.addemo.adapter.MyRecyclerAdapter3;
import com.example.administrator.addemo.config.Constants;
import com.example.administrator.addemo.listener.MyItemClickListener;
import com.example.administrator.addemo.utils.SectionedSpanSizeLookup;

//import butterknife.BindView;

/**
 * Created by LYF on 2017/8/20.
 */

public class NewsFragment extends BaseFragment implements MyItemClickListener {

    private static final String MY_FRIST ="MY_FRIST";

    private int mType;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private MyRecyclerAdapter2 myRecyclerAdapter2;
    private MyRecyclerAdapter3 myRecyclerAdapter3 ;

    public NewsFragment(){

    }

    public static NewsFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(MY_FRIST, type);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getArguments().getInt(MY_FRIST);
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
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(myRecyclerAdapter, layoutManager);

        switch (mType){
            case Constants.MY_FRIST_TYPE:
                myRecyclerAdapter = new MyRecyclerAdapter(getActivity());
                mRecyclerView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.setOnItemClickListener(this);

                 lookup = new SectionedSpanSizeLookup(myRecyclerAdapter, layoutManager);
                layoutManager.setSpanSizeLookup(lookup);
                break;
            case Constants.MY_SECOND_TYPE:
                myRecyclerAdapter2 = new MyRecyclerAdapter2(getActivity());
                mRecyclerView.setAdapter(myRecyclerAdapter2);
                myRecyclerAdapter2.setOnItemClickListener(this);

                 lookup = new SectionedSpanSizeLookup(myRecyclerAdapter2, layoutManager);
                layoutManager.setSpanSizeLookup(lookup);
                break;
            case Constants.MY_THIRD_TYPE:
                myRecyclerAdapter3 = new MyRecyclerAdapter3(getActivity());
                mRecyclerView.setAdapter(myRecyclerAdapter3);
                myRecyclerAdapter3.setOnItemClickListener(this);

                 lookup = new SectionedSpanSizeLookup(myRecyclerAdapter3, layoutManager);
                layoutManager.setSpanSizeLookup(lookup);
                break;
            default:
                myRecyclerAdapter = new MyRecyclerAdapter(getActivity());
                mRecyclerView.setAdapter(myRecyclerAdapter);
                myRecyclerAdapter.setOnItemClickListener(this);

               lookup = new SectionedSpanSizeLookup(myRecyclerAdapter, layoutManager);
                layoutManager.setSpanSizeLookup(lookup);
                break;
        }

        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void ItemClick(View view, int section, int position) {
        Toast.makeText(getActivity(),"click item" + section + position, Toast.LENGTH_LONG).show();
    }
}
