package com.ellison.library.utils.secret;


import retrofit2.Converter;

/**
 *
 * @author Ellison
 * @date 2017/10/10
 * <p>
 * 数据加密解密转发
 */

public class SecretGsonConverterFactory extends Converter.Factory {

    /*private String nullStr = "gson == null";

    private final JSON gson;

    private final boolean isSecret;

    private SecretGsonConverterFactory(Gson gson, boolean isSecret) {
        if (gson == null) {
            throw new NullPointerException(nullStr);
        }
        this.gson = gson;
        this.isSecret = isSecret;
    }

    *//**
     * Create an instance using a default {@link com.google.gson.Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     *//*
    public static SecretGsonConverterFactory create(boolean isSecret) {
        return create(new Gson(), isSecret);
    }

    *//**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     *//*
    public static SecretGsonConverterFactory create(Gson gson, boolean isSecret) {
        return new SecretGsonConverterFactory(gson, isSecret);
    }


    *//**
     * 获取到服务器响应内容进行解密
     *
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     *//*
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new SecretGsonResponseBodyConverter<>(gson, adapter, isSecret);
    }

    *//**
     * 获取到请求体中的数据进行加密
     *
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     *//*
    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new SecretGsonRequestBodyConverter<>(gson, adapter, isSecret);
    }

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }*/
}
