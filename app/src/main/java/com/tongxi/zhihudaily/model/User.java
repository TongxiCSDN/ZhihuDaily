package com.tongxi.zhihudaily.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by qf on 2016/11/15.
 */
public class User extends BmobObject implements Serializable {

    private String name;
    private String passWord;
    private BmobFile picture;
    private String phoneNum;

    public User() {
    }

    public User(String tableName, String name, String passWord, BmobFile picture, String phoneNum) {
        super(tableName);
        this.name = name;
        this.passWord = passWord;
        this.picture = picture;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}






















































































