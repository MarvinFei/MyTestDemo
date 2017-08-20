package com.example.administrator.addemo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.addemo.R;


/**
 * Created by Administrator on 2017/7/26.
 */

public class CountHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
//    ImageView imageView;

    public CountHeaderViewHolder(View itemView) {
        super(itemView);
        textView =(TextView) itemView.findViewById(R.id.title);
//        imageView=(ImageView) itemView.findViewById(R.id.rcv_image);
    }

    public void render(String text){
        textView.setText(text);
//        imageView.setImageResource(R.drawable.res_theme_item_0);
    }
}
