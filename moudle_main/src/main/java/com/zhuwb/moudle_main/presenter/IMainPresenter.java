package com.zhuwb.moudle_main.presenter;

import android.support.v4.app.FragmentManager;

import com.zcy.hnkjxy.customview.BottomTabBar;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/12 12:35
 */

public interface IMainPresenter {
    void init(BottomTabBar tb, FragmentManager fragmentManager);

    void onSelect(int position);
}
