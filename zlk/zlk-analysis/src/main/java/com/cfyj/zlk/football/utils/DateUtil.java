package com.cfyj.zlk.football.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;


/**
 * 时间格式化工具类
 * @author xiami
 * @date   2015-01-20
 * 
 */
public class DateUtil {
	
	private static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private static SimpleDateFormat long2Sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat long3Sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private static SimpleDateFormat long4Sdf = new SimpleDateFormat("yyyyMMdd");

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");

	
	private static SimpleDateFormat short2Sdf = new SimpleDateFormat("MM月dd日");
	
	private static SimpleDateFormat short3Sdf = new SimpleDateFormat("MM-dd");
	
	private static SimpleDateFormat short4Sdf = new SimpleDateFormat("MM-dd HH");
	
	private static SimpleDateFormat short5Sdf = new SimpleDateFormat("yyMMdd");
	
	private static SimpleDateFormat short6Sdf = new SimpleDateFormat("yyMMdd HH:mm");
	
	
	public static String dateToLongDateStr(Date date)  {
		if(date==null) {
			return"";
		}
		return longSdf.format(date);
	}
	
	public static String dateToLong2DateStr(Date date)  {
		if(date==null) {
			return "";
		}
		return long2Sdf.format(date);
	}

	public static String dateToDateStr(Date date)  {
		if(date==null) {
			return"";
		}
		return sdf.format(date);
	}

	public static String dateToLong4SdfDate(Date date)  {
		if(date==null) {
			return"";
		}
		return long4Sdf.format(date);
	}

	public static String dateToShortDate(Date date)  {
		if(date==null) {
			return"";
		}
		return shortSdf.format(date);
	}
	
	public static String dateToShort2Date(Date date)  {
		if(date==null) {
			return"";
		}
		return short2Sdf.format(date);
	}
	public static String dateToShort3Date(Date date)  {
		if(date==null) {
			return"";
		}
		return short3Sdf.format(date);
	}
	public static String dateToShort4Date(Date date)  {
		if(date==null) {
			return"";
		}
		return short4Sdf.format(date);
	}
	
	public static String dateToShort5Date(Date date)  {
		if(date==null) {
			return"";
		}
		return short5Sdf.format(date);
	}
	
	public static String dateToShort6Date(Date date)  {
		if(date==null) {
			return"";
		}
		return short6Sdf.format(date);
	}
	
	

	/**
	 * 将String转换成Date格式的字符串
	 * @author zcb
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Date StringToDate(String time,String formatStr)throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		return simpleDateFormat.parse(time);
	}
	public static Date StringToDate(String time)throws Exception {
		if(StringUtils.isBlank(time)) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time);
//		return sdf.parse(time);
	}
	
	public static Date Long2StringToDate(String time)throws Exception {
		if(StringUtils.isBlank(time)) {
			return null;
		}
		return long2Sdf.parse(time);
	}
	
	public static Date Long4StringToDate(String time)throws Exception {
		if(StringUtils.isBlank(time)) {
			return null;
		}
		return long4Sdf.parse(time);
	}
	
	public static Date Long3StringToDate(String time)throws Exception {
		if(StringUtils.isBlank(time)) {
			return null;
		}
		return long3Sdf.parse(time);
	}
	
	public static Date short5SdfStringToDate(String time)throws Exception {
		if(StringUtils.isBlank(time)) {
			return null;
		}
		return short5Sdf.parse(time);
	}
	
	public static Date short6SdfStringToDate(String time)throws Exception {
		if(StringUtils.isBlank(time)) {
			return null;
		}
		return short6Sdf.parse(time);
	}
	
	
	
	
	/**
	 * 将date转换成formate格式的字符串
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String TimeToString(String date,int d){
		try {
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(new Date(formate.parse(date).getTime() + d * 60 * 60 * 1000));
		} catch (ParseException e) {
			return "0";
		}
	}
	public static Long TimeToLong(String date,int d){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return new Date(formate.parse(date).getTime() + d * 60 * 60 * 1000).getTime()/1000;
		} catch (ParseException e) {
			return null;
		}
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 把秒数转换成mm:ss的格式
	 * @param seconds 秒数
	 * @return mm:ss的格式串
	 */
	public static String secondToTime(int seconds){
		if (seconds == 0) {
			return "00:00";
		}
		int minute = seconds/60;
		int second = seconds%60;
		return String.format("%02d", minute) + ":" + String.format("%02d", second);
	}
	
	/**
	 * 比较两个日期相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static int differenceDay(Date date1,Date date2) throws ParseException {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date1);
//		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		
//		Calendar calendar2 = Calendar.getInstance();
//		calendar2.setTime(date1);
//		calendar2.set(Calendar.MILLISECOND, 0);
//		calendar2.set(Calendar.SECOND, 0);
//		calendar2.set(Calendar.MINUTE, 0);
//		calendar2.set(Calendar.HOUR_OF_DAY, 0);
		
//		long diff = (calendar.getTime().getTime()-calendar2.getTime().getTime())/86400000L;
		
		long diff =(shortSdf.parse(shortSdf.format(date1)).getTime() - shortSdf.parse(shortSdf.format(date2)).getTime())/86400000L;
		
		return (int) diff;	
		
	}
	
	/**
	 * 获取X天以前的日期
	 * @param date
	 */
	public static Date getBeforDate(Date date,int num) {
		
		DateTime dt = new DateTime(date);
		return dt.minusDays(num).toDate();
		
	}
	
	
	/**
	 * 比较两个日期的大小，
	 * @param date1
	 * @param date2
	 * @return 1 大于 0等于 -1小于
	 */
	public static int compareDate(Date date1,Date date2) {
		
		long com = date1.getTime()-date2.getTime();
		if(com>0) {
			return 1;
		}else if(com == 0) {
			return 0;
		}else {
			return -1;
		}
		
	}
	
	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getCurrentMouth() {
		
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1; //因为mouth中的月份是从0开始的
	}
	
	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getCurrentYear() {
		
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR); //因为mouth中的月份是从0开始的
	}
	
	
}
