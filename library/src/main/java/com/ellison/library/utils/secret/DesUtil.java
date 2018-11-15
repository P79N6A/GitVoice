package com.ellison.library.utils.secret;

import android.util.Base64;

import com.ellison.library.base.data.net.bean.response.home.HomeConfigInfo;
import com.ellison.library.utils.common.SharedUtils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * DES加密工具类
 *
 * @author CHENCHAO
 * @date 2018/11/8 15:30
 */
public class DesUtil {

    /**
     * 加解密统一使用的编码方式
     */
    private final static String ENCODING = "UTF-8";

    /**
     * 加密数据
     *
     * @param encryptString
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString) throws Exception {

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKey(), "DES"));
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes(ENCODING));
        return String.valueOf(Base64.encode(encryptedData, Base64.DEFAULT));
    }



    /***
     * 解密数据
     *
     * @param decryptString
     * @return
     * @throws Exception
     */

    public static String decryptDES(String decryptString) throws Exception {

        byte[] sourceBytes = Base64.decode(decryptString, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKey(), "DES"));
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, "UTF-8");

    }


    /**
     * key 不足8位补位
     *
     */
    public static byte[] getKey() {
        Key key = null;
        HomeConfigInfo configInfo = SharedUtils.getInstance().getConfigInfo();
        String a ="";
        if(configInfo!=null) {
            a = configInfo.getKey();
        }
        byte[] keyByte = a.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
        return key.getEncoded();
    }


}
