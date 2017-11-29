package com.zhuwb.moudle_main.adpter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.Utils.Util;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.presenter.BannerParticularsPresenter;
import com.zhuwb.moudle_main.presenter.IBannerParticularsPresenter;

import java.util.List;

/**
 * ======================================
 * 创建人   ： ZhuWB
 * 创建时间 :2017/11/24 09:43
 * ======================================
 */

public class MyRVAdapter extends BaseQuickAdapter<ListMessageitem.MessageBean, BaseViewHolder> {

    private static final String TAG = "MyRVAdapter";
    private FragmentManager manager;

    public MyRVAdapter(int layoutResId, List<ListMessageitem.MessageBean> datas, FragmentManager manager) {
        super(layoutResId, datas);
        Log.i(TAG, "MyRVAdapter: " + datas.size());
        this.manager = manager;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ListMessageitem.MessageBean item) {
        helper.getView(R.id.main_lv_item_marquee).setSelected(true);
        helper.setText(R.id.main_lv_item_typearea, Util.whitchArea(item.getMessage_zone_id()));
        helper.setText(R.id.main_lv_item_type, item.getMsg_type_name());
        helper.setText(R.id.main_lv_item_time, Util.getData(item.getMessage_verify_date()));
        helper.setText(R.id.main_lv_item_address, item.getMessage_ads());
        helper.setText(R.id.main_lv_item_message, item.getMessage_content());
        helper.setText(R.id.main_lv_item_see, item.getBrow_user_cont() + "人浏览");
        helper.setOnClickListener(R.id.h_like_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setImageResource(R.id.main_lv_item_plike, R.mipmap.like_icon_o);
                helper.setText(R.id.main_lv_item_Tlike, "1赞");
            }
        });
        if (item.getMessage_images().length() != 0) {
            helper.setVisible(R.id.main_lv_girdView, true);
            IBannerParticularsPresenter iBannerParticularsPresenter = new BannerParticularsPresenter();
            iBannerParticularsPresenter.setImageAdapter(helper.<NineGridImageView>getView(R.id.main_lv_girdView), item.getMessage_images(), manager);
        } else {
            helper.setGone(R.id.main_lv_girdView, false);
        }


    }

}
