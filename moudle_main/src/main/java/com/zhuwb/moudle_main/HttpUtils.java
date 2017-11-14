package com.zhuwb.moudle_main;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/14 10:29
 */

public class HttpUtils {

    public static void requestNetwork(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

}
