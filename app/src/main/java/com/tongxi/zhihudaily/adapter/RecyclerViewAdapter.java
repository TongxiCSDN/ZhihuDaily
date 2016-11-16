package com.tongxi.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.model.NewDailyBean;
import com.tongxi.zhihudaily.model.OldDailyBean;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 不出来控件，失败
 * Created by qf on 2016/10/22.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "Temy";
    private Context context;
    private List<NewDailyBean.StoriesBean> storiesBeanList;   //每日推送的新闻
    private List<OldDailyBean.StoriesBean> oldDailyBeanList;    //往期消息推送
    private View headerView;
    private LayoutInflater inflater;
    private RVItemOnClickListener listener;

    public void setListener(RVItemOnClickListener listener) {
        this.listener = listener;
    }

    public RecyclerViewAdapter(Context context, List<NewDailyBean.StoriesBean> storiesBeanList, List<OldDailyBean.StoriesBean> oldDailyBeanList, View headerView) {
        this.context = context;
        this.storiesBeanList = storiesBeanList;
        this.inflater = LayoutInflater.from(context);
        this.headerView = headerView;
        this.oldDailyBeanList = oldDailyBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == RecyclerView.INVALID_TYPE) {
            view = headerView;
            return new HeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.adapter_card_view, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            return;
        } else if (position <= storiesBeanList.size()) {
            final NewDailyBean.StoriesBean stories = storiesBeanList.get(position - 1);
            ((RecyclerViewAdapter.ViewHolder) holder).tvDailyTitle.setText(stories.getTitle());
            //加载图片
            List<String> images = stories.getImages();
            final boolean multipic = stories.isMultipic();
            if (multipic) {
                ((ViewHolder) holder).ivManyPics.setVisibility(View.VISIBLE);
            }
            String imageUrl = images.get(0);
            DownLoadImageUtil.load(context, imageUrl, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, ((ViewHolder) holder).ivDailyImage);
            if (listener != null){
                ((RecyclerViewAdapter.ViewHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //设置点击后的文字高亮
                        ((RecyclerViewAdapter.ViewHolder) holder).tvDailyTitle.setTextColor(context.getResources().getColor(R.color.high_light_text));
                        listener.handlerOnItemClick(stories.getId(),multipic);
                    }
                });
            }
        } else {
            //确定拿列表中的第几个日期的往期新闻集合（每期20个新闻数据）
            int listPosition = position - storiesBeanList.size()-1;
            final OldDailyBean.StoriesBean storiesBean = oldDailyBeanList.get(listPosition);
            ((RecyclerViewAdapter.ViewHolder) holder).tvDailyTitle.setText(storiesBean.getTitle());
            List<String> images = storiesBean.getImages();
            final boolean multipic = storiesBean.isMultipic();
            if (multipic) {
                ((ViewHolder) holder).ivManyPics.setVisibility(View.VISIBLE);
            }
            String imageUrl = images.get(0);
            DownLoadImageUtil.load(context, imageUrl, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, ((ViewHolder) holder).ivDailyImage);
            if (listener != null){
                ((ViewHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.handlerOnItemClick(storiesBean.getId(), multipic);
                        ((RecyclerViewAdapter.ViewHolder) holder).tvDailyTitle.setTextColor(context.getResources().getColor(R.color.high_light_text));
                    }
                });
            }

        }
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (storiesBeanList == null && (oldDailyBeanList == null || oldDailyBeanList.size() == 0)) {
            count = 0;
        } else if (storiesBeanList != null && (oldDailyBeanList == null || oldDailyBeanList.size() == 0)) {
            count = storiesBeanList.size();
        } else if (storiesBeanList == null && (oldDailyBeanList != null && oldDailyBeanList.size() > 0)) {
            count = oldDailyBeanList.size();
        } else {
            count = oldDailyBeanList.size() + storiesBeanList.size();
        }
        return count;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDailyTitle)
        TextView tvDailyTitle;
        @BindView(R.id.ivDailyImage)
        ImageView ivDailyImage;
        @BindView(R.id.ivManyPics)
        ImageView ivManyPics;
        @BindView(R.id.cardview)
        CardView cardview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return RecyclerView.INVALID_TYPE;
        } else {
            return position;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_pager)
        ViewPager viewPager;
        @BindView(R.id.llDot)
        LinearLayout llDot;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface RVItemOnClickListener {
        void handlerOnItemClick(int id,boolean isMultipic);
    }
}
