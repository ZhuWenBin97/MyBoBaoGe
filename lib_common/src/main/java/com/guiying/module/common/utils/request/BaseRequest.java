package com.guiying.module.common.utils.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang chen yang on 2017/9/21 0021.
 * Request请求基类
 */

public abstract class BaseRequest<T> extends Request<T>{

    //需要传入的参数
    private Map<String, String> httpParams;
    public BaseRequest(int method,String url, Response.ErrorListener listener) {
        super(method,url, listener);
        httpParams = new HashMap<String,String>();
    }
    /**
     * 传入Http参数
     *
     * @param key   参数名
     * @param value 参数值
     */
    public void addParam(String key, String value) {
        httpParams.put(key, value);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return httpParams;
    }


}
