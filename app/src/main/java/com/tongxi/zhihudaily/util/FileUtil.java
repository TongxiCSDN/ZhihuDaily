package com.tongxi.zhihudaily.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rimon on 2016/9/22.
 */

public class FileUtil {

    private static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static synchronized boolean saveFileToSDCardPrivateFilesDir(Context context, Bitmap bitmap, String type, String fileName) {
        BufferedOutputStream bos = null;
        if (isSDCardMounted()) {
            File file = context.getExternalFilesDir(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null)
                        bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean loadLocalImage(Context context, String type, String fileName, ImageView imageView) {
        if (isSDCardMounted()) {
            File file = new File(context.getExternalFilesDir(type), fileName);
            if (file.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    imageView.setImageBitmap(BitmapFactory.decodeStream(fis));
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Nullable
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    public static boolean writeBitmap(Context context, Bitmap bitmap, String imgName, Bitmap.CompressFormat format) {
        FileOutputStream fos = null;
        try {
            //定义写出的目标文件
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
                    getAbsolutePath() + File.separator + imgName);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    Toast.makeText(context, "文件创建失败", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                file.delete();
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    Toast.makeText(context, "文件创建失败", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            //将图片写入到文件中
            fos = new FileOutputStream(file, false);
            bitmap.compress(format, 100, fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean SavePicture(Bitmap bitmap,String imgName){
        boolean flag = false;
        FileOutputStream fos = null;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + File.separator+ imgName);
        try {
            if (!file.exists()){
                boolean newFile = file.createNewFile();
                if (!newFile){
                    return flag;
                }
            }else {
                file.delete();
                boolean newFile = file.createNewFile();
                if (!newFile){
                    return flag;
                }
            }
            fos = new FileOutputStream(file, false);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

}