package com.zcy.hnkjxy.moudle_main.compoent;

import com.zcy.hnkjxy.moudle_main.module.LoginModule;
import com.zcy.hnkjxy.moudle_main.view.LoginActivity;

import dagger.Component;

/**
 * Created by admin on 2017/11/7.
 */

@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}