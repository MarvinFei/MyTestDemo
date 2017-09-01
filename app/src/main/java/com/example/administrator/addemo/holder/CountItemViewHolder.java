package com.example.administrator.addemo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.listener.MyItemClickListener;


/**
 * Created by Administrator on 2017/7/26.
 */

public class CountItemViewHolder extends RecyclerView.ViewHolder {


    View view;
    LinearLayout container;
    private MyItemClickListener itemClickListener;

    public CountItemViewHolder(View itemView, MyItemClickListener itemClickListener){
        super(itemView);
        container =  (LinearLayout) itemView.findViewById(R.id.my_container);
        this.itemClickListener = itemClickListener;
        this.view = itemView;
    }

    public void setAdLayout(View itemView){
        container.addView(itemView);
    }

    public void setupClick(final int section, final int position){
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                itemClickListener.ItemClick( view, section, position);
            }
        });
    }

}
