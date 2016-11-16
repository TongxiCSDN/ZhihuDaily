package com.tongxi.zhihudaily.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;

import butterknife.BindView;

/**
 * 可收起/展开的TextView
 * Created by qf on 2016/11/12.
 */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

    private static final int MAX_LINES = 10;     //默认最大行数
    private static final int SPREADTEXT_STATE_NONE = 0;     //无状态
    private static final int SPREADTEXT_STATE_RERACT = 1;   //缩回状态
    private static final int SPREADTEXT_STATE_SPREAD = 2;   //展开状态

    @BindView(R.id.tvContent)    //显示内容的文本
    TextView tvContent;
    @BindView(R.id.ivBottom)    //显示操作的文本
    ImageView ivBottom;
    @BindView(R.id.tvBottom)  //显示操作的图片
    TextView tvBottom;
    @BindView(R.id.llContent)
    LinearLayout llContent;

    private int maxLineCount = MAX_LINES;
    private int mState;      //实际状态
    private int actuallyCounts;  //实际行数

    private InnerRunnable runable;
    private boolean flag = false;

    public ExpandableTextView(Context context) {
        this(context, null, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //填充布局
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tv_expandable, this);
        view.setPadding(0, 0, 0, 0);
        llContent.setOnClickListener(this);
        setBottomTextGravity(Gravity.CENTER_HORIZONTAL);
        runable = new InnerRunnable();
    }

    /**
     * 设置展开表示的显示位置
     * @param gravity
     */
    public void setBottomTextGravity(int gravity) {
        llContent.setGravity(gravity);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag){
            flag = !flag;
            //文本内容小于设定的显示行数
            if (actuallyCounts <= MAX_LINES && tvContent.getLineCount() <= MAX_LINES){
                mState = SPREADTEXT_STATE_NONE;
                llContent.setVisibility(GONE);
                tvContent.setMaxLines(MAX_LINES+1);
            }else {
                post(runable);

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onClick(View v) {
        flag = false;
        requestLayout();
    }

    /**
     * 设置最大行数
     * @param maxLineCount
     */
    public void setMaxLineCount(int maxLineCount){
        this.maxLineCount = maxLineCount;
    }

    /**
     * 设置文本颜色
     * @param color
     */
    public void setContentColor(int color){
        this.tvContent.setTextColor(color);
    }

    /**
     * 设置文本字体大小
     * @param size
     */
    public void setContentSize(float size){
        this.tvContent.setTextSize(size);
    }

    /**
     * 设置文本内容
     * @param charSequence
     */
    public final void setContent(CharSequence charSequence){
        tvContent.setText(charSequence,TextView.BufferType.NORMAL);
        post(new Runnable() {
            @Override
            public void run() {
                actuallyCounts = tvContent.getLineCount();
            }
        });
        mState = SPREADTEXT_STATE_SPREAD;
        flag = false;
        requestLayout();
    }

    class InnerRunnable implements Runnable {

        @Override
        public void run() {
            if (mState == SPREADTEXT_STATE_SPREAD){
                tvContent.setMaxLines(MAX_LINES);
                llContent.setVisibility(VISIBLE);
                tvBottom.setText("显示全文");
                ivBottom.setImageResource(R.mipmap.comment_icon_fold);
                mState = SPREADTEXT_STATE_RERACT;
            }else if (mState == SPREADTEXT_STATE_RERACT){
                tvContent.setMaxLines(Integer.MAX_VALUE);
                llContent.setVisibility(VISIBLE);
                tvBottom.setText("收起");
                ivBottom.setImageResource(R.mipmap.comment_icon_expand);
                mState = SPREADTEXT_STATE_SPREAD;
            }
        }
    }

}
















































































