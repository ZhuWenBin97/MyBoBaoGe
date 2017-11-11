package com.guiying.module.common.utils.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.guiying.module.common.utils.MyTime;


import java.io.UnsupportedEncodingException;

/**
 * Created by zhang chen yang on 2017/9/20 0020.
 */

public class TextRequest extends BaseRequest<String> {
    private CallBackListener<String> callBack;
    public TextRequest(int method,String url,CallBackListener listener) {
        super(method,url,listener.getErrorListener());
        this.callBack = listener;

    }
    public TextRequest(String url,CallBackListener listener){
        this(Request.Method.POST,url,listener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        String jsonString;
        try {

            jsonString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));

        } catch (UnsupportedEncodingException e) {
            jsonString = new String(networkResponse.data);
        }
        return  Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(String s) {
        callBack.getListener().onResponse(s);
    }




}
