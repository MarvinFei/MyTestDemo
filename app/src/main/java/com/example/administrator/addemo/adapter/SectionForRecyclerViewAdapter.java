package com.example.administrator.addemo.adapter;

/**
 * Created by Administrator on 2017/8/20.
 */

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/7/25.
 */

public abstract class SectionForRecyclerViewAdapter<H extends RecyclerView.ViewHolder, VH extends RecyclerView.ViewHolder,
        F extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_SECTION_HEADER = -1;
    private static final int TYPE_SECTION_FOOTER = -2;
    private static final int TYEP_ITEM = -3;

    private int[] sectionForPosition = null;//第几组
    private int[] positionWithInSection = null;//在组中的位置数
    private boolean[] isHeader = null;
    private boolean[] isFooter = null;
    private int count = 0;//总数量

    public SectionForRecyclerViewAdapter() {
        super();
        //注册adapter数据观察者
        registerAdapterDataObserver(new SectionDataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        init();
    }

    /**
     *
     * @return 总数量(包括header和footer的数量)
     */
    @Override
    public int getItemCount() {
        return count;
    }

    /**
     * 初始化
     */
    private void init() {
        count = countItems();
        initAllArraysLength(count);
        setupItemViewType();
    }

    /**
     * 设置数组的长度
     */
    private void initAllArraysLength(int count) {
        sectionForPosition = new int[count];
        positionWithInSection = new int[count];
        isHeader = new boolean[count];
        isFooter = new boolean[count];
    }

    //获取count的数量
    private int countItems() {
        int count = 0;
        int sections = getSectionCount();

        for (int i = 0; i < sections; i++) {
            count += 1 + getItemCountForSection(i) + (hasFooterInSection(i) ? 1 : 0);
        }

        return count;
    }

    //设置item的类型
    private void setupItemViewType() {
        int sections = getSectionCount();
        int index = 0;

        for (int i = 0; i < sections; i++) {
            setPrecomputedItem(index, true, false, i, 0);
            index++;

            for (int j = 0; j < getItemCountForSection(i); j++) {
                setPrecomputedItem(index, false, false, i, j);
                index++;
            }

            if (hasFooterInSection(i)) {
                setPrecomputedItem(index, false, true, i, 0);
                index++;
            }
        }
    }

    //为整个count设置是否是头节点，尾节点，节点
    private void setPrecomputedItem(int index, boolean isHeader, boolean isFooter, int section, int position) {
        this.isHeader[index] = isHeader;
        this.isFooter[index] = isFooter;
        sectionForPosition[index] = section;
        positionWithInSection[index] = position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        if (isSectionHeaderViewType(viewType)) {
            viewHolder = onCreateSectionHeaderViewHolder(parent, viewType);
        } else if (isSectionFooterViewType(viewType)) {
            viewHolder = onCreateSectionFooterViewHolder(parent, viewType);
        } else {
            viewHolder = onCreateItemViewHolder(parent, viewType);
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int sections = sectionForPosition[position];
        int index = positionWithInSection[position];

        if (isSectionHeaderPosition(position)) {
            onBindSectionHeaderViewHolder((H) holder, sections);
        } else if (isSectionFooterPosition(position)) {
            onBindSectionFooterViewHolder((F) holder, sections);
        } else {
            onBindItemViewHolder((VH) holder, sections, index);
        }
    }



    @Override
    public int getItemViewType(int position) {
        if (sectionForPosition == null) {
            init();
        }

        int section = sectionForPosition[position];
        int index = positionWithInSection[position];

        if (isSectionHeaderPosition(position)) {
            return getSectionHeaderViewType(section);
        } else if (isSectionFooterPosition(position)) {
            return getSectionFooterViewType(section);
        } else {
            return getSectionItemViewType(section, position);
        }
    }

    /**
     * 获取header视图类型
     */
    protected int getSectionHeaderViewType(int section) {
        return TYPE_SECTION_HEADER;
    }

    /**
     * 获取footer视图类型
     */
    protected int getSectionFooterViewType(int section) {
        return TYPE_SECTION_FOOTER;
    }

    /**
     * 获取item视图类型
     */
    protected int getSectionItemViewType(int section, int position) {
        return TYEP_ITEM;
    }

    /**
     * 判断该节点是否是Header
     */
    public boolean isSectionHeaderPosition(int position) {
        if (isHeader == null) {
            init();
        }

        return isHeader[position];
    }

    /**
     * 判断该节点是否是Footer
     */
    public boolean isSectionFooterPosition(int position) {
        if (isFooter == null) {
            init();
        }
        return isFooter[position];
    }

    /**
     * 判断是否是header视图
     */
    protected boolean isSectionHeaderViewType(int viewType) {
        return  viewType == TYPE_SECTION_HEADER;
    }

    /**
     * 判断是否是footer视图
     */
    protected boolean isSectionFooterViewType(int viewType) {
        return viewType == TYPE_SECTION_FOOTER;
    }

    /**
     * 获取组数
     */
    protected abstract int getSectionCount();

    /**
     * 获取每组的item数
     */
    protected abstract int getItemCountForSection(int section);

    /**
     * 判断是否有footer视图
     */
    protected abstract boolean hasFooterInSection(int section);

    /**
     * 用类H创建header视图
     */
    protected abstract H onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 用类F创建Footer视图
     */
    protected abstract F onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);

    /**
     * 用类VH创建item视图
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定header视图
     */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /**
     * 绑定Footer视图
     */
    protected abstract void onBindSectionFooterViewHolder(F holder, int section);

    /**
     * 绑定item视图
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    private class SectionDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged(){
            init();
        }
    }

}




