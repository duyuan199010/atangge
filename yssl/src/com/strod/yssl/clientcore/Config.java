package com.strod.yssl.clientcore;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Environment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.roid.AbsApplication;
import com.roid.config.AbsConfig;
import com.roid.util.DebugLog;
import com.strod.yssl.MyApplication;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.cache.DiskLruCache;
import com.strod.yssl.clientcore.httprequest.HttpRequestURL;

public class Config extends AbsConfig {

	/** config instance */
	private static Config mInstance;
	
	/**default list pageSize & refresh & loadmore*/
	public static final int PAGE_SIZE = 15;
	public static final int REFRESH = 0;
	public static final int LOAD_MORE = 1;

	/**shared preferences name*/
	private static final String PREFERENCE_NAME = "atangge";
	private SharedPreferences mSharedPreferences;
	
	/**guide*/
	private static final String GUIDE = "guide";
	
	/**night mode*/
	private static final String NIGHT_MODE = "night_mode";
	public boolean isNightMode = false;
	
	/**unWifi download image*/
	private static final String UNWIFI_DOWNLOAD = "unwifi_download";
	public boolean unWifiDownload = true;
	
	/**image display options*/
	private DisplayImageOptions mOptions;
	private DisplayImageOptions mCircleOptions;
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

	/** content cache dir name */
	// http://blog.csdn.net/guolin_blog/article/details/28863651
	private static final String CONTENT_CACHE_DIR = "content";
	private static final long CONTENT_CACHE_SIZE = 5 * 1024 * 1024;
	private DiskLruCache mContentDiskLruCache = null;

	/**
	 * application start invoke init
	 */
	public void init() {
		super.mAllowDebug = true;
		super.HOST = HttpRequestURL.HOST;
		super.mVersionName = getAppVersionName(AbsApplication.getApplication());
		super.mVersionCode = getAppVersion(AbsApplication.getApplication());
		
		mSharedPreferences = MyApplication.getApplication().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		
		getContentDiskCache(AbsApplication.getApplication());
		
		//init night mode
		isNightMode = getNightMode();
		
		//init unWifi download image
		unWifiDownload = getUnWifiDownload();
		
		initImageLoader();
	}
	
	
	/**
	 * init imageloader
	 */
	public void initImageLoader() {
		imageLoader = ImageLoader.getInstance();
		mOptions=new DisplayImageOptions.Builder()
	        .showImageOnLoading(R.drawable.default_image)          // 设置图片下载期间显示的图片  
	        .showImageForEmptyUri(R.drawable.default_image)  // 设置图片Uri为空或是错误的时候显示的图片  
	        .showImageOnFail(R.drawable.default_image)       // 设置图片加载或解码过程中发生错误显示的图片      
	        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
	        .cacheOnDisk(true)                       // 设置下载的图片是否缓存在SD卡中  
	        .displayer(new SimpleBitmapDisplayer())  // 设置成图片
	        .build();

		mCircleOptions=new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_image)          // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.default_image)  // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.default_round_image)       // 设置图片加载或解码过程中发生错误显示的图片      
		.cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
		.cacheOnDisk(true)                       // 设置下载的图片是否缓存在SD卡中
		.considerExifParams(true)
		.displayer(new CircleBitmapDisplayer(Color.WHITE,5))  // 设置成圆型图片
		.build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(MyApplication.getApplication()));
		
	}
	
	public DisplayImageOptions getDisplayImageOptions(){
		return mOptions;
	}
	
	public DisplayImageOptions getDisplayImageOptionsCircle(){
		return mCircleOptions;
	}
	
	/**
	 * set has start guide
	 */
	public void setGuide(){
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(GUIDE, true);
		editor.commit();
	}
	
	/**
	 * get user guide
	 * @return
	 */
	public boolean getGuide(){
		return mSharedPreferences.getBoolean(GUIDE, false);
	}
	
	/**
	 * set night mode
	 */
	public void setNightMode(boolean isNight){
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(NIGHT_MODE, isNight);
		editor.commit();
		isNightMode = isNight;
	}
	
	/**
	 * get night mode
	 * @return true if night mode,otherwise false
	 */
	public boolean getNightMode(){
		return mSharedPreferences.getBoolean(NIGHT_MODE, false);
	}
	
	/**
	 * set unwifi download image
	 */
	public void setUnWifiDownload(boolean unWifiDownload){
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(UNWIFI_DOWNLOAD, unWifiDownload);
		editor.commit();
		this.unWifiDownload = unWifiDownload;
	}
	
	/**
	 * get unwifi download image
	 * @return true if night unwifi download image,otherwise false
	 */
	public boolean getUnWifiDownload(){
		return mSharedPreferences.getBoolean(UNWIFI_DOWNLOAD, true);
	}


	/**
	 * set last refresh time
	 */
	public void setLastRefreshTime(String key,long time){
		Editor editor = mSharedPreferences.edit();
		String hashKey = hashKeyForDisk(key);
		editor.putLong(hashKey, time);
		editor.commit();
	}

	/**
	 * get last refresh time
	 * @return
	 */
	public long getLastRefreshTime(String key){
		String hashKey = hashKeyForDisk(key);
		return mSharedPreferences.getLong(hashKey, 0);
	}
	
	/**
	 * get disk cache file
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	private File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * get application version
	 * @param context
	 * @return
	 */
	public int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * get application versionName
	 * @param context
	 * @return
	 */
	public String getAppVersionName(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "1.0.0";
	}

	/**
	 * get content cache
	 * @param context
	 * @return
	 */
	public DiskLruCache getContentDiskCache(Context context) {
		if (mContentDiskLruCache == null) {
			try {
				File cacheDir = getDiskCacheDir(context, CONTENT_CACHE_DIR);
				if (!cacheDir.exists()) {
					cacheDir.mkdirs();
				}
				mContentDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, CONTENT_CACHE_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
				DebugLog.e(TAG, "create ContentCache error");
			}
		}
		return mContentDiskLruCache;
	}

	private String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}
	
	/**
	 * get cache last modified time
	 * @param context
	 * @param uniqu
	 * @return
	 */
	public long getCacheLastModified(Context context,String uniqu){
		File cacheDir = getDiskCacheDir(context, CONTENT_CACHE_DIR);
		if (!cacheDir.exists()) {
			return 0;
		}
		String key = hashKeyForDisk(uniqu);
		File file = new File(cacheDir, key+".0");
		DebugLog.i(TAG, uniqu+" path:"+file.getAbsolutePath());
		if(!file.exists()){
			return 0;
		}
		return file.lastModified();
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	
	/**
	 * write content cache
	 * @param uniqu
	 * @param json
	 */
	public void writeContentCache(final String uniqu, final String json) {
		if(json==null||json.equals("")){
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String key = hashKeyForDisk(uniqu);
					DiskLruCache.Editor editor = mContentDiskLruCache.edit(key);
					if (editor != null) {
						OutputStream outputStream = editor.newOutputStream(0);
						outputStream.write(json.getBytes());
						editor.commit();
					}
					mContentDiskLruCache.flush();
				} catch (Exception e) {
					e.printStackTrace();
					DebugLog.e(TAG, uniqu+" writeContentCache() error");
				}
			}
		}).start();
	}

	/**
	 * read content cache
	 * @param uniqu
	 * @return
	 */
	public String readContentCache(String uniqu) {
		String json = null;
		try {
			String key = hashKeyForDisk(uniqu);
			DiskLruCache.Snapshot snapShot = mContentDiskLruCache.get(key);
			if (snapShot != null) {
				json = snapShot.getString(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
			DebugLog.e(TAG, uniqu+" readContentCache() error");
		}
		return json;
		
	}
	
	/**
	 * clear content chache
	 */
	public void clearContentCache(){
		try {
			mContentDiskLruCache.delete();
			mContentDiskLruCache = null;
			getContentDiskCache(AbsApplication.getApplication());
		} catch (Exception e) {
			e.printStackTrace();
			DebugLog.e(TAG, "clearContentCache() error");
		}
	}
	
	/**
	 * get contetn cache size
	 */
	public long getContentCacheSize(){
		if(mContentDiskLruCache==null)return 0;
		return mContentDiskLruCache.size();
	}
	
	/**
	 * close content cache
	 */
	public void closeContentCache(){
		try {
			mContentDiskLruCache.close();
		} catch (IOException e) {
			e.printStackTrace();
			DebugLog.e(TAG, "closeContentCache() error");
		}
		DebugLog.i(TAG, "closeContentCache()...");
	}
}
