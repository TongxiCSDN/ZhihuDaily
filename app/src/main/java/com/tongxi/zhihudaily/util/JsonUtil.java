package com.tongxi.zhihudaily.util;

import com.google.gson.Gson;
import com.tongxi.zhihudaily.model.DailyDetail;
import com.tongxi.zhihudaily.model.ExtraMessage;
import com.tongxi.zhihudaily.model.LongComment;
import com.tongxi.zhihudaily.model.NewDailyBean;
import com.tongxi.zhihudaily.model.OldDailyBean;
import com.tongxi.zhihudaily.model.ShortComment;
import com.tongxi.zhihudaily.model.StartImageBean;
import com.tongxi.zhihudaily.model.ThemeContent;
import com.tongxi.zhihudaily.model.ThemeListBean;

/**
 * Created by qf on 2016/10/22.
 */
public class JsonUtil {

    /**
     * 解析新闻详情页
     * @param json
     * @return
     */
    public static DailyDetail parseDailyDetail(String json){
        DailyDetail dailyDetail = null;
        if (json != null){
            dailyDetail = new Gson().fromJson(json,DailyDetail.class);
        }
        return dailyDetail;
    }

    /**
     * 解析额外消息
     * @param json
     * @return
     */
    public static ExtraMessage parseExtraMessage(String json){
        ExtraMessage extraMessage = null;
        if (json != null){
            extraMessage = new Gson().fromJson(json,ExtraMessage.class);
        }
        return extraMessage;
    }

    /**
     * 解析长评（数据可能为空）
     * @param json
     * @return
     */
    public static LongComment parseLongcomment(String json){
        LongComment longComment = null;
        if (json != null){
            longComment = new Gson().fromJson(json,LongComment.class);
        }
        return longComment;
    }

    /**
     * 解析最新消息
     * @param json
     * @return
     */
    public static NewDailyBean parseNewDailyBean(String json){
        NewDailyBean newDailyBean = null;
        if (json != null){
            newDailyBean = new Gson().fromJson(json,NewDailyBean.class);
        }
        return newDailyBean;
    }

    /**
     * 解析短评
     * @param json
     * @return
     */
    public static ShortComment parseShortComment(String json){
        ShortComment shortComment = null;
        if (json != null){
            shortComment = new Gson().fromJson(json,ShortComment.class);
        }
        return shortComment;
    }

    /**
     * 解析启动页的图片
     * @param json
     * @return
     */
    public static StartImageBean parseStartImage(String json){
        StartImageBean startImageBean = null;
        if (json != null){
            startImageBean = new Gson().fromJson(json,StartImageBean.class);
        }
        return startImageBean;
    }

    /**
     * 解析主题内容
     * @param json
     * @return
     */
    public static ThemeContent parseThemeContent(String json){
        ThemeContent themeContent = null;
        if (json != null){
            themeContent = new Gson().fromJson(json,ThemeContent.class);
        }
        return themeContent;
    }

    /**
     * 解析主题列表
     * @param json
     * @return
     */
    public static ThemeListBean parseThemeList(String json){
        ThemeListBean themeListBean = null;
        if (json != null){
            themeListBean = new Gson().fromJson(json,ThemeListBean.class);
        }
        return themeListBean;
    }

    /**
     * 解析往期新闻
     * @param json
     * @return
     */
    public static OldDailyBean parseOldDailyBean(String json){
        OldDailyBean oldDailyBean = null;
        if(json != null){
            oldDailyBean = new Gson().fromJson(json,OldDailyBean.class);
        }
        return oldDailyBean;
    }
}



































































