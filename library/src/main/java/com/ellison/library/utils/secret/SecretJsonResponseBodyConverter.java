package com.ellison.library.utils.secret;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.ellison.library.BuildConfig;
import com.ellison.library.base.data.net.BaseResponse;
import com.ellison.library.logger.L;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Ellison
 * @date 2017/12/7
 * @desc Response
 */
@SuppressWarnings("unchecked")
public class SecretJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];

    private Type mType;

    private ParserConfig config;
    private int featureValues;
    private Feature[] features;

    SecretJsonResponseBodyConverter(Type type, ParserConfig config, int featureValues,
                                    Feature... features) {
        mType = type;
        this.config = config;
        this.featureValues = featureValues;
        this.features = features;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String copyValue = value.string();
//        ResponseBody copyValue = value;
        try {
            // 获取到加密后的总数据
            BaseResponse allResponseData = JSON.parseObject(copyValue, BaseResponse.class);

            switch (allResponseData.getStatus()) {
                case BaseResponse.SUCCESS_RESPONSE:
                    try {
                        // 成功的结果
                        String decrypt = null;
                        decrypt = AesOperator.getInstance().decrypt(allResponseData.getData().toString(), BuildConfig.SCRET, BuildConfig.IVS);
                        if(!TextUtils.isEmpty(decrypt)) {
                            // 如果不为空则是加密的
                            L.json(decrypt);
                            allResponseData.setData(decrypt);
                            L.i(JSON.toJSONString(allResponseData));
                            // 解析成Object
                            return JSON.parseObject(allResponseData.toString(), mType, config, featureValues,
                                    features != null ? features : EMPTY_SERIALIZER_FEATURES);
                        } else {
                            // 如果为空则没有加密
                            // 解析成Object
                            return JSON.parseObject(copyValue, mType, config, featureValues,
                                    features != null ? features : EMPTY_SERIALIZER_FEATURES);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
//                case BaseResponse.RESULT_CODE_TOKEN_EXPIRED:
//                    // 账户在其他地方或者 token到期
//                case BaseResponse.RESULT_CODE_SIGN:
//                case BaseResponse.RESULT_CODE_UNKNOW:
//                    break;
                default:
            }
            return (T) allResponseData;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }
}