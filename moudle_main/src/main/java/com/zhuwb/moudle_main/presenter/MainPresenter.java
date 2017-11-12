package com.zhuwb.moudle_main.presenter;


import android.support.v4.app.FragmentManager;

import com.zcy.hnkjxy.customview.BottomTabBar;
import com.zcy.hnkjxy.customview.bean.BarEntity;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.view.Fragment_gallery;
import com.zhuwb.moudle_main.view.Fragment_groupbying;
import com.zhuwb.moudle_main.view.Fragment_me;
import com.zhuwb.moudle_main.view.Fragment_message;
import com.zhuwb.moudle_main.view.Fragment_release;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/12 11:43
 */

public class MainPresenter implements BottomTabBar.OnSelectListener, IMainPresenter {
    private Fragment_message fragment_message;
    private Fragment_groupbying fragment_groupbying;
    private Fragment_gallery fragment_gallery;
    private Fragment_me fragment_me;
    private Fragment_release fragment_release;
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
                    fragment_message = new Fragment_message();
                }
                tb.switchContent(fragment_message);
                break;
            case 1:
                if (fragment_groupbying == null) {
                    fragment_groupbying = new Fragment_groupbying();
                }
                tb.switchContent(fragment_groupbying);
                break;
            case 2:
                if (fragment_release == null) {
                    fragment_release = new Fragment_release();
                }
                tb.switchContent(fragment_release);
                break;
            case 3:
                if (fragment_gallery == null) {
                    fragment_gallery = new Fragment_gallery();
                }
                tb.switchContent(fragment_gallery);
                break;
            case 4:
                if (fragment_me == null) {
                    fragment_me = new Fragment_me();
                }
                tb.switchContent(fragment_me);
                break;
            default:
                break;

        }
    }
}
