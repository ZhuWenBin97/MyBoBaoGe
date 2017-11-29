package com.zhuwb.moudle_main.HttpUtils;

import android.support.v4.app.FragmentActivity;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/14 10:29
 */

public class OKHttp {

    public static void requestNetwork(String url, Callback callback, FragmentActivity activity) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).tag(activity).build();
        client.newCall(request).enqueue(callback);
        OkHttpUtil.initClient(client);
    }


}
