package com.plan.frame.util;

import com.plan.frame.exception.SystemException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author linzhihua
 * @Description 工具类-日期处理Util
 * @Date create in 2019/8/25 22:19
 * @Param
 **/

public class DateUtil {
    /**
     * 把字串转换为日期
     *
     * @param sdate  字串形式的日期
     * @param format 字串格式
     * @return 转换为日期类型
     */
    public static Date str2Date(String sdate, String format) {
        try {
            if (sdate == null || sdate != null && sdate.trim().length() == 0)
                return null;
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.parse(sdate);
        } catch (ParseException e) {
            throw new SystemException("字符转日期异常", e, "请检测输入的值是否正确");
        }
    }

    /**
     * 日期字符串转字符串
     * @param sourceDate
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    public static String dateStr2Str(String sourceDate,String sourceFormat,String targetFormat){
        if(CommonUtil.isEmpty(sourceDate)){
            return null;
        }
        try {
            SimpleDateFormat sourceSimpleDateFormat = new SimpleDateFormat(sourceFormat);
            Date dateSourceDate = sourceSimpleDateFormat.parse(sourceDate);
            SimpleDateFormat targetDf = new SimpleDateFormat(targetFormat);
            return targetDf.format(dateSourceDate);
        }catch (ParseException e) {
            throw new SystemException("日期字符转字符异常", e, "请检测输入的值是否正确");
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurDateTime() {
        return new Date();
    }

    /**
     * 获取当前时间
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurDateTimeStr() {
        return date2Str(new Date());
    }

    /**
     * 把时间转换为字串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 待转换的时间
     * @return
     */
    public static String date2Str(Date date) {
        String format = "yyyy-MM-dd HH:mm:ss";
        return date2Str(date, format);
    }

    /**
     * 把时间转换为字串 格式：yyyy-MM-dd
     *
     * @param date 待转换的时间
     * @return
     */
    public static String date2StrNotTime(Date date) {
        String format = "yyyy-MM-dd";
        return date2Str(date, format);
    }

    /**
     * 把时间转换为字串
     *
     * @param date   待转换的时间
     * @param format 转换格式
     * @return
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取当前日期：yyyy-MM-dd
     *
     * @return
     */
    public static Date getCurDate() {
        return str2Date(date2Str(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
    }


    /**
     * 获取当前年月 样式为yyyy-MM
     *
     * @return
     */
    public static String getCurYM() {
        return date2Str(new Date(), "yyyy-MM");
    }

    /**
     * 获取当前年度
     *
     * @return
     */
    public static String getCurYear() {
        return date2Str(new Date(), "yyyy");
    }

    /**
     * 获取当前年月日 样式为yyMMdd
     * @return
     */
    public static String getCurYMD(){
        return date2Str(new Date(),"yyMMdd");
    }
    /**
     * 获取当前年月日 样式为yyyyMMdd
     * @return
     */
    public static String getCurYyyyMD(){
        return date2Str(new Date(),"yyyyMMdd");
    }

    /**
     * 根据增加or减少的时间得到新的日期
     *
     * @param curDate   当前日期
     * @param field     需操作的'年'or'月'or'日'
     * @param addNumber 增加or减少的时间
     * @return
     */
    public static Date dateAdd(Date curDate, int field, int addNumber) {
        GregorianCalendar curGc = new GregorianCalendar();
        curGc.setTime(curDate);
        curGc.add(field, addNumber);
        return curGc.getTime();
    }


    /**
     * 根据增加or减少的天数
     *
     * @param curDate   当前日期
     * @param addNumber 增加or减少的天数
     * @return
     */
    public static Date dateAdd(Date curDate, int addNumber) {
        GregorianCalendar curGc = new GregorianCalendar();
        curGc.setTime(curDate);
        curGc.add(GregorianCalendar.DATE, addNumber);
        return curGc.getTime();
    }


    /**
     * @param date
     * @param addNumber
     * @return
     * @throws Exception
     */
    public static Date monthAdd(Date date, int addNumber) {
        return dateAdd(date, Calendar.MONTH, addNumber);
    }

    /**
     * 得到二个日期间隔
     *
     * @param dateBefore 开始日期
     * @param dateAfter  结束日期
     * @param field      间隔类型 (eg. Calendar.Month Calendar.Year)
     * @param amount     间隔数
     * @return 例：
     * getDateInterval(dateBefore,dateAfter,Calendar.Month,1),返回二个日期之间的月份。
     */
    public static int getDateInterval(Date dateBefore, Date dateAfter,
                                      int field, int amount) {
        int interval = 0;
        Calendar cb = new GregorianCalendar();
        Calendar da = new GregorianCalendar();
        cb.setTime(dateBefore);
        da.setTime(dateAfter);
        da.add(field, 1);
        for (Calendar c = cb; c.before(da); c.add(field, amount)) {
            interval++;
        }
        return interval;
    }

    /**
     * 得到两个日期之前的天数
     *
     * @param beginDate String 开始日期 格式:yyyy-MM-dd
     * @param endDate   String 结束日期 格式:yyyy-MM-dd
     * @return int
     */
    public static int getDateInterval(Date beginDate, Date endDate) {
        return getDateInterval(beginDate, endDate, Calendar.DATE, 1);
    }

    /**
     * 两个日期的间隔月份
     *
     * @param dateBefore
     * @param dateAfter
     * @return
     */
    public static int getMonthInterval(Date dateBefore, Date dateAfter) {
        return getDateInterval(dateBefore, dateAfter, Calendar.MONTH, 1);
    }

    /**
     * 时间查询-开始时间转化YY-MM-DD HH-MM-SS date类型
     *
     * @param startTime
     * @return
     */
    public static Date getStartDateTime(Date startTime) {
        SimpleDateFormat formatterStr = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = getStartTime(formatterStr.format(startTime));
        try {
            startTime = formatterDate.parse(startTimeStr);
        } catch (ParseException e) {
            throw new SystemException("时间String转成date类型出错", e, "请联系管理员处理");
        }
        return startTime;
    }

    /**
     * 时间查询-结束时间转化YY-MM-DD HH-MM-SS  date类型
     *
     * @param endTime
     * @return
     */
    public static Date getEndDateTime(Date endTime) {
        SimpleDateFormat formatterStr = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTimeStr = getEndTime(formatterStr.format(endTime));
        try {
            endTime = formatterDate.parse(endTimeStr);
        } catch (ParseException e) {
            throw new SystemException("时间String转成date类型出错", e, "请联系管理员处理");
        }
        return endTime;
    }

    /**
     * 时间查询-开始时间转化YY-MM-DD HH-MM-SS
     *
     * @param startTime
     * @return
     */
    public static String getStartTime(String startTime) {
        StringBuilder startStr = new StringBuilder(startTime);
        startStr.append(" 00:00:00");
        startTime = startStr.toString();
        return startTime;
    }

    /**
     * 时间查询-开始时间转化YY-MM-DD HH-MM-SS  输入date型返回date型
     *
     * @param startTime
     * @return
     */
    public static Date getStartTime(Date startTime) {
        String date2Str = DateUtil.date2Str(startTime, "yyyy-MM-dd");
        String time = DateUtil.getStartTime(date2Str);
        startTime = DateUtil.str2Date(time, "yyyy-MM-dd HH:mm:ss");
        return startTime;
    }

    /**
     * 时间查询-结束时间转化YY-MM-DD HH-MM-SS
     *
     * @param endTime
     * @return
     */
    public static String getEndTime(String endTime) {
        StringBuilder endStr = new StringBuilder(endTime);
        endStr.append(" 23:59:59");
        endTime = endStr.toString();
        return endTime;
    }

    /**
     * 时间查询-结束时间转化YY-MM-DD HH-MM-SS 输入date型返回date型
     *
     * @param endTime
     * @return endTime
     */
    public static Date getEndTime(Date endTime) {
        String date2Str = DateUtil.date2Str(endTime, "yyyy-MM-dd");
        String time = DateUtil.getEndTime(date2Str);
        endTime = DateUtil.str2Date(time, "yyyy-MM-dd HH:mm:ss");
        return endTime;
    }

    /**
     * 比较两个string型的时间
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int comparaDate(String startDate, String endDate) {
        int compara = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDateDate = formatter.parse(startDate);
            Date endDateDate = formatter.parse(endDate);
            compara = endDateDate.compareTo(startDateDate);
        } catch (ParseException e) {
            throw new SystemException("时间String转成date类型出错", e, "请联系管理员处理");
        }

        return compara;
    }

    /**
     * 比较两个date类型的时间
     *
     * @param startDate
     * @param endDate
     * @return startDate>endDate返回-1
     * startDate<endDate返回1
     * startDate=endDate返回0
     */
    public static int comparaDate(Date startDate, Date endDate) {
        int compara = 0;
        if (CommonUtil.isNull(startDate) || CommonUtil.isNull(endDate)) {
            throw new SystemException("比较时间出错", "传入的时间不能为空", "请重新传入要比较的时间");
        } else {
            compara = endDate.compareTo(startDate);
            return compara;
        }
    }

    /**
     * 时间转换：yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date date2DateNotTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String s = sdf.format(date);
            date = sdf.parse(s);
        } catch (ParseException e) {
            throw new SystemException("时间String转成date类型出错", e, "请联系管理员处理");
        }
        return date;
    }

    /**
     *  计算时间差：h:m:s.ms
     * @param beginDateStr
     * @param endDateStr
     * @return long
     */
    public static String getTimeSub(long beginDateStr, long endDateStr) {
        long between = 0;
        // 得到两者的毫秒数
        between = (endDateStr - beginDateStr);
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);
        String timeSub = String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(s) + "." + String.valueOf(ms);
        return timeSub;
    }

    /**
     * 算两个日期间隔多少天
     * @param beginDateStr  yyyy-MM-dd
     * @param endDateStr yyyy-MM-dd
     * @return
     */
    public static int getDaySub(String beginDateStr, String endDateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = str2Date(beginDateStr, "yyyy-MM-dd");
        Date date2 = str2Date(endDateStr, "yyyy-MM-dd");
        int n = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return n;
    }

    /**
     * 获取连续的小时list,HH:00
     */
    public static List<String> getBetweenHours() {
        // 返回的日期集合
        List<String> hours = new ArrayList<String>();
        String h = "";
        for (int i = 0; i < 24; i++) {
            h = i < 10 ? ("0" + i) : ("" + i);
            hours.add(h + ":00");
        }

        return hours;
    }
    /**
     * 获取连续的日期list,M.dd
     */
    public static List<String> getBetweenDays(String minDate, String maxDate) {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        DateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat sdfOut = new SimpleDateFormat("M.dd");
        try {
            Date start = sdfIn.parse(minDate);
            Date end = sdfIn.parse(maxDate);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(sdfOut.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取连续的月份list,yyyy-MM
     */
    public static List<String> getBetweenMonths(String minMonth, String maxMonth) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM");//格式化为年月
            SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy.MM");

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdfIn.parse(minMonth));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdfIn.parse(maxMonth));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdfOut.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取连续的年份list,yyyy
     */
    public static List<String> getBetweenYears(String minYear, String maxYear) {
        Integer begYear = Integer.valueOf(minYear);
        Integer endYear = Integer.valueOf(maxYear);
        Integer curr = begYear;
        ArrayList<String> result = new ArrayList<String>();
        while (curr <= endYear) {
            result.add(curr.toString());
            curr ++;
        }
        return result;
    }

    /**
     * 获取连续的时间序列
     * @param type：0-小时，1-日，2-月份，3-年份
     * @param minDate yyyy-MM-dd
     * @param maxDate yyyy-MM-dd
     * @return
     */
    public static List<String> getBetweenTimeSeries(Integer type, String minDate, String maxDate){
        if(type == 0){
            return getBetweenHours();
        }else if(type == 1){
            return getBetweenDays(minDate.substring(0, 10), maxDate.substring(0, 10));
        }else if(type == 2){
            return getBetweenMonths(minDate.substring(0, 7), maxDate.substring(0, 7));
        }else if(type == 3){
            return getBetweenYears(minDate.substring(0, 4), maxDate.substring(0, 4));
        }else if(type == 4){
            List<String> list = new ArrayList<>();
            for (int i=0;i<24;i++){
                if (i<10){
                    list.add("0"+String.valueOf(i));
                }else {
                    list.add(String.valueOf(i));
                }

            }
            return list;
        }
        return null;
    }

    /**
     * 获取连续的时间序列-大屏
     * @param type：1-日，2-月份，3-年份
     * @param minDate yyyy-MM-dd
     * @param maxDate yyyy-MM-dd
     * @return
     */
    public static List<String> getBetweenTimeSeriesOfScreen(Integer type, String minDate, String maxDate){
        if(type == 1){
            return getBetweenDaysOfScreen(minDate.substring(0, 10), maxDate.substring(0, 10));
        }else if(type == 2){
            return getBetweenMonthsOfScreen(minDate.substring(0, 7), maxDate.substring(0, 7));
        }else if(type == 3){
            return getBetweenYears(minDate.substring(0, 4), maxDate.substring(0, 4));
        }
        return null;
    }


    /**
     * 获取连续的日期list,MM-dd
     */
    public static List<String> getBetweenDaysOfScreen(String minDate, String maxDate) {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        DateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat sdfOut = new SimpleDateFormat("MM-dd");
        try {
            Date start = sdfIn.parse(minDate);
            Date end = sdfIn.parse(maxDate);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(sdfOut.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取连续的月份list,yy-MM
     */
    public static List<String> getBetweenMonthsOfScreen(String minMonth, String maxMonth) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM");//格式化为年月
            SimpleDateFormat sdfOut = new SimpleDateFormat("yy-MM");

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdfIn.parse(minMonth));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdfIn.parse(maxMonth));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdfOut.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 获取指定年月的最后一天
     * @param monthStr yyyy-MM
     * @return yyyy-MM-dd
     */
    public static String getLastDayOfMonth(String monthStr) {
        String strTemp[] = monthStr.split("-");
        int year = Integer.parseInt(strTemp[0]);
        int month = Integer.parseInt(strTemp[1]);

        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取去年的年份
     * @return yyyy
     */
    public static String getLastYear(){
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        return (year - 1) + "";
    }
    /**
     * 获取前i年的年份
     * @return yyyy
     */
    public static String getLastYear(int i){
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        return (year - i) + "";
    }

    /**
     * 获取上个月份
     * @return yyyy-MM
     */
    public static String getLastMonth() {

        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        return dtf.format(today);

    }

    /**
     * 获取上i个月份
     * @return yyyy-MM
     */
    public static String getLastMonth(int i) {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(i);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        return dtf.format(today);
    }

    /**
     * 获取某个月份的上i个月份
     * @return yyyy-MM
     */
    public static String getLastMonth(Date begin,int i) {
        Instant instant = begin.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate today = instant.atZone(zoneId).toLocalDate();
        today = today.minusMonths(i);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        return dtf.format(today);
    }

    /**
     * 获取当前日期周数
     * @return yyyy-MM
     */
    public static int getWeekNum(String begin) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //开始时间
        Date  startDate = sdf.parse(begin);
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(startDate);
        return g.get(Calendar.WEEK_OF_YEAR); //获得周数
    }

    /**
     * 获取两个日期相差周数
     * @return yyyy-MM
     */
    public static int getWeekNum(String begin,String end)throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //开始时间
        Date  startDate = sdf.parse(begin);
        //结束时间
        Date  endDate = sdf.parse(end);

        long betweenDate = (endDate.getTime() - startDate.getTime())/(60*60*24*1000);

        int week = (int)betweenDate/7;

        return week; //获得周数
    }

    /**
     * 获取昨日日期
     * @return yyyy-MM-dd
     */
    public static String getYesterday(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    /**
     * 获取去年的昨日日期
     * @return yyyy-MM-dd
     */
    public static String getYesterdayOfLastYear(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR,-1);
        cal.add(Calendar.DATE,-1);
        Date d = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }



    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime,
                                         Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.equals(begin)||(date.after(begin)&& date.before(end))||(date.equals(end))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取最近一年的起始时间（不包括本月）
     * @return 例如当前日期为2020-9-21，则返回：2019-09-01 00:00:00、2020-08-31 23:59:59
     */
    public static String[] getNearlyYear() {
        String beginMonth = getLastMonth(12);
        String endMonth = getLastMonth();

        String[] arr = new String[2];
        arr[0] = beginMonth + "-01 00:00:00";
        arr[1] = getLastDayOfMonth(endMonth) + " 23:59:59";
        return arr;
    }

    /**
     * 获取最近一年的上一周期的起始时间
     * 例如当前日期为2020-9-21，则返回2018-09-01 00:00:00、2019-08-31 23:59:59
     * @return
     */
    public static String[] getNearlyYearOfLast() {
        String beginMonth = getLastMonth(24);
        String endMonth = getLastMonth(13);

        String[] arr = new String[2];
        arr[0] = beginMonth + "-01 00:00:00";
        arr[1] = getLastDayOfMonth(endMonth) + " 23:59:59";
        return arr;
    }

    /**
     * 获取最近半年的起始时间（不包括本月）
     * 例如当前日期为2020-9-21，则返回2018-03-01 00:00:00、2019-08-31 23:59:59
     */
    public static String[] getNearlyHalfYear() {
        String beginMonth = getLastMonth(6);
        String endMonth = getLastMonth();

        String[] arr = new String[2];
        arr[0] = beginMonth + "-01 00:00:00";
        arr[1] = getLastDayOfMonth(endMonth) + " 23:59:59";
        return arr;
    }

    /**
     * 获取最近半年的上一周期的起始时间
     * 例如当前日期为2020-9-21，则返回2018-09-01 00:00:00、2019-02-29 23:59:59
     */
    public static String[] getNearlyHalfYearOfLast() {
        String beginMonth = getLastMonth(12);
        String endMonth = getLastMonth(7);

        String[] arr = new String[2];
        arr[0] = beginMonth + "-01 00:00:00";
        arr[1] = getLastDayOfMonth(endMonth) + " 23:59:59";
        return arr;
    }

    /**
     * 正则表达式判断传入时间的格式-默认是yyyy-MM-dd HH:mm:ss
     * 用于时间转成Date类型时使用
     * @param date
     * @return
     */
    public static String getTimeFormat(String date){
        String format = "yyyy-MM-dd HH:mm:ss";

        String pathOne="\\d{4}-\\d{2}-\\d{2}";//定义匹配规则
        Pattern patternOne=Pattern.compile(pathOne);//实例化Pattern
        Matcher matcherOne=patternOne.matcher(date);//验证字符串内容是否合法
        if(matcherOne.matches())//使用正则验证
        {
            format= "yyyy-MM-dd";
            return format;
        }

        String pathTwo="\\d{8}";//定义匹配规则
        Pattern patternTwo=Pattern.compile(pathTwo);//实例化Pattern
        Matcher matcherTwo=patternTwo.matcher(date);//验证字符串内容是否合法
        if(matcherTwo.matches())//使用正则验证
        {
            format= "yyyyMMdd";
            return format;
        }
        String pathThree="\\d{4}";//定义匹配规则
        Pattern patternThree=Pattern.compile(pathThree);//实例化Pattern
        Matcher matcherThree=patternThree.matcher(date);//验证字符串内容是否合法
        if(matcherThree.matches())//使用正则验证
        {
            format= "yyyy";
            return format;
        }

        String pathFour="\\d{4}-\\d{2}";//定义匹配规则
        Pattern patternFour=Pattern.compile(pathFour);//实例化Pattern
        Matcher matcherFour=patternFour.matcher(date);//验证字符串内容是否合法
        if(matcherFour.matches())//使用正则验证
        {
            format= "yyyy-MM";
        }
        return format;
    }

    /**
     * 格式化秒为 hh:MM:ss
     * @param seconds
     * @return
     */
    public static String secondToTime(int seconds) {
        if(seconds < 0) {
            throw new IllegalArgumentException("Seconds must be a positive number!");
        } else {
            int hour = seconds / 3600;
            int other = seconds % 3600;
            int minute = other / 60;
            int second = other % 60;
            StringBuilder sb = new StringBuilder();
            if(hour < 10) {
                sb.append("0");
            }

            sb.append(hour);
            sb.append(":");
            if(minute < 10) {
                sb.append("0");
            }

            sb.append(minute);
            sb.append(":");
            if(second < 10) {
                sb.append("0");
            }

            sb.append(second);
            return sb.toString();
        }
    }

    public static void main(String[] args)throws Exception {
        String[] arr = getNearlyHalfYear();
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        String[] arr1 = getNearlyHalfYearOfLast();
        System.out.println(arr1[0]);
        System.out.println(arr1[1]);
        System.out.println(getWeekNum("2020-11-01","2020-11-31"));
    }
}
