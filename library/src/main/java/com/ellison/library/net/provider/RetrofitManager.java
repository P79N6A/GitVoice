package com.ellison.library.net.provider;

import android.os.Build;
import android.util.Log;
import android.util.SparseArray;

import com.ellison.library.BuildConfig;
import com.ellison.library.application.DefaultApplication;
import com.ellison.library.logger.L;
import com.ellison.library.utils.common.CommonUtils;
import com.ellison.library.utils.common.JsonUtils;
import com.ellison.library.utils.common.Md5Utils;
import com.ut.device.UTDevice;

import java.net.URLDecoder;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Retrofit Manager
 * Created by Yoosir on 2016/10/24 0024.
 */
public class RetrofitManager {

    private static final int CONNECT_TIME_OUT = 60;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;

    private Retrofit mRetrofit;

    /*** 签名私密key  */
    private static String PIVATEKEY = "(iuliao_prophet#@!)";

    /**
     * SparseArray VS  HashMap
     */
    private static SparseArray<RetrofitManager> retrofitManagers = new SparseArray<>();

    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = retrofitManagers.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(hostType);
            retrofitManagers.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }

    private RetrofitManager(@HostType.HostTypeChecker int hostType) {

        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(getHost(hostType))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {

        // 实现拦截，添加通用的请求信息
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        // 添加打印信息
        httpclient.addNetworkInterceptor(logInterceptor);

        httpclient.addInterceptor(chain ->
                {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder();
                    L.i("请求体=url", URLDecoder.decode(original.url().toString()) + "");
                    // 获取到用户的session
//                    ConfigInfo.UserBean userBean = SharedUtils.getInstance().getUserInfo(DefaultApplication.getApplication());
//                    if (null != userBean) {
//                        requestBuilder.addHeader(CommonUtils.TOKEN, userBean.getToken());
//                    }
                    requestBuilder.addHeader("Content-Type", "application/json;charset=utf-8");
                    //添加请求头
                    //            1.appVersion 		: APP版本号
                    requestBuilder.addHeader(CommonUtils.CLIENT_VERSION, BuildConfig.VERSION_NAME);
                    //            2.platform		：平台 1.安卓 2.iOS
                    requestBuilder.addHeader(CommonUtils.MACHINE_TYPE, BuildConfig.PLATFORM);
                    //            4.appName		：APP名称
//                    requestBuilder.addHeader(CommonUtils.APP_NAME, "天弈");
                    //            3.OSversion		：手机系统版本
                    requestBuilder.addHeader(CommonUtils.OSVERSION, Build.VERSION.RELEASE);
                    //            5.deviceName		：设备名称
                    requestBuilder.addHeader(CommonUtils.DEVICE_NAME, Build.MODEL);
                    //            6.UDID			: 设备唯一标示
                    requestBuilder.addHeader(CommonUtils.UDID, UTDevice.getUtdid(DefaultApplication.getApplication()));
                    // 7、设备名称
                    requestBuilder.addHeader(CommonUtils.PHONE_MODE, Build.BRAND);
                    // 8、设备主机地址
                    requestBuilder.addHeader(CommonUtils.HOST, Build.HOST);

                    requestBuilder.addHeader(CommonUtils.A, Md5Utils.md5("tnt505123456"));
                    requestBuilder.addHeader(CommonUtils.B, "123456");
                    requestBuilder.addHeader(CommonUtils.TIME, String.valueOf(System.currentTimeMillis()));
                    requestBuilder.addHeader(CommonUtils.PROJECT_NAME, BuildConfig.PROJECT_NAME);

                    L.d("请求头为：", CommonUtils.CLIENT_VERSION + "=" + BuildConfig.VERSION_NAME + "\n" +
                            CommonUtils.MACHINE_TYPE + "=" + BuildConfig.PLATFORM + "\n" +
                            CommonUtils.APP_NAME + "=" + valueOf(DefaultApplication.getApplication().getApplicationInfo().labelRes) + "\n" +
                            CommonUtils.OSVERSION + "=" + Build.VERSION.RELEASE + "\n" +
                            CommonUtils.DEVICE_NAME + "=" + Build.MODEL + "\n" +
                            CommonUtils.UDID + "=" + Build.FINGERPRINT + "\n" +
                            CommonUtils.PHONE_MODE + "=" + Build.BRAND + "\n" +
                            CommonUtils.HOST + "=" + Build.HOST + "\n"
                    );

                    return chain.proceed(requestBuilder.build());
                }
        ).connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        httpclient.addInterceptor(chain -> {
            // 设置用户的Cookie值
            Response originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<String>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                    Log.v("OkHttp", "save Header: " + header);
                }
            }
            return originalResponse;
        });
        return httpclient.build();
    }

    /**
     * 获取对应的 Url Host
     *
     * @param hostType host 类型
     * @return host
     */

    private String getHost(@HostType.HostTypeChecker int hostType) {
        switch (hostType) {
            case HostType.COMMON_URL:
                return NetConstant.CURRENT_SERVICE;
            case HostType.IuLiao:
                return NetConstant.DIG_SERVICE;
            default:
                return "";
        }
    }

    /**
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     *
     * @param service 服务接口
     * @return T
     */
    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T createService(final Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 打印
     */
    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonUtils.formatJson(JsonUtils.decodeUnicode(message));
            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                if (mMessage.length() < 65536) {
                    L.d(mMessage.toString());
                }
            }
        }
    }
}
