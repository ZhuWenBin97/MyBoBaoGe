package com.zhuwb.moudle_main.presenter;


import android.support.v4.app.FragmentManager;

import com.zcy.hnkjxy.customview.BottomTabBar;
import com.zcy.hnkjxy.customview.bean.BarEntity;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.view.Fragmentgallery;
import com.zhuwb.moudle_main.view.Fragmentgroupbying;
import com.zhuwb.moudle_main.view.Fragmentme;
import com.zhuwb.moudle_main.view.Fragmentmessage;
import com.zhuwb.moudle_main.view.Fragmentrelease;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/12 11:43
 */

public class MainPresenter implements BottomTabBar.OnSelectListener, IMainPresenter {
    private Fragmentmessage fragment_message;
    private Fragmentgroupbying fragment_groupbying;
    private Fragmentgallery fragment_gallery;
    private Fragmentme fragment_me;
    private Fragmentrelease fragment_release;
    private BottomTabBar tb;
    private FragmentManager fragmentManager;
    /**
     * lib_uikit内bean文件夹下的BarEntity
     */
    private List<BarEntity> bars;

    @Override
    public void init(BottomTabBar tb, FragmentManager fragmentManager) {
        this.tb = tb;
        this.fragmentManager = fragmentManager;
        bars = new ArrayList<>();
        bars.add(new BarEntity("信息", R.mipmap.main_message_select, R.mipmap.main_message_unselect));
        bars.add(new BarEntity("团购", R.mipmap.main_groupbying_select, R.mipmap.main_groupbying_unselect));
        bars.add(new BarEntity("发布", R.mipmap.main_release, R.mipmap.main_releaseunselect));
        bars.add(new BarEntity("图库", R.mipmap.main_gallery_select, R.mipmap.main_gallery_unselect));
        bars.add(new BarEntity("我", R.mipmap.main_me_select, R.mipmap.main_me_unselect));
        tb.setManager(fragmentManager).setOnSelectListener(this).setBars(bars);


    }

    @Override
    public void onSelect(int position) {
        switch (position) {
            case 0:
                if (fragment_message == null) {
                    fragment_message = new Fragmentmessage();
                }
                tb.switchContent(fragment_message);
                break;
            case 1:
                if (fragment_groupbying == null) {
                    fragment_groupbying = new Fragmentgroupbying();
                }
                tb.switchContent(fragment_groupbying);
                break;
            case 2:
                if (fragment_release == null) {
                    fragment_release = new Fragmentrelease();
                }
                tb.switchContent(fragment_release);
                break;
            case 3:
                if (fragment_gallery == null) {
                    fragment_gallery = new Fragmentgallery();
                }
                tb.switchContent(fragment_gallery);
                break;
            case 4:
                if (fragment_me == null) {
                    fragment_me = new Fragmentme();
                }
                tb.switchContent(fragment_me);
                break;
            default:
                break;

        }
    }
}
