package com.roid;

import com.roid.config.AbsConfig;
import com.roid.crash.ExceptionLogger;

import android.app.Application;
import android.content.res.Configuration;

public abstract class AbsApplication extends Application{

	protected static AbsApplication mApplication;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}
	
	public static AbsApplication getApplication(){
		return mApplication;
	}
}
