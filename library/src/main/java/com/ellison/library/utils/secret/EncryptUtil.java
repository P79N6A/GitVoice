package com.ellison.library.utils.secret;

import com.ellison.library.BuildConfig;
import com.tencent.smtt.sdk.WebView;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author Ellison
 * @date 2018/10/24
 * @desc 用一句话描述这个类的作用
 */
public class EncryptUtil {

    private static final String CipherMode = "AES/CBC/PKCS5Padding";

    public static String byte2hex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & WebView.NORMAL_MODE_ALPHA);
            if (toHexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(toHexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    private static IvParameterSpec createIV(String str) {
        byte[] bytes;
        if (str == null) {
            str = BuildConfig.VERSION_NAME;
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(16);
        }
        try {
            bytes = stringBuffer.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = null;
        }
        return new IvParameterSpec(bytes);
    }

    private static SecretKeySpec createKey(String str) {
        byte[] bytes;
        if (str == null) {
            str = BuildConfig.VERSION_NAME;
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(16);
        }
        try {
            bytes = stringBuffer.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = null;
        }
        return new SecretKeySpec(bytes, "AES");
    }

    public static String decrypt(String str, String str2, String str3) {
        byte[] hex2byte;
        try {
            hex2byte = hex2byte(str);
        } catch (Exception e) {
            e.printStackTrace();
            hex2byte = null;
        }
        byte[] decrypt = decrypt(hex2byte, str2, str3);
        if (decrypt == null) {
            return str;
        }
        try {
            str3 = new String(decrypt, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            str3 = null;
        }
        return str3 == null ? str : str3;
    }

    public static byte[] decrypt(byte[] bArr, String str, String str2) {
        try {
            Key createKey = createKey(str);
            Cipher instance = Cipher.getInstance(CipherMode);
            instance.init(2, createKey, createIV(str2));
            return instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String str, String str2, String str3) {
        byte[] bytes;
        try {
            bytes = str.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            bytes = null;
        }
        bytes = encrypt(bytes, str2, str3);
        return bytes == null ? "QWE54T54ZCXCV12FGJ154M5BGHJ1YU7LEIPI" : byte2hex(bytes);
    }

    public static byte[] encrypt(byte[] bArr, String str, String str2) {
        try {
            Key createKey = createKey(str);
            Cipher instance = Cipher.getInstance(CipherMode);
            instance.init(1, createKey, createIV(str2));
            return instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] hex2byte(String str) {
        int i = 0;
        if (str != null) {
            if (str.length() >= 2) {
                str = str.toLowerCase();
                int length = str.length() / 2;
                byte[] bArr = new byte[length];
                while (i < length) {
                    int i2 = i * 2;
                    bArr[i] = (byte) (Integer.parseInt(str.substring(i2, i2 + 2), 16) & WebView.NORMAL_MODE_ALPHA);
                    i++;
                }
                return bArr;
            }
        }
        return new byte[0];
    }

}
