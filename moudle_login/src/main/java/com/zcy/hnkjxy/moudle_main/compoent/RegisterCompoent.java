package com.zcy.hnkjxy.moudle_main.compoent;


import com.zcy.hnkjxy.moudle_main.module.RegisterModule;
import com.zcy.hnkjxy.moudle_main.view.RegisterActivity;

import dagger.Component;

/**
 * Created by admin on 2017/11/7.
 */
@Component(modules = RegisterModule.class)
public interface RegisterCompoent {
    void inject(RegisterActivity activity);
}
