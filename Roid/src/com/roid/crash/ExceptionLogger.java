package com.roid.crash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.roid.AbsApplication;
import com.roid.util.DebugLog;

public class ExceptionLogger implements UncaughtExceptionHandler {

	private UncaughtExceptionHandler defaultUEH;
	private String localPath;
	private String url;
	private SimpleDateFormat mDateFormat;
	private boolean mStartedExceptionAct = false;

	/*
	 * if any of the parameters is null, the respective functionality will not
	 * be used
	 */
	public ExceptionLogger(String localPath, String url) {
		this.localPath = localPath;
		this.url = url;
		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		this.mDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
	}
	
	public void dumpException(Throwable e) {
		String timestamp = mDateFormat.format(new Date());		
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		String stacktrace = result.toString();
		printWriter.close();
		String filename = timestamp + ".stacktrace";

		//DebugLog.log("CrashDump", stacktrace);
		
		if (localPath != null) {
			// create a File object for the parent directory
			File directory = new File(localPath);
		    if (!directory.exists()) {
		        if (!directory.mkdirs()) {
		        	// have the object build the directory structure, if needed.
		        	Log.e("ExceptionLogger", "Problem creating Image folder -> " + localPath);
		        }
		    }
			
			writeToFile(stacktrace, filename);
		}
		if (url != null) {
			sendToServer(stacktrace, filename);
		}
		
		if (!mStartedExceptionAct)
		{
			mStartedExceptionAct = true;
			/* Start crash report activity */
			CrashReportActivity.start(AbsApplication.getApplication());
		}
	}

	public void uncaughtException(Thread t, Throwable e) {
		/* Dump exception */
		dumpException(e);
		/* Handle the exception */
		defaultUEH.uncaughtException(t, e);
	}

	private void writeToFile(String stacktrace, String filename) {
		try {
			BufferedWriter bos = new BufferedWriter(new FileWriter(localPath
					+ "/" + filename));
			bos.write(LogcatDump.getSettings());
			bos.write(stacktrace);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			DebugLog.e("ExeceptionLogger", "//------- Catch Exception --------//\n");
			e.printStackTrace();
		}
	}

	private void sendToServer(String stacktrace, String filename) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("filename", filename));
		nvps.add(new BasicNameValuePair("stacktrace", stacktrace));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			httpClient.execute(httpPost);
		} catch (IOException e) {
			DebugLog.e("ExeceptionLogger", "//------- Catch Exception --------//\n");
			e.printStackTrace();
		}
	}
}
