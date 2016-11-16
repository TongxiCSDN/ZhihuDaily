package com.tongxi.zhihudaily.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongxi.zhihudaily.Constant;
import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.adapter.CommentAdapter;
import com.tongxi.zhihudaily.model.LongComment;
import com.tongxi.zhihudaily.model.ShortComment;
import com.tongxi.zhihudaily.util.HttpUtil;
import com.tongxi.zhihudaily.util.JsonUtil;
import com.tongxi.zhihudaily.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity {


    @BindView(R.id.toolBarComment)
    Toolbar toolBarComment;
    @BindView(R.id.lvComment)
    ListView lvComment;
    @BindView(R.id.tvEmptyComment)
    TextView tvEmptyComment;
    private int dailyId;
    private List<LongComment.CommentsBean> longComments = new ArrayList<>();
    private List<ShortComment.CommentsBean> shortComments;
    private boolean isExpanded;


    private Handler handler = new Handler() {
        private TextView tvLongCount;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MESSAGE_LONG_COMMENT:
                    LongComment longComment = (LongComment) msg.obj;
                    List<LongComment.CommentsBean> lComments = longComment.getComments();
                    tvLongCount = ((TextView) headView.findViewById(R.id.tvLongCount));
                    tvLongCount.setText(lComments.size()+"条长评");
                    initListView();
                    break;
                case Constant.MESSAGE_SHORT_COMMENT:
                    ShortComment shortComment = (ShortComment) msg.obj;
                    List<ShortComment.CommentsBean> sComments = shortComment.getComments();
                    shortComments = new ArrayList<>();
                    shortComments.addAll(sComments);
                    initListView();
                    break;
            }
        }
    };
    private int commentCount;
    private View headView;
    private CommentAdapter commentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        dailyId = getIntent().getIntExtra("Id", -1);
        commentCount = getIntent().getIntExtra("commentCount", -1);


        //下载长评
        downLoadLongComment();

        //下载短评
        downLoadShortComment();

        //初始化ListView
        initListView();

        //初始化ToolBar
        if (commentCount != -1) {
            toolBarComment.setTitle(commentCount + "条评论");
        }
        setSupportActionBar(toolBarComment);
        toolBarComment.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);

        headView = getLayoutInflater().inflate(R.layout.item_long_comment_count, lvComment, false);
        lvComment.addHeaderView(headView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.writeComment:
                Toast.makeText(CommentActivity.this, "写评论，待实现...", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /**
     * 初始化长评和短评的ListView
     */
    private void initListView() {
        commentAdapter = new CommentAdapter(this,longComments,shortComments);
        lvComment.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    /**
     * 下载长评
     */
    private void downLoadLongComment() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getLongComment(dailyId, CommentActivity.this, true);
                LongComment longComment = JsonUtil.parseLongcomment(json);
                if (longComment != null) {
                    Message msg = handler.obtainMessage(Constant.MESSAGE_LONG_COMMENT, longComment);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    /**
     * 下载短评
     */
    private void downLoadShortComment() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getShortComment(dailyId, CommentActivity.this, true);
                ShortComment shortComment = JsonUtil.parseShortComment(json);
                if (shortComment != null) {
                    Message msg = handler.obtainMessage(Constant.MESSAGE_SHORT_COMMENT, shortComment);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
