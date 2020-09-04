package com.example.dildil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created by Administrator on 2018/4/24 0024.
 */

public class TimeDateUtils {

    // String类型时间的几种格式
    public static final String FORMAT_TYPE_1 = "yyyyMMdd";
    public static final String FORMAT_TYPE_2 = "MM月dd日 hh:mm";
    public static final String FORMAT_TYPE_3 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TYPE_4 = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 获取当前时间戳
     * @return
     */
    public static long currentTimeMillis(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期和时间
     * @param formatType
     * @return
     */
    public static String getCurrentDateStr(String formatType){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(date);
    }

    /**
     * 时间转换 Date ——> long
     * @param date
     * @return
     */
    public static long date2Long(Date date){
        return date.getTime();
    }

    /**
     * 时间转换 Date ——> String
     * @param date
     * @param formatType
     * @return
     */
    public static String date2String(Date date, String formatType){
        return new SimpleDateFormat(formatType).format(date);
    }

    /**
     * 时间转换 long ——> Date
     * @param time
     * @param formatType
     * @return
     */
    public static Date long2Date(long time, String formatType){
        Date oldDate = new Date(time);
        String dateStr = date2String(oldDate, formatType);
        Date date = string2Date(dateStr, formatType);
        return date;
    }

    /**
     * 时间转换 long ——> String
     * @param time
     * @param formatType
     * @return
     */
    public static String long2String(long time, String formatType){
        Date date = long2Date(time, formatType);
        String strTime = date2String(date, formatType);
        return strTime;
    }

    /**
     * 时间转换 String ——> Date
     * @param strTime
     * @param formatType
     * @return
     */
    public static Date string2Date(String strTime, String formatType){
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = format.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间转换 String ——> long
     * @param strTime
     * @param formatType
     * @return
     */
    public static long string2Long(String strTime, String formatType){
        Date date = string2Date(strTime, formatType);
        if(date == null){
            return 0;
        } else {
            long time = date2Long(date);
            return time;
        }
    }
}