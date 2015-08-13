package com.roid.config;

import android.os.Environment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.roid.AbsApplication;

public abstract class AbsConfig {
	
	/**allow debug log*/
	public static boolean mAllowDebug = true;
	public static boolean ENABLE_LOG_STACK_TRACE = true;
	public static ImageLoader imageLoader;
	public static String DATABASE_NAME="atangge";
	
	/**log tag*/
	public static String TAG = "";

	/**application name*/
	protected String mAppName;
	
	/**current app version code*/
	protected int mVersionCode;

	/**current app version name*/
	protected String mVersionName;

	/**current app channel code*/
	protected String mChannelCode;

	/**local language*/
	protected String mLanguage;
	
	/**preference file name*/
	protected String mPreferenceName;
		
	/**database name*/
	protected String mDatabaseName;
	
	private static String mRootDirectory = Environment.getExternalStorageDirectory() + "/Atangge";

	public static String getRootDirectory(){
		return mRootDirectory;
	}
	
	public AbsConfig(){
		initImageLoader();
	}
	
	/**
	 * init imageloader
	 */
	public void initImageLoader() {
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(AbsApplication.getApplication()));
	}
}
