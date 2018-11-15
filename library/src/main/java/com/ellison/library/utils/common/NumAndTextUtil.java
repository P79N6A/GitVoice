package com.ellison.library.utils.common;

import java.util.HashMap;

/**
 * @author wzs
 * @date 2018/6/8
 * @desc 阿拉伯数字与中文数字的转换
 */

public class NumAndTextUtil {

    private static NumAndTextUtil sNumAndTextUtil = new NumAndTextUtil();
    private HashMap<String, String> mNumAndTextMap;

    public static NumAndTextUtil getInstance() {
        return sNumAndTextUtil;
    }

    public NumAndTextUtil init() {
       mNumAndTextMap = new HashMap<String, String>(30);

       mNumAndTextMap.put("1", "一");
       mNumAndTextMap.put("2", "二");
       mNumAndTextMap.put("3", "三");
       mNumAndTextMap.put("4", "四");
       mNumAndTextMap.put("5", "五");
       mNumAndTextMap.put("6", "六");
       mNumAndTextMap.put("7", "七");
       mNumAndTextMap.put("8", "八");
       mNumAndTextMap.put("9", "九");
       mNumAndTextMap.put("10", "十");
       mNumAndTextMap.put("11", "十一");
       mNumAndTextMap.put("12", "十二");
       mNumAndTextMap.put("13", "十三");
       mNumAndTextMap.put("14", "十四");
       mNumAndTextMap.put("15", "十五");
       mNumAndTextMap.put("16", "十六");
       mNumAndTextMap.put("17", "十七");
       mNumAndTextMap.put("18", "十八");
       mNumAndTextMap.put("19", "十九");
       mNumAndTextMap.put("20", "二十");
       mNumAndTextMap.put("21", "二十一");
       mNumAndTextMap.put("22", "二十二");
       mNumAndTextMap.put("23", "二十三");
       mNumAndTextMap.put("24", "二十四");
       mNumAndTextMap.put("25", "二十五");
       mNumAndTextMap.put("26", "二十六");
       mNumAndTextMap.put("27", "二十七");
       mNumAndTextMap.put("28", "二十八");
       mNumAndTextMap.put("29", "二十九");
       mNumAndTextMap.put("30", "三十");

        return this;
    }

    public String getTextByNum(String num) {
        return mNumAndTextMap.get(num);
    }



}
