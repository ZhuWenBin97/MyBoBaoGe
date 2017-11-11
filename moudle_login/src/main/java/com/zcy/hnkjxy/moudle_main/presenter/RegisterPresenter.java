package com.zcy.hnkjxy.moudle_main.presenter;

import com.zcy.hnkjxy.moudle_main.bean.BasisUser;
import com.zcy.hnkjxy.moudle_main.contract.RegisterContract;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by admin on 2017/11/7.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.Model model;
    private RegisterContract.View view;
    private BasisUser user;
    private static final int PHONE_NUMBER_LENGTH = 11;
    private static final int WECHAT_ID_LENGTH = 5;
    private static final int PASSWORLD_LENGTH = 6;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, final RegisterContract.View view) {
        this.model = model;
        this.view = view;
        this.user = new BasisUser();
        this.model.setCallBack(new RegisterContract.Model.CallBack() {
            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("code")) {
                        int code = jsonObject.getInt("code");
                        switch (code) {
                            case -50006004:
                                view.toast("该号码已注册！");
                                break;
                            case -20001002:
                                view.toast("验证码错误");
                                break;
                            case 1:
                                if ("Success".equals(jsonObject.getString("message"))) {
                                    view.toast("注册成功");
                                } else {
                                    view.toast("已发送验证码！");
                                }
                                view.startButtonTime();
                                break;
                            default:
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail() {

            }

        });
    }

    //从view获取电话号码交给model 层
    public void getVer() {
        if (view.getPhoneNumber().trim().length() != PHONE_NUMBER_LENGTH) {
            view.toast("手机号码错误");
            return;
        }
        this.model.getVerification(view.getPhoneNumber());

    }


    /***
     * 注册
     */
    public void doRegister() {
        if (view.getPhoneNumber().length() != 11) {
            view.toast("手机号码错误");

        }
        if ("".equals(view.getCheckCode().trim())) {

            view.toast("验证码不能为空");
            return;
        }
        if (view.getPassWorld().trim().length() < PASSWORLD_LENGTH) {
            view.toast("密码不能小于6位");
            return;
        }
        if (view.getWeChatId().trim().length() < WECHAT_ID_LENGTH) {
            view.toast("微信长度不能小于5位");
            return;
        }
        user.setPhoneNum(view.getPhoneNumber().trim());
        user.setPassWorld(view.getPassWorld().trim());
        user.setVerificationNum(view.getCheckCode().trim());
        user.setWeChatNum(view.getWeChatId().trim());
        user.setQqNum(view.getQQId().trim());
        //执行注册方法
        this.model.register(user);
    }






}
