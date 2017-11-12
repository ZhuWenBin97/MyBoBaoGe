package com.zhuwb.moudle_main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.zcy.hnkjxy.customview.BottomTabBar;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.presenter.IMainPresenter;
import com.zhuwb.moudle_main.presenter.MainPresenter;


public class MainActivity extends FragmentActivity {
    private BottomTabBar tb;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = (BottomTabBar) findViewById(R.id.main_activty_bottom);
        IMainPresenter presenter = new MainPresenter();
        fragmentManager = getSupportFragmentManager();
        presenter.init(tb, fragmentManager);
    }

}
