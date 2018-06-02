package org.it611.das.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getLocalTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getLocalNextTime(long millisecond){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date(System.currentTimeMillis() + millisecond));// new Date()为获取当前系统时间
    }


    public static void main(String[] args) {
        System.out.println(getLocalTime());
        System.out.println(getLocalNextTime(-1000*60*60));
    }

   /* public static String getLocalMin() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getLocalMinNextTime(long millisecond) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        return df.format(new Date(System.currentTimeMillis() + millisecond));// new Date()为获取当前系统时间
    }

    public static String getLocalHour() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getLocalHourNextTime(long millisecond) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
        return df.format(new Date(System.currentTimeMillis() + millisecond));// new Date()为获取当前系统时间
    }

    public static String getLocalDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getLocalDayNextTime(long millisecond) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date(System.currentTimeMillis() + millisecond));// new Date()为获取当前系统时间
    }*/

}
