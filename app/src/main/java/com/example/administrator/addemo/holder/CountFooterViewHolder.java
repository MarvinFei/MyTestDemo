package com.example.administrator.addemo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.addemo.R;


/**
 * Created by Administrator on 2017/7/26.
 */

public class CountFooterViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public CountFooterViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.title);   //存疑
    }
    public void render(String text){
        textView.setText(text);
        textView.setBackgroundColor(0xff009688);
    }

}
