package com.strod.yssl;

import com.roid.AbsApplication;
import com.strod.yssl.clientcore.Config;

import android.content.res.Configuration;

public class MyApplication extends AbsApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		Config.getInstance().init();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

}
