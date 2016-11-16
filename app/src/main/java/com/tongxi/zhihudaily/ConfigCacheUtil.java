package com.tongxi.zhihudaily;

import android.content.Context;

import java.io.File;

/**
 * 待完成，没有用到，贴一个链接：http://www.jb51.net/article/79342.htm
 * Created by qf on 2016/10/25.
 */
public class ConfigCacheUtil {
    /**1秒超时时间*/
    public static final int CONFIF_CACHE_SHORT_TIMEOUT=1000 * 60 * 5; // 5 分钟
    /**5分钟超时时间*/
    public static final int CONFIG_CACHE_MEDIUM_TIMEOUT=1000 * 3600 * 2; // 2小时
    /** 中长缓存时间 */
    public static final int CONFIG_CACHE_ML_TIMEOUT=1000 * 60 * 60 * 24 * 1; // 1天
    /** 最大缓存时间 */
    public static final int CONFIG_CACHE_MAX_TIMEOUT=1000 * 60 * 60 * 24 * 7; // 7天

    /**CONFIG_CACHE_MODEL_LONG : 长时间(7天)缓存模式
     * CONFIG_CACHE_MODEL_ML : 中长时间(12小时)缓存模式
     * CONFIG_CACHE_MODEL_MEDIUM: 中等时间(2小时)缓存模式
     * CONFIG_CACHE_MODEL_SHORT : 短时间(5分钟)缓存模式  */
    public enum ConfigCacheModel{
        CONFIG_CACHE_MODEL_SHORT,
        CONFIG_CACHE_MODEL_MEDIUM,
        CONFIG_CACHE_MODEL_ML,
        CONFIG_CACHE_MODEL_LONG;
    }

    public static String getUrlCache(String url, ConfigCacheModel model, Context context){
        if (url == null){
            return null;
        }
        String result = null;
        String path = context.getExternalCacheDir().getAbsolutePath()+ File.separator+url+"txt";
        File file = new File(path);
        if(file.exists() && file.isFile()){

        }
        return result;
    }
}
