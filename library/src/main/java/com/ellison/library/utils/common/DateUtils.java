package com.ellison.library.utils.common;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SY
 * @date 2017/3/25
 */

public class DateUtils {
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";
    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    private static String NULLSTR = "null";

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format  Str
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || NULLSTR.equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    public static void main(String[] args) {
        String timeStamp = timeStamp();
        //运行输出:timeStamp=1470278082
        System.out.println("timeStamp=" + timeStamp);
        //运行输出:1470278082980
        System.out.println(System.currentTimeMillis());
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        //运行输出:date=2016-08-04 10:34:42
        System.out.println("date=" + date);

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        //运行输出:1470278082
        System.out.println(timeStamp2);
    }

    /**
     * 将秒数转换成时分秒
     *
     * @param time
     * @return
     */
    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        time = time / 1000;
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
//                if (hour > 99) {
//                    return "99:59:59";
//                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String secToTimeSub(long time) {
        if (time <= 1000) {
            return "000";
        } else {
            time = time % 1000;
            if (time == 0) {
                return "000";
            } else {
                String s = String.valueOf(time);
                if (s.length() == 1) {
                    return "00" + s;
                } else if (s.length() == 2) {
                    return "0" + s;
                } else {
                    return s;
                }
            }
        }
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Long.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }


    private final static long minute = 60 * 1000;   // 1分钟
    private final static long hour = 60 * minute;   // 1小时
    private final static long day = 24 * hour;      // 1天
    private final static long week = 7 * day;       // 1周
    private final static long month = 31 * day;     // 月
    private final static long year = 12 * month;    // 年

    /**
     * 传入接口中的String来获取到格式化的时间
     *
     * @param dateL
     * @return
     */
    public static String getFormatTime(String dateL, boolean isNeedSeconds) {
        if (TextUtils.isEmpty(dateL)) {
            return "";
        }
        Date date = formatDateTimeToDate(dateL);
        return formatFriendly(date, isNeedSeconds);
    }

    /**
     * 通过长数据获取Date
     *
     * @param dateL
     * @return
     */
    public static Date formatDateTimeToDate(String dateL) {
        if (TextUtils.isEmpty(dateL) || "null".equals(dateL)) {
            return null;
        }
        long dLong = Long.parseLong(dateL);
        return new Date(dLong);
    }

    /**
     * 将日期以 HH:mm 格式化
     *
     * @param dateL 日期
     * @return
     */
    public static String formatDateTimeHm(long dateL) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_HH_MM);
        return sdf.format(new Date(dateL));
    }


    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几周前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatFriendly(Date date, boolean isNeedSeconds) {
        if (date == null) {
            return null;
        }
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > week) {
            r = (diff / week);
            return r + "周前";
        }
        if (diff > (2 * day)) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > day) {
            if (isNeedSeconds) {
                long time = date.getTime();
                String s = formatDateTimeHm(time);
                return "昨天 " + s;
            } else {
                return "昨天";
            }
        }
        if (diff > hour) {
//            if(isNeedSeconds) {
//                long time = date.getTime();
//                String s = formatDateTimeHm(time);
//                return "今天 " + s;
//            } else {
//                return "今天";
//            }
            int l = (int) (diff / hour);
            return l + "小时前";
        }
        return "刚刚";
    }
}
