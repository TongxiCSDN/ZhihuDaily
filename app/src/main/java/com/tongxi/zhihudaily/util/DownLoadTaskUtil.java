package com.tongxi.zhihudaily.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qf on 2016/11/10.
 */
public class DownLoadTaskUtil extends AsyncTask<String,Integer,Bitmap> {

    private ImageCallBack callBack;

    public void setCallBack(ImageCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        if (callBack != null){
            callBack.onPreExecute();
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(params[0])
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()){
                inputStream = response.body().byteStream();
               // bitmap = BitmapFactory.decodeStream(inputStream);    为了显示进度，使用字节数组输出流
                baos = new ByteArrayOutputStream();
                int length = -1;
                int progress = 0;    //进度
                int totleLength = (int)response.body().contentLength(); //获取下载内容的总长度
                byte[] bytes = new byte[1024];
                while ((length = inputStream.read(bytes)) != -1){
                    progress += length;
                    if (totleLength == 0){      //如果总长度为0，返回进度值为-1
                        publishProgress(-1);
                    }else {                     //返回实时更新的进度值
                        publishProgress((int) ((float) progress / totleLength * 100));
                    }
                    if (isCancelled()){     //任务呗取消，返回null
                        return bitmap;
                    }
                    baos.write(bytes,0,length);     //写入字节数组
                    bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),0,baos.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (callBack != null){
            callBack.onProgressUpdate(values);
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (callBack != null){
            callBack.onPostExecute(bitmap);
        }
    }

    public interface ImageCallBack{
        void onPreExecute();
        void onProgressUpdate(Integer... values);
        void onPostExecute(Bitmap bitmap);
    }
}
