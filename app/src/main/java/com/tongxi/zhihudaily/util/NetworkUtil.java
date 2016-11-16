package com.tongxi.zhihudaily.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by qf on 2016/10/25.
 */
public class NetworkUtil {

    public static final boolean isNetWorkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.e("Temy", "isNetWorkAvailable: "+cm );
        if (cm != null) {
                NetworkInfo[] info = cm.getAllNetworkInfo();
            Log.e("Temy", "isNetWorkAvailable: "+info );
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            Log.e("Temy", "isNetWorkAvailable: "+"@@@@@@@@@@@" );
                            return true;
                        }
                    }
                }
            }
        return false;
    }

}
