package com.zhuwb.moudle_main.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.zhuwb.lib_magicindicator.MagicIndicator;

import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 20:12
 */

public interface IMessagePresenter {
    void initView(Context context, FragmentManager manager, final ViewPager mainFragmentViewPager, MagicIndicator mainFragmentMagicIndicator);
}
