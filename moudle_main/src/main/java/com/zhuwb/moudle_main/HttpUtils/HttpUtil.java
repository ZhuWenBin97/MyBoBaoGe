package com.zhuwb.moudle_main.HttpUtils;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;

import com.guiying.module.common.config.Config;
import com.guiying.module.common.utils.Util;

import java.util.HashMap;


/**
 * @author ZhuWB
 *         创建时间 :2017/11/21 09:23
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";
    static HashMap<String, String> map = new HashMap<String, String>();

    /**
     * 获取列表信息的Url
     *
     * @param mold    街道
     * @param curPage 页码
     * @return
     */
    public static String getListUrl(Integer mold, Integer curPage, Integer type) {
        map.clear();
        map.put("action", "message");
        map.put("func", "getMessageList");
        map.put("categoryId", "");
        map.put("pageSize", "10");
        map.put("state", "1,4");
        map.put("mold", mold.toString());
        map.put("keyValue", "");
        map.put("userid", "0");
        map.put("curPage", curPage.toString());
        map.put("type", type.toString());
        map.put("lastId", "0");
        map.put("zoneIDs", "1");
        String url = Util.getUrl(map);
        Log.i(TAG, "getListUrl: " + url);
        return url;

    }

    /**
     * 获取轮播图的Url
     *
     * @param mold
     * @return
     */
    public static String getBannerUrl(Integer mold) {
        map.clear();
        map.put("action", "adv");
        map.put("func", "getAdvList");
        map.put("userId", "");
        map.put("imei", Config.androidIMEI);
        map.put("mold", mold.toString());
        String url = Util.getUrl(map);
        return url;
    }

    public static String getType() {
        map.clear();
        map.put("action", "type");
        map.put("func", "getTypeList");
        map.put("search","");
        String url = Util.getUrl(map);
        return url;
    }


}
