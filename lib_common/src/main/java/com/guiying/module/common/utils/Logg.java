package com.guiying.module.common.utils;

import android.util.Log;

/**
 * Created by zhang chen yang on 2017/9/20 0020.
 * 日志记录工具类
 */

public class Logg {
    /**
     *是否开启日志
     */
    public static boolean isDebug = true;

    public static void i(String TAG,String msg){
        if(isDebug){
            Log.i(TAG, "----------info: "+msg+"----------");
        }
    }
    public static void d(String TAG,String msg){
        if(isDebug){
            Log.d(TAG, "----------debug: "+msg+"----------");
        }
    }
    public static void e(String TAG,String msg){
        if(isDebug){
            Log.e(TAG, "----------error: "+msg+"----------");
        }
    }
}
