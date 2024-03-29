package com.strod.yssl.pages.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.roid.ui.AbsAppCompatActivity;
import com.roid.ui.AbsFragment;
import com.roid.ui.AbsFragmentActivity;
import com.roid.ui.ActivityManager;
import com.roid.util.CommonUtils;
import com.roid.util.DebugLog;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.clientcore.service.OnSwitchThemeListener;
import com.strod.yssl.clientcore.service.SwitchThemeListenerMgr;
import com.strod.yssl.view.TitleBar;

public class MainActivity extends AbsAppCompatActivity implements OnCheckedChangeListener ,OnSwitchThemeListener{

	// Debugging
	private static final String TAG = "MainActivity";

	private long firstTime = 0;
	FragmentManager mFragmentManager;

	/** four fragment tag */
	private static final String TAG_INDEX = "index";
	private static final String TAG_PUBLISH = "publish";
	private static final String TAG_COLLECT = "collect";
	private static final String TAG_PERSONAL = "personal";
	/** current fragment tag */
	private static String mCurrentTag = "";

	/** index fragment page */
	private IndexFragment mIndexFragment;
	private PublishFragment mPublishFragment;
	private CollectFragment mCollectFragment;
	private PersonalFragment mPersonalFragment;
	/** current fragment */
	private AbsFragment mCurrentFragment;

	/** title bar */
	private TitleBar mTitleBar;
	private RadioGroup mRadioGroup;
	
	private FrameLayout mCenterLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Config.getInstance().isNightMode) {
			setTheme(R.style.NightTheme);
		} else {
			setTheme(R.style.DayTheme);
		}
		setContentView(R.layout.activity_main);
		DebugLog.i(TAG, "onCreate()");

		// title bar
		mTitleBar = (TitleBar) findViewById(R.id.title_bar);
		mTitleBar.setMiddleText(R.string.app_name);

		// bottom bar
		mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
		mRadioGroup.setOnCheckedChangeListener(this);
		
		mCenterLayout = (FrameLayout) findViewById(R.id.main_center_fragment);

		// show indexFragment
		showIndexFragment();

		SwitchThemeListenerMgr.getInstance().addListener(this);
	}

	@Override
	public void switchTheme(boolean isNight) {
		if (isNight) {
			mRadioGroup.setBackgroundResource(R.color.night_bottom_color);
			mCenterLayout.setBackgroundResource(R.color.night_bg_color);
		} else {
			mRadioGroup.setBackgroundResource(R.color.bottom_color);
			mCenterLayout.setBackgroundResource(R.color.bg_color);
		}
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_index:// 首页
			showIndexFragment();
			break;
		case R.id.radio_publish:// 掌厨
			showPublishFragment();
			break;
		case R.id.radio_collect:// 收藏
			showCollectFragment();
			break;
		case R.id.radio_personal:// 个人
			showPersonalFragment();
			break;

		default:
			break;
		}
	}

	/**
	 * show index fragment
	 */
	private void showIndexFragment() {
		if (mCurrentTag.equals(TAG_INDEX)) {
			return;
		}
		mCurrentTag = TAG_INDEX;
		mTitleBar.setMiddleText(R.string.app_name);
		if (mIndexFragment == null) {
			mIndexFragment = new IndexFragment();
		}
		showFragment(mIndexFragment);
	}

	/**
	 * show publish fragment
	 */
	private void showPublishFragment() {
		if (mCurrentTag.equals(TAG_PUBLISH)) {
			return;
		}
		mCurrentTag = TAG_PUBLISH;
		mTitleBar.setMiddleText(R.string.main_publish);
		if (mPublishFragment == null) {
			mPublishFragment = new PublishFragment();
		}
		showFragment(mPublishFragment);
	}

	/**
	 * show collect fragment
	 */
	private void showCollectFragment() {
		if (mCurrentTag.equals(TAG_COLLECT)) {
			return;
		}
		mCurrentTag = TAG_COLLECT;
		mTitleBar.setMiddleText(R.string.main_collect);
		if (mCollectFragment == null) {
			mCollectFragment = new CollectFragment();
		}
		showFragment(mCollectFragment);
	}

	/**
	 * show personal fragment
	 */
	private void showPersonalFragment() {
		if (mCurrentTag.equals(TAG_PERSONAL)) {
			return;
		}
		mCurrentTag = TAG_PERSONAL;
		mTitleBar.setMiddleText(R.string.main_personal);
		if (mPersonalFragment == null) {
			mPersonalFragment = new PersonalFragment();
		}
		showFragment(mPersonalFragment);
	}

	/**
	 * show fragment
	 * 
	 * @param fragment
	 */
	private void showFragment(AbsFragment fragment) {
		if (mFragmentManager == null) {
			mFragmentManager = getSupportFragmentManager();
		}

		FragmentTransaction fragementTransaction = mFragmentManager.beginTransaction();
		// if not add
		if (null == mFragmentManager.findFragmentByTag(mCurrentTag)) {
			fragementTransaction.add(R.id.main_center_fragment, fragment, mCurrentTag);
		}

		// hide fragment when current fragment not null
		if (null != mCurrentFragment) {
			fragementTransaction.hide(mCurrentFragment);
		}

		fragementTransaction.show(fragment);
		fragementTransaction.commitAllowingStateLoss();

		// update current fragment
		mCurrentFragment = fragment;
	}
	
	public void restartActivity(){
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		SwitchThemeListenerMgr.getInstance().removeListener(this);
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
				Toaster.showDefToast(this, R.string.click_again);
				firstTime = secondTime;// 更新firstTime
				return true;
			} else {
				Config.getInstance().closeContentCache();
				ActivityManager.getInstance().popAllActivity();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			return true;
		}
		return false;
	}

}
