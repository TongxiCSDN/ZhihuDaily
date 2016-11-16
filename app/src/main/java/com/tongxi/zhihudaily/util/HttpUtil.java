package com.tongxi.zhihudaily.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qf on 2016/10/19.
 */
public class HttpUtil {

    private static String startImageUrl = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
    private static String newDailyUrl = "http://news-at.zhihu.com/api/4/news/latest";
    private static String dailyDetailUrl = "http://news-at.zhihu.com/api/4/news/";
    private static String oldDailyUrl = "http://news.at.zhihu.com/api/4/news/before/";
    private static String themeListUrl = "http://news-at.zhihu.com/api/4/themes";


    /**
     * 启动界面图像获取
     *
     * @return
     */
    public static String getStartPicture(Context context,boolean isRefresh) {
        String json = getStringByOkhttp(startImageUrl,context,isRefresh);
        return json;
    }

    /**
     * 最新日报获取
     * @return
     */
    public static String getNewDaily(Context context,boolean isRefresh) {
        String json = getStringByOkhttp(newDailyUrl,context,isRefresh);
        return json;
    }

    /**
     * 日报详情获取
     * @param id 最新消息 中获得的 id
     * @return
     */
    public static String getDailyDetail(int id,Context context,boolean isRefresh) {
        String json = getStringByOkhttp(dailyDetailUrl+id,context,isRefresh);
        Log.e("Temy", "getDailyDetail : "+json );
        return json;
    }

    /**
     * 过往日报获取
     *
     * @param date 日期 --- 20130520 以后
     * @return
     */
    public static String getOldDaily(String date,Context context,boolean isRefresh) {
        String json = getStringByOkhttp(oldDailyUrl + date,context,isRefresh);
        return json;
    }

    /**
     * 额外消息获取（点赞数量和评论）
     * @param dailyId 新闻的ID
     * @return
     */
    public static String getExtraMessage(int dailyId,Context context,boolean isRefresh) {
        String json = getStringByOkhttp("http://news-at.zhihu.com/api/4/story-extra/" + dailyId,context,isRefresh);
        return json;
    }

    /**
     * 长评获取（可能为0）
     *
     * @param dailyId 最新消息 中获得的 id
     * @return
     */
    public static String getLongComment(int dailyId,Context context,boolean isRefresh) {
        String json = getStringByOkhttp("http://news-at.zhihu.com/api/4/story/" + dailyId + "/long-comments", context, isRefresh);
        return json;
    }

    /**
     * 短评获取
     *
     * @param dailyId 最新消息 中获得的 id
     * @return
     */
    public static String getShortComment(int dailyId,Context context,boolean isRefresh) {
        String json = getStringByOkhttp("http://news-at.zhihu.com/api/4/story/" + dailyId + "/short-comments", context, isRefresh);
        return json;
    }

    /**
     * 主题列表获取
     * @return
     */
    public static String getThemeList(Context context,boolean isRefresh) {
        String json = getStringByOkhttp(themeListUrl,context,isRefresh);
        return json;
    }

    /**
     * 主题内容获取
     * @return
     */
    public static String getThemeContent(int id,Context context,boolean isRefresh) {
        String json = getStringByOkhttp("http://news-at.zhihu.com/api/4/theme/"+id,context,isRefresh);
        return json;
    }

    /**
     * 新闻推荐者信息获取
     * @param id 新闻ID
     * @return
     */
    public static String getDailyPresenter(int id,Context context,boolean isRefresh) {
        String json = getStringByOkhttp("http://news-at.zhihu.com/api/4/story/" + id + "/recommenders",context,isRefresh);
        return json;
    }

    public static Bitmap downLoadImage(String url){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                inputStream = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    private static String getStringByOkhttp(String url, Context context,boolean isRefresh) {
        String json = "";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CacheInterceptor())
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000,TimeUnit.MILLISECONDS)
                .cache(new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024))   //配置缓存路径及缓存空间
                .build();

        Request request = new Request.Builder()
                .url(url)
                .tag("Tag")
                .build();

        try {
            Log.e("Temy", "getStringByOkhttp: 看看有没有联网："+NetworkUtil.isNetWorkAvailable(context) );
            if (!isRefresh || !NetworkUtil.isNetWorkAvailable(context)){    //选择不刷新或者没有网络，从缓存中拿取数据
                Response response = okHttpClient.newCall(request).execute();
                Log.e("Temy", "HttpUtil--getStringByOkhttp: response.isSuccessful() = "+response.isSuccessful());
                if (response.isSuccessful()) {
                    json = response.body().string();
                }
            }else {              //选择刷新并且在有网络的情况下，联网更新数据
                request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    json = response.body().string();
                    Log.e("Temy", "getStringByOkhttp: 联网拿数据：json = "+json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static class CacheInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "max-age=" + 3600*24*7)       //缓存7天
                    .build();
            return response1;
        }
    }

}





























































