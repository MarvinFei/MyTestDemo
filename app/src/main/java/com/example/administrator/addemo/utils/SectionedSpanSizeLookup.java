package com.example.administrator.addemo.utils;

/**
 * Created by Administrator on 2017/7/26.
 */

import android.support.v7.widget.GridLayoutManager;

import com.example.administrator.addemo.adapter.SectionForRecyclerViewAdapter;

public class SectionedSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    protected SectionForRecyclerViewAdapter<?, ?, ?> adapter = null;
    protected GridLayoutManager layoutManager = null;

    public SectionedSpanSizeLookup(SectionForRecyclerViewAdapter<?, ?, ?> adapter,
                                   GridLayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.isSectionHeaderPosition(position) ||
                adapter.isSectionFooterPosition(position)) {
            return layoutManager.getSpanCount();
        } else {
            return 1;
        }
    }
}
