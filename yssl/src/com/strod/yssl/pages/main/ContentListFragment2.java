package com.strod.yssl.pages.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.strod.yssl.view.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;

public final class ContentListFragment2 extends AbsFragment implements OnRefreshListener2<RecyclerView>, ContentListAdapter2.OnItemClickListener, OnHttpRespondLisenter {

	/** debug log tag */
	private static final String TAG = "ContentListFragment";
	/** save the most cache data size */
	private static final int CACHE_SIZE = 30;

	/** data key */
	private static final String KEY_CONTENT = "ContentListFragment:Content";

	public static ContentListFragment2 newInstance(ItemType itemType) {
		ContentListFragment2 fragment = new ContentListFragment2();

		fragment.mItemType = itemType;

		return fragment;
	}

	private ItemType mItemType;

	/** UI listview */
	private PullToRefreshRecyclerView mPullRefreshListView;
	/** listview datasource */
	private ArrayList<ContentType> mContentList;
	/** listview adapter */
	private ContentListAdapter2 mAdapter;
	/** empty view*/
//	private View mEmptyView;

	/** data key*/
	private String mKey;

	/** hidden */
	private boolean mIsHidden;


	private RecyclerView.LayoutManager mLayoutManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContentList = savedInstanceState.getParcelableArrayList(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_content_list2, container, false);
		//init key value
		mKey = mItemType.getItemId() + mItemType.getName();
		DebugLog.e(TAG, "[id:%d name:%s] onCreateView()", mItemType.getItemId(), mItemType.getName());
		if (mContentList == null) {
			mContentList = new ArrayList<ContentType>();
			readCache();
		}


		initView(rootView);

		return rootView;
	}

	/**
	 * read disk cache
	 */
	private void readCache() {
		long start = System.currentTimeMillis();
		// read cache from disk
		String json = Config.getInstance().readContentCache(mKey);
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
		mPullRefreshListView = (PullToRefreshRecyclerView) rootView.findViewById(R.id.pull_refresh_list);
//		mEmptyView = rootView.findViewById(R.id.empty_layout);

		RecyclerView actualListView = mPullRefreshListView.getRefreshableView();
		mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		actualListView.setLayoutManager(mLayoutManager);

		mAdapter = new ContentListAdapter2(getActivity(), mContentList);
		mAdapter.setOnItemClickListener(this);
		actualListView.setAdapter(mAdapter);

		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<RecyclerView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<RecyclerView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
				if (state.equals(PullToRefreshBase.State.PULL_TO_REFRESH)) {

					if (direction == PullToRefreshBase.Mode.PULL_FROM_START) {

						long lastRefreshTime = Config.getInstance().getLastRefreshTime(mKey);

						//have last refresh time,update label
						if (lastRefreshTime != 0) {
							String label = DateUtil.formatDateToString(lastRefreshTime);
							// Update the LastUpdatedLabel
							refreshView.getHeaderLayout().setLastUpdatedLabel(getString(R.string.pull_to_refresh_last_refresh_label) + " : " + label);
//							mPullRefreshListView.getHeaderLoadingLayout().setLastUpdatedLabel(getString(R.string.pull_to_refresh_last_refresh_label) + " : " + label);
						}
					} else if (direction == PullToRefreshBase.Mode.PULL_FROM_END) {

						refreshView.getFooterLayout().setLastUpdatedLabel("");
					}

				}
			}
		});
//		actualListView.setOnItemClickListener(this);
		
	}
	
	/**
	 * is need auto request
	 */
	private boolean isNeedRequest() {
		boolean needRequest = false;
		if(!NetMonitor.isNetworkConnected(getActivity())){
			return needRequest;
		}
		long time = Config.getInstance().getCacheLastModified(getActivity(), mKey);
		long currentTime = System.currentTimeMillis();
		// judge cache last modified time,in wifi state,if >4 hours,need request data
		if (currentTime - time > 4 * 60 *60 * 1000 && NetMonitor.isWifiState(getActivity())) {
			needRequest = true;
		} else if (currentTime - time > 24 * 60 * 60 * 1000 && (!NetMonitor.isWifiState(getActivity()))) {
			// judge cache last modified time,not in wifi state,if >24 hours,need request data
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
//		if (isNeedRequest()) {
//			mPullRefreshListView.setRefreshing();
//		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mContentList != null) {
			outState.putParcelableArrayList(KEY_CONTENT, mContentList);
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
		//save refresh time
		Config.getInstance().setLastRefreshTime(mKey,System.currentTimeMillis());

		ArticleFactory articleFactory = new ArticleFactory(mItemType.getItemId(),Config.REFRESH,getRefreshTime());
		HttpManager.getInstance().post(this.getActivity(), this, HttpRequestId.CONTENT_LIST_REFRESH, HttpRequestURL.CONTENT_LIST, 
				articleFactory.product().parameters());
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

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
	public void onItemClick(View view, final int position) {
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
				intent.putExtra(DetailsActivity.ARTICLE, mContentList.get(position - 1));
				startActivity(intent);
			}
		});
	}


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
		Config.getInstance().writeContentCache(mKey, cacheJson);
	}

	@Override
	public void onHttpSuccess(int taskId, Object response, String json) {
		DebugLog.d(TAG, "onHttpSuccess:" + taskId);
		// Call onRefreshComplete when the list has been refreshed.
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mPullRefreshListView.onRefreshComplete();
			}
		});
		try {
			DebugLog.i(TAG,json);
			Article article = new Gson().fromJson(json, Article.class);
			if (taskId == HttpRequestId.CONTENT_LIST_REFRESH) {
				//if haven't refresh data,return
				if(article.getData()==null || article.getData().size()==0){
					if(isVisible() && (!mIsHidden)) {
						Toaster.showDefToast(getActivity(), R.string.data_newest);
						setEmptyView();
					}
					return;
				}
				long time = Config.getInstance().getCacheLastModified(getActivity(), mKey);
				long currentTime = System.currentTimeMillis();
				
				// judge cache last mofified time,if > one day,clear cache data
				if (currentTime - time > 24 * 60 * 60 * 1000) {
					mContentList.clear();
				}
				
				// refresh add index 0
				mContentList.addAll(0, article.getData());
			} else if (taskId == HttpRequestId.CONTENT_LIST_LOADMORE) {
				//if haven't no more data,return
				if(article.getData()==null || article.getData().size()==0){
					if(isVisible() && (!mIsHidden)) {
						Toaster.showDefToast(getActivity(), R.string.data_no_more);
						setEmptyView();
					}
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
			if(isVisible() && (!mIsHidden)) {
				Toaster.showDefToast(getActivity(), R.string.net_data_error);
				setEmptyView();
			}
		}
	}

	@Override
	public void onHttpFailure(int taskId, String message) {
		DebugLog.d(TAG, "onHttpFailure:" + taskId);
		// Call onRefreshComplete when the list has been refreshed.
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mPullRefreshListView.onRefreshComplete();
			}
		});
		if(isVisible() && (!mIsHidden)) {
			Toaster.showDefToast(getActivity(), R.string.error_network_connection);
			setEmptyView();
		}

	}

	/**
	 * set empty view when listview no data
	 */
	private void setEmptyView() {
		if (mContentList==null || mContentList.size()==0){
//			mPullRefreshListView.setEmptyView(mEmptyView);
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		mIsHidden = hidden;
	}

	@Override
	public void onDestroyView() {
		DebugLog.e(TAG, "[id:%d name:%s] onDestroyView()", mItemType.getItemId(), mItemType.getName());
		super.onDestroyView();
	}

	
}
