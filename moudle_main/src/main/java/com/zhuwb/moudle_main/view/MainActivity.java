package com.zhuwb.moudle_main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zcy.hnkjxy.customview.BottomTabBar;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.R2;
import com.zhuwb.moudle_main.presenter.IMainPresenter;
import com.zhuwb.moudle_main.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/main/Message")
public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    @BindView(R2.id.main_activty_bottom)
    BottomTabBar tb;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        //ARouter.getInstance().build("/main/message").navigation();
        //tb = (BottomTabBar) findViewById(R.id.main_activty_bottom);
        IMainPresenter presenter = new MainPresenter();
        int i = 0;
        fragmentManager = getSupportFragmentManager();
        presenter.init(tb, fragmentManager);
        Log.i(TAG, "onCreate: " + "log测试");
    }

}
