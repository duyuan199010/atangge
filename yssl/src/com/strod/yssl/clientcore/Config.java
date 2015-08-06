package com.strod.yssl.clientcore;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import com.roid.config.AbsConfig;

public class Config extends AbsConfig {

	/** config instance */
	private static Config mInstance;

	/**
	 * get siglon instance
	 * 
	 * @return
	 */
	public static Config getInstance() {
		if (mInstance == null) {
			mInstance = new Config();
		}
		return mInstance;
	}

	public void init() {
		super.mAllowDebug = true;
	}

	public File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}
	
	public int getAppVersion(Context context) {
	    try {
	      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	      return info.versionCode;
	    } catch (NameNotFoundException e) {
	      e.printStackTrace();
	    }
	    return 1;
	  }
}
