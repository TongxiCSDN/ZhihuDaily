package com.tongxi.zhihudaily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.activity.DailyDetailActivity;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/10/26.
 */
public class NewDailyFragment extends Fragment {

    View view;
    @BindView(R.id.ivMainHeaderPic)
    ImageView ivMainHeaderPic;
    @BindView(R.id.tvMainHeaderPic)
    TextView tvMainHeaderPic;
    @BindView(R.id.rlFragment)
    RelativeLayout rlFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main_new_daily, container, false);
        }
        ButterKnife.bind(this, view);

        //处理从MainActivity接收过来的数据
        tvMainHeaderPic.setText(getArguments().getString("title"));
        String imageUrl = getArguments().getString("imageUrl");
        DownLoadImageUtil.load(getContext(), imageUrl, R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivMainHeaderPic);
        final int id = getArguments().getInt("id");
        rlFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DailyDetailActivity.class);
                intent.putExtra("dailyId", id);
                startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

