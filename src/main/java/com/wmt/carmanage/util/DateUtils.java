package com.wmt.carmanage.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @description	
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	private final static SimpleDateFormat sdfhs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

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
			if(date==null){
				return null;
			}
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
	 * 得到年份字符串 格式（yyyy）
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}
	
	/**
	 * 得到月份字符串 格式（MM）
	 */
	public static String getMonth(Date date) {
		return formatDate(date, "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}
	
	/**
	 * 得到天字符串 格式（dd）
	 */
	public static String getDay(Date date) {
		return formatDate(date, "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek(Date date) {
		return formatDate(date, "E");
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
		} catch (Exception e) {
			e.printStackTrace();
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
	public static int getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (int) ((afterTime - beforeTime) / 86400000);
	}
	
	/**
	 * HH:mm
	 * @param beginTimeStr
	 * @param endTimeStr
	 */
	public static boolean isBelong(String beginTimeStr,String endTimeStr){
	    SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
	    Date now =null;
	    Date beginTime = null;
	    Date endTime = null;
	    try {
	        now = df.parse(df.format(new Date()));
	        beginTime = df.parse(beginTimeStr);
	        endTime = df.parse(endTimeStr);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return belongCalendar(now, beginTime, endTime);
	}
	
	/**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
    	Calendar t_24 = Calendar.getInstance(); 
    	t_24.set(Calendar.HOUR_OF_DAY, 24);
    	
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        
        if (begin.after(end) && (date.after(begin)||date.after(t_24)||date.before(end))){
        	return true;
        }
        if (begin.before(end) && (date.after(begin) && date.before(end))) {
            return true;
        } 
        return false;
    }
    
    
    /**
     * 根据指定日期--获取指定指定的本周内的时间
     * <p>注意已经设置周一为本周的第一天
     * @param date --可以为字符串,也支持时间
     * @param CalendarInt
     * @return
     */
    public static Date getDayOfWeek(Object date,int CalendarInt){
    	return getDayOfWeek(date, CalendarInt, Calendar.MONDAY);
    }
    
    /**
     * 根据指定日期--获取指定指定的本周内的时间
     * @param date
     * @param CalendarInt
     * @return
     */
    public static Date getDayOfWeek(Object date,int CalendarInt,int firstDayOfWeek){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setFirstDayOfWeek(firstDayOfWeek);
    	if(date instanceof Date){
    		calendar.setTime((Date)date);
    	}else if(date instanceof String){
    		calendar.setTime(parseDate(date));
    	}else{
    		throw new IllegalArgumentException("date必须为字符串或者日期!");
    	}
    	calendar.set(Calendar.DAY_OF_WEEK, CalendarInt);
    	return calendar.getTime();
    }
    
    
    /**
     * 获取当前日期--没有时分秒,默认00:00:00
     * @param date
     * @return
     */
    public static Date getDate(Date date){
    	return parseDate(formatDate(date));
    }
    
    /**
     * 判断一个日期是否是周几
     * @param dateStr
     * @param CalendarInt 周几
     * @return
     */
    public static boolean dayOfWeek(int CalendarInt,String dateStr){
    	Date parseDate = parseDate(dateStr);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(parseDate);
    	if(calendar.get(Calendar.DAY_OF_WEEK)==CalendarInt){
    		return true;
    	}
		return false;
    }
    
    /**
     * 当前季度的开始日期字符串
     * @return
     */
    public static String getQuarterStartDate(Object...date) {
    	Calendar c = Calendar.getInstance();
    	if(date.length==1){
    		Object o = date[0];
    		if(o instanceof Date){
    			c.setTime((Date)o);
    		}else if(o instanceof String){
    			Date parseDate = parseDate(o);
    			if(parseDate!=null){
    				c.setTime(parseDate);
    			}
    		}
    	}
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate(now);
    }
    
    /**
     * 当前季度的结束日期
     * @return
     */
    public static String getQuarterEndDate(Object...date) {
        Calendar c = Calendar.getInstance();
        if(date.length==1){
    		Object o = date[0];
    		if(o instanceof Date){
    			c.setTime((Date)o);
    		}else if(o instanceof String){
    			Date parseDate = parseDate(o);
    			if(parseDate!=null){
    				c.setTime(parseDate);
    			}
    		}
    	}
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate(now);
    }

    /**
     * java计算两个时间相差（天、小时、分钟、秒）
     * @param startTime 開始时间
     * @param endTime 结束时间
     * @param format 时间格式
     * @return String
     */
    public static String dateDiff(String startTime, String endTime,     
            String format) {     
        // 按照传入的格式生成一个simpledateformate对象     
        SimpleDateFormat sd = new SimpleDateFormat(format);     
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数     
        long nh = 1000 * 60 * 60;// 一小时的毫秒数     
        long nm = 1000 * 60;// 一分钟的毫秒数     
        long ns = 1000;// 一秒钟的毫秒数     
        long diff;     
        long day = 0;     
        long hour = 0;     
        long min = 0;     
        long sec = 0;
        String diffstr = "";
        // 获得两个时间的毫秒时间差异     
        try {     
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();     
            day = diff / nd;// 计算差多少天     
            hour = diff % nd / nh + day * 24;// 计算差多少小时     
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟     
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
			diffstr = day + "天" + (hour - day * 24) + "时"+ (min - day * 24 * 60) + "分";
        } catch (ParseException e) {     
            e.printStackTrace();     
        }
		return diffstr;     
    }
    /**
     * 获取该时间（date）的周五-下周四
     * @param date
     * @return
     */
    public static String getWeekMonAndSun(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	cal.add(Calendar.DATE, -1);
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); 
    	String monday =df.format(cal.getTime());
    	//map.put("monday", df.format(cal.getTime()));
    	// 这种输出的是上个星期周日的日期，
    	if(date.before(cal.getTime())){// true  date<cal.getTime()
    		// 减少一个星期
    		cal.add(Calendar.WEEK_OF_YEAR, -1);
        	cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); 
        	monday =df.format(cal.getTime());        	
    	}
    	// 增加一个星期
    	cal.add(Calendar.WEEK_OF_YEAR, 1);
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
    	String sunday =df.format(cal.getTime());
    	//map.put("sunday", df.format(cal.getTime()));
    	return monday+"~"+sunday;
    }
    /**
     * 获取该时间（date）的前count周的周五-下周四(不包括该时间的周五-下周四)
     * @param date
     * @return
     */
    public static List<String> getWeekMonAndSunCount(Date date,Integer count) {
    	String week = getWeek(date);
    	Date a = date;
    	//Map map = new HashMap();
    	if("星期一".endsWith(week)){
    		a= DateUtils.addDays(date, -1); 
    	}else if ("星期二".endsWith(week)) {
    		a= DateUtils.addDays(date, -2); 
		}else if ("星期三".endsWith(week)) {
			a= DateUtils.addDays(date, -3); 
		}else if ("星期四".endsWith(week)) {
			a= DateUtils.addDays(date, -4); 
		}
    	List<String> list = new LinkedList<>();
    	for (int i = 1; i < count; i++) {
    		list.add(getWeekMonAndSun(DateUtils.addDays(a, -7*i)));
		}
    	return list;
    }
    /**
     * 获取当前月前六个月月份集合(如:201709)
     * @return
     */
    public static List<String> getOldSixMonth() {
    	List<String> list = new ArrayList<>();
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for(int i = 0; i < 6; i++) {  
        	calendar.add(Calendar.MONTH, -1);
             String mon = format.format(calendar.getTime());
             list.add(i, mon);
        }
    	return list;
    }
    /**
     * 获取当前月n个月月份集合(如:2017-09)
     * @return
     */
    public static List<String> getOldMonth(Integer n,Date date) {
    	List<String> list = new ArrayList<>();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        list.add(0, format.format(calendar.getTime()));
        for(int i = 1; i < n; i++) {  
        	 calendar.add(Calendar.MONTH, -1);
             String mon = format.format(calendar.getTime());
             list.add(i, mon);
        }
    	return list;
    }
    /**
     * 获取上一个月的月份
     * @return
     */
    public static String getOldOneMonth() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        String mon = format.format(calendar.getTime());
    	return mon;
    }
    /**
     * 根据时间获取上一个月的月份
     * @return
     */
    public static String getUpMonth(Date date) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, -1);
    	String mon = format.format(calendar.getTime());
    	return mon;
    }
    /**
     * 根据年月查询该年月的开始时间
     * @param year
     * @param month
     * @return
     */
    public static Date getBeginTime(int year, int month) {  
        YearMonth yearMonth = YearMonth.of(year, month);  
        LocalDate localDate = yearMonth.atDay(1);  
        LocalDateTime startOfDay = localDate.atStartOfDay();  
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));  
        return Date.from(zonedDateTime.toInstant());  
    }  
    /**
     * 根据年月查询该年月的结束时间
     * @param year
     * @param month
     * @return
     */
    public static Date getEndTime(int year, int month) {  
        YearMonth yearMonth = YearMonth.of(year, month);  
        LocalDate endOfMonth = yearMonth.atEndOfMonth();  
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);  
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));  
        return Date.from(zonedDateTime.toInstant());  
    }
    /**
     * 当年的开始时间
     * @return
     */
    public static Date getCurrYearFirstByBate(Date date){  
        Calendar currCal=Calendar.getInstance();    
        currCal.setTime(date);
        int currentYear = currCal.get(Calendar.YEAR);  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, currentYear);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }

	 /**
	  *获取某年第一天日期
     * @param year 年份
     * @return Date
     */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}


	/**
	 * 获取当年的最后一天
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}
	/**
     * 当年的开始时间
     * @return
     */
    public static Date getCurrYearFirst(){
    	return getCurrYearFirstByBate(new Date());
    } 
    /**
     * 根据日期获取该月的天数
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return actualMaximum;  
    } 
    /**
     * 根据日期获取去年的开始时间
     * @param date
     * @return
     */
    public static Date getOldYearFirst(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.YEAR, -1);
    	int oldYear = calendar.get(Calendar.YEAR);
    	Calendar instance = Calendar.getInstance();
    	instance.clear();
    	instance.set(Calendar.YEAR, oldYear);
        Date oldDate = instance.getTime();
        return oldDate;
    } 
    /**
     * 根据日期获取去年的结束时间
     * @param date
     * @return
     */
    public static Date getOldYearEnd(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.YEAR, -1);
    	int oldYear = calendar.get(Calendar.YEAR);
    	Calendar instance = Calendar.getInstance();
    	instance.clear();
    	Date nowEnd = null;  
        try {  
        	instance.set(Calendar.YEAR, oldYear);
        	instance.set(Calendar.MONTH, 11);  
        	instance.set(Calendar.DATE, 31);  
        	nowEnd = sdfhs.parse(sdf.format(instance.getTime())+" 23:59:59");
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
    	return nowEnd;
    }
    /**
     * 根据日期字符串获取昨天的日期
     * @return
     */
    public static Date getYesterday(String powerTime) {
    	Calendar calendar = Calendar.getInstance();
    	Date time = new Date();
    	try {
			calendar.setTime(sdf.parse(powerTime));
			calendar.add(Calendar.DATE,-1);
	    	time = calendar.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
    }
	/**
	 * 根据日期字符串获取明天的日期
	 * @return
	 */
	public static Date getTomorrowday(String powerTime) {
		Calendar calendar = Calendar.getInstance();
		Date time = new Date();
		try {
			calendar.setTime(sdf.parse(powerTime));
			calendar.add(Calendar.DATE,1);
			time = calendar.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
	/**
	 * 获取某段时间内的所有日期
	 * @param start
	 * @param end
	 * @return 字符类型
	 */
	public static List<String> getXAxis(Date start,Date end){
		List<String> xAxis=new ArrayList<>();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		xAxis.add(formatter.format(start));
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(start);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(end);
		// 测试此日期是否在指定日期之后
		while (end.after(calBegin.getTime()))  {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			xAxis.add(formatter.format(calBegin.getTime()));
		}
		return xAxis;
	}
	/**
	 * 获取某段时这里写代码片间内的所有日期
	 * @param dBegin
	 * @param dEnd
	 * @return 日期类型
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime()))  {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}


    public static void main(String[] args) {
    	//List<String> list = getWeekMonAndSunCount(DateUtils.addDays(new Date(), 7), 13);
    	//System.out.println(list);
    	//System.out.println(list.size());
    	String diff = dateDiff("2017-07-07", "2017-07-22", "yyyy-MM-dd");
    	List<String> oldMonth = getOldMonth(12, DateUtils.addDays(new Date(), 99));
    	System.err.println(oldMonth);
	}
}
