package com.ellison.library.utils.secret;


import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 *
 * @author Ellison
 * @date 2017/10/10
 */

public class SecretGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
/*

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final boolean mIsSecret;

    public SecretGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter, boolean isSecret) {
        this.gson = gson;
        this.adapter = adapter;
        this.mIsSecret = isSecret;
    }
*/


    /**
     * 获取到请求体中的数据进行加密
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public RequestBody convert(T value) throws IOException {
       /* Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        try {
            // 通过是否加密来选择
            return mIsSecret
                    ? RequestBody.create(MEDIA_TYPE, AesOperator.getInstance().encrypt(buffer.readByteString().toString(), BuildConfig.SCRET, BuildConfig.IVS))
                    : RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
