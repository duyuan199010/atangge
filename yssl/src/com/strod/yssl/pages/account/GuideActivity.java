package com.strod.yssl.pages.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roid.ui.AbsActivity;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.pages.main.MainActivity;
import com.strod.yssl.view.viewpagerindicator.CirclePageIndicator;
import com.strod.yssl.view.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * user guide activity
 * first instanll app show this activity
 * 
 * @author user
 *
 */
public class GuideActivity extends AbsActivity {
	
	ViewPager mPager;
	PageIndicator mIndicator;
	List<Integer> guideList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		guideList = new ArrayList<Integer>();
		guideList.add(0);
		guideList.add(1);
		guideList.add(2);
		guideList.add(3);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new ImagePagerAdapter(this,guideList));

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

	}

	private class ImagePagerAdapter extends PagerAdapter{

		private Context mContext;
		List<Integer> mGuideList;


		public ImagePagerAdapter(Context context,List<Integer> guideList){
			this.mContext = context;
			this.mGuideList = guideList;
		}


		@Override
		public int getCount() {
			return guideList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mContext);
			ViewGroup.LayoutParams params = iv.getLayoutParams();
			params.width = ViewGroup.LayoutParams.MATCH_PARENT;
			params.height = ViewGroup.LayoutParams.MATCH_PARENT;
			iv.setLayoutParams(params);
        	iv.setBackgroundResource(R.color.title_bg_color);
			container.addView(iv);
			if (position == 3){
				iv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(GuideActivity.this, MainActivity.class));
						GuideActivity.this.finish();
					}
				});
			}
			return iv;
		}

		@Override
		public boolean isViewFromObject(View view, Object o) {
			return false;
		}
	}

	

	@Override
	protected void onDestroy() {
		Config.getInstance().setGuide();
		super.onDestroy();
	}

}
