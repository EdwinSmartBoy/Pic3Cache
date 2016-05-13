package com.example.edwin.pic3cache;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.edwin.pic3cache.Pic3CacheUtils.LocalCacheUtils;
import com.example.edwin.pic3cache.Pic3CacheUtils.MemoryCacheUtils;
import com.example.edwin.pic3cache.Pic3CacheUtils.NetCacheUtils;

import static com.example.edwin.pic3cache.R.id.iv_image;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView mIv;

    private static final String ImageViewUrl = "http://188.188.4.35:8080/1.jpg";

    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = (ImageView) findViewById(iv_image);

        mLocalCacheUtils = new LocalCacheUtils();

        mMemoryCacheUtils = new MemoryCacheUtils();

        Log.d(TAG, "开始从内存加载图片数据");
        Bitmap bitmapFromMemory = mMemoryCacheUtils.getBitmapFromMemory(ImageViewUrl);
        //优先从本地加载图片数据
        if (bitmapFromMemory != null) {
            mIv.setImageBitmap(bitmapFromMemory);
            Log.d(TAG, "从内存加载图片数据成功");
            return;
        }

        Log.d(TAG, "内存没有图片数据,从本地加载图片数据");
        Bitmap bitmapFromLocal = mLocalCacheUtils.getBitmapFromLocal(ImageViewUrl);
        if (bitmapFromLocal != null) {
            mIv.setImageBitmap(bitmapFromLocal);
            Log.d(TAG, "从本地加载图片数据成功");
            //将图片数据保存到本地
            mMemoryCacheUtils.setBitmapToMemory(ImageViewUrl, bitmapFromLocal);
            Log.d(TAG, "将图片数据保存到内存成功");
            return;
        }
        Log.d(TAG, "本地和内存都没有数据,从网络加载数据");
        //当从内存和本地都找不到图片数据时,从网络获取图片数据
        NetCacheUtils netCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
        Log.d(TAG, "开始从网络加载图片数据");
        netCacheUtils.getBitmapFromNet(mIv, ImageViewUrl);
    }


}
