package com.zcy.hnkjxy.moudle_main.contract;

import com.zcy.hnkjxy.moudle_main.bean.BasisUser;

/**
 * Created by admin on 2017/11/7.
 */

public class LoginContract {
   public interface Model {
        interface CallBack{
            void doSuccess(String s);
            void doError();
        }
        void setCallBack(CallBack callBack);
        void doLogin(BasisUser user);
    }

    public  interface Presenter {

    }

    public interface View {
        String getUserName();
        String getUserPassWorld();
        void clearUserName();
        void clearUserPassWorld();
        void showProgress();
        void dismissProgress();
        void loginSuccess();
        void toast(String msg);
        void loginFail();
    }

}
