package com.zcy.hnkjxy.moudle_main.contract;

import com.zcy.hnkjxy.moudle_main.bean.BasisUser;

/**
 * Created by admin on 2017/11/7.
 */

public class RegisterContract {
    public interface Model {
        //注册
        void register(BasisUser user);

        //获取验证码
        void getVerification(String phoneNum);

        void setCallBack(CallBack callBack);
        interface CallBack {
            void success(String result);

            void fail();
        }
    }

    public interface View {
        String getPhoneNumber();
        String getCheckCode();
        String getPassWorld();
        String getWeChatId();
        String getQQId();
        void clearCheckCode();
        void toast(String msg);
        void startButtonTime();
    }

    public interface Presenter {

    }
}
