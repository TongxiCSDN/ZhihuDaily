package com.tongxi.zhihudaily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tongxi.zhihudaily.Constant;
import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.adapter.RVCollectionAdapter;
import com.tongxi.zhihudaily.dao.MyCollectionBeanDao;
import com.tongxi.zhihudaily.model.MyCollectionBean;
import com.tongxi.zhihudaily.util.GreenDaoUtil;
import com.tongxi.zhihudaily.util.ThreadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity implements RVCollectionAdapter.RVCollectionItemClick {

    @BindView(R.id.toolBarCollecte)
    Toolbar toolBarCollecte;
    @BindView(R.id.rvCollecte)
    RecyclerView rvCollecte;

    private List<MyCollectionBean> collectionBeen;
    private MyCollectionBeanDao dao;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.MESSAGE_DAO_COLLECTION:
                    //刷新列表
                    initRecyclerView();
                    //设置ToolBar的Title
                    toolBarCollecte.setTitle(collectionBeen.size()+"条收藏");
                    break;
            }
        }
    };
    private RVCollectionAdapter collectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        dao = GreenDaoUtil.getSingleTon().getDaoSession().getMyCollectionBeanDao();

        //初始化Toolar
        initToolBar();

        //获取数据
        getDataFromDao();

        //初始化RecyclerView
        initRecyclerView();

    }

    /**
     * 开启子线程，从数据库中读取数据
     */
    private void getDataFromDao() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                collectionBeen = dao.loadAll();
                Message msg = handler.obtainMessage(Constant.MESSAGE_DAO_COLLECTION);
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        collectionAdapter = new RVCollectionAdapter(this, collectionBeen);
        collectionAdapter.setItemClick(this);
        rvCollecte.setAdapter(collectionAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rvCollecte.setLayoutManager(layout);
        collectionAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化ToolBar,设置图标、标题和回退事件
     */
    private void initToolBar() {
        setSupportActionBar(toolBarCollecte);
        toolBarCollecte.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        toolBarCollecte.setTitle("我的收藏");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(CollectionActivity.this,MainActivity.class));
                break;
        }
        return true;
    }

    /**
     * Rv的接口回调，跳转到详情界面
     * @param dailyId  点击的条例唯一Id
     */
    @Override
    public void OnRVCollecteItemClick(int dailyId,boolean multipic) {
        Intent intent = new Intent(this,DailyDetailActivity.class);
        intent.putExtra("dailyId",dailyId);
        intent.putExtra("isMultipic",multipic);
        startActivity(intent);
    }
}
