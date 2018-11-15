package com.ellison.library.utils.common;

import android.text.TextUtils;
import android.util.Base64;

import com.ellison.library.Constants;
import com.ellison.library.application.DefaultApplication;
import com.ellison.library.base.data.net.bean.response.home.HomeConfigInfo;
import com.ellison.library.utils.fileutil.SharedPreferencesUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Ellison
 * @date 2017/12/19
 * @desc 用一句话描述这个类的作用
 */

public class SharedUtils {

    private static SharedUtils sharedUtils = new SharedUtils();

    public static SharedUtils getInstance() {
        return sharedUtils;
    }

    /**
     * 设置是否有好评
     *
     * @param isGuidetFavourableCommend
     */
    public void setIsGuidetFavourableCommend(boolean isGuidetFavourableCommend) {
        putObj(Constants.FAVOURABLE_COMMEND, isGuidetFavourableCommend);
    }

    /**
     * 获取到是否有好评
     *
     * @return
     */
    public boolean getIsGuidetFavourableCommend() {
        try {
            return (boolean) getObj(Constants.FAVOURABLE_COMMEND);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置是否是vip
     *
     * @param isVip
     */
    public void setIsVip(boolean isVip) {
        putObj(Constants.VOICE_IS_VIP, isVip);
    }

    /**
     * 获取到是否是vip
     *
     * @return
     */
    public boolean getIsVip() {
        try {
            return (boolean) getObj(Constants.VOICE_IS_VIP);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 保存闪屏页面图片路径
     *
     * @param fileNamePath
     */
    public void setSplashImagePath(String fileNamePath) {
        putObj(Constants.PROJECT_SPLASH_IMAGE, fileNamePath);
    }

    /**
     * 获取到闪屏页面路径
     *
     * @return
     */
    public String getSplashImagePath() {
        try {
            return (String) getObj(Constants.PROJECT_SPLASH_IMAGE);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 保存闪屏页面图片路径
     *
     * @param splashImageUrl
     */
    public void setSplashImageUrl(String splashImageUrl) {
        putObj(Constants.PROJECT_SPLASH_URL, splashImageUrl);
    }

    /**
     * 获取到闪屏页面路径
     *
     * @return
     */
    public String getSplashImageUrl() {
        try {
            return (String) getObj(Constants.PROJECT_SPLASH_URL);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 设置app配置信息
     *
     * @param data
     */
    public void setConfigInfo(HomeConfigInfo data) {
        putObj(Constants.PROJECT_CONFIG_INFO, data);
    }

    /**
     * 获取到配置信息
     *
     * @return
     */
    public HomeConfigInfo getConfigInfo() {
        try {
            return (HomeConfigInfo) getObj(Constants.PROJECT_CONFIG_INFO);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * key = key+userid
     * 将对象进行base64编码后保存到SharePref中
     *
     * @param key
     * @param object 必须Serializable化
     */
    public void putObj(String key, Object object) {
        putObj(key, object, true);
    }

    public void putObj(String key, Object object, boolean isAddUserId) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            // 将对象的转为base64码
            String objBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));

            SharedPreferencesUtils.getInstance(DefaultApplication.getApplication()).put(key, objBase64);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将SharePref中经过base64编码的对象读取出来
     *
     * @param key
     * @return
     */
    public Object getObj(String key) {
        return getObj(key, true);
    }

    /**
     * 将SharePref中经过base64编码的对象读取出来
     *
     * @param key
     * @return
     */
    public Object getObj(String key, boolean isAddUserId) {
        String objBase64 = SharedPreferencesUtils.getInstance(DefaultApplication.getApplication()).getString(key, null);
        if (TextUtils.isEmpty(objBase64)) {
            return null;
        }

        // 对Base64格式的字符串进行解码
        byte[] base64Bytes = Base64.decode(objBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

        ObjectInputStream ois;
        Object obj = null;
        try {
            ois = new ObjectInputStream(bais);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (obj == null || TextUtils.isEmpty(obj.toString())) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
}



































