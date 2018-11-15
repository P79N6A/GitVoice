package com.ellison.library.utils.secret;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 *
 * @author Ellison
 */
public class AesOperator {

    private int secretKeyLength = 16;


    /**
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     *
     * //key，可自行修改
     */
    private String sKey = "smkldospdosldaaa";
    /**
     * //偏移量,可自行修改
     */
    private String ivParameter = "0392039203920300";
    private static AesOperator instance = null;

    private AesOperator() {

    }

    /**
     * 获取对象实例
     * @return 获取到Operator
     */
    public static AesOperator getInstance() {
        if (instance == null) {
            instance = new AesOperator();
        }
        return instance;
    }


    public String encrypt(String encData, String secretKey, String vector) throws Exception {

        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != secretKeyLength) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        // 此处使用BASE64做转码。
        return new BASE64Encoder().encode(encrypted);
    }

    // 加密
    public String encrypt(String sSrc) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        // 此处使用BASE64做转码。
        return new BASE64Encoder().encode(encrypted);
    }

    /**
     * 解密
     */
    public String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public String decrypt(String sSrc, String key, String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }


    public static void main(String[] args) throws Exception {

        // 需要加密的字串
        String cSrc = "[{\"request_no\":\"1001\",\"service_code\":\"FS0001\",\"contract_id\":\"100002\",\"order_id\":\"0\",\"phone_id\":\"13913996922\",\"plat_offer_id\":\"100094\",\"channel_id\":\"1\",\"activity_id\":\"100045\"}]";

        String enString = AesOperator.getInstance().encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString);


        String deString = AesOperator.getInstance().decrypt(enString);
        System.out.println("解密后的字串是：" + deString);


        // 测试加密后的数据
//        String testStr = "wdNUZHRKKRBAQ6BM07k+nTA2Iv+ySo4Ahl62RTUmbrzOZVyizOlJwQVbCiIC/pa5JbmAYFDeieg2 xbXhu2b78wCO89sNNUGaWFXKDn8kkIseC1V75o98eDSioFCqDkD2Dn+SZSNvI5hHZ0KzWNG16+aC zS4K1tF0XDb2wFpbw6pzu14EHXebHAcWWXFJlxtpDD5SoicTCri53Ga72B3g1khPYLXQnIJ9Z1Zp dCN3Spk=";
//        System.out.println("解密后 ： " + AesOperator.getInstance().decrypt(testStr));
    }

}

































