package com.strod.yssl.clientcore;


import com.roid.AbsApplication;
import com.roid.R;
import com.roid.util.Toaster;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络监控类
 * @author Administrator
 *
 */
public class NetworkMonitor {

	/**
	 * 判断网络是否已连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkReady(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null)
			return false;
		return activeNetwork.isConnected()&&activeNetwork.isAvailable();
	}
	
	/**
	 * 判断是否连接网络，无网络连接给予提示信息
	 * @param context
	 * @return
	 */
	public static boolean hasNetWork(){
		if(isNetworkReady(AbsApplication.getApplication())){
			return true;
		}
		Toaster.showDefToast(AbsApplication.getApplication(), R.string.error_network_connection);
		return false;
	}
	/**
	 * 判断是否有网络，不提示toast
	 * @return
	 */
	public static boolean hasNetWorkNoToast(){
		if(isNetworkReady(AbsApplication.getApplication())){
			return true;
		}
		return false;
	}
}