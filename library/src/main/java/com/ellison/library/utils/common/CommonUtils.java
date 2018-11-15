package com.ellison.library.utils.common;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ellison.library.R;
import com.ellison.library.logger.L;
import com.ellison.library.utils.fileutil.SharedPreferencesUtils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ellison
 * @date 2017/11/20
 * @desc 一些常用的方法工具类
 */

public class CommonUtils {
//            1.appVersion 		: APP版本号
//            2.platform		：平台 1.安卓 2.iOS
//            3.OSversion		：手机系统版本
//            4.appName		：APP名称
//            5.deviceName		：设备名称
//            6.UDID			: 设备唯一标示
    //        7.tokenId         :
    /**
     * 密码类型
     */
    public static int PASSWORD_TYPE = EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;
    /**
     * 数字类型
     */
    public static int NUMBER_TYPE = EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;
    /**
     * 短信验证码时间
     */
    public static final Long MAX_CODE_TIME = 60L;

    /**
     * 用户唯一码
     */
    public static String TOKENID = "tokenId";

    /**
     * 用户token
     */
    public static String TOKEN = "token";

    /**
     * session
     */
    public static String SESSIONID = "sessionId";
    /**
     * APP的版本号
     */
    public static String CLIENT_VERSION = "clientVersion";

    /**
     * 机器类型
     */
    public static String MACHINE_TYPE = "type";

    /**
     * 手机系统版本
     */
    public static String OSVERSION = "OSversion";

    /**
     * 设备名称
     */
    public static String DEVICE_NAME = "deviceName";

    /**
     * 极光推送ID
     */
    public static String REGISTRATION_ID = "registrationID";

    /**
     * 设备唯一标示
     */
    public static String UDID = "account";

    /**
     * APP名称
     */
    public static String APP_NAME = "appName";
    /**
     * 当前APP的语言
     */
    public static String LANGUAGE = "language";

    /**
     * 手机名称 SM-N9100 三星
     */
    public static String PHONE_MODE = "mode";

    /**
     * 设备主机地址
     */
    public static String HOST = "device_host";

    /**
     * 是否引导过
     */
    private static String GUIDE_STR = "app_qbit";

    /**
     * 是否引导过挖矿进入
     */
    private static String GUIDE_GID = "guide_dig";

    /**
     * 挖矿界面未领取收益转入到钱包
     */
    private static String TURN_TO_WALLET = "turn_to_wallet";
    /**
     * 用户CID
     */
    public static String USER_CID = "cid";

    /**
     * 是否进入到了欢迎界面
     */
    private boolean enterWelcomePage;
    /**
     * 签名key
     */
    public static final String A = "sign";

    /**
     * 加密密钥
     */
    public static final String B = "pa";

    public static final String PROJECT_NAME = "projectName";

    public static final String TIME = "timestamp";

    private static CommonUtils commonUtils = new CommonUtils();

    public static CommonUtils getInstance() {
        return commonUtils;
    }

    public CommonUtils() {
    }

    public boolean isEnterWelcomePage() {
        return enterWelcomePage;
    }

    public void setEnterWelcomePage(boolean enterWelcomePage) {
        this.enterWelcomePage = enterWelcomePage;
    }

    /**
     * 判断activity是否引导过
     *
     * @param context
     * @return 是否已经引导过 true引导过了 false未引导
     */
    public boolean activityIsGuided(Context context) {
        if (context == null) {
            return false;
        }
        // 取得所有类名
        String[] classNames = SharedPreferencesUtils.getInstance(context)
                .getString("guide_activity", "").split("\\|");
        for (String string : classNames) {
            if (GUIDE_STR.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @return void 返回类型
     * @Title : setIsGuided
     * @Description : 设置该activity被引导过了, 将类名已 |a|b|c这种形式保存为value
     * @params
     */
    public void setIsGuided(Context context) {
        if (context == null) {
            return;
        }
        String classNames = SharedPreferencesUtils.getInstance(context).getString(
                "guide_activity", "");
        // 添加值
        StringBuilder sb = new StringBuilder(classNames).append("|").append(GUIDE_STR);
        SharedPreferencesUtils.getInstance(context).put("guide_activity", sb.toString());
    }

    /**
     * 是否引导过挖矿
     *
     * @param context
     * @return
     */
    public boolean isGuideDig(Context context) {
        boolean guideDig = SharedPreferencesUtils.getInstance(context).getBoolean(GUIDE_GID);
        return guideDig;
    }

    /**
     * 设置引导
     *
     * @param context
     * @param guideDig
     */
    public void setGuideDig(Context context, boolean guideDig) {
        SharedPreferencesUtils.getInstance(context).put(GUIDE_GID, guideDig);
    }

    /**
     * 获取到转入的信息
     *
     * @param context
     * @return
     */
    public boolean isTurnIncomeToWallet(Context context) {
        return SharedPreferencesUtils.getInstance(context).getBoolean(TURN_TO_WALLET);
    }

    /**
     * 是否转入
     *
     * @param context
     * @param turnIn
     */
    public void setTurnIncomeToWallet(Context context, boolean turnIn) {
        SharedPreferencesUtils.getInstance(context).put(TURN_TO_WALLET, turnIn);
    }

    /**
     * 获取长的占位图
     *
     * @return
     */
    public RequestOptions getLongCrowdConfig() {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.library_crowd_long)
                .error(R.drawable.library_crowd_long)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    /**
     * 获取长的占位图
     *
     * @return
     */
    public RequestOptions getLongCrowdFitXYConfig() {
        return new RequestOptions()
//                .fitCenter()
                .placeholder(R.drawable.library_crowd_long)
                .error(R.drawable.library_crowd_long)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    /**
     * 获取正常的占位图
     *
     * @return
     */
    public RequestOptions getCrowdConfig() {

        return new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.library_crowd)
                .error(R.drawable.library_crowd)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    /**
     * 获取到头像配置
     *
     * @return
     */
    public RequestOptions getHeaderConfig() {
        return new RequestOptions()
                .placeholder(R.drawable.library_header_default)
                .error(R.drawable.library_header_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    /**
     * 获取正常的占位图
     *
     * @return
     */
    public RequestOptions getRoundCrowdConfig(int placeholderResId, int errorHolderResId) {
        return new RequestOptions()
                .centerCrop()
                .placeholder(placeholderResId)
                .error(errorHolderResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

    }


    /**
     * 获取圆的占位图
     *
     * @return
     */
    public RequestOptions getRoundCrowdConfig() {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.library_crowd)
                .error(R.drawable.library_crowd)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    /**
     * 判断邮箱是否正确
     *
     * @param string 邮箱号
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null) {
            return false;
        }
        String regEx1 = "^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches("^(13[0-9]|14[3579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", mobile);
    }

    /**
     * 判断是否是正确的资金密码
     *
     * @param pwd
     * @return
     */
    public static boolean isLegalPwd(String pwd) {
        if (pwd == null) {
            return false;
        }
        String regEx1 = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]{8,16}$";
//        String regEx1 = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$/";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(pwd);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取到图片的地址 设置到文件中
     *
     * @param imageName
     * @return
     */
    public String getImageUriPath(String imageName) {
        return Environment.getExternalStorageDirectory() + "/images/" + imageName + ".jpg";
    }

    /**
     * 判断是否黑屏
     *
     * @param context
     * @return
     */
    public boolean isScreenLocked(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return !mKeyguardManager.inKeyguardRestrictedInputMode();
    }


    public static <T> boolean isListNull(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static <T> boolean isListNotNull(Collection<T> collection) {
        return !isListNull(collection);
    }

    public static <T> boolean isListNotNull(Map map) {
        return !isListNull(map);
    }

    public static <T> boolean isListNull(Map map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    public static String getImei(Context context) {
        String ret = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            ret = telephonyManager.getDeviceId();
        } catch (Exception e) {
            L.i(CommonUtils.class.getSimpleName(), e.getMessage());
        }
        return ret;
    }

    public static boolean isHasImei(Context context) {
        String ret = getImei(context);
        return !TextUtils.isEmpty(ret);
    }

    public static void closeSoftKeyBoard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * dp单位转px
     *
     * @param value
     * @param context
     * @return
     */
    public static int dp2px(float value, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
