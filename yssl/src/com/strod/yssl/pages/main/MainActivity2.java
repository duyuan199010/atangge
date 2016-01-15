package com.strod.yssl.pages.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.roid.ui.AbsAppCompatActivity;
import com.roid.ui.AbsFragment;
import com.roid.ui.ActivityManager;
import com.roid.util.DebugLog;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.ItemType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.clientcore.service.OnSwitchThemeListener;
import com.strod.yssl.clientcore.service.SwitchThemeListenerMgr;
import com.strod.yssl.view.TitleBar;

import java.util.ArrayList;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class MainActivity2 extends AbsAppCompatActivity implements ViewPager.OnPageChangeListener,OnCheckedChangeListener ,OnSwitchThemeListener{

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
//	private TitleBar mTitleBar;
	private RadioGroup mRadioGroup;
	
	private FrameLayout mCenterLayout;


	private CoordinatorLayout mCoordinatorLayout;
	private AppBarLayout mAppBarLayout;
	private Toolbar mToolbar;
	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	/**title item type list*/
	private ArrayList<ItemType> mItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Config.getInstance().isNightMode) {
			setTheme(R.style.NightTheme);
		} else {
			setTheme(R.style.DayTheme);
		}
		setContentView(R.layout.activity_main2);
		DebugLog.i(TAG, "onCreate()");

		if(mItemList == null){
			mItemList = new ArrayList<ItemType>();
			mItemList.add(new ItemType(41, "最热"));
			mItemList.add(new ItemType(42, "最新"));
			mItemList.add(new ItemType(43, "美容"));
			mItemList.add(new ItemType(44, "养生"));
			mItemList.add(new ItemType(45, "去火"));
			mItemList.add(new ItemType(46, "减肥"));
		}

		// title bar
//		mTitleBar = (TitleBar) findViewById(R.id.title_bar);
//		mTitleBar.setMiddleText(R.string.app_name);

		mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
		mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
		mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
		mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		// bottom bar
		mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
		mRadioGroup.setOnCheckedChangeListener(this);
		
		mCenterLayout = (FrameLayout) findViewById(R.id.main_center_fragment);

		// show indexFragment
//		showIndexFragment();

		configViews();
		SwitchThemeListenerMgr.getInstance().addListener(this);
	}

	private void configViews() {

		// 设置显示Toolbar
		setSupportActionBar(mToolbar);


		// 初始化ViewPager的适配器，并设置给它
		IndexAdapter mViewPagerAdapter = new IndexAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		// 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
		mViewPager.addOnPageChangeListener(this);

		mTabLayout.setTabMode(MODE_SCROLLABLE);
		// 将TabLayout和ViewPager进行关联，让两者联动起来
		mTabLayout.setupWithViewPager(mViewPager);
		// 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
		mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);

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
		/*if (mCurrentTag.equals(TAG_INDEX)) {
			return;
		}
		mCurrentTag = TAG_INDEX;
//		mTitleBar.setMiddleText(R.string.app_name);
		if (mIndexFragment == null) {
			mIndexFragment = new IndexFragment();
		}
		showFragment(mIndexFragment);*/

		mTabLayout.setVisibility(View.VISIBLE);
		mViewPager.setVisibility(View.VISIBLE);
		mCenterLayout.setVisibility(View.GONE);
	}

	/**
	 * show publish fragment
	 */
	private void showPublishFragment() {
		mTabLayout.setVisibility(View.GONE);
		mViewPager.setVisibility(View.GONE);
		mCenterLayout.setVisibility(View.VISIBLE);

		if (mCurrentTag.equals(TAG_PUBLISH)) {
			return;
		}
		mCurrentTag = TAG_PUBLISH;
//		mTitleBar.setMiddleText(R.string.main_publish);
		if (mPublishFragment == null) {
			mPublishFragment = new PublishFragment();
		}
		showFragment(mPublishFragment);
	}

	/**
	 * show collect fragment
	 */
	private void showCollectFragment() {
		mTabLayout.setVisibility(View.GONE);
		mViewPager.setVisibility(View.GONE);
		mCenterLayout.setVisibility(View.VISIBLE);

		if (mCurrentTag.equals(TAG_COLLECT)) {
			return;
		}
		mCurrentTag = TAG_COLLECT;
//		mTitleBar.setMiddleText(R.string.main_collect);
		if (mCollectFragment == null) {
			mCollectFragment = new CollectFragment();
		}
		showFragment(mCollectFragment);
	}

	/**
	 * show personal fragment
	 */
	private void showPersonalFragment() {
		mTabLayout.setVisibility(View.GONE);
		mViewPager.setVisibility(View.GONE);
		mCenterLayout.setVisibility(View.VISIBLE);

		if (mCurrentTag.equals(TAG_PERSONAL)) {
			return;
		}
		mCurrentTag = TAG_PERSONAL;
//		mTitleBar.setMiddleText(R.string.main_personal);
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


	class IndexAdapter extends FragmentPagerAdapter {
		public IndexAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ContentListFragment2.newInstance(mItemList.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mItemList.get(position).getName();
		}

		@Override
		public int getCount() {
			return mItemList.size();
		}
	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		mToolbar.setTitle(mItemList.get(position).getName());
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
