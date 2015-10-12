package com.strod.yssl.clientcore;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.roid.AbsApplication;
import com.roid.util.DebugLog;
import com.roid.util.NetMonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class NetworkReceiver extends BroadcastReceiver{
	
	public static final String TAG = "NetworkReceiver";

//		private IntentFilter mConnectivityFilter;
//
//		public NetworkReceiver() {
//			/* Registers BroadcastReceiver to track network connection changes. */
//			mConnectivityFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//		}

		@Override
		public void onReceive(Context context, Intent intent) {
			final boolean networkReady = NetMonitor.isNetworkConnected(context);
			if (!networkReady) {
				DebugLog.e(TAG,"断开连接。。。");
			}else{
				DebugLog.e(TAG,"建立连接。。。");
				if(!Config.getInstance().unWifiDownload)return;
				if(NetMonitor.isWifiState(context)){
					//resume load image from uri
					ImageLoader.getInstance().resume();
				}else{
					//pause load image from uri
					ImageLoader.getInstance().pause();
				}
			}
		}

//		/**
//		 *网络监听广播注册
//		 */
//		public void register() {
//			if (mConnectivityFilter != null) {
//				AbsApplication.getApplication().registerReceiver(this, mConnectivityFilter);
//			}
//		}
//		/**
//		 *网络监听广播注销
//		 */
//		public void unregister() {
//			// Unregisters BroadcastReceiver when app is destroyed.
//			try {
//				AbsApplication.getApplication().unregisterReceiver(this);
//			} catch (IllegalArgumentException e) {
//				DebugLog.e(TAG,e.toString());
//			}
//		}

		
}
