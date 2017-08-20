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
    private static final int TYPE_SECTION_ITEM =-3;

    private int[] sectionForPosition = null;    //第几组
    private int[] positionWithInSection = null; //组中的item位置
    private boolean[] isHeader = null;
    private boolean[] isFooter = null;
    private int count = 0;                     //总数量


    public SectionForRecyclerViewAdapter(){
        super();
        //注册adapter数据观察者 ，，，，，干嘛的
        registerAdapterDataObserver(new SectionDataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        init();
    }

    /*
    * 获取count数量
    * */
    private int countItems(){
        int count = 0;
        int sections = getSectionCount();

        for(int i = 0; i < sections ;i++){
            count += 1 + (hasHeaderInSection(i)? 1 : 0) +getItemCountForSection(i) + (hasFooterInSection(i) ? 1 : 0);
        }
        return count;
    }

    /*
    * 设置item类型
    * */
    private void setupItemViewType(){
        int sections = getSectionCount();
        int index = 0;

        for (int i=0; i<sections; i++) {
            setPrecomputerdItem(index, false, false, i, 0);
            index++;
            for (int j=0; j< getItemCountForSection(i); j++){
                setPrecomputerdItem(index, false, false, i, j);
                index++;
            }

            if (hasHeaderInSection(i)){
                setPrecomputerdItem(index, false, true, i, 0);
                index++;
            }

            if (hasFooterInSection(i)){
                setPrecomputerdItem(index, false, true, i, 0);
                index++;
            }
        }
    }

    /*
    * 为整个count设置是否是头结点,尾节点,节点
    * */
    private void setPrecomputerdItem(int index, boolean isHeader, boolean isFooter, int section, int position){
        this.isHeader[index] = isHeader;
        this.isFooter[index] = isFooter;
        sectionForPosition[index] = section;
        positionWithInSection[index] = position;
    }

    /*
    * 设置数组长度
    * */
    private void initAllArraysLength(int count){
        sectionForPosition = new int[count];
        positionWithInSection = new int[count];
        isHeader = new boolean[count];
        isFooter = new boolean[count];
    }


    @Override
    public int getItemCount(){
        return count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        if (isSectionHeaderViewType(viewType)){
            viewHolder = onCreateSectionHeaderViewHolder(parent, viewType);
        }else if (isSectionFooterViewType(viewType)){
            viewHolder = onCreateSectionFooterViewHolder(parent, viewType);
        }else {
            viewHolder = onCreateItemViewHolder(parent, viewType);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int sections = sectionForPosition[position];
        int index = positionWithInSection[position];

        if (isSectionHeaderPosition(position)){
            onBindSectionHeaderViewHolder((H) holder, sections);
        } else if (isSectionFooterPosition(position)){
            onBindSectionFooterViewHolder((F) holder, sections);
        } else {
            onBindItemViewHolder((VH) holder, sections, index);
        }
    }

    @Override
    public int getItemViewType(int position){
        if (sectionForPosition == null){
            init();
        }

        int section = sectionForPosition[position];
        int index = positionWithInSection[position];

        if (isSectionHeaderPosition(position)) {
            return getSectionHeaderViewType(section);
        }else if (isSectionFooterPosition(position)) {
            return getSctionFooterViewType(section);
        }else {
            return getSectionItemViewType(section, position);
        }
    }

    /*
    * 获取header视图
    * */
    public int getSectionHeaderViewType(int section){
        return TYPE_SECTION_HEADER;
    }

    /*
    *获取footer视图类型
    * */
    public int getSctionFooterViewType(int section){
        return TYPE_SECTION_FOOTER;
    }

    /*
    * 获取items视图
    * */
    public int getSectionItemViewType(int section, int position){
        return TYPE_SECTION_ITEM;
    }

    /*
    * 判断该节点是不是Header
    * */
    public boolean isSectionHeaderPosition(int positon){
        if (isHeader == null){
            init();
        }

        return isHeader[positon];
    }

    /*
    * 判断是不是该节点是不是Footer
    * */
    public boolean isSectionFooterPosition(int position){
        if (isFooter == null) {
            init();
        }

        return isFooter[position];
    }

    /*
    * 判断是否是footer视图
    * */
    protected boolean isSectionFooterViewType(int viewType){
        return viewType == TYPE_SECTION_FOOTER;
    }

    /*
    * 判断是否是header视图
    * */
    protected boolean isSectionHeaderViewType(int viewType){
        return viewType == TYPE_SECTION_HEADER;
    }



    //abstract
    /*
    * 获取组数
    * */
    protected abstract int getSectionCount();

    /*
    * 获取每组item数量
    * */
    protected abstract int getItemCountForSection(int section);

    /*
    * 判断是否有Footer视图
    * */
    protected abstract boolean hasFooterInSection(int section);
    /*

    /*
    * 判断是否有Footer视图
    * */
    protected abstract boolean hasHeaderInSection(int section);
    /*


    /*
    * 用H类获取Header视图
    * */
    protected abstract H onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /*
    * 用F类创建Footer视图
    * */
    protected abstract F onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);

    /*
    * 用VH创建item视图
    * */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /*
    * 绑定Header视图
    * */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /*
    * 绑定Footer视图
    * */
    protected abstract void onBindSectionFooterViewHolder(F holder, int section);

    /*
    * 绑定Item视图
    * */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    private void init() {
        count = countItems();
        initAllArraysLength(count);
        setupItemViewType();
    }

    private class SectionDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged(){
            init();
        }
    }

}




