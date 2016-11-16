package com.tongxi.zhihudaily.activity;

/**
 * Created by qf on 2016/10/22.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongxi.zhihudaily.Constant;
import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.adapter.HeaderViewPagerAdapter;
import com.tongxi.zhihudaily.adapter.NavigationMenuAdapter;
import com.tongxi.zhihudaily.adapter.RecyclerViewAdapter;
import com.tongxi.zhihudaily.fragment.NewDailyFragment;
import com.tongxi.zhihudaily.model.NewDailyBean;
import com.tongxi.zhihudaily.model.OldDailyBean;
import com.tongxi.zhihudaily.model.ThemeListBean;
import com.tongxi.zhihudaily.util.DateUtil;
import com.tongxi.zhihudaily.util.HttpUtil;
import com.tongxi.zhihudaily.util.JsonUtil;
import com.tongxi.zhihudaily.util.ThreadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.RVItemOnClickListener {

    @BindView(R.id.toorBar)
    Toolbar toorBar;
    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.ivFavorites)
    ImageView ivFavorites;
    @BindView(R.id.tvFavorites)
    TextView tvFavorites;
    @BindView(R.id.ivDownLoad)
    ImageView ivDownLoad;
    @BindView(R.id.tvDownLoad)
    TextView tvDownLoad;
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.tvHome)
    TextView tvHome;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srlRefesh)
    SwipeRefreshLayout srlRefesh;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.llDot)
    LinearLayout llDot;
    @BindView(R.id.llViewPagerRoot)
    LinearLayout llViewPagerRoot;
    @BindView(R.id.llMain)
    LinearLayout llMain;
    @BindView(R.id.lvNavigation)
    ListView lvNavigation;
    @BindView(R.id.llNavigationRoot)
    LinearLayout llNavigationRoot;

    private boolean nightMode = false;
    private NavigationMenuAdapter menuAdapter;
    private List<HashMap<String, Object>> themeList = new ArrayList<>();
    private List<Fragment> newDailyFragments = new ArrayList<>();
    private List<NewDailyBean.TopStoriesBean> top_stories;
    private List<NewDailyBean.StoriesBean> stories;
    private List<OldDailyBean.StoriesBean> oldDailyBeanList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private HeaderViewPagerAdapter headerViewPagerAdapter;
    private int dotCurrentPostion;
    private String currentDate;
    private boolean ssoLoginSucceed;


    private long pressedTime = 0;   //双击退出事件的时间差

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MESSAGE_MAIN_THEME_LIST:
                    ThemeListBean themeListBean = (ThemeListBean) msg.obj;
                    if (themeListBean == null) {
                        break;
                    }
                    List<ThemeListBean.OthersBean> themeName = themeListBean.getOthers();
                    if (themeName != null) {
                        for (ThemeListBean.OthersBean bean : themeName) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("theme", bean);
                            map.put("isAdd", false);
                            Log.e("Temy", "MainActivity_handleMessage: " + bean.getName());
                            themeList.add(map);
                        }
                        initLvNavigation();
                    }
                    break;
                case Constant.MESSAGE_MAIN_NEW_DAILY:
                    NewDailyBean newDailyBean = (NewDailyBean) msg.obj;
                    currentDate = newDailyBean.getDate();
                    top_stories = newDailyBean.getTop_stories();
                    //初始化导航
                    initNewDailyFragment(top_stories);
                    //初始化新闻列表 -- 增加数据
                    stories = newDailyBean.getStories();
                    initDailyList(stories, oldDailyBeanList);
                    break;
                case Constant.MESSAGE_MAIN_OLD_DAILY:
                    //获取往期新闻
                    OldDailyBean oldDailyBean = (OldDailyBean) msg.obj;
                    if (oldDailyBean != null && oldDailyBean.getStories() != null) {
                        List<OldDailyBean.StoriesBean> stories = oldDailyBean.getStories();
                        oldDailyBeanList.addAll(stories);
                    }
                    //刷新adapter
                    recyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化分享ShareSDK
        ShareSDK.initSDK(this);

        //初始化ToolBar
        initToorBar();

        //初始化侧滑菜单中的ListView数据
        downLoadData();

        //获取最新新闻（top）
        downLoadNewDaily();

        //初始化下拉刷新
        pullDownRefesh();

        //初始化新闻列表
        initDailyList(stories, oldDailyBeanList);

        //初始化导航条上的小圆点
        initHeaderPoint();

        //设置RecyclerView的滚动监听
        rvScrollChanged();

        //初始化导航条
        if (top_stories != null) {
            initNewDailyFragment(top_stories);
        }

        //导航的滚动监听和点击监听
        vpPageAndTouchChange();

        //注册事件监听
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.tvUserName)
    public void onLogin(){
        startActivity(new Intent(this,LoginActivity.class));
        drawerLayout.closeDrawer(Gravity.LEFT);
    }


    /**
     * 设置RecyclerView的上拉刷新
     */
    private void rvScrollChanged() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        int firstItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        int lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        if (layoutManager.getItemCount() >= 6) {
                            toorBar.setTitle("今日热闻");
                        }
                        if (lastItemPosition >= layoutManager.getItemCount() - 1) {
                            //下载更新往期消息
                            downLoadOldDaily();
                        }
                    }
                }
            }
        });
    }

    /**
     * 下载往期新闻
     */
    private void downLoadOldDaily() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String date = DateUtil.getOtherDateString(currentDate, -1, new SimpleDateFormat("yyyyMMdd"));
                String json = HttpUtil.getOldDaily(date, MainActivity.this, true);
                OldDailyBean oldDailyBean = null;
                if (json != null) {
                    oldDailyBean = JsonUtil.parseOldDailyBean(json);
                }
                currentDate = date;
                Message msg = handler.obtainMessage(Constant.MESSAGE_MAIN_OLD_DAILY, oldDailyBean);
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 设置导航条的页面变化和点击页面的监听
     */
    private void vpPageAndTouchChange() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置小圆点装填变化
                if (llDot.getChildCount() > position) {
                    llDot.getChildAt(dotCurrentPostion).setEnabled(false);
                    llDot.getChildAt(position).setEnabled(true);
                    dotCurrentPostion = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * 下拉刷新
     */
    private void pullDownRefesh() {
        srlRefesh.setColorSchemeColors(getResources().getColor(R.color.actionBar_bg));
        srlRefesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downLoadNewDaily();
                srlRefesh.setRefreshing(false);
            }
        });
    }

    /**
     * 将首页的导航碎片添加进ViewPager
     *
     * @param top_stories --- 数据
     */
    private void initNewDailyFragment(List<NewDailyBean.TopStoriesBean> top_stories) {
        for (int i = 0; i < top_stories.size(); i++) {
            NewDailyFragment fragment = new NewDailyFragment();
            NewDailyBean.TopStoriesBean topStoriesBean = top_stories.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl", topStoriesBean.getImage());
            bundle.putString("title", topStoriesBean.getTitle());
            bundle.putInt("id",topStoriesBean.getId());
            fragment.setArguments(bundle);
            newDailyFragments.add(fragment);
        }
        fragmentManager = getSupportFragmentManager();
        headerViewPagerAdapter = new HeaderViewPagerAdapter(fragmentManager, newDailyFragments);
        headerViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(headerViewPagerAdapter);
    }

    /**
     * 根据首页头部碎片的数量绘制联动小圆点
     */
    private void initHeaderPoint() {
        for (int i = 0; i < 5; i++) {
            ImageView pointImage = new ImageView(this);
            pointImage.setImageResource(R.drawable.fragment_index_point);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 10;
            if (i > 0) {
                layoutParams.rightMargin = 10;
                pointImage.setEnabled(false);
            }
            pointImage.setLayoutParams(layoutParams);
            llDot.addView(pointImage);
        }
    }

    /**
     * 下载最新日报
     */
    private void downLoadNewDaily() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String newDailyJson = HttpUtil.getNewDaily(MainActivity.this, true);
                NewDailyBean newDailyBean = null;
                if (newDailyJson != null) {
                    newDailyBean = JsonUtil.parseNewDailyBean(newDailyJson);
                }
                if (newDailyBean != null) {
                    Message msg = handler.obtainMessage(Constant.MESSAGE_MAIN_NEW_DAILY, newDailyBean);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    /**
     * 初始化新闻列表
     */
    private void initDailyList(List<NewDailyBean.StoriesBean> stories, List<OldDailyBean.StoriesBean> oldDailyBeanList) {
        llMain.removeView(llViewPagerRoot);
        recyclerViewAdapter = new RecyclerViewAdapter(this, stories, oldDailyBeanList, llViewPagerRoot);
        recyclerViewAdapter.setListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter.notifyDataSetChanged();
    }


    /**
     * 初始化ToolBar
     */
    private void initToorBar() {
        setSupportActionBar(toorBar);
        toorBar.setTitle("首页");
        toorBar.setNavigationIcon(R.mipmap.side_menu);
    }


    /**
     * 初始化侧滑菜单中的ListView数据
     */
    private void downLoadData() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String themeListJson = HttpUtil.getThemeList(MainActivity.this, false);
                Log.e("Temy", "Main_Activity_run: themeListJson = " + themeListJson);
                ThemeListBean themeListBean = null;
                if (themeListJson != null) {
                    themeListBean = JsonUtil.parseThemeList(themeListJson);
                }
                Message msg = handler.obtainMessage(Constant.MESSAGE_MAIN_THEME_LIST, themeListBean);
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 初始化侧滑菜单数据
     */
    private void initLvNavigation() {
        menuAdapter = new NavigationMenuAdapter(MainActivity.this, themeList, drawerLayout);
        lvNavigation.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
    }

    /**
     * 侧滑菜单中的ListView点击监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lvNavigation)
    public void onLvNavigationItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, Object> map = themeList.get(position);
        ThemeListBean.OthersBean theme = (ThemeListBean.OthersBean) map.get("theme");
        int themeId = theme.getId();
        String name = theme.getName();
        Intent intent = new Intent(this,ThemeActivity.class);
        intent.putExtra("themeId",themeId);
        intent.putExtra("themeName",name);
        startActivity(intent);
        drawerLayout.closeDrawer(Gravity.LEFT,true);
        menuAdapter.notifyDataSetChanged();
    }


    /**
     * 侧滑菜单中【我的收藏】点击事件
     *
     * @param view
     */
    @OnClick({R.id.tvFavorites, R.id.ivFavorites})
    public void onFavoritesClick(View view) {
        startActivity(new Intent(this, CollectionActivity.class));
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    @OnClick({R.id.tvDownLoad, R.id.ivDownLoad})
    public void onDownLoadClick(View view) {
        Toast.makeText(MainActivity.this, "进入离线下载界面", Toast.LENGTH_SHORT).show();
    }

    /**
     * 读取菜单资源，载入动作项
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return true;
    }

    /**
     * ToolBar 的菜单项点击事件
     *
     * @param item 菜单项
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.action_message:
                Toast.makeText(MainActivity.this, "打开消息界面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_mode:
                Toast.makeText(MainActivity.this, "设置模式", Toast.LENGTH_SHORT).show();
                if (!nightMode) {
                    item.setTitle("白天模式");
                    nightMode = true;
                } else {
                    item.setTitle("夜间模式");
                    nightMode = false;
                }
                break;
            case R.id.action_seeting:
                Toast.makeText(MainActivity.this, "打开设置界面", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    /**
     * 实现RecyclerViewAdapter的接口，获取被点击的Item的新闻Id，传给详情Activity处理
     * 跳转到详情页Activity
     * @param id
     */
    @Override
    public void handlerOnItemClick(int id,boolean isMultipic) {
        Intent intent = new Intent(this, DailyDetailActivity.class);
        intent.putExtra("dailyId", id);
        intent.putExtra("isMultipic",isMultipic);
        startActivity(intent);
    }

    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if ((nowTime - pressedTime) > 2000){
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            pressedTime = nowTime;
        }else {
            this.finish();
            System.exit(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void dealProgressEventMain(Integer progress){
        Log.e("Temy", "MainActivity:progress: "+progress);
        tvDownLoad.setText(progress);
        tvDownLoad.invalidate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void dealLoginSuccessed(BmobUser bmobUser){
        tvUserName.setText(bmobUser.getUsername());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}

