package com.ellison.library.utils.secret;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ellison.library.logger.L;
import com.ellison.library.utils.common.SHA1Utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Ellison
 * @date 2017/11/6
 * @desc 对参数进行加密
 */

public class SignNetUtils {

    /**
     * 在Post的时候，给Field字段占位
     */
    public final static String EMPTY_HOLDER = "empty_holder";

    public static RequestBody getRequestBody(Class clazz, Map<String, String> params) {
        // 先将参数排序，然后再添加到Map中
        String sign = getSignParams(params);
        try {
            Object obj = clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                // 设置签名
                if ("sign".equals(field.getName())) {
                    field.set(obj, sign);
                    continue;
                }

                for (Map.Entry<String, String> next : params.entrySet()) {
                    String param = next.getKey();

                    // 设置每个字段对应的值
                    if (param.equals(field.getName())) {
                        // 将每个值都设置进去
                        field.set(obj, next.getValue());
                        break;
                    }
                }
            }
            String postJson = JSON.toJSONString(obj);
            JSONObject jsonObject = JSONObject.parseObject(postJson);
            // 去掉value为空的键值对
            Iterator<Map.Entry<String, Object>> it = jsonObject.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> temp = it.next();
                if (temp.getValue().toString().length() < 1) {
                    it.remove();
                }
            }

            L.json(jsonObject.toJSONString());
            return RequestBody.create(MediaType.parse("application/json"), jsonObject.toJSONString());

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名
     *
     * @param params
     * @return
     */
    private static String getSignParams(Map<String, String> params) {
//        Map<String, String> collectParams = new HashMap<>(20);
//        try {
//            Field[] fields = clazz.getDeclaredFields();
//            for (Field field : fields) {
//                field.setAccessible(true);
//                if(!"sign".equals(field.getName())) {
//                    collectParams.put(field.getName(), params.get(field.getName()));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Map<String, String> newMap = new TreeMap<>(params);
        StringBuilder newParams = new StringBuilder();

        Set<String> keySet = newMap.keySet();

        for (String key : keySet) {
            if ("img".equals(key)) {
                continue;
            }
            String value = newMap.get(key);
            if (!"".equals(value)) {
                newParams.append(value);
                newParams.append("#$");
            }
        }
        newParams.insert(0, "@#qiubi$prophet@#");
        L.d("签名为：", newParams.toString());
        return SHA1Utils.SHA1(newParams.toString());
    }

    /**
     * TODO 将字段赋值  含内部类的时候
     *
     * @param clazz
     * @param params
     * @throws Exception
     */
    private static void putValueToFiled(Class clazz, Map<String, String> params) throws IllegalAccessException, InstantiationException {
        Object obj = clazz.newInstance();
        // 给字段赋值
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 设置签名
            if ("sign".equals(field.getName())) {
                field.set(obj, "");
                continue;
            }

            for (Map.Entry<String, String> next : params.entrySet()) {
                String param = next.getKey();

                // 设置每个字段对应的值
                if (param.equals(field.getName())) {
                    // 将每个值都设置进去
                    field.set(obj, next.getValue());
                    break;
                }
            }
        }
    }
}
