package com.ellison.library.utils.secret;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Ellison
 * @date 2017/10/10
 */

public class SecretGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

  /*  private final Gson gson;
    private final TypeAdapter<T> adapter;
    private boolean mIsSecret;

    public SecretGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, boolean isSecret) {
        this.gson = gson;
        this.adapter = adapter;
        this.mIsSecret = isSecret;
    }*/

    /**
     * 获取到加密的内容进行解密
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
       /* JsonReader jsonReader = gson.newJsonReader(value.charStream());
        String response = value.string();
        L.e("retrofitResponse ======== ", response.toString());

        try {
            // 获取到加密后的总数据
            AllData allResponseData = gson.fromJson(response, AllData.class);
            String decrypt = AesOperator.getInstance().decrypt(allResponseData.getResultData().toString(), BuildConfig.SCRET, BuildConfig.IVS);
            allResponseData.setResultData(decrypt);
            String json = gson.toJson(allResponseData);
            return (T)gson.fromJson(json, BaseResponse.class);
            // 以下可以返回根据是否进行加密来控制解密后的数据
//            return mIsSecret
//                    ? adapter.fromJson(json)
//                    : adapter.read(jsonReader);
        } catch (Exception e) {
            L.e("数据获取错误 : " + e.toString());
        } finally {
            value.close();
        }*/
        return null;
    }
}
