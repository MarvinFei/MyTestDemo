package com.example.administrator.addemo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.adapter.MyRecyclerAdapter;


/**
 * Created by Administrator on 2017/7/26.
 */

public class CountItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView2;
//    ImageView imageView3;
    View containerView;

    private View view;
    private MyRecyclerAdapter.MyItemClickListener itemClickListener;

    public CountItemViewHolder(View itemView, MyRecyclerAdapter.MyItemClickListener itemClickListener){
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.item_title);
        imageView2 =(ImageView) itemView.findViewById(R.id.another_image);
        containerView = itemView.findViewById(R.id.container);

        this.view = itemView;
        this.itemClickListener = itemClickListener;
    }

//    public void render(int imageNumber, String text, int color){
//        if (imageNumber == 2){
//        imageView2.setImageResource(R.drawable.res_theme_item_12);
//        }else if (imageNumber == 3){
//            imageView2.setImageResource(R.drawable.res_theme_item_13);
//        }else {
//            textView.setText(text);
//            containerView.setBackgroundColor(color);
//        }
//        containerView.setBackgroundColor(color);
//    }
    public void renderColor(String text, int color){
        textView.setText(text);
        containerView.setBackgroundColor(color);
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
