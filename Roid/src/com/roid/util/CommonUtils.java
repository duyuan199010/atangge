package com.roid.util;

/**
 * 用于判断第二次点击时是否太快，默认500ms没只能点击一次，快递点击第二次不响应事件
 * @author Administrator
 *
 */
public class CommonUtils {
	private static long lastClickTime = 0;
	private static long minTimeInterval = 500;

	public synchronized static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long duration = time - lastClickTime;
		lastClickTime = System.currentTimeMillis();
		if (duration > 0 && duration < minTimeInterval)
			return true;
		return false;
	}
}
