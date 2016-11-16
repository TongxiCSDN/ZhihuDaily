package com.tongxi.zhihudaily.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qf on 2016/10/29.
 */
public class DateUtil {

    private static final String TAG = "Temy";

    public static String getOtherDateString(String date, int num, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            try {
                calendar.setTime(format.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "DateUtil :calendar.getTime : "+calendar.getTime());
        }
        calendar.roll(Calendar.DAY_OF_MONTH, num);
        Log.e(TAG, "DateUtil : 上一天日期：" + format.format(calendar.getTime()));
        return format.format(calendar.getTime());
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return date;
    }
}
