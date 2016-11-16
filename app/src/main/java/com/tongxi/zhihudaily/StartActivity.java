package com.tongxi.zhihudaily;

/**
 * Created by qf on 2016/10/22.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongxi.zhihudaily.activity.MainActivity;
import com.tongxi.zhihudaily.model.StartImageBean;
import com.tongxi.zhihudaily.util.FileUtil;
import com.tongxi.zhihudaily.util.HttpUtil;
import com.tongxi.zhihudaily.util.JsonUtil;
import com.tongxi.zhihudaily.util.SharedPreferencesUtil;
import com.tongxi.zhihudaily.util.ThreadUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.ivStartPicture)
    ImageView ivStartPicture;
    @BindView(R.id.tvCopyright)
    TextView tvCopyright;
    @BindView(R.id.ivLog)
    ImageView ivLog;
    @BindView(R.id.tvStart)
    TextView tvStart;
    private String spImgUrl;
    private String spCopyright;
    private File file;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MESSAGE_START_IMAGE_NONE:
                    ivStartPicture.setImageResource(R.mipmap.login_bg);//使用默认背景
                    break;
                case Constant.MESSAGE_START_IMAGE_LOCAL:
                    showLocalImage();                         //使用本地图片
                    break;
                case Constant.MESSAGE_START_IMAGE_INTERNET:
                    Bitmap bitmap = (Bitmap) msg.obj;            //使用网络下载图片
                    ivStartPicture.setImageBitmap(bitmap);
                    tvCopyright.setText(spCopyright);
                    tvCopyright.setVisibility(View.VISIBLE);
                    break;
            }
            //显示图片三秒后跳转,并下载首页的数据：最新新闻的Json --- 发送给MainActivity
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();    //结束当前Activity，确保欢迎页只在打开程序时显示一次
                }
            }, 2000);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        spImgUrl = (String) SharedPreferencesUtil.getParam(this,"WelcomeImgUrl","imgUrl");
        spCopyright = (String) SharedPreferencesUtil.getParam(this,"copyright","copyright");

        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), Constant.START_IMAGE_NAME);

        checkImageData();
    }

    /**
     * 开启子线程，检查数据更新，确定显示的图片
     */
    private void checkImageData() {
        ThreadUtil.execute(new Runnable() {
                               @Override
                               public void run() {
                                   //获取启动页图片Json
                                   String imageJson = HttpUtil.getStartPicture(StartActivity.this,true);
                                   StartImageBean startImageBean = JsonUtil.parseStartImage(imageJson);
                                   String imgUrl = null;
                                   String copyrigth = null;
                                   if (startImageBean != null) {
                                       imgUrl = startImageBean.getImg();
                                       copyrigth = startImageBean.getText();
                                   }
                                   Log.e("Temy", "StartActivity 小线程spImgUrl: " + spImgUrl);
                                   Log.e("Temy", "StartActivity 小线程imgUrl: " + imgUrl);

                                   Message msg = handler.obtainMessage();
                                   if (imgUrl == null && spImgUrl == null) {    //没有从网络上加载到图片，也没有本地保存图片，使用默认背景
                                       ivStartPicture.setImageResource(R.mipmap.splash);
                                       msg.what = Constant.MESSAGE_START_IMAGE_NONE;
                                   } else if (imgUrl != null && !imgUrl.equals(spImgUrl)) {        //下载图片，保存到本地并显示在启动页上,并将图片信息更新至sharedPreferences

                                       Bitmap bitmap = HttpUtil.downLoadImage(imgUrl);
                                       FileUtil.saveFileToSDCardPrivateFilesDir(StartActivity.this, bitmap,
                                               Environment.DIRECTORY_PICTURES,
                                               Constant.START_IMAGE_NAME);
                                       msg.what = Constant.MESSAGE_START_IMAGE_INTERNET;
                                       msg.obj = bitmap;
                                       SharedPreferencesUtil.setParam(StartActivity.this,"WelcomeImgUrl",imgUrl);
                                       SharedPreferencesUtil.setParam(StartActivity.this,"copyrigth",copyrigth);


                                   } else {
                                       msg.what = Constant.MESSAGE_START_IMAGE_LOCAL;

                                   }
                                   Log.e("Temy", "run:msg.what =  " + msg.what);
                                   handler.sendMessage(msg);
                               }
                           }
        );
    }


    private void showLocalImage() {
        String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + File.separator
                + Constant.START_IMAGE_NAME;
        ivStartPicture.setImageBitmap(FileUtil.getSmallBitmap(filePath));
        tvCopyright.setText(spCopyright);
        tvCopyright.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public interface SendNewDailyListener{
        void handleNewDailyJson(String json);
    }
}

