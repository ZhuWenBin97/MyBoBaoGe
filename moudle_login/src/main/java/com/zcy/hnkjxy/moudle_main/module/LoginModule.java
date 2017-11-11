package com.zcy.hnkjxy.moudle_main.module;

import com.zcy.hnkjxy.moudle_main.contract.LoginContract;
import com.zcy.hnkjxy.moudle_main.model.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/11/7.
 */
@Module
public class LoginModule {
    private LoginContract.View modelView;
    //        @Inject
    private LoginContract.Model loginModel;

    public LoginModule(LoginContract.View modelView) {
        this.modelView = modelView;
        this.loginModel = new LoginModel();
        //注入loginModel;
        //            LoginPresenter_LoginModule_MembersInjector
        //                    .create(LoginModel_Factory.create())
        //                    .injectMembers(this);
    }

    @Provides
    LoginContract.View provideMainView() {
        return modelView;
    }

    @Provides
    LoginContract.Model provideLoginModel() {
        return loginModel;
    }
}
