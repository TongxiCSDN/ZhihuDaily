package com.tongxi.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.activity.DailyDetailActivity;
import com.tongxi.zhihudaily.model.ThemeContent;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/15.
 */
public class RVThemeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ThemeContent themeContent;
    private LayoutInflater inflater;
    private List<ThemeContent.StoriesBean> storiesBeen;

    public RVThemeContentAdapter(Context context,ThemeContent themeContent) {
        this.context = context;
        this.themeContent = themeContent;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == RecyclerView.INVALID_TYPE) {
            view = inflater.inflate(R.layout.adapter_themecontent_head_view, parent, false);
            return new HeadViewHolder(view);
        }
        view = inflater.inflate(R.layout.adapter_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头布局
        if (position == 0){
            DownLoadImageUtil.load(context,
                    themeContent.getImage(),
                    R.mipmap.mq_ic_emoji_normal,
                    R.mipmap.fail_img,
                    ((HeadViewHolder) holder).ivTheme);
            ((HeadViewHolder) holder).tvTheme.setText(themeContent.getDescription());
            List<ThemeContent.EditorsBean> editors = themeContent.getEditors();
            for (int i = 0; i < editors.size(); i++) {
                ThemeContent.EditorsBean editorsBean = editors.get(i);
                String url = editorsBean.getAvatar();
                ImageView imageView = new ImageView(context);
                DownLoadImageUtil.load(context,url,R.mipmap.mq_ic_emoji_normal,R.mipmap.fail_img,imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5,5,5,5);
                imageView.setLayoutParams(layoutParams);
                ((HeadViewHolder) holder).llThemeEditor.addView(imageView);
            }
        }else {
            storiesBeen = themeContent.getStories();
            final ThemeContent.StoriesBean bean = storiesBeen.get(position - 1);
            List<String> images = bean.getImages();
            ((ViewHolder) holder).tvDailyTitle.setText(bean.getTitle());
            if (images == null || images.size() == 0){
                ((ViewHolder) holder).rlImage.setVisibility(View.GONE);
            }else {
                DownLoadImageUtil.load(context,
                        images.get(0),
                        R.mipmap.mq_ic_emoji_normal,
                        R.mipmap.fail_img,
                        ((ViewHolder) holder).ivDailyImage);
            }
            ((ViewHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DailyDetailActivity.class);
                    intent.putExtra("dailyId",bean.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return themeContent == null ? 0 : themeContent.getStories().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return RecyclerView.INVALID_TYPE;
        }
        return position;
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivTheme)
        ImageView ivTheme;
        @BindView(R.id.llThemeEditor)
        LinearLayout llThemeEditor;
        @BindView(R.id.tvTheme)
        TextView tvTheme;

        HeadViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvDailyTitle)
        TextView tvDailyTitle;
        @BindView(R.id.ivDailyImage)
        ImageView ivDailyImage;
        @BindView(R.id.ivManyPics)
        ImageView ivManyPics;
        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.rlImage)
        RelativeLayout rlImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
