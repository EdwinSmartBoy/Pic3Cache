package com.example.edwin.pic3cache.Pic3CacheUtils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 创建者      Created by Edwin
 * 创建时间    2016/05/12
 * 描述        内存缓存工具类 将从网络获取到的图片数据保存内存中
 * <p/>
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
public class MemoryCacheUtils {

    //当前为强引用,为了防止出现内存溢出的问题,使用如下的弱引用,当系统的内存吃紧的时候,可以将弱引用的对象进行回收,防止发生OOM的问题
    //private HashMap<String, Bitmap> mMemoryCache = new HashMap<>();
    //当前为弱引用,android2.3以后,系统会优先考虑回收弱引用对象,所以官方推荐使用LruCache
    //private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        //设置缓存的大小  得到当前手机允许的最大内存的1/8,当超过
        long mMemoryCacheSize = Runtime.getRuntime().maxMemory() / 8;
        //需要传入允许的内存最大值,虚拟机默认内存16M
        mMemoryCache = new LruCache<String, Bitmap>((int) mMemoryCacheSize) {
            //用于计算每个条目的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //获取到图片的大小
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    /**
     * @param url
     * @return
     * @desc 通过图片的url获取图片数据
     */
    public Bitmap getBitmapFromMemory(String url) {
        //强引用的方法
        //Bitmap bitmap = mMemoryCache.get(url);
        //弱引用的方法
        /*
        SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
        if (bitmapSoftReference != null) {
            Bitmap bitmap = bitmapSoftReference.get();
            return bitmap;
        }*/
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;
    }

    /**
     * @param url
     * @param bitmap
     * @desc 将图片数据保存到内存中
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        //强引用的方法
        //mMemoryCache.put(url, bitmap);
        //弱引用的方法
        //mMemoryCache.put(url, new SoftReference<>(bitmap));
        //将图片数据保存到本地
        mMemoryCache.put(url, bitmap);
    }
}
