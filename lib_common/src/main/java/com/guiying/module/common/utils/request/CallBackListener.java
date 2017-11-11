package com.guiying.module.common.utils.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by zhang chen yang on 2017/9/20 0020.
 * 网络请求的回调，在UI线程中回调
 *
 */

public abstract class CallBackListener<T> {

    private Response.Listener<T> listener;

    private Response.ErrorListener errorListener;

    public CallBackListener() {
        listener = new Response.Listener<T>(){
            @Override
            public void onResponse(T t) {
                CallBackListener.this.onResponse(t);
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CallBackListener.this.onErrorResponse(volleyError);
            }
        };
    }


    public Response.Listener getListener() {
        return listener;
    }

    public Response.ErrorListener getErrorListener() {
        return errorListener;
    }

    public abstract void onResponse(T t);
    public abstract void onErrorResponse(VolleyError volleyError);
}
