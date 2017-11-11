package com.zcy.hnkjxy.moudle_main.presenter;

import com.zcy.hnkjxy.moudle_main.bean.BasisUser;
import com.zcy.hnkjxy.moudle_main.contract.LoginContract;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by zhang chen yang on 2017/11/2 0002.
 */

public class LoginPresenter {
    private LoginContract.Model model;
    private LoginContract.View view;
    private BasisUser user;

    //构造函数依赖注入
    @Inject
    LoginPresenter(LoginContract.View iview, LoginContract.Model imodel) {
        this.view = iview;
        this.model = imodel;
        this.user = new BasisUser();
        //因为model是异步处理请求，所以设置model回调方法
        this.model.setCallBack(new LoginContract.Model.CallBack() {
            @Override
            public void doSuccess(String str) {
                view.dismissProgress();
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    if (jsonObject.has("code")) {
                        int code = jsonObject.getInt("code");
                        switch (code) {
                            case 1:
                                view.loginSuccess();
                                break;
                            default:
                                view.loginFail();
                                break;
                        }
                    } else {
                        view.loginFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doError() {
                view.loginFail();
                view.dismissProgress();
            }
        });
    }

    //登录操作
    public void login() {
        if ("".equals(view.getUserName())) {
            view.toast("账号不能为空");
            return;
        }
        if ("".equals(view.getUserPassWorld())) {
            view.toast("密码不能为空");
            return;
        }
        view.showProgress();
        user.setAccount(view.getUserName());
        user.setPassWorld(view.getUserPassWorld());
        model.doLogin(user);
    }




}
