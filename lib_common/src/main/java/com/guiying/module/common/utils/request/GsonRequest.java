package com.guiying.module.common.utils.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhang chen yang on 2017/9/20 0020.
 * 定制化的Volley请求，请求JSON数据直接返回该数据的对象。
 */

public class GsonRequest<T> extends BaseRequest<T> {

    private CallBackListener<T> callBack;
    private Class<T> parserClass;
    private Gson mGson;

    /**
     * 初始化一个Volley的Request请求
     *
     * @param method   Http请求方法
     * @param url      Http请求地址
     * @param clazz    与服务器返回的json数据所对应的类
     * @param listener 请求执行结束的回调接口
     */
    public GsonRequest(int method, String url, Class<T> clazz, CallBackListener listener) {
        super(method, url, listener.getErrorListener());
        this.callBack = listener;
        this.parserClass = clazz;
        this.mGson = new Gson();
//        this.httpParams = new HashMap<>();
    }

    /**
     * 默认使用Get方式
     * <p>
     * 初始化一个Volley的Request请求
     *
     * @param url      Http请求地址
     * @param clazz    与服务器返回的json数据所对应的类
     * @param listener 请求执行结束的回调接口
     */
    public GsonRequest(String url, Class<T> clazz, CallBackListener listener) {
        this(Request.Method.POST, url, clazz, listener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(jsonString, parserClass),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        callBack.getListener().onResponse(t);
    }


}
