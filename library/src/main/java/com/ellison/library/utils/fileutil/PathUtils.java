package com.ellison.library.utils.fileutil;

import android.content.Context;

import com.ellison.library.provider.cache.CacheConfig;

import java.io.File;

/**
 * Created by Ellison.Sun on 2017/3/16.
 */

public class PathUtils {


    /**
     * 设置图片缓存的路径
     * @param context
     * @param config
     * @return
     */
    public static File getDiskCacheDir(Context context, int config) {
        File file = null;
        if(config!=-1 && config== CacheConfig.IMG_DIR) {
            file = context.getExternalCacheDir();
        }
        return file;
    }
}
