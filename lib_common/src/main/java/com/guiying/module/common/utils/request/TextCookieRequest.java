package com.guiying.module.common.utils.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.guiying.module.common.config.Config;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 */

public class TextCookieRequest extends BaseRequest<String> {
    private CallBackListener<String> callBack;
    //需要传入的参数
    private Map<String, String> httpParams;

    public TextCookieRequest(int method, String url, CallBackListener listener) {
        super(method, url, listener.getErrorListener());
        this.callBack = listener;
        httpParams = new HashMap<>();
        //加入cookie
        this.addHeader("Cookie", Config.cookie);
    }

    public TextCookieRequest(String url, CallBackListener listenerr) {
        this(Request.Method.POST, url, listenerr);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        Map<String, String> responseHeaders = networkResponse.headers;
        //        读取cookie
        String rawCookies = responseHeaders.get("Set-Cookie");
        Config.cookie = rawCookies != null ? rawCookies : Config.cookie;
        String jsonString;
        try {
            jsonString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            jsonString = new String(networkResponse.data);
        }
        return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return httpParams;
    }

    public void addHeader(String key, String value) {
        httpParams.put(key, value);

    }

    @Override
    protected void deliverResponse(String s) {
        callBack.getListener().onResponse(s);
    }
}
