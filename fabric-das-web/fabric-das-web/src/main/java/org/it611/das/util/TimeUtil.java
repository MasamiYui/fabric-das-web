package org.it611.das.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String getLocalTime() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getLocalTimeFirstSecondTime(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");//设置日期格式
        String dataStr = df.format(date);// new Date()为获取当前系统时间
        return dataStr;
    }

    public static String getLocalTimeEndSecondeTime(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:59");//设置日期格式
        String dataStr = df.format(date);// new Date()为获取当前系统时间
        return dataStr;
    }

    public static String getLocalTimeDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getLocalTimeBeforeDay(){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }



    public static String getLocalNextTime(long millisecond){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date(System.currentTimeMillis() + millisecond));// new Date()为获取当前系统时间
    }


    public static String getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
        return  new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
    }


    public static String getFirstDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        return   new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
    }


    public static void main(String[] args) {



        String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        Calendar rightNow = Calendar.getInstance();
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1;

        String nowTime = TimeUtil.getFirstDayOfMonth(year, month)+" 00:00:00";

        //判断今天是否是新年的第一天,则上一个月为上一年的12月
        if(month == 1){
            year = year-1;
            month = 12;
        }

        //否则月份-1即可
        month = month -1;


        //获取该月第一天和最后一天
        String startTime = TimeUtil.getFirstDayOfMonth(year, month);
        String endTime = TimeUtil.getLastDayOfMonth(year, month);

        System.out.println(localTime);
        System.out.println(startTime);
        System.out.println(endTime);



        //System.out.println(getLocalTimeBeforeDay());

    /*    String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        String startTime = TimeUtil.getLocalTimeFirstSecondTime(new Date(System.currentTimeMillis()-1000*24*60*60));
        String endTime = TimeUtil.getLocalTimeEndSecondeTime(new Date(System.currentTimeMillis()-1000*24*60*59));
        System.out.println(localTime+"\n"+startTime+"\n"+endTime);*/
/*        System.out.println(getLocalTime());
        System.out.println(getLocalNextTime(-1000*60*60));*/
        //getBeforeMonthInfomation();
       // System.out.println(getLastDayOfMonth(2000,2));
       // System.out.println(getLastDayOfMonth(2017,2));

        //System.out.println(getLocalTimeStartFirstMin());


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
