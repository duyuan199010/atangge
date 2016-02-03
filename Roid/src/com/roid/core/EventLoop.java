package com.roid.core;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @author lying
 */
public class EventLoop {
    private static EventLoop ourInstance = new EventLoop();

    /** Main Handler */
    Handler mMainHandler;
    /** background Handler */
    Handler mBgHandler;
    /** background Handler thread */
    HandlerThread mBgHandlerThread;

    public static EventLoop getInstance() {
        return ourInstance;
    }

    private EventLoop() {
        mMainHandler = new Handler(Looper.getMainLooper());

        mBgHandlerThread = new HandlerThread("Background_Thread");
        mBgHandlerThread.start();
        mBgHandler = new Handler(mBgHandlerThread.getLooper());
    }

    /**
     * Post AsyncTask to main thread
     * @param task
     */
    public void postMain(final AsyncTask task) {
        if (null == mMainHandler)
            throw new RuntimeException("main handler must init");
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        });
    }

    /**
     * Post runnable to main thread
     * @param r
     */
    public void postMain(Runnable r) {
        if (null == mMainHandler)
            throw new RuntimeException("main handler must init");
        mMainHandler.post(r);
    }

    /**
     * Post runnable to main thread after delayMillis
     * @param r
     * @param delayMillis
     */
    public void postMainDelayed(Runnable r, long delayMillis) {
        if (null == mMainHandler)
            throw new RuntimeException("main handler must init");
        mMainHandler.postDelayed(r, delayMillis);
    }

    /**
     * Post AsyncTask to background thread
     * @param task
     */
    public void postBg(final AsyncTask task) {
        if (null == mBgHandler)
            throw new RuntimeException("background handler must init");
        mBgHandler.post(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        });
    }

    /**
     * Post runnable to background thread
     * @param r
     */
    public void postBg(Runnable r) {
        if (null == mBgHandler)
            throw new RuntimeException("background handler must init");
        mBgHandler.post(r);
    }

    /**
     * Post runnable to background thread after delayMillis
     * @param r
     * @param delayMillis
     */
    public void postBgDelayed(Runnable r, long delayMillis) {
        if (null == mBgHandler)
            throw new RuntimeException("background handler must init");
        mBgHandler.postDelayed(r, delayMillis);
    }

    /**
     * quit bg handler thread
     */
    public void quitBgHandlerThread(){
        if (null != mBgHandlerThread){
            mBgHandlerThread.quit();
        }
    }


}
