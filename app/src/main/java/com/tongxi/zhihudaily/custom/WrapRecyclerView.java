//package com.tongxi.zhihudaily.custom;
//
//import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//
//import com.tongxi.zhihudaily.adapter.WrapRecyclerAdapter;
//
///**
// * Created by qf on 2016/10/24.
// */
//public class WrapRecyclerView extends RecyclerView {
//
//    private int headerLayout;   //头布局
//    private Adapter adapter;
//
//    public void setHeaderLayout(int headerLayout) {
//        this.headerLayout = headerLayout;
//    }
//
//    public void setAdapter(Context context,Adapter adapter){
//        this.adapter = new WrapRecyclerAdapter(context,adapter,headerLayout);
//    }
//
//    public WrapRecyclerView(Context context) {
//        super(context);
//    }
//
//    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    public void addHeaderView(Context context, int headerLayout){
//        if (!(adapter instanceof WrapRecyclerAdapter)){      //说明是头布局
//            adapter = new WrapRecyclerAdapter(context, adapter,headerLayout);
//        }
//    }
//
//
//
//
//}
