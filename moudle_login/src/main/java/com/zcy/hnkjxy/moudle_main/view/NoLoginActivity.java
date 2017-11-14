package com.zcy.hnkjxy.moudle_main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guiying.module.common.base.BaseActivity;
import com.zcy.hnkjxy.moudle_main.R;

@Route(path = "/login/nologin")
public class NoLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_login);
        setupToolBar((Toolbar) findViewById(R.id.noLoginToolBar), true);
        findViewById(R.id.btnMainLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
            }
        });
        findViewById(R.id.btnMainRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(RegisterActivity.class);
                //FragmentActivity fragmentActivity = (FragmentActivity)
                ARouter.getInstance().build("/main/Message").navigation();
            }
        });

    }

    private void startActivity(Class<?> clazz) {
        Intent intent = new Intent(NoLoginActivity.this, clazz);
        startActivity(intent);
    }
}
