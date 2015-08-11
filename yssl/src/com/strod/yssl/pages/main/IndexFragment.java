package com.strod.yssl.pages.main;

/**
 * 首页面
 */
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.roid.ui.AbsFragment;
import com.strod.yssl.R;
import com.strod.yssl.pages.main.entity.ItemType;
import com.viewpagerindicator.TabPageIndicator;

public class IndexFragment extends AbsFragment{
	
	/**title item type list*/
	private List<ItemType> mItemList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_index, container, false);
		if(mItemList == null){
			mItemList = new ArrayList<ItemType>();
			mItemList.add(new ItemType(0, "最热"));
			mItemList.add(new ItemType(1, "最新"));
		}
		initView(rootView);
		return rootView;
	}

	private void initView(View rootView){
		FragmentPagerAdapter adapter = new IndexAdapter(getChildFragmentManager());

        ViewPager pager = (ViewPager)rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)rootView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
	}
	
	class IndexAdapter extends FragmentPagerAdapter {
        public IndexAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ContentListFragment.newInstance(mItemList.get(position));
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
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

}