package com.xtf.xtflib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class TimeUtil {

    public static boolean checkAdult(Date date) {

        Calendar current = Calendar.getInstance();
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(date);
        Integer year = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (year > 18) {
            return true;
        } else if (year < 18) {
            return false;
        }
        // 如果年相等，就比较月份
        Integer month = current.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        if (month > 0) {
            return true;
        } else if (month < 0) {
            return false;
        }
        // 如果月也相等，就比较天
        Integer day = current.get(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH);
        return  day >= 0;
    }
    public static boolean isSameDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
//            throw new IllegalArgumentException("The date must not be null");
            return false;
        }
    }
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if(cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }
    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }




    public static boolean isInWorkTime(){
        try {
            SimpleDateFormat dayFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat detailFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dayStr= dayFormat.format(new Date());

            long startWorkTime=  detailFormat.parse(dayStr+" 09:00").getTime();
            long endWorkTime=  detailFormat.parse(dayStr+" 21:00").getTime();
            long nowTime=System.currentTimeMillis();
            return nowTime>startWorkTime&&nowTime<endWorkTime;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static long getDayInterval(long time1, long time2) {
        // 获得当前时区
        TimeZone tz = TimeZone.getDefault();
        // UTC毫秒加上这个偏移值，得到本地时区的时间
        long delta = tz.getRawOffset();
        long base = 24*3600*1000L;
        long day1 = (time1+delta)/base + 1L;
        long day2 = (time2+delta)/base + 1L;
        System.out.println(new Date(time1));
        System.out.println(new Date(time2));
        System.out.println(day1);
        System.out.println(day2);
        return Math.abs((day1-day2));
    }
}
