package com.zhuwb.moudle_main.presenter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.zcy.hnkjxy.customview.ZoomPhotoView;
import com.zhuwb.moudle_main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuWB
 *         创建时间 :2017/11/18 15:00
 */

public class BannerParticularsPresenter implements IBannerParticularsPresenter {
    private static final String TAG = "BannerParticularsPresen";
    ArrayList<String> listimg = new ArrayList<>();
    private FragmentManager mManager;

    @Override
    public void setImageAdapter(NineGridImageView nineGridImageView, String imgs, FragmentManager manager) {
        mManager = manager;
        nineGridImageView.setAdapter(madpter);
        String[] arrayimg = imgs.split("787311295");

        if (arrayimg.length >= 1) {
            for (int i = 0; i < arrayimg.length; i++) {
                listimg.add(arrayimg[i]);
                Log.i(TAG, "setImageAdapter: ");
            }
        } else {
            String[] arrayimg2 = imgs.split("http");
            for (int i = 0; i < arrayimg.length; i++) {
                listimg.add("http" + arrayimg2[i]);
            }
        }
        nineGridImageView.setImagesData(listimg);
    }

    private NineGridImageViewAdapter<String> madpter = new NineGridImageViewAdapter<String>() {

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            Glide.with(context).load(s).thumbnail(0.1f)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.main_pictures_no)
                    .into(imageView);
            Log.i(TAG, "onDisplayImage: " + s);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, int index, List<String> list) {
            ZoomPhotoView.Create.build().addImages(listimg).show(mManager, "s");
        }

    };


}
