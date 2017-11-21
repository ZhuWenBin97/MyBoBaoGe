package com.zhuwb.moudle_main.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhuwb.lib_magicindicator.MagicIndicator;
import com.zhuwb.lib_magicindicator.ViewPagerHelper;
import com.zhuwb.lib_magicindicator.buildins.commonnavigator.CommonNavigator;
import com.zhuwb.lib_magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.zhuwb.lib_magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.zhuwb.lib_magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.zhuwb.lib_magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import com.zhuwb.moudle_main.view.indicator.Fragmentbrand;
import com.zhuwb.moudle_main.view.indicator.Fragmentfood;
import com.zhuwb.moudle_main.view.indicator.Fragmentinstantmessage;
import com.zhuwb.moudle_main.view.indicator.Fragmentlostandfound;
import com.zhuwb.moudle_main.view.indicator.Fragmentmoldbaby;
import com.zhuwb.moudle_main.view.indicator.Fragmentrecruitment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/13 19:50
 *         消息界面的ViewPager的配置
 */

public class MessagePresenter implements IMessagePresenter {
    /**
     * 指示器
     */
    private String[] mDatas = new String[]{"即时信息", "爆款街", "品牌街", "招聘街", "美食街", "失物招领"};
    /**
     * 转化为List集合
     */
    private List<String> mDatalist = new ArrayList<String>(Arrays.asList(mDatas));
    /**
     * viewPagerHelper
     */
    private ViewPagerHelper viewPagerHelper;
    /**
     * Fragment集合
     */
    private List<Fragment> fragmentList;
    /**
     * 街道Fragment
     */
    private Fragmentinstantmessage fragment_instantmessage;
    private Fragmentmoldbaby fragment_moldbaby;
    private Fragmentbrand fragment_brand;
    private Fragmentrecruitment fragment_recruitment;
    private Fragmentfood fragment_food;
    private Fragmentlostandfound fragment_lostandfound;
    private ViewPager mainFragmentViewPager;
    private MagicIndicator mainFragmentMagicIndicator;
    /**
     * 上下文
     */
    private Context context;
    private FragmentManager manager;

    @Override
    public void initView(Context context, FragmentManager manager, final ViewPager mainFragmentViewPager, MagicIndicator mainFragmentMagicIndicator) {
        this.mainFragmentMagicIndicator = mainFragmentMagicIndicator;
        this.mainFragmentViewPager = mainFragmentViewPager;
        this.context = context;
        this.manager = manager;

        newFragment();

        //MagicIndicator的配置
        CommonNavigator commonNavigator = new CommonNavigator(context);
        mainFragmentViewPager.setAdapter(new MyFrageStatePagerAdapter(manager));
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDatalist.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                //指示器的配置
                clipPagerTitleView.setText(mDatalist.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainFragmentViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                //无指示器
                return null;
            }
        });
        mainFragmentMagicIndicator.setNavigator(commonNavigator);
        viewPagerHelper.bind(mainFragmentMagicIndicator, mainFragmentViewPager);


    }


    //new Fragment
    public void newFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragment_instantmessage = new Fragmentinstantmessage(manager);
            fragment_moldbaby = new Fragmentmoldbaby(manager);
            fragment_brand = new Fragmentbrand(manager);
            fragment_recruitment = new Fragmentrecruitment(manager);
            fragment_food = new Fragmentfood(manager);
            fragment_lostandfound = new Fragmentlostandfound(manager);

            fragmentList.add(fragment_instantmessage);
            fragmentList.add(fragment_moldbaby);
            fragmentList.add(fragment_brand);
            fragmentList.add(fragment_recruitment);
            fragmentList.add(fragment_food);
            fragmentList.add(fragment_lostandfound);
        }
    }


    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
