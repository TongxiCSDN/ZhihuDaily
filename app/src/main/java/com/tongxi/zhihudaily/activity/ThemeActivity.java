package com.tongxi.zhihudaily.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tongxi.zhihudaily.Constant;
import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.adapter.RVThemeContentAdapter;
import com.tongxi.zhihudaily.model.ThemeContent;
import com.tongxi.zhihudaily.util.HttpUtil;
import com.tongxi.zhihudaily.util.JsonUtil;
import com.tongxi.zhihudaily.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeActivity extends AppCompatActivity {

    @BindView(R.id.toolBarTheme)
    Toolbar toolBarTheme;
    @BindView(R.id.rvThemeContent)
    RecyclerView rvThemeContent;
    private int themeId;
    private List<ThemeContent.StoriesBean> storiesList = new ArrayList<>();
    private ThemeContent themeContent;
    private String themeName;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MESSAGE_THEME_CONTENT:
                    themeContent = ((ThemeContent) msg.obj);
                    List<ThemeContent.StoriesBean> stories = themeContent.getStories();
                    storiesList.addAll(stories);
                    initRecycleView();
                    break;
            }
        }
    };
    private RVThemeContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);

        themeId = getIntent().getIntExtra("themeId", -1);
        themeName = getIntent().getStringExtra("themeName");

        //下载数据详情
        if (themeId != -1) {
            downLoadThemeContent();
        }

        //设置ToolBar
        initToolBar();

        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new RVThemeContentAdapter(this,themeContent);
        rvThemeContent.setAdapter(adapter);
        rvThemeContent.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    //简单设置ToolBar
    private void initToolBar() {
        toolBarTheme.setTitle(themeName);
        setSupportActionBar(toolBarTheme);
        toolBarTheme.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
//        Menu menu = toolBarTheme.getMenu();
//        MenuItem item = menu.findItem(android.R.id.home);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                onBackPressed();
//                return true;
//            }
//        });
    }

    /**
     * 下载主题详情页
     */
    private void downLoadThemeContent() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getThemeContent(themeId, ThemeActivity.this, true);
                ThemeContent themeContent = JsonUtil.parseThemeContent(json);
                if (themeContent != null) {
                    Message msg = handler.obtainMessage(Constant.MESSAGE_THEME_CONTENT, themeContent);
                    handler.sendMessage(msg);
                }
            }
        });
    }
}
