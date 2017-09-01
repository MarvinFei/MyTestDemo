package com.example.administrator.addemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.adlayout.BigAdViewInPopLayout;
import com.example.administrator.addemo.adloadhelper.AnimateNativeAdViewLayout;
import com.example.administrator.addemo.config.Constants;
import com.example.administrator.addemo.holder.CountFooterViewHolder;
import com.example.administrator.addemo.holder.CountHeaderViewHolder;
import com.example.administrator.addemo.holder.CountItemViewHolder;
import com.example.administrator.addemo.listener.MyItemClickListener;

/**
 * Created by Administrator on 2017/7/26.
 */

public class MyRecyclerAdapter extends SectionForRecyclerViewAdapter<CountHeaderViewHolder, CountItemViewHolder,
        CountFooterViewHolder> {

    private Context mContext;

    private MyItemClickListener itemClickListener;

    public MyRecyclerAdapter(Context context){
        this.mContext = context;
    }

    public void setOnItemClickListener(MyItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    protected LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mContext);
    }

    @Override
    protected int getSectionCount() {
        return 3;
    }

    @Override
    protected int getItemCountForSection(int section) {
            return 1;
    }


    @Override
    protected boolean hasFooterInSection(int section) {
        return true;
    }


    @Override
    protected CountHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_header, parent, false);
        return new CountHeaderViewHolder(view);
    }

    @Override
    protected CountFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_footer, parent, false);
        return new CountFooterViewHolder(view);
    }

    @Override
    protected CountItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_item, parent, false);
//        AnimateNativeAdViewLayout myFirst = new AnimateNativeAdViewLayout(parent.getContext(), new BigAdViewInPopLayout(parent.getContext()),
//                Constants.AD_VIEW_PAGE_ONE_1, null);

        return new CountItemViewHolder(view, itemClickListener);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(CountHeaderViewHolder holder, int section) {
        holder.render("Header PageOne" + (section + 1));
    }

    @Override
    protected void onBindSectionFooterViewHolder(CountFooterViewHolder holder, int section) {
        holder.render("第" + (section + 1) + "个section的footer");
    }

    protected int[] colors = new int[]{0xfff44336, 0xff2196f3, 0xff009688, 0xff8bc34a, 0xffff9800};
    @Override
    protected void onBindItemViewHolder(CountItemViewHolder holder, int section, int position) {

//        Context context = holder.itemView.getContext();
        switch (section) {
            case 0:
                AnimateNativeAdViewLayout myFirst1 = new AnimateNativeAdViewLayout(mContext, new BigAdViewInPopLayout(mContext),
                        Constants.AD_VIEW_PAGE_ONE_1, null);
                holder.setAdLayout(myFirst1);
                break;
            case 1:
                AnimateNativeAdViewLayout myFirst2 = new AnimateNativeAdViewLayout(mContext, new BigAdViewInPopLayout(mContext),
                        Constants.AD_VIEW_PAGE_ONE_2, null);
                holder.setAdLayout(myFirst2);
                break;
            case 2:
                AnimateNativeAdViewLayout myFirst3 = new AnimateNativeAdViewLayout(mContext, new BigAdViewInPopLayout(mContext),
                        Constants.AD_VIEW_PAGE_ONE_3, null);
                holder.setAdLayout(myFirst3);
                break;
            default:
                break;

        }
        holder.setupClick(section, position);
    }

}
