package com.roid.util;

import java.text.DecimalFormat;

public class DoubleConverter {
	private static final DecimalFormat mDF10 = new DecimalFormat("#.##########");
	public static final double RELATIVE_SMALL_DOUBLE_VALUE = 0.0000000001d;
	
	public static double normalize(double value) {
		try {
			return Double.valueOf(mDF10.format(value));
		} catch (NumberFormatException e) {
			return value;
		}
	}
	
	public static float normalize(float value) {
		try {
			return Float.valueOf(mDF10.format(value));
		} catch (NumberFormatException e) {
			return value;
		}
	}
	
	public static boolean isZero(double value) {
		return (value < RELATIVE_SMALL_DOUBLE_VALUE && value > -RELATIVE_SMALL_DOUBLE_VALUE);
	}
	
	public static boolean isGTZero(double value) {
		return (value > RELATIVE_SMALL_DOUBLE_VALUE);
	}
	
	public static boolean isLTZero(double value) {
		return (value < -RELATIVE_SMALL_DOUBLE_VALUE);
	}
	
	public static boolean isGT(double v1, double v2, int digits)
	{
		final double power = Math.pow(10, digits);
		final long v1L = Math.round(v1 * power);
		final long v2L = Math.round(v2 * power);
		return (v1L > v2L);
	}
	
	public static boolean isLT(double v1, double v2, int digits)
	{
		final double power = Math.pow(10, digits);
		final long v1L = Math.round(v1 * power);
		final long v2L = Math.round(v2 * power);
		return (v1L < v2L);
	}

	public static boolean isEqual(double v1, double v2, int digits)
	{
		final double power = Math.pow(10, digits);
		final long v1L = Math.round(v1 * power);
		final long v2L = Math.round(v2 * power);
		return (v1L == v2L);
	}
	
	public static double ceil(int digits, double value) {
		final double pow10 = Math.pow(10, digits);
		double powVal = value * pow10 - 0.01;		// Minus adjust value
		powVal = Math.ceil(powVal);
		return powVal / pow10;
	}

	public static double floor(int digits, double value) {
		final double pow10 = Math.pow(10, digits);
		double powVal = value * pow10 + 0.01;		// Add adjust value
		powVal = Math.floor(powVal);
		return powVal / pow10;
	}

}
