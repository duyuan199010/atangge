package com.roid.util;

import com.roid.config.AbsConfig;

import android.util.Log;

public class DebugLog {

	private static boolean allowDebug = AbsConfig.mAllowDebug;

	public static void i(String tag, String msg) {
		if (allowDebug) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String format, Object... args) {
		if (allowDebug) {
			Log.i(tag, String.format(format, args));
		}
	}

	public static void d(String tag, String msg) {
		if (allowDebug) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String format, Object... args) {
		if (allowDebug) {
			Log.d(tag, String.format(format, args));
		}
	}

	public static void e(String tag, String msg) {
		if (allowDebug) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String format, Object... args) {
		if (allowDebug) {
			Log.e(tag, String.format(format, args));
		}
	}
}
