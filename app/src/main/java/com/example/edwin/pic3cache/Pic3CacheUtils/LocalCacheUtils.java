package com.example.edwin.pic3cache.Pic3CacheUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 创建者      Created by Edwin
 * 创建时间    2016/05/12
 * 描述        本地缓存工具类 初次访问网络时,将图片数据保存到本地  使用MD5加密图片的网络地址来作为图片数据的=名称
 * <p/>
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
public class LocalCacheUtils {

    //保存网络图片数据的路径
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WerbNews";

    /**
     * @param url
     * @desc 从本地获取缓存中获取图片数据
     */
    public Bitmap getBitmapFromLocal(String url) {

        try {
            //将图片数据的url进行MD5的加密,将其当作图片文件的文件名
            String fileName = Md5Util.encodeStr(url);
            //指定图片路径
            File file = new File(CACHE_PATH, fileName);

            //从本地获取图片数据
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            //将图片数据返回
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @param bitmap
     * @desc 将图片数据保存到本地
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        //将图片数据的url进行MD5的加密,将其当作图片文件的文件名
        String fileName = Md5Util.encodeStr(url);
        //指定图片路径
        File file = new File(CACHE_PATH, fileName);

        //获取到当前文件的父文件  并判断当前文件的父文件是否存在
        File parentFile = file.getParentFile();
        //如果当前文件的父文件不存在  创建文件夹
        if(!parentFile.exists()){
            parentFile.mkdir();
        }

        try {
            //将图片数据保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
