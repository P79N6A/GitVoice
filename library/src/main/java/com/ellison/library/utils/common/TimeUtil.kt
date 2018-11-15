package com.ellison.library.utils.common

import java.text.SimpleDateFormat
import java.util.*

/**
 * description :  时间管理工具
 * Created by wdb on 2017/7/11.
 */
class TimeUtil {


    companion object {

        /* 默认的时间格式*/
        public var timeFormat = "yyyy-MM-dd HH:mm:ss"

        /**
         * @param formatString    :  时间格式化规则
         * @param time            :  时间戳
         * @return                时间字符串
         */
        fun getFormatTime(time: Long, formatString: String = timeFormat): String {
            val dateFormat = SimpleDateFormat(formatString)
            return dateFormat.format(time * 1000)
        }


        /**
         * 获取当前的日
         * @param time  时间戳
         * @return String  返回日期中的日
         */
        fun getDateDay(time: Long, formatString: String = timeFormat): String {
            val dateFormat = SimpleDateFormat(formatString)
            return dateFormat.format(time)
        }

        /**
         * 获取比赛已经开始的真实时间
         */
        fun getMatchLiveTime(timestamp: Long): Int {
            val time =  ((System.currentTimeMillis() - timestamp * 1000 - DefaultApplication.getApplication().differenceTime) / 1000 / 60).toInt()
            return  time

        }


        /**
         * 字符串转时间戳
         * @param timeString 时间字符串
         * @return String  时间戳
         */
        fun getTimeString(timeString: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.parse(timeString).time.toString()
        }

        fun getWeek(timeStamp: Long): String {

            /**
             * 获取指定日期转换成星期几
             * @param timeStamp  时间戳
             * @return  String  星期
             */
            val cd = Calendar.getInstance()
            cd.time = Date(timeStamp)
            return when (cd.get(Calendar.DAY_OF_WEEK)) {
                1 -> "日"
                2 -> "一"
                3 -> "二"
                4 -> "三"
                5 -> "四"
                6 -> "五"
                else -> "六"
            }

        }


        /**
         * 智能提示时间
         * @param timestamp  时间戳
         * @return String
         */
        fun getIntelligenceTime(timestamp: Long, formatTime: String): String {
            val simpleDateFormat = SimpleDateFormat(formatTime)
            val date = simpleDateFormat.format(timestamp * 1000)
            val dayAndMillis = date.substring(10, 16)
            val diffMillis = (System.currentTimeMillis() - timestamp * 1000) / 1000
            val time = diffMillis / 60
            val day = time / 60 / 24
            if (time < 3)
                return "刚刚"
            if (time < 60)
                return "${time}分钟前"
            if (time >= 60 && time / 60 < 24)
                return "${time / 60}小时前"
            return when (day.toInt()) {
                1 -> "昨天$dayAndMillis"
                2 -> "前天$dayAndMillis"
                else -> date.substring(0, 16)
            }

        }

        /**
         * 智能提示比赛开始时间
         * @param timestamp  时间戳
         * @return String
         */
        fun getMatchTime(timestamp: Long, formatTime: String): String {
            val simpleDateFormat = SimpleDateFormat(formatTime)
            val date = simpleDateFormat.format(timestamp * 1000)
            val system = simpleDateFormat.format(System.currentTimeMillis())
            val dayAndMillis = date.substring(11, 16)
            if (date.substring(5, 10) == system.substring(5, 10)) {
                return "今日$dayAndMillis"
            }
            val diffMillis = (timestamp * 1000 - System.currentTimeMillis()) / 1000
            if (diffMillis < 0) {
                return date.substring(5, 16)
            }
            val time = diffMillis / 60
            val day = time / 60 / 24
            return when (day.toInt()) {
                0 -> "明日$dayAndMillis"
                else -> date.substring(5, 16)
            }
        }

        /**
         * 获取两个时间相距多少天
         * 时间不是毫秒值
         */
        fun getTimeDistance(firstTime: Long, secondTime: Long): String =
                "${Math.abs(firstTime - secondTime) / 60 / 60 / 24}天"

    }


}