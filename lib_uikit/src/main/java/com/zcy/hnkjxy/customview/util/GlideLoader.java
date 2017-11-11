package com.zcy.hnkjxy.customview.util;

import android.app.Activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zcy.hnkjxy.customview.R;


/**
 * Created by zhang chen yang on 2017/9/20 0020.
 * {@link GlideLoader}封装的Glide图片加载框架，简单的传入3个参数即可实现高效率的图片加载
 */

public class GlideLoader {

    public static boolean isCache = false;
    public static int loadingImg = R.drawable.image_loading;
    public static int loadErrorImg = R.drawable.image_load_fail;

    /**
     * @param activity
     * @param loadUrl  图片地址
     * @param intoView 放置图片的ImageView
     */
    public static void load(Activity activity, String loadUrl, ImageView intoView) {
        if (isCache) {
            Glide.with(activity).load(loadUrl)
                    .placeholder(loadingImg)
                    .error(loadErrorImg)
                    .into(intoView);
        } else {
            Glide.with(activity).load(loadUrl)
                    .placeholder(loadingImg)
                    .error(loadErrorImg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(intoView);
        }
    }

    /**
     * @param fragment
     * @param loadUrl  图片地址
     * @param intoView 放置图片的ImageView
     */
    public static void load(Fragment fragment, String loadUrl, ImageView intoView) {
        if (isCache) {
            Glide.with(fragment).load(loadUrl)
                    .placeholder(loadingImg)
                    .error(loadErrorImg)
                    .into(intoView);
        } else {
            Glide.with(fragment).load(loadUrl)
                    .placeholder(loadingImg)
                    .error(loadErrorImg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(intoView);
        }
    }

    /**
     * @param context
     * @param loadUrl  图片地址
     * @param intoView 放置图片的ImageView
     */
    public static void load(Context context, String loadUrl, ImageView intoView) {
        if (isCache) {
            Glide.with(context).load(loadUrl)
                    .placeholder(loadingImg)
                    .error(loadErrorImg)
                    .into(intoView);
        } else {
            Glide.with(context).load(loadUrl)
                    .placeholder(loadingImg)
                    .error(loadErrorImg)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(intoView);
        }
    }

    /**
     * 清除图片缓存，要在子线程中执行
     */
    public static void clear(Context context) {
        final Context mContext = context;
        Glide.get(mContext).clearMemory();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Glide.get(mContext).clearDiskCache();

            }
        }.start();

    }


}
