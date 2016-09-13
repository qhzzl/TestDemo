package com.zzl.mmm.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author zzl
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 获取今天是星期几
     * @return
     */
    public static int getDayinWeek(){
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if(isFirstSunday){
            weekDay = weekDay - 1;
            if(weekDay == 0){
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 根据毫秒获取时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm
     */
    public static String getDate(long time) {
        Date currentTime = new Date(time);
//		if (currentTime.getYear()==Calendar.getInstance().get(Calendar.YEAR)) {
//			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
//			String dateString = formatter.format(currentTime);
//			return dateString;
//		}
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在日期
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     *  根据毫秒获取日期
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShortByTime(Long time) {
        Date currentTime = new Date(time);
//		if (currentTime.getYear()==Calendar.getInstance().get(Calendar.YEAR)) {
//			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
//			String dateString = formatter.format(currentTime);
//			return dateString;
//		}
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * yyyy-MM-dd HH:mm:ss转date
     *
     * @return date
     * @throws ParseException
     */
    public static Date str2Date(String strdate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=formatter.parse(strdate);
        return date;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = new Date();
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
                    * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

}