package com.guiying.module.common.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.guiying.module.common.base.BaseApplication;
import com.guiying.module.common.utils.request.BaseRequest;


import java.util.HashSet;
import java.util.Set;

/**
 * {@link NetUtil}网络请求类,封装的Volley。实现Get和Post请求。
 * Created by zhang chen yang on 2017/9/20 0020.
 */

public class NetUtil {
    //存放所有的请求队列
    private RequestQueue requestQueue;
    private Set<String> requestTag;
    //单例模式
    public static NetUtil Instance = InstanceHelper.Instance;

    static class InstanceHelper{
        static NetUtil Instance = new NetUtil();
    }
    private NetUtil(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        requestTag = new HashSet<String>();
        Logg.i(TAG,"NetUti 实例化");
    }
    private NetUtil() {
        this(BaseApplication.getIns());
    }
    private void sendHttp(Request request){
        request.setTag(request.toString());
        requestQueue.add(request);
        requestTag.add(request.getTag().toString());
    }

    /**
     *通过http方式获取数据，并解析数据
     * @param request Volley请求对象
     */
    private static final String TAG = "NetUtil";
    public void sendByHttp(BaseRequest request){
        Logg.i(TAG, "sendByHttp: "+request.getUrl());
        this.sendHttp(request);
        Logg.i("NetWork", "sendByHttp: "+request.getUrl());
    }



    public void cancelAllRequest(){

        for(String tag:requestTag){
            requestQueue.cancelAll(tag);
        }
    }
    public void cancelRequest(String tag){
        requestQueue.cancelAll(tag);
    }

}
