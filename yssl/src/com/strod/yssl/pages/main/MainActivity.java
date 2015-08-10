package com.strod.yssl.pages.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.roid.ui.ActivityManager;
import com.roid.ui.FragmentControlActivity;
import com.roid.util.Toaster;
import com.strod.yssl.R;

public class MainActivity extends FragmentControlActivity {

	// Debugging
	private static final String TAG = "MainActivity";

	private long firstTime = 0;
	FragmentManager mFragmentManager;
	
	private IndexFragment mIndexFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 此处加载library中的布局
		setContentView(R.layout.activity_main);

		// 加载indexFragment
		mFragmentManager = getSupportFragmentManager();
		FragmentTransaction mFragementTransaction = mFragmentManager.beginTransaction();
		if(mIndexFragment==null){
			mIndexFragment = new IndexFragment();
		}
		mFragementTransaction.replace(R.id.main_center_fragment, mIndexFragment);
		mFragementTransaction.show(mIndexFragment);
		mFragementTransaction.commitAllowingStateLoss();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
				Toaster.showDefToast(this, R.string.click_again);
				firstTime = secondTime;// 更新firstTime
				return true;
			} else {
				ActivityManager.getInstance().popAllActivity();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			return true;
		}
		return false;
	}



}
