package com.zcy.hnkjxy.moudle_main.model;

import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.guiying.module.common.base.BaseApplication;
import com.guiying.module.common.config.Config;
import com.guiying.module.common.utils.MD5;
import com.guiying.module.common.utils.Util;
import com.guiying.module.common.utils.request.CallBackListener;
import com.guiying.module.common.utils.request.TextRequest;
import com.zcy.hnkjxy.moudle_main.bean.BasisUser;
import com.zcy.hnkjxy.moudle_main.contract.LoginContract;

import java.util.HashMap;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 */

public class LoginModel implements LoginContract.Model{
    private CallBack callBack;
//    @Inject
//    LoginModel(){
//
//    }


    private static final String TAG = "LoginModel";

    @Override
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
    @Override
    public void doLogin(BasisUser user) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "login");
        map.put("func", "login");
        map.put("account", user.getAccount());
        map.put("imei", Config.androidIMEI);
        map.put("password", MD5.getMD5(user.getPassWorld()));
        String url = Util.getUrl(map);
        Log.i(TAG, "doLogin: "+url);
        final TextRequest request = new TextRequest(url, new CallBackListener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i(TAG, "onResponse: "+s);
                callBack.doSuccess(s);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.doError();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Volley.newRequestQueue(BaseApplication.getIns()).add(request);
            }
        },2000);
    }
}
