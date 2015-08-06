package com.roid.ui;

import java.util.Stack;

import android.app.Activity;

public class ActivityManager {

	private static ActivityManager mActivityManager;

	private static Stack<Activity> mActivityStack;

	public static ActivityManager getInstance() {
		if (mActivityManager == null)
			mActivityManager = new ActivityManager();
		return mActivityManager;
	}

	public synchronized void pushActivity(Activity activity) {
		if (activity == null)
			return;
		if (mActivityStack==null) {
			mActivityStack=new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	public synchronized void popActivity(Activity activity) {
		if(activity!=null && mActivityStack!=null){
			mActivityStack.remove(activity);
			activity = null;
		}
	}
	
	public synchronized void popActivity() {
		Activity activity = mActivityStack.lastElement();
		if(activity!=null && mActivityStack!=null){
			mActivityStack.remove(activity);
			activity = null;
		}
	}
	
	public Activity currentActivity(){
		Activity activity = null;
		if(mActivityStack!=null && !mActivityStack.isEmpty()){
			activity = mActivityStack.lastElement();
		}
		return activity;
	}
	
	 public synchronized void popAllActivityExceptOne(Activity act) {
		 if (mActivityStack == null)
			 return;
			 
		 Activity activity;
		 int actCount = mActivityStack.size();
		 for (int i = 0; i < actCount; i++) {
			 activity = mActivityStack.get(actCount-i-1);
			 if (activity != null && activity != act)
				 activity.finish();
		 }
	 }	
}
