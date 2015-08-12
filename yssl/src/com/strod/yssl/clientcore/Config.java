package com.strod.yssl.clientcore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.roid.config.AbsConfig;
import com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache;

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

	/** image cache dir name */
	// http://blog.csdn.net/guolin_blog/article/details/28863651
	private static final String IMAGE_CACHE_DIR = "bitmap";
	private static final long IMAGE_CACHE_SIZE = 10 * 1024 * 1024;
	private static final String CONTENT_CACHE_DIR = "content";
	private static final long CONTENT_CACHE_SIZE = 5 * 1024 * 1024;
	private DiskLruCache mImageDiskLruCache = null;
	private DiskLruCache mContentDiskLruCache = null;

	public void init() {
		super.mAllowDebug = true;
	}

	private File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
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

	public DiskLruCache getImageDiskCache(Context context) {
		if (mImageDiskLruCache == null) {
			try {
				File cacheDir = getDiskCacheDir(context, IMAGE_CACHE_DIR);
				if (!cacheDir.exists()) {
					cacheDir.mkdirs();
				}
				mImageDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, IMAGE_CACHE_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mImageDiskLruCache;
	}

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
			}
		}
		return mContentDiskLruCache;
	}

	public String hashKeyForDisk(String key) {
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

	public void writeImageCache() {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// try {
		// String imageUrl =
		// "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
		// String key = hashKeyForDisk(imageUrl);
		// DiskLruCache.Editor editor = mImageDiskLruCache.edit(key);
		// if (editor != null) {
		// OutputStream outputStream = editor.newOutputStream(0);
		// if (downloadUrlToStream(imageUrl, outputStream)) {
		// editor.commit();
		// } else {
		// editor.abort();
		// }
		// }
		// mImageDiskLruCache.flush();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();
	}

	public void readImageCache() {
		try {
			String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
			String key = hashKeyForDisk(imageUrl);
			DiskLruCache.Snapshot snapShot = mImageDiskLruCache.get(key);
			if (snapShot != null) {
				InputStream is = snapShot.getInputStream(0);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				// mImage.setImageBitmap(bitmap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeImageCache() {
		try {
			String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
			String key = hashKeyForDisk(imageUrl);
			mImageDiskLruCache.remove(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getImageCacheSize() {
		if (mImageDiskLruCache == null)
			return 0;
		return mImageDiskLruCache.size();
	}

	public void deleteImageCache() {
		if (mImageDiskLruCache == null)
			return;
		try {
			mImageDiskLruCache.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

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
		}
		return json;
	}
}
