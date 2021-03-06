/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyyMM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		
		long beforeTime =(before==null?0:before.getTime());
		long afterTime = (after==null?0:after.getTime());
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/****
	 * 
	 * @param before
	 * @return format(before,'YYYY-MM-DD 00:00:00'
	 */
	
	public static Date getDateStart(Date before) {
		if (before == null) return null;
		Date after = parseDate(DateUtils.formatDate(before) + " 00:00:00");
		return after;
	}
	
	/****
	 * 
	 * @param before
	 * @return format(before,'YYYY-MM-DD 23:59:59'
	 */
	
	public static Date getDateEnd(Date before) {
		if (before == null) return null;
		Date after = parseDate(DateUtils.formatDate(before) + " 23:59:59");
		return after;
	}	

	//add by zhouxj 2016/10/26 start
	/**
	 * 获取指定日期的前一个月
	 * @param date
	 * @return yyyyMM
	 */
	public static String getLastDate(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,-1);
		return formatDate(cal.getTime(),"yyyyMM");
	}
	 /**
     * 获取当前日期的前一个月对应日期
     * @return date
     * @author liangcq
     * @date 2018-02-24
     */
    public static Date getLastMonthDate(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        return cal.getTime();
    }
    public static Date getLastMonthDate(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,-1);
        return cal.getTime();
    }
	
	/**
	 * 获取指定天数前的日期
	 * @author qinfei
	 * @param days 指定的天数
	 * @return
	 */
	public static Date getBeforeDaysDate(int days){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -days);
		return cal.getTime();
	}
	
	/**
	 * 获取月份的第一天eg:2016/10/1
	 * @param date
	 * @return
	 */
	public static Date getFistDay(Date date){
		if(date==null){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return parseDate(formatDate(cal.getTime())+" 00:00:00");
	}
	
	public static Date getLastDay(Date date){
		if(date==null){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DATE));
		
		return parseDate(formatDate(cal.getTime())+" 23:59:59");
	}
	//add by zhouxj 2016/10/26 end
	/**
	 * 获取指定的日期  
	 * DateUtils.getLastDate(date,Calendar.YEAR, -3);
	 * @author fyf
	 *  
	*/
	public static Date getLastDate(Date date,int field,int amout){
		if(date==null){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amout);
		return parseDate(formatDate(cal.getTime())+" 23:59:59");
		
	}
	public static Date getNewDate(Date date,int field,int amout){
		if(date==null){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amout);
		return cal.getTime();
		
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(DateUtils.parseDate("201712"));
//		System.out.println(DateUtils.formatDate(DateUtils.getNewDate(DateUtils.parseDate("201712"),Calendar.MONTH,1),"yyyyMM"));
//		Calendar cal = Calendar.getInstance();
		// 获取上个月份
//		cal.add(Calendar.MONTH, -1);
//		System.out.println(formatDate(cal.getTime(), "yyyyMM"));
//		System.out.println(formatDate(getBeforeDaysDate(1), "yyyy-MM-dd"));
		System.out.println(getLastMonthDate());
	}
}
