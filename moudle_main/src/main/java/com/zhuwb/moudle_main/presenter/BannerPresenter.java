package com.zhuwb.moudle_main.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.guiying.module.common.config.Config;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhuwb.moudle_main.model.HttpUtils;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.view.BannerParticularsActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.guiying.module.common.utils.Util.getUrl;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/14 10:34
 */

public class BannerPresenter implements IBannerPresenter {
    private static final String TAG = "AutoShufflingPresenter";
    private Context context;
    /**
     * 存放轮播图图片数组
     */
    private List<String> Listimage;

    /**
     * 线程库
     */
    private Banner banner;
    /**
     * 街道指示表示
     */
    private int mold;

    private BannerMessage Messages;
    private List<BannerMessage.MessageBean> messageBeans;


    @Override
    public void getImages(final FragmentActivity context, final Banner banner, Integer mold) {
        this.context = context;
        this.banner = banner;
        this.mold = mold;
        /**
         * 设置url
         */
        HashMap<String, String> map = new HashMap<String, String>();
        //      String str = "http://bobaoge.oss-cn-shanghai.aliyuncs.com/adv/banner30.jpg?v=2.2787311295" +
        //       "http://bobaoge.oss-cn-shanghai.aliyuncs.com/adv/banner31.jpg?v=2.2787311295" +
        //       "http://bobaoge.oss-cn-shanghai.aliyuncs.com/adv/banner32.jpg?v=2.2787311295";
        //    String[] arr = str.split("http");
        //    for (int i = 1; i < arr.length; i++) {
        //            Log.i(TAG, "getImages: " + arr[i]);
        //     }
        // http://test.bobaoge.com/?
        // mold=1&
        // bobao_token=408d808e0c96682b789ddc4a207eb22f&
        // imei=862414030478622&
        // action=adv&userId=&func=getAdvList&
        // spm=PRO+6+Plus.3.51.0.1510704000.0

        map.put("action", "adv");
        map.put("func", "getAdvList");
        map.put("userId", "");
        map.put("imei", Config.androidIMEI);
        map.put("mold", mold.toString());
        String url = getUrl(map);
         Log.i(TAG, "getImages: " + url);
        HttpUtils.requestNetwork(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
                e.printStackTrace();
                Toast.makeText(context, "加载失败，请检查网络", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                Messages = gson.fromJson(json, BannerMessage.class);
                messageBeans = Messages.getMessage();
                Listimage = getImgs(json);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置banner样式
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        //图片集合
                        banner.setImages(Listimage);
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
                                bannerMessage.setMessage(messageBeans);
                                Log.i(TAG, "OnBannerClick: " + "点击了" + position);
                                EventBus.getDefault().post(bannerMessage);
                            }
                        });
                        banner.start();
                    }
                });
            }

        });

//
    }

    private List<String> getImgs(String json) {
        List<String> ListImage = new ArrayList<>();
        //取轮播图首页图片
        for (int i = 0; i < messageBeans.size(); i++) {
            BannerMessage.MessageBean message = (BannerMessage.MessageBean) messageBeans.get(i);
            //去掉资源中多余的字符
            String[] str = message.getMessage_master().split("787311295");
            ListImage.add(str[0]);
        }

        return ListImage;
    }

    private class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

}
