package com.zhuwb.moudle_main.presenter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.zcy.hnkjxy.customview.RefreshListView;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/17 09:12
 */

public interface IListMessage {
    void getMessage(FragmentActivity context, RefreshListView listView, Integer mold, FragmentManager manager);
}
