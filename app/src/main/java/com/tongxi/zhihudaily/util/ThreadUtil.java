package com.tongxi.zhihudaily.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qf on 2016/10/22.
 */
public class ThreadUtil {

    private static ExecutorService executorService;

    public static void execute(Runnable task){
        if (executorService == null){
            executorService = Executors.newFixedThreadPool(10);
        }
        executorService.submit(task);
    }

}
