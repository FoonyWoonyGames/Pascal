package Util;

import java.util.Calendar;
import java.util.Date;

public class PTime {
	public static final int MONTH_JANUARY = 1;
	public static final int MONTH_FEBRUARY = 2;
	public static final int MONTH_MARCH = 3;
	public static final int MONTH_APRIL = 4;
	public static final int MONTH_MAY = 5;
	public static final int MONTH_JUNE = 6;
	public static final int MONTH_JULY = 7;
	public static final int MONTH_AUGUST = 8;
	public static final int MONTH_SEPTEMBER = 9;
	public static final int MONTH_OCTOBER = 10;
	public static final int MONTH_NOVEMBER = 11;
	public static final int MONTH_DECEMBER = 12;
	
	public static int getMonth() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		
		return month+1;
	}
	public static int getYear() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		
		return year;
	}
	public static int getDate() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return day;
	}
	public static int getHour() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		return hour;
	}
	public static int getMinute() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int minute = cal.get(Calendar.MINUTE);
		
		return minute;
	}
}
