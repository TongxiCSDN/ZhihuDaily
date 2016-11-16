package com.tongxi.zhihudaily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongxi.zhihudaily.Constant;
import com.tongxi.zhihudaily.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolBarLogin)
    Toolbar toolBarLogin;
    @BindView(R.id.ivZhihu)
    ImageView ivZhihu;
    @BindView(R.id.tvUser)
    TextView tvUser;
    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.tvPassWord)
    TextView tvPassWord;
    @BindView(R.id.etPassWord)
    EditText etPassWord;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //初始化后端云
        Bmob.initialize(this, Constant.BMOB_ID);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick(){
        String userName = etUser.getText().toString();
        String userPassWord = etPassWord.getText().toString();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassWord)){
            BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(userName);
            bmobUser.setPassword(userPassWord);
            bmobUser.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null){
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(bmobUser);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else {
                        Log.e("Temy", "done: "+e);
                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @OnClick(R.id.btnRegister)
    public void onRegister(){
        startActivity(new Intent(this,RegisterActivity.class));
    }

}



















































































