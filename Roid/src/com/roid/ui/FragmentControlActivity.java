package com.roid.ui;

import java.util.LinkedList;
import java.util.List;
import com.roid.config.AbsConfig;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

public class FragmentControlActivity extends FragmentActivity {
	private List<Runnable> mPendingActions = null;
	private boolean mFragActResumed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		if(AbsConfig.mTranslucentStatus){
//			setTranslucentStatus();
//		}
		super.onCreate(savedInstanceState);

		/* Initialize pending actions */
		initialActionList();

		/* Push Activity into stack */
		ActivityManager.getInstance().pushActivity(this);
	}
	
	/**
	 * set translucent status bar
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(0);// 状态栏无背景

	}

	private void initialActionList() {
		mPendingActions = new LinkedList<Runnable>();
		mPendingActions.clear();
	}

	public void appendAction(Runnable action) {
		if (action == null)
			return;

		if (mPendingActions == null)
			initialActionList();

		mPendingActions.add(action);
	}

	private void executeActions() {
		if (mPendingActions != null) {
			for (Runnable act : mPendingActions) {
				act.run();
			}
			mPendingActions.clear();
		}
	}

	public boolean isFragmentActivityResumed() {
		return mFragActResumed;
	}

	@Override
	protected void onPause() {
		super.onPause();

		mFragActResumed = false;
	}

	@Override
	protected void onResume() {
		// DebugLog.log("ActionDialog", "%s: onResume\n", toString());

		super.onResume();

		mFragActResumed = true;
		executeActions();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mFragActResumed = false;
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		/* Pop Activity off stack */
		ActivityManager.getInstance().popActivity(this);
		super.onDestroy();
	}

}
