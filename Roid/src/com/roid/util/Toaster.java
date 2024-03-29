package com.roid.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 自定义Toast
 */
public class Toaster {

	private static Toast sToast;

	private static void init(Context context) {
		if (sToast == null) {
			sToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
		}
	}
	
	public static void showToast(Context context, String text) {
		init(context);
		sToast.setText(text);
		sToast.show();

	}

	public static void showResIdToast(Context context, int resId) {
		init(context);
		sToast.setText(resId);
		sToast.show();
	}

	/**
	 * 1.1默认效果
	 * 
	 * 显示字符串，默认短时间，居底。
	 */
	public static void showDefToast(Context context, String text) {
		showToast(context,text);
	}

	/**
	 * 1.2默认效果
	 * 
	 * 自定义存活时间 显示字符串，居底。
	 */
	public static void showDefToastByDuration(Context context, String text,
			int duration) {
		Toast.makeText(context, text, duration).show();
	}

	/**
	 * 1.3默认效果
	 * 
	 * 显示字符串资源，默认短时间，居底。
	 */
	public static void showDefToast(Context context, int resId) {
		showResIdToast(context,resId);
	}

	/**
	 * 1.4默认效果
	 * 
	 * 自定义存活时间 显示字符串资源，居底。
	 */
	public static void showDefToast(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}

	/**
	 * 2.1自定义显示位置效果并自定义存活时间
	 * 
	 * 显示字符串
	 */
	public static void showToastByGravity(Context context, String text,
			int gravity, int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	/**
	 * 2.2自定义显示位置效果并自定义存活时间
	 * 
	 * 显示字符串资源
	 */
	public static void showToastByGravity(Context context, int resId,
			int gravity, int duration) {
		Toast toast = Toast.makeText(context, resId, duration);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	/**
	 * 3.1带图片效果并自定义存活时间
	 * 
	 * 居中显示字符串
	 */
	public static void showImgToast(Context context, String text, int imgResId,
			int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView image = new ImageView(context);
		image.setImageResource(imgResId);
		toastView.addView(image, 0);
		toast.show();
	}

	/**
	 * 3.2带图片效果并自定义存活时间
	 * 
	 * 居中显示字符串资源
	 */
	public static void showImgToast(Context context, int resId, int imgResId,
			int duration) {
		Toast toast = Toast.makeText(context, resId, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView image = new ImageView(context);
		image.setImageResource(imgResId);
		toastView.addView(image, 0);
		toast.show();
	}
}
