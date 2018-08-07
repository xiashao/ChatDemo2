package com.example.xinxin.chatdemo.tool;

import android.text.TextUtils;

import com.example.xinxin.chatdemo.bean.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_FORMAT4 = "yyyy-MM-dd HH:mm";
    public static String TIME_FORMAT1 = "yyyy-MM-dd";
    public static String TIME_FORMAT_MD = "MM-dd";
    public static String TIME_FORMAT_MDHMS = "MM-dd HH:mm:ss";
    public static String TIME_FORMAT_MDHM = "MM-dd HH:mm";
    private static String TIME_FORMAT3 = "yyyy-MM";
    private static String TIME_FORMAT2 = "MM/dd";

    private static String TAG = "DateTool";

    public static long convertString2long(String date) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
            return sf.parse(date).getTime();
        } catch (ParseException e) {
        }
        return 0l;
    }

    public static long convertString2long(String date, String pattern) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            return sf.parse(date).getTime();
        } catch (ParseException e) {
        }
        return 0l;
    }

    public static long convertString2long2(String date) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT4);
            return sf.parse(date).getTime();
        } catch (ParseException e) {
        }
        return 0l;
    }

    public static String convertLong2String(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        String tempDate = format.format(date);
        return tempDate;
    }

    public static String convertLong2String1(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT1);
        String tempDate = format.format(date);
        return tempDate;
    }

    //年月日时分
    public static String getYMDHM(String time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT4);
        String tempDate = format.format(date);
        return tempDate;
    }

    public static String convertLong2YearMonth(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT3);
        String tempDate = format.format(date);
        return tempDate;
    }

    /**
     * @return 当前时间
     */
    public static String getTimeStr() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(d);
    }

    public static String timeLong2Str(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static Date string2Date(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String string2Format(String time) {
        String formatTime = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT2);
        formatTime = dateFormat.format(string2Date(time));
        return formatTime;
    }

    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        String s = sdf.format(date);
        return s;
    }

    /**
     * 取得当前日期所在周的第一天
     */
    public static long getFirstDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 取得当前日期所在周的最后一天
     */
    public static long getLastDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.WEEK_OF_MONTH, 1);
        return c.getTimeInMillis() - 1;
    }

    /**
     * 获得当前日期所在月的第一天
     */
    public static long getFirstDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获得当前日期所在月的最后一天
     */
    public static long getLastDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() - 1;
    }

    public static long dateDiff(String startTime, String endTime) {
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff, min = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            min = diff % nd % nh / nm;// 计算差多少分钟
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return min;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @throws Exception
     * @Exception 发生异常
     */

    public static String dayForWeek(String pTime) throws Exception {
        String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT1);
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return weeks[dayForWeek];
    }

    /**
     * 获得昨天日期
     *
     * @return
     */
    public static String getYesterDay() {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT1);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        String yesterday = format.format(c.getTime());
        return yesterday;
    }

    /**
     * 判断是不是 今天及以后 的时间
     *
     * @param endTime
     * @return
     */
    public static boolean isTodayAndAfterRow(String endTime) {
        try {
            long end = convertString2long(endTime);
            long now = System.currentTimeMillis();

            if (end - now > 0) {
                return true;
            } else {
                String endDate = convertLong2String1(end);
                String nowDate = convertLong2String1(now);
                if (endDate.equals(nowDate)) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 气泡中时间样式
     * 1.最后两个气泡之间在一分钟之内，不显示，一分钟之外显示
     * 2.显示：不属于当年的显示年份，属于当年的，
     *
     * @param time yyyy-MM-dd HH:mm:ss
     *             0-4 年; 5-7 月; 8-10 日; 11-13 时; 14-16 分; 17-19 秒
     * @return
     */
    public static String getTime(String time) {
        if (TextUtils.isEmpty(time)) return "";
        String nowTime = DateTool.getTimeStr();
        String showTime = "";
        if (time.length() >= 17) {
            if (time.substring(0, 4).equals(nowTime.substring(0, 4))) {//当年
                if (time.substring(0, 10).equals(DateTool.getYesterDay())) {//昨天
                    showTime = "昨天" + time.substring(10, 16);
                } else if (time.substring(5, 10).equals(DateTool.getTimeStr().substring(5, 10))) {//当天
                    showTime = time.substring(11, 16);
                } else if (DateTool.convertString2long(time) >= DateTool.getFirstDayOfWeek() && DateTool.convertString2long(showTime) <= DateTool.getLastDayOfWeek()) {//本周，写上是周几
                    try {
                        showTime = DateTool.dayForWeek(time) + time.substring(10, 16);
                    } catch (Exception e) {
                        Tool.showLog(Constants.basePath, e.toString());
                    }
                } else {
                    showTime = time.substring(5, 16);
                }
            } else {//其它年份
                showTime = time;
            }
        } else {
            showTime = time;
        }

        return showTime;
    }

    public static String getTimePattern(String date) {
        if (TextUtils.isEmpty(date)) return "";
        if (date.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2}:\\d{2}")) { //yyyy-MM-dd hh:mm:ss
            return date;
        } else if (date.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2}")) { //yyyy-MM-dd hh:mm
            return date + ":00";
        } else if (date.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {//yyyy-MM-dd
            return date + " 00:00:00";
        } else if (date.matches("\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2}:\\d{2}")) {//MM-dd hh:mm:ss
            Calendar c = Calendar.getInstance();  //获取Calendar的方法
            int year = c.get(Calendar.YEAR); //获取当前年份
            Tool.showLog("getTime", "year " + year);
            return year + "-" + date;
        } else if (date.matches("\\d{2}:\\d{2}:\\d{2}")) { //hh:mm:ss
            Calendar c = Calendar.getInstance();  //获取Calendar的方法
            int year = c.get(Calendar.YEAR); //获取当前年份
            int month = c.get(Calendar.MONTH) + 1; //获取当前月份
            int day = c.get(Calendar.DAY_OF_MONTH); //获取当前年份

            String monthStr;
            if (month < 10) {
                monthStr = "0" + month;
            } else {
                monthStr = "" + month;
            }
            Tool.showLog("getTime", "year month " + year + "-" + monthStr + "-" + day + " " + date);
            return year + "-" + monthStr + "-" + day + " " + date;
        } else if (date.matches("\\d{1,2}-\\d{1,2}\\s\\d{2}:\\d{2}") //MM-dd hh:mm
                || date.matches("\\d{1,2}-\\d{1,2}") //MM-dd
                ) {
            Calendar c = Calendar.getInstance();  //获取Calendar的方法
            int year = c.get(Calendar.YEAR); //获取当前年份
            Tool.showLog("getTime", "year " + year);
            return year + "-" + date;

        } else if (date.matches("\\d{2}:\\d{2}")) { //hh:mm
            Calendar c = Calendar.getInstance();  //获取Calendar的方法
            int year = c.get(Calendar.YEAR); //获取当前年份
            int month = c.get(Calendar.MONTH) + 1; //获取当前月份
            int day = c.get(Calendar.DAY_OF_MONTH); //获取当前年份

            String monthStr;
            if (month < 10) {
                monthStr = "0" + month;
            } else {
                monthStr = "" + month;
            }
            Tool.showLog("getTime", "year month " + year + "-" + monthStr + "-" + day + " " + date);
            return year + "-" + monthStr + "-" + day + " " + date;
        }
        return "";
    }

    public static int getTimeHour(String time) {// yyyy-MM-dd HH:mm:ss
        if (TextUtils.isEmpty(time)) return -1;
        int indexBlank = time.indexOf(" ");
        int indexColon = time.indexOf(":");
        String hourStr = time.substring(indexBlank + 1, indexColon);

        Tool.showLog(TAG, "getTimeHour " + hourStr);
        return Integer.parseInt(hourStr);
    }

    //小时、分钟
    public static String getTimeHourAndM(String time) {// yyyy-MM-dd HH:mm:ss
        if (TextUtils.isEmpty(time)) return "";
        int indexBlank = time.indexOf(" ");
        int indexColon = time.lastIndexOf(":");
        String hourMStr = time.substring(indexBlank + 1, indexColon);

        Tool.showLog(TAG, "getTimeHourAndM " + hourMStr);
        return hourMStr;
    }


    /**
     *
     * @param _ms 毫秒
     * @return 把 ms 转化为 时分秒
     */
    public static String ms2HMS(long _ms) {
        String HMStime;
        _ms /= 1000;
        long hour = _ms / 3600;
        long mint = (_ms % 3600) / 60;
        long sed = _ms % 60;
        String hourStr = String.valueOf(hour);
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        String mintStr = String.valueOf(mint);
        if (mint < 10) {
            mintStr = "0" + mintStr;
        }
        String sedStr = String.valueOf(sed);
        if (sed < 10) {
            sedStr = "0" + sedStr;
        }
        if ("00".equals(hourStr)){
            HMStime = mintStr + ":" + sedStr;
        }else {
            HMStime = hourStr + ":" + mintStr + ":" + sedStr;
        }

        return HMStime;
    }
    public static int longtoInt(long temp){
        if(temp>0){
            return 1;
        }else if(temp<0){
            return -1;
        }else
            return 0;
    }

}
