package com.ellison.library.utils.common;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wzs
 * @date 2018/6/16
 * @desc 盘口数字与文字表达之间的转换
 */

public class HandicapUtils {
    private static HandicapUtils sHandicapUtils = new HandicapUtils();
    private  HashMap<String, String> mHandicapNumberStringMap;

    public static HandicapUtils getInstance() {
        return sHandicapUtils;
    }

    public HandicapUtils init() {
        mHandicapNumberStringMap = new HashMap<String, String>();
        mHandicapNumberStringMap.put("*5", "受让五球");
        mHandicapNumberStringMap.put("*4.5/5", "受让四球半/五");
        mHandicapNumberStringMap.put("*4.5", "受让四球半");
        mHandicapNumberStringMap.put("*4/4.5", "受让四/四球半");
        mHandicapNumberStringMap.put("*4", "受让四球");
        mHandicapNumberStringMap.put("*3.5/4", "受让三球半/四");
        mHandicapNumberStringMap.put("*3.5", "受让三球半");
        mHandicapNumberStringMap.put("*3/3.5", "受让三/三球半");
        mHandicapNumberStringMap.put("*3", "受让三球");
        mHandicapNumberStringMap.put("*2.5/3", "受让两球半/三");
        mHandicapNumberStringMap.put("*2.5", "受让两球半");
        mHandicapNumberStringMap.put("*2/2.5", "受让两/两球半");
        mHandicapNumberStringMap.put("*2", "受让两球");
        mHandicapNumberStringMap.put("*1.5/2", "受让球半/两");
        mHandicapNumberStringMap.put("*1.5", "受让球半");
        mHandicapNumberStringMap.put("*1/1.5", "受让一/球半");
        mHandicapNumberStringMap.put("*1", "受让一球");
        mHandicapNumberStringMap.put("*0.5/1", "受让半/一");
        mHandicapNumberStringMap.put("*0.5", "受让半球");
        mHandicapNumberStringMap.put("*0/0.5", "受让平/半");

        mHandicapNumberStringMap.put("0", "平手");

        mHandicapNumberStringMap.put("0/0.5", "让平/半");
        mHandicapNumberStringMap.put("0.5", "让半球");
        mHandicapNumberStringMap.put("0.5/1", "让半/一");
        mHandicapNumberStringMap.put("1", "让一球");
        mHandicapNumberStringMap.put("1/1.5", "让一/球半");
        mHandicapNumberStringMap.put("1.5", "让球半");
        mHandicapNumberStringMap.put("1.5/2", "让球半/两");
        mHandicapNumberStringMap.put("2", "让两球");
        mHandicapNumberStringMap.put("2/2.5", "让两/两球半");
        mHandicapNumberStringMap.put("2.5", "让两球半");
        mHandicapNumberStringMap.put("2.5/3", "让两球半/三");
        mHandicapNumberStringMap.put("3", "让三球");
        mHandicapNumberStringMap.put("3/3.5", "让三/三球半");
        mHandicapNumberStringMap.put("3.5", "让三球半");
        mHandicapNumberStringMap.put("3.5/4", "让三球半/四");
        mHandicapNumberStringMap.put("4", "让四球");
        mHandicapNumberStringMap.put("4/4.5", "让四/四球半");
        mHandicapNumberStringMap.put("4.5", "让四球半");
        mHandicapNumberStringMap.put("4.5/5", "让四球半/五");
        mHandicapNumberStringMap.put("5", "让五球");
        return this;
    }


    /**
     * 通过数字型盘口获取文字型的盘口
     *
     * @param handicapNumber 数字型盘口
     * @return
     */
    public  String getStringByHandicapNumber(String handicapNumber) {
        String s = mHandicapNumberStringMap.get(handicapNumber);
        if (TextUtils.isEmpty(s)){
            return "";
        }
        return s;
    }

    /**
     * 通过文字型盘口获取数字型盘口
     * @return
     */
    public String getHandicapNumberByString(String handicapString){
        for (Map.Entry<String, String> entry : mHandicapNumberStringMap.entrySet()) {
            String value = entry.getValue();
            if (value.equals(handicapString)){
                String key = entry.getKey();
                return key;
            }
        }
        return "";
    }
}
