package com.zhuwb.moudle_main.HttpUtils;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * ======================================
 * 创建人   ： ZhuWB
 * 创建时间 :2017/11/27 16:13
 * ======================================
 */

public class OkHttpUtil {
    private volatile static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;

    public OkHttpUtil(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

    }

    public static OkHttpUtil getInstance() {
        return initClient(null);
    }

    public static OkHttpUtil initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

}
