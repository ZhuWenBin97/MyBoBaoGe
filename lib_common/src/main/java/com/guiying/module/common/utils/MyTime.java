package com.guiying.module.common.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sjw
 */
@SuppressLint("SimpleDateFormat")
public class MyTime {


    //得到时间戳
    public static String getTimestamp() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date curDate = new Date(System.currentTimeMillis());
        String currentTime = formatter.format(curDate);
        currentTime = currentTime.substring(0, currentTime.length() - 6) + "00分00秒";
        return getTime(currentTime);

    }

    //字符串转时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }
}
