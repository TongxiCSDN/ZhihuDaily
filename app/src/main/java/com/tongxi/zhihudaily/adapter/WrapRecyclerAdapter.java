//package com.tongxi.zhihudaily.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// *失败中
// * Created by qf on 2016/10/24.
// */
//public class WrapRecyclerAdapter extends RecyclerView.Adapter{
//
//    private RecyclerView.Adapter adapter;
//    private int headerlayout;
//    private int currentPosition;
//    private Context context;
//
//    public WrapRecyclerAdapter(Context context,RecyclerView.Adapter adapter, int headerLayout) {
//        this.context = context;
//        this.adapter = adapter;
//        this.headerlayout = headerLayout;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == RecyclerView.INVALID_TYPE){
//            View headView = LayoutInflater.from(context).inflate(headerlayout,parent,false);
//            return new HeaderViewHolder(headView);
//        }else {
//            return adapter.onCreateViewHolder(parent,viewType);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (position == 0){
//            return;
//        }else {
//            adapter.onBindViewHolder((RecyclerViewAdapter.ViewHolder)holder,position);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return adapter.getItemCount()+1;    //主内容加上一个头布局
//     }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0){
//            return RecyclerView.INVALID_TYPE;     //头布局位置
//        }else {
//            return position;      //该位置是CardView
//        }
//    }
//
//    private class HeaderViewHolder extends RecyclerView.ViewHolder {
//
//        public HeaderViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//}
