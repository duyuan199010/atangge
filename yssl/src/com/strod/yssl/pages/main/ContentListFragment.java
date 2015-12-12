package com.strod.yssl.pages.main;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.roid.core.HttpManager;
import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.ui.AbsFragment;
import com.roid.util.CommonUtils;
import com.roid.util.DebugLog;
import com.roid.util.JsonParser;
import com.roid.util.NetMonitor;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article;
import com.strod.yssl.bean.main.Article.ContentType;
import com.strod.yssl.bean.main.ItemType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.clientcore.httprequest.ArticleFactory;
import com.strod.yssl.clientcore.httprequest.HttpRequestId;
import com.strod.yssl.clientcore.httprequest.HttpRequestURL;
import com.strod.yssl.pages.details.DetailsActivity;
import com.strod.yssl.util.DateUtil;
import com.strod.yssl.view.RippleView;
import com.strod.yssl.view.RippleView.OnRippleCompleteListener;
import com.strod.yssl.view.pulltorefresh.PullToRefreshBase;
import com.strod.yssl.view.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.strod.yssl.view.pulltorefresh.PullToRefreshListView;

public final class ContentListFragment extends AbsFragment implements OnRefreshListener2<ListView>, OnItemClickListener, OnHttpRespondLisenter {

	/** debug log tag */
	private static final String TAG = "ContentListFragment";
	/** refresh complete tag */
	private static final int REFRESH_COMPLETE = 0;
	/** save the most cache data size */
	private static final int CACHE_SIZE = 30;

	/** data key */
	private static final String KEY_CONTENT = "ContentListFragment:Content";

	public static ContentListFragment newInstance(ItemType itemType) {
		ContentListFragment fragment = new ContentListFragment();

		fragment.mItemType = itemType;

		return fragment;
	}

	private ItemType mItemType;

	/** UI listview */
	private PullToRefreshListView mPullRefreshListView;
	/** listview datasource */
	private ArrayList<ContentType> mContentList;
	/** listview adapter */
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
		DebugLog.e(TAG, "[id:%d name:%s] onCreateView()", mItemType.getItemId(), mItemType.getName());
		if (mContentList == null) {
			mContentList = new ArrayList<ContentType>();
			readCache();
		}

//		mContentList = new ArrayList<ContentType>();
//		mContentList.add(new ContentType(0,21,"","","","大图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,22,"","","","左图右文模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,23,"","","","左图右文模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,24,"","","","左图右文模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(2,25,"","","","一标三图模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//
//		mContentList.add(new ContentType(0,21,"","","","大图模式","","阿汤哥",12,24,1287260660l,""));
//		mContentList.add(new ContentType(1,22,"","","","左图右文模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,23,"","","","左图右文模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,24,"","","","左图右文模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(2,25,"","","","一标三图模式","我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//
//		mContentList.add(new ContentType(0,21,"","","","大图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,22,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,23,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,24,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(2,25,"","","","一标三图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//
//		mContentList.add(new ContentType(0,21,"","","","大图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,22,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,23,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,24,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(2,25,"","","","一标三图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//
//		mContentList.add(new ContentType(0,21,"","","","大图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,22,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,23,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(1,24,"","","","左图右文模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));
//		mContentList.add(new ContentType(2,25,"","","","一标三图模式","","阿汤哥",12,24,1287260660l,"http://www.09jike.com/Home/Article/detail/id/3.html"));


		initView(rootView);

		return rootView;
	}

	/**
	 * read disk cache
	 */
	private void readCache() {
		long start = System.currentTimeMillis();
		// read cache from disk
		String key = mItemType.getItemId() + mItemType.getName();
		String json = Config.getInstance().readContentCache(key);
		if (json == null || json.equals("")) {
			// no cache
			return;
		}
		// have cache
		JsonParser<Article> parser = new JsonParser<Article>();
		Article article = parser.parseJson(json, Article.class);
		mContentList.addAll(article.getData());
		long end = System.currentTimeMillis();
		DebugLog.i(TAG, "[id:%d name:%s] read cache size:%d used:%dms", mItemType.getItemId(), mItemType.getName(), mContentList.size(),end-start);
	}


	/**
	 * init view
	 * 
	 * @param rootView
	 */
	private void initView(View rootView) {
		mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
		ListView actualListView = mPullRefreshListView.getRefreshableView();

		mAdapter = new ContentListAdapter(getActivity(), mContentList);
		mPullRefreshListView.setAdapter(mAdapter);

		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
				if (state.equals(PullToRefreshBase.State.PULL_TO_REFRESH)) {

					if (direction == PullToRefreshBase.Mode.PULL_FROM_START){
//						refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.pull_to_refresh_pull_label));
//						refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.pull_to_refresh_release_label));
//						refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_refreshing_label));

						String key = mItemType.getItemId() + mItemType.getName();
						long lastRefreshTime = Config.getInstance().getLastRefreshTime(key);

						//have last refresh time,update label
						if (lastRefreshTime != 0) {
							String label = DateUtil.formatDateToString(lastRefreshTime);
							// Update the LastUpdatedLabel
							refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(getString(R.string.pull_to_refresh_last_refresh_label) + " : " + label);
						}
					}else if (direction == PullToRefreshBase.Mode.PULL_FROM_END){
//						refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.pull_to_refresh_from_bottom_pull_label));
//						refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.pull_to_refresh_from_bottom_release_label));
//						refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.pull_to_refresh_from_bottom_refreshing_label));

						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("");
					}

				}
			}
		});
//		actualListView.setSelector(R.drawable.item_background_selector);
		actualListView.setOnItemClickListener(this);
		
	}
	
	/**
	 * is need auto request
	 */
	private boolean isNeedRequest() {
		boolean needRequest = false;
		if(!NetMonitor.isNetworkConnected(getActivity())){
			return needRequest;
		}
		long time = Config.getInstance().getCacheLastModified(getActivity(), mItemType.getItemId() + mItemType.getName());
		long currentTime = System.currentTimeMillis();
		// judge cache last mofified time,in wifi state,if >four hours,need request data
		if (currentTime - time > 4 * 60 *60 * 1000 && NetMonitor.isWifiState(getActivity())) {
			needRequest = true;
		} else if (currentTime - time > 24 * 60 * 60 * 1000 && (!NetMonitor.isWifiState(getActivity()))) {
			// judge cache last mofified time,not in wifi state,if >24 hours,need request data
			needRequest = true;
		} else {
			//other didn't need request
			needRequest = false;
		}
		return needRequest;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isNeedRequest()) {
			mPullRefreshListView.setRefreshing(true);
		}
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
		//save refresh time
		String key = mItemType.getItemId() + mItemType.getName();
		Config.getInstance().setLastRefreshTime(key,System.currentTimeMillis());

		ArticleFactory articleFactory = new ArticleFactory(mItemType.getItemId(),Config.REFRESH,getRefreshTime());
		HttpManager.getInstance().post(this.getActivity(), this, HttpRequestId.CONTENT_LIST_REFRESH, HttpRequestURL.CONTENT_LIST, 
				articleFactory.product().parameters());
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		ArticleFactory articleFactory = new ArticleFactory(mItemType.getItemId(),Config.LOAD_MORE,getLoadMoreTime());
		HttpManager.getInstance().post(this.getActivity(), this, HttpRequestId.CONTENT_LIST_LOADMORE, HttpRequestURL.CONTENT_LIST, 
				articleFactory.product().parameters());
		
	}
	
	/**
	 * get the list first position time
	 * @return
	 */
	private long getRefreshTime(){
		if(mContentList==null || mContentList.size()==0){
			return 0;
		}
		return mContentList.get(0).getTime();
	}
	
	/**
	 * get the list last position time
	 * @return
	 */
	private long getLoadMoreTime(){
		if(mContentList==null || mContentList.size()==0){
			return 0;
		}
		return mContentList.get(mContentList.size()-1).getTime();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		DebugLog.i(TAG, "onItemClick()");
		if (CommonUtils.isFastDoubleClick())
			return;
		if (mContentList == null)
			return;

		RippleView rippleView = (RippleView) view.findViewById(R.id.ripple_view);
		rippleView.setOnRippleCompleteListener(new OnRippleCompleteListener() {
			
			@Override
			public void onComplete(RippleView rippleView) {
				Intent intent = new Intent(getActivity(), DetailsActivity.class);
				intent.putExtra(DetailsActivity.ARTICLE, mContentList.get(position-1));
				startActivity(intent);
			}
		});
	}
	
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mPullRefreshListView.onRefreshComplete();
				break;

			default:
				break;
			}
		}

	};

	/**
	 * sava data to cache
	 */
	private void saveDataCache() {
		if (mContentList == null || mContentList.size() == 0) {
			// no data to save cache
			return;
		}
		JsonParser<Article> parser = new JsonParser<Article>();
		Article article = new Article();
		article.setRet_code(0);
		article.setRet_msg("success");
		// save CACHE_SIZE data most
		if (mContentList.size() > CACHE_SIZE) {
			article.setData(mContentList.subList(0, CACHE_SIZE));
		} else {
			article.setData(mContentList);
		}
		DebugLog.i(TAG, "[id:%d name:%s] save cache size:%d", mItemType.getItemId(), mItemType.getName(), article.getData().size());

		String cacheJson = parser.toJson(article);
		// save cache data
		Config.getInstance().writeContentCache(mItemType.getItemId() + mItemType.getName(), cacheJson);
	}

	@Override
	public void onHttpSuccess(int taskId, Object response, String json) {
		DebugLog.d(TAG, "onHttpSuccess:" + taskId);
		// Call onRefreshComplete when the list has been refreshed.
		mHandler.sendEmptyMessage(REFRESH_COMPLETE);
		try {
			DebugLog.i(TAG,json);
			Article article = new Gson().fromJson(json, Article.class);
			if (taskId == HttpRequestId.CONTENT_LIST_REFRESH) {
				//if haven't refresh data,return
				if(article.getData().size()==0){
					if(isVisible())
						Toaster.showDefToast(getActivity(), R.string.data_newest);
					return;
				}
				long time = Config.getInstance().getCacheLastModified(getActivity(), mItemType.getItemId() + mItemType.getName());
				long currentTime = System.currentTimeMillis();
				
				// judge cache last mofified time,if > one day,clear cache data
				if (currentTime - time > 24 * 60 * 60 * 1000) {
					mContentList.clear();
				}
				
				// refresh add index 0
				mContentList.addAll(0, article.getData());
			} else if (taskId == HttpRequestId.CONTENT_LIST_LOADMORE) {
				//if haven't no more data,return
				if(article.getData().size()==0){
					if(isVisible())
						Toaster.showDefToast(getActivity(), R.string.data_no_more);
					return;
				}
				
				// loadmore add index end
				mContentList.addAll(article.getData());
			}
			mAdapter.notifyDataSetChanged();
			saveDataCache();
		} catch (Exception e) {
			DebugLog.i(TAG,json);
			e.printStackTrace();
			if(isVisible())
				Toaster.showDefToast(getActivity(), R.string.net_data_error);
		}
	}

	@Override
	public void onHttpFailure(int taskId, String message) {
		DebugLog.d(TAG, "onHttpFailure:" + taskId);
		// Call onRefreshComplete when the list has been refreshed.
		mHandler.sendEmptyMessage(REFRESH_COMPLETE);
		if(isVisible())
			Toaster.showDefToast(getActivity(), R.string.error_network_connection);
	}

	@Override
	public void onDestroyView() {
		DebugLog.e(TAG, "[id:%d name:%s] onDestroyView()", mItemType.getItemId(), mItemType.getName());
		super.onDestroyView();
	}

	
}
