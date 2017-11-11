package com.zcy.hnkjxy.moudle_main.model;

import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.guiying.module.common.base.BaseApplication;
import com.guiying.module.common.config.Config;
import com.guiying.module.common.utils.MD5;
import com.guiying.module.common.utils.Util;
import com.guiying.module.common.utils.request.CallBackListener;
import com.guiying.module.common.utils.request.TextCookieRequest;
import com.zcy.hnkjxy.moudle_main.bean.BasisUser;
import com.zcy.hnkjxy.moudle_main.contract.RegisterContract;

import java.util.HashMap;

/**
 * Created by admin on 2017/11/7.
 */

public class RegisterModel implements RegisterContract.Model {
    CallBack callBack;
    private String TAG="RegisterModel";

    @Override
    public void register(BasisUser user) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "login");
        map.put("func", "register");
        map.put("user_phone", user.getPhoneNum());
        map.put("code", user.getVerificationNum());
        map.put("user_password",  MD5.getMD5(user.getPassWorld()));
        map.put("user_weixin", user.getWeChatNum());
        map.put("user_qq", user.getQqNum());
        String url = Util.getUrl(map);
        Log.i(TAG, "register: "+url);
        TextCookieRequest request = new TextCookieRequest(url, new CallBackListener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i(TAG, "onResponse: "+s+"--->cookie:"+Config.cookie);
                callBack.success(s);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.fail();
            }
        });
        Volley.newRequestQueue(BaseApplication.getIns()).add(request);
    }




    @Override
    public void getVerification(String phoneNum) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "user");
        map.put("func", "sendMessage");
        map.put("user_phone", phoneNum);
        map.put("validate_type", "1");
        String url = Util.getUrl(map);
        Log.i(TAG, "getVerification: "+url);
        TextCookieRequest request = new TextCookieRequest(url, new CallBackListener<String>() {
            @Override
            public void onResponse(String o) {
                Log.i(TAG, "onResponse: "+o);
                Log.i(TAG, "onResponse: "+"--->cookie:"+ Config.cookie);
                callBack.success(o);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "onErrorResponse: "+volleyError.toString());
                callBack.fail();
            }
        });

        //发送请求
        Volley.newRequestQueue(BaseApplication.getIns()).add(request);
    }


    @Override
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
