package com.example.edwin.pic3cache.Pic3CacheUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 创建者      Created by Edwin
 * 创建时间    2016/05/12
 * 描述        网络缓存工具类  使用AsyncTask进行异步网络数据的加载
 * <p/>
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
public class NetCacheUtils {

    private static final String TAG = "MainActivity";
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.mLocalCacheUtils = localCacheUtils;
        this.mMemoryCacheUtils = memoryCacheUtils;
    }


    /**
     * @param imageView
     * @param url
     * @desc 从网络获取图片数据 使用AsyncTask进行网络加载数据的封装
     */
    public void getBitmapFromNet(ImageView imageView, String url) {
        Log.d(TAG, "执行从网络加载图片数据的getBitmapFromNet方法");
        new MyBitmapTask().execute(imageView, url);
    }

    /**
     * @desc 网络加载的封装
     * @desc AsyncTask就是对Handler与线程池的封装
     */
    class MyBitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView mImageVIew;
        private String url;

        /**
         * @desc 进行网络加载的初始化 运行在主线程
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * @param params
         * @return
         * @desc 真正在子线程执行任务
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            //获取图片的对象
            mImageVIew = (ImageView) params[0];
            //获取网络访问的url
            url = (String) params[1];
            return downLoadBitmap(url);
        }

        /**
         * @param values
         * @desc 状态栏或进度条的更新 运行在主线程
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * @param bitmap
         * @desc 任务执行完成时调用该方法  运行在主线程
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "网络图片数据加载成功,让其展示在界面中");
            //将获取到的图片数据显示在界面上
            mImageVIew.setImageBitmap(bitmap);

            // TODO: 2016/05/12 将获取到的数据保存到内存中
            Log.d(TAG, "将从网络获取到的数据保存到本地文件中");
            mLocalCacheUtils.setBitmapToLocal(url, bitmap);
            // TODO: 2016/05/12 将获取到的数据保存到本地
            Log.d(TAG, "将从网络获取到的数据保存到内存中");
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);

            super.onPostExecute(bitmap);
        }
    }

    /**
     * @param path 获取图片的url
     * @return 返回获取到的图片对象
     * @desc 从网络拉取图片数据
     */
    private Bitmap downLoadBitmap(String path) {
        Log.d(TAG, "真正执行从网络获取图片数据的方法");
        HttpURLConnection conn = null;
        //使用HttpURLConnection来进行网络图片的获取
        if (path != null && path.length() > 0) {
            try {
                URL url = new URL(path);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    //设置图片的压缩
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //将图片的宽高压缩为原来的一半
                    options.inSampleSize = 2;
                    //设置图片压缩的格式
                    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    //将图片进行压缩编码
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                    //将图片的返回
                    return bitmap;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //断开网络连接
                conn.disconnect();
            }
        }
        return null;
    }
}
