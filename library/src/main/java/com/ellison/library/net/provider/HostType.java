package com.ellison.library.net.provider;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Url Host Type
 *
 * @version 1.0
 *          Created by Yoosir on 2016/11/10 0010.
 */
public class HostType {

    /**
     * 多少种 Host 类型
     */
    public static final int TYPE_COUNT = 5;

    /**
     * 正常的 Host
     */
    public static final int COMMON_URL = 1;

    /**
     * 挖矿 HOST
     */
    public static final int IuLiao = 2;

    /**
     * 替代枚举的方案，使用 IntDef保证类型安全
     */
    @IntDef({COMMON_URL, IuLiao})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }

}
