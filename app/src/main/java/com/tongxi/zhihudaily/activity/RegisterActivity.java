package com.tongxi.zhihudaily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tongxi.zhihudaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.cancel)
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

//        Bmob.initialize(this, Constant.BMOB_ID);
    }

    @OnClick(R.id.register)
    public void onRegisterClick(){
        String userName = etUsername.getText().toString();
        String userPassWord = etPassword.getText().toString();
        String userEmail = etEmail.getText().toString();
        if (userName != null && userEmail != null && userPassWord != null){
            BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(userName);
            bmobUser.setPassword(userPassWord);
            bmobUser.setEmail(userEmail);
            bmobUser.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null){
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(RegisterActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @OnClick(R.id.cancel)
    public void onCancelClick(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
