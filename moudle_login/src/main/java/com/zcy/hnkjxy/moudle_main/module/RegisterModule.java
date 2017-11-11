package com.zcy.hnkjxy.moudle_main.module;


import com.zcy.hnkjxy.moudle_main.contract.RegisterContract;
import com.zcy.hnkjxy.moudle_main.model.RegisterModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/11/7.
 */

@Module
public  class RegisterModule {
    private RegisterContract.Model model;
    private RegisterContract.View view;

    public RegisterModule(RegisterContract.View view) {
        this.view = view;
        model = new RegisterModel();
    }

    @Provides
    public RegisterContract.View provideRegisterView() {
        return this.view;
    }

    @Provides
    public RegisterContract.Model provideRegisterModel() {
        return this.model;
    }
}
