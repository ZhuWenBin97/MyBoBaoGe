package com.zhuwb.moudle_main.presenter;

import android.support.v4.app.FragmentManager;

import com.jaeger.ninegridimageview.NineGridImageView;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/20 09:27
 */

public interface IBannerParticularsPresenter {
    /**
     *
     * @param nineGridImageView
     * @param imgs
     */
    void setImageAdapter(NineGridImageView nineGridImageView, String imgs,FragmentManager manager);
}
