package com.tongxi.zhihudaily.util;

import android.database.sqlite.SQLiteDatabase;

import com.tongxi.zhihudaily.activity.MyApp;
import com.tongxi.zhihudaily.dao.DaoMaster;
import com.tongxi.zhihudaily.dao.DaoSession;

/**
 * Created by qf on 2016/11/8.
 */
public class GreenDaoUtil {
    private static GreenDaoUtil greenDaoUtil;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private GreenDaoUtil(){}

    public static GreenDaoUtil getSingleTon(){
        if (greenDaoUtil == null){
            greenDaoUtil = new GreenDaoUtil();
        }
        return greenDaoUtil;
    }

    private void initGreenDao(){
        helper = new DaoMaster.DevOpenHelper(MyApp.getApplication(),"collection-db",null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        if (daoMaster == null){
            initGreenDao();
        }
        return daoSession;
    }

    public SQLiteDatabase getDb(){
        if (db == null){
            initGreenDao();
        }
        return db;
    }
}





































































