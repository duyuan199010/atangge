package com.roid;

import com.roid.config.AbsConfig;
import com.roid.crash.ExceptionLogger;

import android.app.Application;
import android.content.res.Configuration;

public abstract class AbsApplication extends Application{

	protected static AbsApplication mApplication;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		/* Start logger */
		if (AbsConfig.ENABLE_LOG_STACK_TRACE)
		{
			Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogger(
					AbsConfig.getRootDirectory() + "/log/", null));
		}
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
	
	public static AbsApplication getApplication(){
		return mApplication;
	}
}
