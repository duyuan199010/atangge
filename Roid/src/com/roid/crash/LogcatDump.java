package com.roid.crash;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager.MemoryInfo;
import android.os.Build;
import android.util.DisplayMetrics;

import com.roid.config.AbsConfig;
import com.roid.ui.ActivityManager;
import com.roid.ui.dialog.PopupAboutDialog;
import com.roid.util.DebugLog;

public class LogcatDump {
	public static final boolean LOG_ALL_TIME = true;
	public static final int LOG_EXPIRED_DAYS = 120;
	public static final String LOG_DIRECTORY = AbsConfig.getRootDirectory() + "/log/";

	private static final long LOG_EXPIRED_TIME_MS = 3600000L;
	private static final String LOGCAT_COMMAND = "logcat -d -v threadtime -f ";
	private static final String LOGCAT_FILTER = " DEBUG:I *:I";
	private static final String LogFilePrefix = "logcat_";
	private static final String LogFileSuffix = ".log";
	private static final int LogFilePrefixLen = LogFilePrefix.length();
	private static final int LogFileSuffixLen = LogFileSuffix.length();
	private static final int MinLogFileNameLen = LogFilePrefixLen + LogFileSuffixLen;
	private static final String LOG_LINE_SEPERATOR = "//--------------------------------------//\n";
	private static final String Tag = "LogcatDump";
	private static LogcatDump mInstance = null;
	
	private boolean mLogAllTime = LOG_ALL_TIME;
	private SimpleDateFormat mFormat;
	private SimpleDateFormat mFullFormat;
	private Date mLatestLogFileDate = null;
	private Process mLogProc = null;
	
	public static LogcatDump instance() {
		if (mInstance == null)
		{
			mInstance = new LogcatDump();
		}
		return mInstance;
	}
	
	public static String getSettings() {
		String result = LOG_LINE_SEPERATOR;
		result += String.format("// Device: %s\n", Build.DEVICE);
		result += String.format("// Manufacturer: %s\n", Build.MANUFACTURER);
		result += String.format("// Product: %s\n", Build.PRODUCT);
		result += String.format("// Model: %s\n", Build.MODEL);
		result += String.format("// Serial: %s\n", Build.SERIAL);
		result += String.format("// Release: %s\n", Build.VERSION.RELEASE);
		
		Activity act = ActivityManager.getInstance().currentActivity();
		if (act != null)
		{
			result += String.format("// Build Version: %s\n", PopupAboutDialog.getAppVersion(act));
			result += String.format("// Build Date: %s\n", PopupAboutDialog.getAppDate(act));
			
			MemoryInfo mi = new MemoryInfo();
			android.app.ActivityManager am = (android.app.ActivityManager)act.getBaseContext().getSystemService(Activity.ACTIVITY_SERVICE);
			am.getMemoryInfo(mi);
			final long availableMegs = mi.availMem / 1048576L;
			result += String.format("// Avail Mem: %dMB\n", availableMegs);
			
			DisplayMetrics metrics = act.getResources().getDisplayMetrics();
			result += String.format("// Screen:\n");
			result += String.format("//   - dpi: %d\n", metrics.densityDpi);
			result += String.format("//   - Density: %.2f\n", metrics.density);
			result += String.format("//   - Width: %dpx | %.2fdpi\n", metrics.widthPixels, metrics.xdpi);
			result += String.format("//   - Height: %dpx | %.2fdpi\n", metrics.heightPixels, metrics.ydpi);		
		}
		
		result += LOG_LINE_SEPERATOR;
		return result;
	}
	
	public void setLogAllTime(boolean logall) {
		mLogAllTime = logall;
	}
	
	public LogcatDump() {
		mFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		mFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
		/* Create the missing directories for log files storage */
		File logDir = new File(LOG_DIRECTORY);
		if (!logDir.exists())
		{
			logDir.mkdirs();
		}
	}
	
	private Date convert(String filename) {
		if (!filename.startsWith(LogFilePrefix))
			return null;

		if (filename.length() <= MinLogFileNameLen)
			return null;
		
		String dateStr = filename.substring(LogFilePrefixLen);
		dateStr = dateStr.substring(0, dateStr.length() - LogFileSuffixLen);
		
		try {
			return mFormat.parse(dateStr);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	private String convert(Date date) {
		String dateStr = mFormat.format(date);
		return LogFilePrefix + dateStr + LogFileSuffix;
	}
	
	private void removeOutdatedLogs() {
		File logDir = new File(LOG_DIRECTORY);
		File[] logFiles = logDir.listFiles();
		if (logFiles != null)
		{
			List<Date> logDateList = new LinkedList<Date>();
			logDateList.clear();
						
			for (File log : logFiles)
			{
				Date date = convert(log.getName());
				if (date != null)
					logDateList.add(date);
			}
			
			if (logDateList.size() > LOG_EXPIRED_DAYS)
			{
				Collections.sort(logDateList);
				
				/* Delete the exceeding log files from the start of list */
				final int numOfFilesDeleting = logDateList.size() - LOG_EXPIRED_DAYS + 1;
				for (int i=0; i<numOfFilesDeleting; i++)
				{
					final String filename = convert(logDateList.get(i));
					File deletingFile = new File(LOG_DIRECTORY + filename);
					deletingFile.delete();
				}
			}
		}
	}
	
	private boolean isLoggerFileExpired() {
		if (mLatestLogFileDate == null || mLogAllTime)
		{
			mLatestLogFileDate = new Date();
			return true;
		}
		else
		{
			Date now = new Date();
			final long timeDiffMS = now.getTime() - mLatestLogFileDate.getTime();
			if (timeDiffMS >= LOG_EXPIRED_TIME_MS)
			{
				mLatestLogFileDate = now;
				return true;
			}
		}
		return false;
	}
	
	public void dump(String stacktrace) {		
		isLoggerFileExpired();
		
		/* Remove all outdated files */
		removeOutdatedLogs();
		
		/* Stop previous executed processes */
		if (mLogProc != null)
			mLogProc.destroy();
		
		if (stacktrace != null)
		{
			DebugLog.d("LogcatBackup", getSettings());
			DebugLog.d("LogcatBackup", stacktrace);
		}
		
		final String logfilepath = LOG_DIRECTORY + convert(mLatestLogFileDate);
		DebugLog.d(Tag, "//--------------------------------------------\n");
		DebugLog.d(Tag, "// Logcat Dump at %s [%s]\n", logfilepath, mFullFormat.format(new Date()));
		DebugLog.d(Tag, "//--------------------------------------------\n");
		
		try {
			File logDir = new File(LOG_DIRECTORY);
			logDir.mkdirs();
			File logfile = new File(LOG_DIRECTORY + convert(mLatestLogFileDate));
			logfile.createNewFile();
			String cmd = LOGCAT_COMMAND + logfile.getAbsolutePath() + LOGCAT_FILTER;
			mLogProc = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* Log process is empty */
		if (mLogProc == null)
			return;
		
		DebugLog.d(Tag, ">>>>>>> Running at PID -> %s\n", mLogProc.toString());
		
		try {
			mLogProc.waitFor();
			mLogProc.destroy();
			mLogProc = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
