package com.ellison.library.utils.common;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wzs
 * @date 2018/7/13
 * @desc 币种类型的工具类：
 * 功能：币种编码与中文的转换
 */

public class CoinTypeUtils {
    private static CoinTypeUtils mInstance;
    private static HashMap<String, String> mStringHashMap = new HashMap<>();

    /**
     * key：币种代码  value：币种名称
     */
    private CoinTypeUtils() {
        mStringHashMap.put("ETH", "ETH(以太坊)");
        mStringHashMap.put("OF", "OF(福币)");
        mStringHashMap.put("PAG", "PAG(天弈)");
    }


    public static CoinTypeUtils getInstance() {
        if (mInstance == null) {
            synchronized (CoinTypeUtils.class) {
                if (mInstance == null) {
                    mInstance = new CoinTypeUtils();
                }
            }
        }
        return mInstance;
    }


    /**
     * 通过币种代码获取币种名称
     *
     * @param coinCode 币种代码
     * @return 币种名称
     */
    public String getNameByCode(String coinCode) {
        String coinName = mStringHashMap.get(coinCode);
        if (TextUtils.isEmpty(coinName)) {
            return "";
        }
        return coinName;
    }

    /**
     * 通过币种名称获取币种代码
     *
     * @param coinName 币种名称
     * @return 币种代码
     */
    public String getCodeByName(String coinName) {
        String coinCode="";
        Set<Map.Entry<String, String>> set = mStringHashMap.entrySet();
        for(Map.Entry<String, String> entry : set){
            if(entry.getValue().equals(coinName)){
                coinCode = entry.getKey();
                break;
            }
        }
        return coinCode;
    }
}
