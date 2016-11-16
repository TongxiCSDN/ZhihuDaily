package com.tongxi.zhihudaily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongxi.zhihudaily.Constant;
import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.dao.MyCollectionBeanDao;
import com.tongxi.zhihudaily.model.DailyDetail;
import com.tongxi.zhihudaily.model.ExtraMessage;
import com.tongxi.zhihudaily.model.MyCollectionBean;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;
import com.tongxi.zhihudaily.util.GreenDaoUtil;
import com.tongxi.zhihudaily.util.HttpUtil;
import com.tongxi.zhihudaily.util.JsonUtil;
import com.tongxi.zhihudaily.util.ThreadUtil;
import com.tongxi.zhihudaily.util.WebUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DailyDetailActivity extends AppCompatActivity {

    private static final String TAG = "Temy";

    @BindView(R.id.webViewDetail)
    WebView webViewDetail;
    @BindView(R.id.ivDetailImage)
    ImageView ivDetailImage;
    @BindView(R.id.toolBarDetail)
    Toolbar toolBarDetail;
    @BindView(R.id.ctl)
    CollapsingToolbarLayout ctl;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    private int dailyId;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MESSAGE_DETAIL_DAILY:
                    dailyDetail = (DailyDetail) msg.obj;
                    List<String> css = dailyDetail.getCss();
                    String body = dailyDetail.getBody();
                    String data = WebUtils.BuildHtmlWithCss(body, css, false);
                    initWebView(data);
                    //设置标题和图片
                    dailyTitle = dailyDetail.getTitle();
                    share_url = dailyDetail.getShare_url();
                    ctl.setTitle(dailyTitle);
                    DownLoadImageUtil.load(DailyDetailActivity.this, dailyDetail.getImage(), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, ivDetailImage);
                    break;
                case Constant.MESSAGE_EXTRA_MESSAGE:
                    extraMessage = (ExtraMessage) msg.obj;
                    if (extraMessage != null) {
                        comments = extraMessage.getComments();
                        popularity = extraMessage.getPopularity();
                        Menu menu = toolBarDetail.getMenu();
                        TextView tvComment = findMenuView(menu, R.id.comment, R.id.tvComment);
                        tvComment.setText(comments + "");
                        TextView tvLike = findMenuView(menu, R.id.like, R.id.tvLike);
                        tvLike.setText(popularity + "");
                    }
                    break;
            }
        }
    };
    private DailyDetail dailyDetail;
    private ExtraMessage extraMessage;
    private MyCollectionBeanDao dao;
    private boolean collected;
    private MyCollectionBean myCollectionBean;
    private boolean isMultipic;
    private TextView tvComment;
    private TextView tvLike;
    private int comments;
    private int popularity;
    private String dailyTitle;
    private String share_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_detail);
        ButterKnife.bind(this);

        dailyId = getIntent().getIntExtra("dailyId", -1);
        isMultipic = getIntent().getBooleanExtra("isMultipic", false);

        //初始化分享ShareSDK
        ShareSDK.initSDK(this);

        //初始化数据库
        dao = GreenDaoUtil.getSingleTon().getDaoSession().getMyCollectionBeanDao();

        //设置ToolBar
        setSupportActionBar(toolBarDetail);
        toolBarDetail.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);

        //下载数据
        downLoadDetail();
        //初始化WebView
        initWebView("");
        //查询该文章是否被收藏
        collected = isCollected(dailyId);
        //下载额外消息
        downLoadExtra();


        //测试
        List<MyCollectionBean> text = dao.loadAll();
        for (MyCollectionBean bean : text) {
            Log.e(TAG, "bean: " + bean.toString());
        }

//        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.e(TAG, "onOffsetChanged: "+verticalOffset);
//                if (verticalOffset <-180){
//                    AlphaAnimation animation = new AlphaAnimation(0,1);
//                    animation.setRepeatCount(1);
//                    animation.setDuration(2000);
//                    toolBarDetail.setAnimation(animation);
//                    toolBarDetail.setVisibility(View.GONE);
//                }
//            }
//        });

//        AppBarLayout abl = null;
//        abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//            }
//        });

    }

    private void downLoadExtra() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getExtraMessage(dailyId, DailyDetailActivity.this, true);
                ExtraMessage extraMessage = JsonUtil.parseExtraMessage(json);
                if (extraMessage != null) {
                    Message msg = handler.obtainMessage(Constant.MESSAGE_EXTRA_MESSAGE, extraMessage);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daily_detail_action, menu);
        if (collected) {
            menu.findItem(R.id.collect).setIcon(R.mipmap.collected);
        } else {
            menu.findItem(R.id.collect).setIcon(R.mipmap.collect);
        }

        final MenuItem commentItem = menu.findItem(R.id.comment);
        View commentView = commentItem.getActionView();
        commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(commentItem);
            }
        });

        final MenuItem likeItem = menu.findItem(R.id.like);
        View likeView = likeItem.getActionView();
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(likeItem);
            }
        });

        return true;
    }

    private TextView findMenuView(Menu menu, int itemId, int viewId) {
        MenuItem item = menu.findItem(itemId);
        View view = item.getActionView();
        TextView text = (TextView) view.findViewById(viewId);
        return text;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.share:
                //第三方分享
                showShare();
                break;
            case R.id.collect:
                collected = isCollected(dailyId);
                if (collected) {
                    Toast.makeText(DailyDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.mipmap.collect);
                    if (myCollectionBean != null) {
                        dao.delete(myCollectionBean);
                    }
                } else {
                    if (dailyDetail != null) {
                        Toast.makeText(DailyDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        item.setIcon(R.mipmap.collected);
                        MyCollectionBean insertCollect = new MyCollectionBean(null, dailyId, isMultipic, dailyDetail.getImage(), dailyDetail.getTitle());
                        Log.e(TAG, "DailyDetailActivity: insertCollect = " + insertCollect.toString());
                        dao.insert(insertCollect);
                    } else {
                        Toast.makeText(DailyDetailActivity.this, "数据加载中...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.comment:

                int comments = -1;
                if (extraMessage != null) {
                    comments = extraMessage.getComments();
                }
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("Id", dailyId);
                intent.putExtra("commentCount", comments);
                startActivity(intent);
                break;
            case R.id.like:
                Toast.makeText(DailyDetailActivity.this, "点赞", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /**
     * 判断该新闻是否被添加到收藏
     *
     * @param dailyId
     * @return
     */
    private boolean isCollected(int dailyId) {
        boolean flag = false;
        List<MyCollectionBean> myCollections = dao.loadAll();
        if (myCollections == null || myCollections.size() == 0) {
            return flag;
        } else {
            for (int i = 0; i < myCollections.size(); i++) {
                if (dailyId == myCollections.get(i).getNewsId()) {
                    myCollectionBean = myCollections.get(i);
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }


    /**
     * 下载详情数据
     */
    private void downLoadDetail() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getDailyDetail(dailyId, DailyDetailActivity.this, true);
                DailyDetail dailyDetail = null;
                if (json != null) {
                    dailyDetail = JsonUtil.parseDailyDetail(json);
                }
                if (dailyDetail != null) {
                    Message msg = handler.obtainMessage(Constant.MESSAGE_DETAIL_DAILY, dailyDetail);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    /**
     * 初始化WebView
     *
     * @param data
     */
    private void initWebView(String data) {
        webViewDetail.getSettings().setJavaScriptEnabled(true);    //设置WebView的属性，能够执行JavaScript脚本
        webViewDetail.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
        webViewDetail.setWebViewClient(new HelloWebViewClient());      //设置Web视图
        webViewDetail.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        webViewDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先加载缓存

        webViewDetail.addJavascriptInterface(this, "imagelistner");
    }


    @JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("imgUrl", img);
        startActivity(intent);
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);

            // html加载完成之后，添加监听图片的点击js函数
            webViewDetail.loadUrl("javascript:(function(){"
                    + "  var objs = document.getElementsByTagName(\"img\"); "
                    + "  for(var i=0;i<objs.length;i++){"
                    + "     objs[i].onclick=function(){"
                    + "          window.imagelistner.openImage(this.src);  "
                    + "     }"
                    + "  }"
                    + "})()");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(dailyTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(share_url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(share_url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(dailyTitle);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(share_url);

        // 启动分享GUI
        oks.show(this);
    }
}

