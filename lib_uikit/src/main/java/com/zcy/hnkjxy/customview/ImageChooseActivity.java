package com.zcy.hnkjxy.customview;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zcy.hnkjxy.customview.bean.ImageBean;
import com.zcy.hnkjxy.customview.bean.ImageFolderBean;
import com.zcy.hnkjxy.customview.fragment.DetailImageFragment;
import com.zcy.hnkjxy.customview.fragment.PhotoAlbumFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageChooseActivity extends AppCompatActivity implements DetailImageFragment.OnSelectedFinishListener, PhotoAlbumFragment.OnSelectPhotoListener {
    public static final int RESULT_CODE = 123;
    public static final String IMAGES_URI = "images_uri";
    private static final String TAG = "ImageChooseActivity";
    private Context mContext;
    private List<ImageFolderBean> imageFolderList;
    private TextView tvLeft, tvMiddle, tvMiddle2, tvRight;

    private ViewPager vpFragment;
    //详情Fragment
    private DetailImageFragment detailFragment;
    //相册Fragment
    private PhotoAlbumFragment photoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_choose);
        mContext = this;
        //后台扫描图片
        new ScanImageTask().execute("");

    }

    private void initView() {
        //初始化详情页和相册页，并分别设置数据
        detailFragment = new DetailImageFragment(this, imageFolderList.get(0));
        photoFragment = new PhotoAlbumFragment(this);
        photoFragment.setImageFolderBeen(imageFolderList);
        //findViewById获取控件
        tvLeft = (TextView) findViewById(R.id.tvChooseImgLeft);
        tvMiddle = (TextView) findViewById(R.id.tvChooseImgMiddle);
        tvMiddle2 = (TextView) findViewById(R.id.tvChooseImgMiddle2);
        tvRight = (TextView) findViewById(R.id.tvChooseImgRight);
        vpFragment = (ViewPager) findViewById(R.id.vpChooseImg);
        vpFragment.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 1)
                    return detailFragment;
                else
                    return photoFragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        //设置默认为详情页面
        vpFragment.setCurrentItem(1);
        //设置监听器
        vpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (vpFragment.getCurrentItem() == 0) {
                    tvMiddle.setVisibility(View.GONE);
                    tvLeft.setVisibility(View.GONE);
                    tvMiddle2.setVisibility(View.VISIBLE);
                } else {
                    tvMiddle2.setVisibility(View.GONE);
                    tvMiddle.setVisibility(View.VISIBLE);
                    tvLeft.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置点击事件
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpFragment.setCurrentItem(0);
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //
    @Override
    public void onSelectImages(List<String> selectedImages) {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(IMAGES_URI, (ArrayList<String>) selectedImages);
        this.setResult(RESULT_CODE,intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageFolderList.clear();
        vpFragment.removeAllViews();
        imageFolderList = null;
        detailFragment = null;
        photoFragment = null;
        vpFragment = null;
    }

    @Override
    public void onSelectPhoto(ImageFolderBean imageFolder) {
        detailFragment.setListFolderBean(imageFolder);
        tvMiddle.setText("" + imageFolder.getImgFolderName());
        vpFragment.setCurrentItem(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && vpFragment.getCurrentItem() == 1) {
            vpFragment.setCurrentItem(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 扫描图片地址
     */
    class ScanImageTask extends AsyncTask<String, Integer, HashMap<String, List<String>>> {
        private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
        private ProgressDialog proDia;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            proDia = new ProgressDialog(mContext);
            proDia.setTitle("正在扫描本地图片");
            proDia.setCancelable(true);
            proDia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            proDia.setIndeterminate(false);
            proDia.show();
        }

        @Override
        protected void onPostExecute(HashMap<String, List<String>> maps) {
            super.onPostExecute(maps);
            proDia.dismiss();
            List<ImageFolderBean> imgList;
            imgList = new ArrayList<>();
            for (String string : maps.keySet()) {
                //获取图片路径
                ImageBean[] imageBeens = new ImageBean[maps.get(string).size()];
                for (int i = 0; i < maps.get(string).size(); i++) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setImgUrl(maps.get(string).get(i));
                    imageBean.setSelected(false);
                    imageBeens[i] = imageBean;
                }
                //同一个文件夹下的图片集合
                ImageFolderBean folderBean = new ImageFolderBean();
                folderBean.setImgFirstName(imageBeens[0].getImgUrl());
                folderBean.setImgFolderCount(imageBeens.length);
                folderBean.setImgFolderName(string);
                folderBean.setImageBeens(imageBeens);
                imgList.add(folderBean);
            }
            //写一个全部照片的
            List<ImageBean> list = new ArrayList<>();
            for (ImageFolderBean f : imgList) {
                for (ImageBean b : f.getImageBeens()) {
                    list.add(b);
                }
            }
            Log.i(TAG, "onPostExecute: listSize:" + list.size());
            ImageFolderBean folderBean = new ImageFolderBean();
            ImageBean[] b = list.toArray(new ImageBean[list.size()]);
            folderBean.setImgFolderCount(b.length);
            Log.i(TAG, "onPostExecute: " + b.length);
            folderBean.setImageBeens(b);
            folderBean.setImgFolderName("全部照片");
            folderBean.setImgFirstName(folderBean.getImageBeens()[0].getImgUrl());
            imgList.add(0, folderBean);
            imageFolderList = imgList;
            initView();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            proDia.setMax(values[0]);
            proDia.setProgress(values[1]);
        }

        @Override
        protected HashMap<String, List<String>> doInBackground(String... params) {
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = ImageChooseActivity.this.getContentResolver();
            //只查询jpeg和png的图片
            Cursor mCursor = mContentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png", "image/gif"}, MediaStore.Images.Media.DATE_MODIFIED);

            if (mCursor == null) {
                return null;
            }

            int count = 0;
            while (mCursor.moveToNext()) {
                count++;
                publishProgress(mCursor.getCount(), count);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取图片的路径
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                //获取该图片的父路径名
                String parentName = new File(path).getParentFile().getName();
                //根据父路径名将图片放入到mGruopMap中
                if (!mGruopMap.containsKey(parentName)) {
                    List<String> chileList = new ArrayList<String>();
                    chileList.add(path);
                    mGruopMap.put(parentName, chileList);
                } else {
                    mGruopMap.get(parentName).add(path);
                }
            }
            Log.i(TAG, "doInBackground: Count:" + count);
            mCursor.close();
            return mGruopMap;
        }
    }

}
