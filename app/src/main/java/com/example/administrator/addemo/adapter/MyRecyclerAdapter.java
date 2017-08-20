package com.example.administrator.addemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.holder.CountFooterViewHolder;
import com.example.administrator.addemo.holder.CountHeaderViewHolder;
import com.example.administrator.addemo.holder.CountItemViewHolder;

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
        return 5;
    }

    @Override
    protected int getItemCountForSection(int section) {
        if (section == 0){
            return 2;
        }else{
            return section + 1;
        }
    }

    @Override
    protected boolean hasHeaderInSection(int section) {
        return true;
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
        return new CountItemViewHolder(view, itemClickListener);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(CountHeaderViewHolder holder, int section) {

    }

    @Override
    protected void onBindSectionFooterViewHolder(CountFooterViewHolder holder, int section) {
        if (section == 0){
            holder.render("第一个section的footer");
        } else {
            holder.render("第" + section+1 + "个section的footer");
        }
    }

    protected int[] colors = new int[]{0xfff44336, 0xff2196f3, 0xff009688, 0xff8bc34a, 0xffff9800};
    @Override
    protected void onBindItemViewHolder(CountItemViewHolder holder, int section, int position) {

        holder.renderColor(String.valueOf(position + 1), colors[section] );

//        Log.e("TAG", "onBindItemViewHolder: " + position+ "others");
//        holder.renderColor(String.valueOf(position + 1), colors[section]);

//        holder.render(String.valueOf(position + 1), colors[section]);
        holder.setupClick(section, position);
    }

    public interface MyItemClickListener {
        void ItemClick(View view, int section, int position);
    }
}
