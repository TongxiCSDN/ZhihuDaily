package com.tongxi.zhihudaily;

/**
 * 存放静态数据
 * Created by qf on 2016/10/22.
 */
public class Constant {

    public static final String PREFERENCE_NAME = "StratInfo";     //启动界面
    public static final int MESSAGE_START_IMAGE_NONE = 10;        //Message：启动页使用默认背景
    public static final int MESSAGE_START_IMAGE_LOCAL = 11;        //Message：启动页使用本地保存图片
    public static final int MESSAGE_START_IMAGE_INTERNET = 12;        //Message：启动页使用网络下载图片
    public static final String START_IMAGE_NAME = "welcome.png";   //启动页图片名

    public static final int MESSAGE_MAIN_THEME_LIST = 13;         //MainActivity中发送解析后的主题列表Bean
    public static final int MESSAGE_MAIN_NEW_DAILY = 14;         //MainActivity中发送解析后的最新新闻
    public static final int MESSAGE_MAIN_OLD_DAILY = 15;         //MainActivity中发送解析后的往期新闻

    public static final int MESSAGE_DETAIL_DAILY = 16;   //DailyDetail中发送解析后的新闻详情
    public static final int MESSAGE_EXTRA_MESSAGE = 17;   //DailyDetail中发送解析后的额外消息（评论数，点赞数）

    public static final int MESSAGE_DAO_COLLECTION = 18;   //CollectionActivity中发送从本地数据库中读取的“我的收藏”

    public static final int MESSAGE_LONG_COMMENT = 19;    //CommentActivity中发送长评数据
    public static final int MESSAGE_SHORT_COMMENT = 20;    //CommentActivity中发送短评数据

    public static final int MESSAGE_THEME_CONTENT = 21;    //ThemeActivity中发送主题日报的详细内容

    public static final String BMOB_ID = "7ffa031ef7cf3744555866ceb5e33643";   //后端云id



}
