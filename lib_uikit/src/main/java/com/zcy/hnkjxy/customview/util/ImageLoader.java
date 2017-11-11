package com.zcy.hnkjxy.customview.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;


/**
 * ͼƬ������
 * http://blog.csdn.net/lmj623565791/article/details/41874561
 *
 * @author zhy
 */
public class ImageLoader {
    private static ImageLoader mInstance;
    private LruCache<String, Bitmap> mLruCache;
    private ExecutorService mThreadPool;
    private static final int DEAFULT_THREAD_COUNT = 1;
    private Type mType = Type.LIFO;
    private LinkedList<Runnable> mTaskQueue;
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    private Handler mUIHandler;

    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    private boolean isDiskCacheEnable = true;

    private static final String TAG = "ImageLoader";

    public enum Type {
        FIFO, LIFO
    }

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    /**
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        initBackThread();


        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

        };


        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);
    }


    private void initBackThread() {

        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Runnable a = getTask();
                        if (a != null)
                            mThreadPool.execute(a);
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                        }
                    }
                };

                mSemaphorePoolThreadHandler.release();
                Log.i("TAGXX", "run: 执行一次释放锁");
                Looper.loop();
            }

        };
        mPoolThread.start();
    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(DEAFULT_THREAD_COUNT, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    public static ImageLoader getInstance(int threadCount, Type type) {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }


    public void loadImage(final String path, final ImageView imageView, final boolean isFromNet) {
        imageView.setTag(path);
        if (mUIHandler == null) {
            Log.i("TAGXX", "loadImage: mUIHandler创建了");
            mUIHandler = new Handler() {
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageview = holder.imageView;
                    String path = holder.path;
                    if (imageview.getTag().toString().equals(path)) {
                        imageview.setImageBitmap(bm);
                    }
                }

            };
        }
        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            refreashBitmap(path, imageView, bm);
        } else {
            addTask(buildTask(path, imageView, isFromNet));
        }
    }


    private Runnable buildTask(final String path, final ImageView imageView, final boolean isFromNet) {
        return new Runnable() {
            @Override
            public void run() {
                Bitmap bm = null;
                if (isFromNet) {
                    File file = getDiskCacheDir(imageView.getContext(), md5(path));
                    if (file.exists()) {
                        Log.e(TAG, "find image :" + path + " in disk cache .");
                        bm = loadImageFromLocal(file.getAbsolutePath(), imageView);
                    } else {
                        if (isDiskCacheEnable) {
                            boolean downloadState = DownloadImgUtils.downloadImgByUrl(path, file);
                            if (downloadState) {
                                Log.e(TAG, "download image :" + path + " to disk cache . path is "
                                        + file.getAbsolutePath());
                                bm = loadImageFromLocal(file.getAbsolutePath(), imageView);
                            }
                        } else {
                            Log.e(TAG, "load image :" + path + " to memory.");
                            bm = DownloadImgUtils.downloadImgByUrl(path, imageView);
                        }
                    }
                } else {
                    bm = loadImageFromLocal(path, imageView);
                }
                addBitmapToLruCache(path, bm);
                refreashBitmap(path, imageView, bm);
                mSemaphoreThreadPool.release();
            }
        };
    }

    private Bitmap loadImageFromLocal(final String path,
                                      final ImageView imageView) {
        Bitmap bm;
        ImageSizeUtil.ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
        bm = decodeSampledBitmapFromPath(path, imageSize.width,
                imageSize.height);
        return bm;
    }

    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            try {
                if (mTaskQueue.getLast() != null)
                    return mTaskQueue.removeLast();
            } catch (Exception e) {
                Log.i("TAGXX", "getTask: " + e.toString());
            }

        }
        return null;
    }

    public String md5(String str) {
        byte[] digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            digest = md.digest(str.getBytes());
            return bytes2hex02(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ��ʽ��
     *
     * @param bytes
     * @return
     */
    public String bytes2hex02(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // ��ÿ���ֽ���0xFF���������㣬Ȼ��ת��Ϊ10���ƣ�Ȼ�������Integer��ת��Ϊ16����
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// ÿ���ֽ�8Ϊ��תΪ16���Ʊ�־��2��16����λ
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }

        return sb.toString();

    }

    private void refreashBitmap(final String path, final ImageView imageView,
                                Bitmap bm) {
        Message message = Message.obtain();
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * ��ͼƬ����LruCache
     *
     * @param path
     * @param bm
     */
    protected void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null) {
            if (bm != null)
                mLruCache.put(path, bm);
        }
    }

    /**
     * ����ͼƬ��Ҫ��ʾ�Ŀ�͸߶�ͼƬ����ѹ��
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    protected Bitmap decodeSampledBitmapFromPath(String path, int width,
                                                 int height) {
        // ���ͼƬ�Ŀ�͸ߣ�������ͼƬ���ص��ڴ���
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options,
                width, height);

        // ʹ�û�õ���InSampleSize�ٴν���ͼƬ
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        // if(mPoolThreadHandler==null)wait();
        try {
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e) {
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * ��û���ͼƬ�ĵ�ַ
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * ����path�ڻ����л�ȡbitmap
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }
}
