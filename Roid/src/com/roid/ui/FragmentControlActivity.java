package com.roid.ui;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FragmentControlActivity extends FragmentActivity {
	private List<Runnable> mPendingActions = null;
	private boolean mFragActResumed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Initialize pending actions */
		initialActionList();

		/* Push Activity into stack */
		ActivityManager.getInstance().pushActivity(this);
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
