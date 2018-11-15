package com.ellison.library.net.provider;

/**
 * @author Ellison.Sun
 * @date 2016/8/16
 */
public class NetConstant {

    /**
     * 内网测试，开发环境
     */
    public static final int LOCAL = 0;
    /**
     * 外网测试环境，开发环境
     */
    public static final int STAG = 1;
    /**
     * 正式环境
     */
    public static final int PROD = 2;
    /**
     * 测试组环境
     */
    public static final int DEMO = 3;
    /**
     * 当前服务地址
     */
    public static String CURRENT_SERVICE = "";
    /**
     * 当前图片服务地址
     */
    public static String CURRENT_IMG_SERVICE = "";
    /**
     * 当前推送的服务地址
     */
    public static String CURRENT_PUSH_SERVICE = "";

    /**
     * stag环境
     */
    public static final String STAG_SERVICE = "";
    public static final String IMG_STAG_SERVICE = "";
    /**
     * 产品环境
     */
    public static final String PROD_SERVICE = "http://admin.tanmojiaoyou.com/";
    public static final String DIG_SERVICE = "https://prophet.api.iuliao.com/";
    public static final String IMG_PROD_SERVICE = "";
    /**
     * demo环境
     */
    public static final String DEMO_SERVICE = "";
    public static final String IMG_DEMO_SERVICE = "";
    /**
     * 本地环境
     */
//    public static final String LOCAL_SERVICE = "http://192.168.0.108:8080/";
    public static final String LOCAL_SERVICE = "http://admin.tanmojiaoyou.com/";
    public static final String IMG_LOCAL_SERVICE = "";

    /**
     * socket地址
     */
    public static final String SOCKET_URL = "http://47.92.114.44:30302";

    /**
     * @param @param flag 设定文件
     * @return void 返回类型
     * @throws
     * @Title: initService
     * @Description: (设置服务器)
     */
    public static void initService(int flag) {
        if (flag == LOCAL) {
            // 本地
            CURRENT_SERVICE = LOCAL_SERVICE;
            CURRENT_IMG_SERVICE = IMG_PROD_SERVICE;
        } else if (flag == STAG) {
            // stag
            CURRENT_SERVICE = STAG_SERVICE;
            // 图片服务器混乱，都用正式环境
            CURRENT_IMG_SERVICE = IMG_STAG_SERVICE;
        } else if (flag == PROD) {
            // 产品
            CURRENT_SERVICE = PROD_SERVICE;
            CURRENT_IMG_SERVICE = IMG_PROD_SERVICE;
        } else if (flag == DEMO) {
            CURRENT_SERVICE = DEMO_SERVICE;
            CURRENT_IMG_SERVICE = IMG_DEMO_SERVICE;
        } else {
            CURRENT_SERVICE = LOCAL_SERVICE;
            CURRENT_IMG_SERVICE = LOCAL_SERVICE;
        }
    }

    public static String getCurrentService() {
        return CURRENT_SERVICE;
    }
}
