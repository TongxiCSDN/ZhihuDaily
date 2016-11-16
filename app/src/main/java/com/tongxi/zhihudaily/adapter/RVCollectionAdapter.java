package com.tongxi.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.model.MyCollectionBean;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/9.
 */
public class RVCollectionAdapter extends RecyclerView.Adapter<RVCollectionAdapter.ViewHolder> {

    private Context context;
    private List<MyCollectionBean> myCollectionBeens;
    private LayoutInflater inflater;

    private RVCollectionItemClick itemClick;
    private boolean multipic;

    public RVCollectionAdapter(Context context, List<MyCollectionBean> myCollectionBeens) {
        this.context = context;
        this.myCollectionBeens = myCollectionBeens;
        this.inflater = LayoutInflater.from(context);
    }

    public void setItemClick(RVCollectionItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_collection_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MyCollectionBean bean = myCollectionBeens.get(position);
        multipic = bean.getMultipic();
        Log.e("Temy", "RVCollectionAdapter: multipic = "+multipic);
        if (multipic){
            holder.ivManyPics.setVisibility(View.VISIBLE);
        }
        holder.tvDailyTitle.setText(bean.getTitle());
        DownLoadImageUtil.load(context,bean.getImage(),R.mipmap.mq_ic_emoji_normal,R.mipmap.fail_img,holder.ivDailyImage);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null){
                    itemClick.OnRVCollecteItemClick(bean.getNewsId(),multipic);
                    holder.tvDailyTitle.setTextColor(context.getResources().getColor(R.color.high_light_text));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return myCollectionBeens == null ? 0 : myCollectionBeens.size();
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

    public interface RVCollectionItemClick{
        public void OnRVCollecteItemClick(int dailyId,boolean multipic);
    }
}
