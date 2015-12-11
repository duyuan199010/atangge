package com.strod.yssl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String formatDateToString(long time) {
		String interval = "";
		//当前时间和time时间的间隔
		long millis = System.currentTimeMillis() - time;
		if (millis / 1000 < 10 && millis / 1000 >= 0) {
			// 如果时间间隔小于10秒则显示“刚刚”millis/10得出的时间间隔的单位是秒
			interval = "刚刚";

		} else if (millis / 1000 < 60 && millis / 1000 > 0) {
			// 如果时间间隔小于60秒则显示多少秒前
			int se = (int) ((millis % 60000) / 1000);
			interval = se + "秒前";

		} else if (millis / 60000 < 60 && millis / 60000 > 0) {
			// 如果时间间隔小于60分钟则显示多少分钟前
			int m = (int) ((millis % 3600000) / 60000);// 得出的时间间隔的单位是分钟
			interval = m + "分钟前";

		} else if (millis / 3600000 < 24 && millis / 3600000 > 0) {
			// 如果时间间隔小于24小时则显示多少小时前
			int h = (int) (millis / 3600000);// 得出的时间间隔的单位是小时
			interval = h + "小时前";

		} else if (isTimeInThisYear(time)){
			// 大于24小时，在今年内，则显示正常的时间，但是不显示秒
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			interval = sdf.format(new Date(time));
		}else{
			// 不在今年内，则显示正常的时间，但是不显示秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			interval = sdf.format(new Date(time));
		}
		return interval;
	}
	
	private static boolean isTimeInThisYear(long millis){

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		int year = c.get(Calendar.YEAR);
		if (year-getCurrentYear()==0){//在同一年
			return true;
		}else{
			return false;
		}
	}

	private static int getCurrentYear(){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		return c.get(Calendar.YEAR);
	}
}
