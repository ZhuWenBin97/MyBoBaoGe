package com.zhuwb.moudle_main.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guiying.module.common.config.Config;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zcy.hnkjxy.customview.AutoShufflingView;
import com.zhuwb.moudle_main.HttpUtils;
import com.zhuwb.moudle_main.bean.BannerMessage;
import com.zhuwb.moudle_main.view.Fragment_instantmessage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.guiying.module.common.utils.Util.getUrl;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/14 10:34
 */

public class AutoShufflingPresenter implements IAutoShufflingPresenter {
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


    @Override
    public void getImages(final FragmentActivity context, final Banner banner) {
        this.context = context;
        this.banner = banner;
        /**
         * 设置url
         */
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", "adv");
        map.put("func", "getAdvList");

        map.put("userId", "");
//        String str = "http://bobaoge.oss-cn-shanghai.aliyuncs.com/adv/banner30.jpg?v=2.2787311295" +
//                "http://bobaoge.oss-cn-shanghai.aliyuncs.com/adv/banner31.jpg?v=2.2787311295" +
//                "http://bobaoge.oss-cn-shanghai.aliyuncs.com/adv/banner32.jpg?v=2.2787311295";
//        String[] arr = str.split("http");
//        for (int i = 1; i < arr.length; i++) {
//            Log.i(TAG, "getImages: " + arr[i]);
//        }
        //http://test.bobaoge.com/?
        // mold=1&
        // bobao_token=408d808e0c96682b789ddc4a207eb22f&
        // imei=862414030478622&
        // action=adv&userId=&func=getAdvList&
        // spm=PRO+6+Plus.3.51.0.1510704000.0

        map.put("imei", Config.androidIMEI);
        map.put("mold", "1");
        String url = getUrl(map);
        Log.i(TAG, "getImages: " + url);
        System.out.println("start");
        HttpUtils.requestNetwork(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
                e.printStackTrace();
                System.out.println("false");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Listimage = toGson(json);
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
                        banner.start();
                    }
                });
            }

        });

//
    }

    private List<String> toGson(String json) {
        Gson gson = new Gson();
        List<String> ListImage = new ArrayList<>();
//        Type type = new TypeToken<List<BannerMessage>>() {
//        }.getType();
        // Log.i(TAG, "toGson: " + .size());
        BannerMessage Messages = gson.fromJson(json, BannerMessage.class);
        List<BannerMessage.MessageBean> messageBeans = Messages.getMessage();
        Log.i(TAG, "toGson: " + Messages.getMessage().size());
        //for (Iterator iterator = Messages.getMessage().iterator(); iterator.hasNext(); ) {
        for (int i = 0; i < messageBeans.size(); i++) {
            BannerMessage.MessageBean message = (BannerMessage.MessageBean) messageBeans.get(i);
            String[] str = message.getMessage_master().split("787311295");
            Log.i(TAG, "toGson: " + str[0]);
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
