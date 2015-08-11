package com.strod.yssl.pages.main;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.roid.ui.AbsFragment;
import com.strod.yssl.R;
import com.strod.yssl.pages.main.entity.ContentType;
import com.strod.yssl.pages.main.entity.ItemType;
import com.strod.yssl.util.DateUtil;

public final class ContentListFragment extends AbsFragment implements OnRefreshListener2<ListView>, OnItemClickListener {
	
	/**data key*/
	private static final String KEY_CONTENT = "ContentListFragment:Content";

	public static ContentListFragment newInstance(ItemType itemType) {
		ContentListFragment fragment = new ContentListFragment();

		fragment.mItemType = itemType;

		return fragment;
	}

	private ItemType mItemType;

	/**UI listview*/
	private PullToRefreshListView mPullRefreshListView;
	/**listview datasource*/
	private ArrayList<ContentType> mContentList;
	/**listview adapter*/
	private ContentListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContentList = savedInstanceState.getParcelableArrayList(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_content_list, container, false);

		if (mContentList == null) {
			mContentList = new ArrayList<ContentType>();
			mContentList.add(new ContentType(0,"","haha","hehe",88,66,1439282000092L,""));
			mContentList.add(new ContentType(1,"","adsf","heerqhe",88,66,1439282020092L,""));
			mContentList.add(new ContentType(2,"","tyujytj","herehe",88,66,1439282003792L,""));
			mContentList.add(new ContentType(3,"","tyeh","heaegehe",88,66,1439282020092L,""));
			mContentList.add(new ContentType(4,"","rtwurtyrt","heaeghe",88,66,1439282023792L,""));
		}

		mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
//		ListView actualListView = mPullRefreshListView.getRefreshableView();
		
		mAdapter = new ContentListAdapter(getActivity(), mContentList);
		mPullRefreshListView.setAdapter(mAdapter);
				
		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.setOnItemClickListener(this);
		mPullRefreshListView.setRefreshing(true);

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mContentList != null) {
			outState.putParcelableArrayList(KEY_CONTENT, mContentList);
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtil.formatDateToString(System.currentTimeMillis());

		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		// Call onRefreshComplete when the list has been refreshed.
		// mPullRefreshListView.onRefreshComplete();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtil.formatDateToString(System.currentTimeMillis());
		
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		// Call onRefreshComplete when the list has been refreshed.
		// mPullRefreshListView.onRefreshComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
