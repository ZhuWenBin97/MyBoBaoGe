package com.zhuwb.moudle_main.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ======================================
 * 创建人   ： ZhuWB
 * 创建时间 :2017/11/27 08:45
 * ======================================
 */

public class Util {

    //==================      获取商圈       ========================================
    public static String whitchArea(String id) {
        switch (Integer.parseInt(id)) {
            case 1:
                return "商圈";
            case 2:
                return "洪合";
            case 3:
                return "濮院";
            case 4:
                return "海宁";
            default:
                return "横扇";
        }

    }

    //==================      截取时间       ========================================
    public static String getData(String data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            data = simpleDateFormat.format(simpleDateFormat.parse(data)).substring(11);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }


}
