package com.strod.yssl.pages.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.roid.ui.FragmentControlActivity;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

/**
 * user guide activity
 * first instanll app show this activity
 * 
 * @author user
 *
 */
public class GuideActivity extends FragmentControlActivity {
	
	ViewPager mPager;
	PageIndicator mIndicator;
	
	/**guide page count*/
	private static final int COUNT = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return COUNT;
			}
			
			@Override
			public Fragment getItem(int position) {
				return GuideFragment.newInsatnce(position);
			}
		});

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

	}

	

	@Override
	protected void onDestroy() {
		Config.getInstance().setGuide();
		super.onDestroy();
	}

}
