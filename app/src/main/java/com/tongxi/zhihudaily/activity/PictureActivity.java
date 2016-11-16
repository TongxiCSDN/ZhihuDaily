package com.tongxi.zhihudaily.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.util.DownLoadImageUtil;
import com.tongxi.zhihudaily.util.DownLoadTaskUtil;
import com.tongxi.zhihudaily.util.FileUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureActivity extends AppCompatActivity implements DownLoadTaskUtil.ImageCallBack {

    @BindView(R.id.ivShowPic)
    ImageView ivShowPic;
    @BindView(R.id.toolBarPicture)
    Toolbar toolBarPicture;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        //获取图片地址，将图片显示在控件上
        imgUrl = getIntent().getStringExtra("imgUrl");
        DownLoadImageUtil.load(this, imgUrl, R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, ivShowPic);

        //初始化ToolBar
        setSupportActionBar(toolBarPicture);
        toolBarPicture.setTitle("");
        toolBarPicture.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picture_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.savePic:
                DownLoadTaskUtil task = new DownLoadTaskUtil();
                task.setCallBack(this);
                task.execute(imgUrl);
                break;
        }
        return true;
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(PictureActivity.this, "开始下载图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(Integer... values) {
        Integer progress = values[0];
        if (progress != -1) {
            Log.e("Temy", "PictureActivity: " + progress);
            EventBus.getDefault().postSticky(progress);
        }
    }

    @Override
    public void onPostExecute(Bitmap bitmap) {
        String[] split = imgUrl.split("/");
        String imgName = split[split.length - 1];
        imgName = imgName.replace("-", "");
        boolean isSaved = FileUtil.SavePicture(bitmap, imgName + ".png");
        if (isSaved) {
            Toast.makeText(PictureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PictureActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
