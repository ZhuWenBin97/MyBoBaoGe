package com.zhuwb.moudle_main.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.youth.banner.Banner;
import com.zcy.hnkjxy.customview.AutoShufflingView;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/15 14:48
 */

public interface IBannerPresenter {
    void getImages(FragmentActivity context, Banner banner, Integer mold);
}
