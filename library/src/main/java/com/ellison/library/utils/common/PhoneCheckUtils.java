package com.ellison.library.utils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Ellison
 * @date 2018/1/25
 * @desc 判断是否为手机号
 */

public class PhoneCheckUtils {

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+任意数
     * 17+除9的任意数
     * 199
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147)|(166)|(199))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是不是正确的手机号
     * @param phone
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isPhone(String phone) throws PatternSyntaxException {
        String[] rule =
                {
                        "^1(34[0-8]|(3[5-9]|4[7]|5[0127-9]|8[23478])\\d)\\d{7}$",
                        "^1(3[0-2]|45|76|5[256]|8[56])\\d{8}$",
                        "^1((33|53|7[703]|8[019])[0-9]|349)\\d{7}$",
                        "^1(3[0-9]|5[0-35-9]|7[037]|8[025-9])\\d{8}$"
                };
        boolean[] isLegal = new boolean[rule.length];
        for (int i = 0; i < rule.length; i++) {
            Pattern p = Pattern.compile(rule[i]);
            Matcher m = p.matcher(phone);
            isLegal[i] = m.matches();
        }
        return isLegal[0] || isLegal[1] || isLegal[2] || isLegal[3];
    }

}
