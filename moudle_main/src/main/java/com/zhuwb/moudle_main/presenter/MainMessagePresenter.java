package com.zhuwb.moudle_main.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhuwb.moudle_main.HttpUtils.HttpUtil;
import com.zhuwb.moudle_main.HttpUtils.OkHttpUtil;
import com.zhuwb.moudle_main.model.InstantModel;
import com.zhuwb.moudle_main.R;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.bean.ListMessageitem;
import com.zhuwb.moudle_main.contract.MessageContract;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/21 10:49
 */

public class MainMessagePresenter implements MessageContract.IFragmentModel.OnLoadListener, MessageContract.IFragmentPresenter {
    private static final String TAG = "MainMessagePresenter";
    private InstantModel instantModel;
    private FragmentActivity activity;
    private MessageContract.IFragmentView iFragmentView;
    private List<BannerMessage.BannerBean> messageBeanBanner;
    private int mold;
    private int curPage;
    private int type;

    public MainMessagePresenter(Integer mold, Integer type, MessageContract.IFragmentView iFragmentView, FragmentActivity activity) {
        instantModel = new InstantModel(this);
        this.mold = mold;
        this.type = type;
        this.iFragmentView = iFragmentView;
        this.activity = activity;
    }

    @Override
    public void onFail(Exception e) {


    }

    @Override
    public void onSucced(String json) {
        Gson gson = new Gson();
        try {
            BannerMessage messages = gson.fromJson(json, BannerMessage.class);
            List<BannerMessage.BannerBean> messageBeans = messages.getMessage();

            List<String> listImage = new ArrayList<>();
            //取轮播图首页图片
            for (int i = 0; i < messageBeans.size(); i++) {
                BannerMessage.BannerBean message = (BannerMessage.BannerBean) messageBeans.get(i);
                //去掉资源中多余的字符
                String[] str = message.getMessage_master().split("787311295");
                listImage.add(str[0]);
            }
            this.messageBeanBanner = messageBeans;
            iFragmentView.showBanner(listImage);

        } catch (Exception e) {
            Log.i(TAG, "onSucced: " + "Exception");
            ListMessageitem message = gson.fromJson(json, ListMessageitem.class);
            List<ListMessageitem.MessageBean> messageBeans = new ArrayList<>();
            for (ListMessageitem.MessageBean messageBean : message.getMessage()) {
                if (messageBean != null) {
                    messageBeans.add(messageBean);
                }
            }
            iFragmentView.showList(messageBeans);
        }
    }

    @Override
    public void loadListMessage(Integer curPage) {
        instantModel.requestNetwork(HttpUtil.getListUrl(mold, curPage, type), activity);

    }

    @Override
    public void loadBannerMessage(Banner banner) {
        instantModel.requestNetwork(HttpUtil.getBannerUrl(mold), activity);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new MainMessagePresenter.GlideImageLoader());
        //动画样式
        banner.setBannerAnimation(Transformer.RotateDown);
        //轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerMessage bannerMessage = new BannerMessage();
                bannerMessage.setCode(position);
                bannerMessage.setMessage(messageBeanBanner);
                BannerMessage.BannerBean bannerBean = messageBeanBanner.get(position);
                Log.i(TAG, "OnBannerClick: " + "点击了" + position);
                EventBus.getDefault().post(bannerMessage);
            }
        });

    }

    @Override
    public void destory() {
        iFragmentView = null;
        if (instantModel != null) {
            OkHttpUtil.getInstance().cancelTag(activity);
        }
        instantModel = null;
    }


    public static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path)
                    .thumbnail(0.1f).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.main_pictures_no)
                    .into(imageView);
        }
    }

}
