package com.wiggins.zoominscrollview.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Description 文件工具类
 * @Author 一花一世界
 */
public class FileUtil {

    /**
     * @Description 获取缓存目录
     */
    public static String getCachePath(Context context) {
        String cachePath;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            cachePath = Environment.getExternalStorageDirectory().getPath() + File.separator + AppUtil.getAppName(context) + File.separator + "image";//获取sdCard的根目录
        } else {
            cachePath = context.getExternalCacheDir().getPath();//获取APP的Cache缓存目录
        }
        return cachePath;
    }
}
