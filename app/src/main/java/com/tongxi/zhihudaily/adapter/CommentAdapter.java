package com.tongxi.zhihudaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.model.LongComment;
import com.tongxi.zhihudaily.model.ShortComment;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/12.
 */
public class CommentAdapter extends BaseAdapter {

    private final String TAG = "Temy";

    private Context context;
    private List<LongComment.CommentsBean> lComments;
    private List<ShortComment.CommentsBean> sComments;
    private LayoutInflater inflater;
    private boolean isAddShort;
    private ViewHolder holder;
    private ViewHolderHead headHolder;

    public CommentAdapter(Context context, List<LongComment.CommentsBean> lComments, List<ShortComment.CommentsBean> sComments) {
        this.context = context;
        this.lComments = lComments;
        this.sComments = sComments;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;
        if ((lComments == null || lComments.size() == 0) && sComments == null) {
            count = 1;     //空白页和短评总数量
        }else if ((lComments == null || lComments.size() == 0) && sComments != null && isAddShort) {
            count = 1 + sComments.size();
        }else if (lComments != null && lComments.size() > 0 && (sComments == null || !isAddShort)) {
            count = 1 + lComments.size();
        }else {
            count = 1 + lComments.size() + sComments.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == ListView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_short_comment_count, parent, false);
                headHolder = new ViewHolderHead(convertView);
            }
            if (lComments == null || lComments.size() == 0) {
                headHolder.ivEmpty.setVisibility(View.VISIBLE);
            } else {
                headHolder.ivEmpty.setVisibility(View.GONE);
            }
            if (sComments != null) {
                headHolder.tvCount.setText(sComments.size() + "条短评");
            } else {
                headHolder.tvCount.setText("0条短评");
            }
        } else {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_comment_list_view, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position < lComments.size()) {
                LongComment.CommentsBean commentsBean = lComments.get(position);
                String author = commentsBean.getAuthor();
                String avatar = commentsBean.getAvatar();
                String content = commentsBean.getContent();
                int likes = commentsBean.getLikes();
                holder.tvAuthor.setText(author);
                holder.tvLikeCount.setText(likes + "");
                holder.etvComment.setText(content);
                DownLoadImageUtil.load(context, avatar, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivAuthor);
            } else {
                int currentPosition = position - lComments.size()-1;
                ShortComment.CommentsBean commentsBean = sComments.get(currentPosition);
                String author = commentsBean.getAuthor();
                String avatar = commentsBean.getAvatar();
                String content = commentsBean.getContent();
                int likes = commentsBean.getLikes();
                holder.tvAuthor.setText(author);
                holder.tvLikeCount.setText(likes + "");
                holder.etvComment.setText(content);
                DownLoadImageUtil.load(context, avatar, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, holder.ivAuthor);
            }
        }

        headHolder.llShortComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAddShort) {
                    headHolder.ivSwitch.setImageResource(R.mipmap.comment_icon_expand);
                } else {
                    headHolder.ivSwitch.setImageResource(R.mipmap.comment_icon_fold);
                }
                isAddShort = !isAddShort;
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if ((lComments == null || lComments.size() == 0) && position == 0) {
            return ListView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
        }
        if (lComments != null && lComments.size() > 0 && position == lComments.size()) {
            return ListView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
        }
        return super.getItemViewType(position);
    }




    static class ViewHolder {
        @BindView(R.id.ivAuthor)
        ImageView ivAuthor;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvLikeCount)
        TextView tvLikeCount;
        @BindView(R.id.etvComment)
        TextView etvComment;
        @BindView(R.id.tvCommentTime)
        TextView tvCommentTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderHead {
        @BindView(R.id.ivEmpty)
        ImageView ivEmpty;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.ivSwitch)
        ImageView ivSwitch;
        @BindView(R.id.llShortComment)
        LinearLayout llShortComment;

        ViewHolderHead(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
