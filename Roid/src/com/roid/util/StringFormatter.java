package com.roid.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class StringFormatter {
	
	private static class TimeZoneDateFormat {
		public SimpleDateFormat mDF_Time = new SimpleDateFormat("HH:mm:ss");
		public SimpleDateFormat mDF_TimeSimple = new SimpleDateFormat("HH:mm");
		public SimpleDateFormat mDF_TimeInMS = new SimpleDateFormat("HH:mm:ss.SSS");
		public SimpleDateFormat mDF_Date = new SimpleDateFormat("yyyy-MM-dd");
		//public SimpleDateFormat mDF_DateSimple = new SimpleDateFormat("yyyyMMdd");
		public SimpleDateFormat mDF_DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public SimpleDateFormat mDF_SimpleDateTime = new SimpleDateFormat("MM/dd HH:mm");
		public SimpleDateFormat mDF_SimpleDateTimeShort = new SimpleDateFormat("MMddHHmm");
		
		public SimpleDateFormat mDF_DateGMT0 = new SimpleDateFormat("yyyy-MM-dd");
		
		public TimeZoneDateFormat()
		{
			/* Configure time zone with GTConfig settings */
//			setTimeZone(GTConfig.instance().getTimeZone());
			setTimeZone("GMT");
			
			final TimeZone tzGMT0 = TimeZone.getTimeZone("GMT+0");
			mDF_DateGMT0.setTimeZone(tzGMT0);
		}
		
		public void setTimeZone(String timeZoneStr)
		{
			final TimeZone tz = TimeZone.getTimeZone(timeZoneStr);
			mDF_Time.setTimeZone(tz);
			mDF_TimeSimple.setTimeZone(tz);
			mDF_TimeInMS.setTimeZone(tz);
			mDF_Date.setTimeZone(tz);
			//mDF_DateSimple.setTimeZone(tz);
			mDF_DateTime.setTimeZone(tz);
			mDF_SimpleDateTime.setTimeZone(tz);
			mDF_SimpleDateTimeShort.setTimeZone(tz);
		}
	}
	
	private static TimeZoneDateFormat mDF = new TimeZoneDateFormat();

	public static void setTimeZone(String tzString)
	{
		mDF.setTimeZone(tzString);
	}
	
	@SuppressLint("DefaultLocale")
	public static String toPrice(double data, boolean bWithComma) {
		if (DoubleConverter.isZero(data))
			return "0.00";
		
		if(!bWithComma)
			return to2Decimal(data);

		return String.format("%1$,.2f", data);
	}
	
	public static String toPrice(double data) {
		return toPrice(data, true);
	}
	
	@SuppressLint("DefaultLocale")
	public static String to2Decimal(double data) {
		if (DoubleConverter.isZero(data))
			return "0.00";
		return String.format("%1$.2f", data);
	}
	
	@SuppressLint("DefaultLocale")
	public static String to4Decimal(double data) {
		if (DoubleConverter.isZero(data))
			return "0.0000";
		return String.format("%1$.4f", data);
	}
		
	public static String toTime(Date date) {
		return mDF.mDF_Time.format(date);
	}
	
	public static String toTimeSimple(Date date) {
		return mDF.mDF_TimeSimple.format(date);
	}
	
	public static String toTimeInMs(Date date) {
		return mDF.mDF_TimeInMS.format(date);
	}
	
	public static String toDate(Date date) {
		return mDF.mDF_Date.format(date);
	}
	
	public static String toDateGMT0(Date date) {
		return mDF.mDF_DateGMT0.format(date);
	}

//	public static String toDateSimple(Date date) {
//		return mDF.mDF_DateSimple.format(date);
//	}

	public static String toDateTime(Date date) {
		return mDF.mDF_DateTime.format(date);
	}

	public static String toSimpleDateTime(Date date) {
		return mDF.mDF_SimpleDateTime.format(date);
	}

	public static String toSimpleDateTimeShort(Date date) {
		return mDF.mDF_SimpleDateTimeShort.format(date);
	}
	
}
