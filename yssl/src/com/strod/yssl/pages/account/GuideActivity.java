package com.strod.yssl.pages.account;

import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new ImagePagerAdapter(this));

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

	}

	private class ImagePagerAdapter extends PagerAdapter{

		private Context mContext;
		List<View> pageViews = new ArrayList<View>();


		public ImagePagerAdapter(Context context){
			this.mContext = context;

			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

			ImageView view1 = new ImageView(mContext);
			view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view1.setLayoutParams(params);
			view1.setImageResource(R.drawable.guide_one);
			pageViews.add(view1);

			ImageView view2 = new ImageView(mContext);
			view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view2.setLayoutParams(params);
			view2.setImageResource(R.drawable.guide_two);
			pageViews.add(view2);

			ImageView view3 = new ImageView(mContext);
			view3.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view3.setLayoutParams(params);
			view3.setImageResource(R.drawable.guide_three);
			pageViews.add(view3);

			ImageView view4 = new ImageView(mContext);
			view4.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view4.setLayoutParams(params);
			view4.setImageResource(R.drawable.guide_four);
			pageViews.add(view4);
		}


		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = pageViews.get(position);
			container.addView(view);
			if (position == pageViews.size()-1){
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(mContext, MainActivity.class));
						((Activity)mContext).finish();
					}
				});
			}
			return view;
		}

		@Override
		public boolean isViewFromObject(View view, Object o) {
			return view==o;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	

	@Override
	protected void onDestroy() {
		Config.getInstance().setGuide();
		super.onDestroy();
	}

}
