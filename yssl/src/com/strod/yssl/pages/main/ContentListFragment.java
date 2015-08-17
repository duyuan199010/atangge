package com.strod.yssl.pages.main;

import java.util.ArrayList;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.roid.core.Manager;
import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.net.http.Task;
import com.roid.ui.AbsFragment;
import com.roid.util.CommonUtils;
import com.roid.util.DebugLog;
import com.roid.util.JsonParser;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.clientcore.HttpRequestId;
import com.strod.yssl.clientcore.HttpRequestURL;
import com.strod.yssl.pages.details.DetailsActivity;
import com.strod.yssl.pages.main.entity.Article;
import com.strod.yssl.pages.main.entity.Article.ContentType;
import com.strod.yssl.pages.main.entity.ItemType;
import com.strod.yssl.util.DateUtil;
import com.strod.yssl.util.NetMonitor;

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

	private boolean needRequest = true;

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
		DebugLog.e(TAG, "[id:%d name:%s] onCreateView()", mItemType.getId(), mItemType.getName());
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
		// read cache from disk
		String key = mItemType.getId() + mItemType.getName();
		String json = Config.getInstance().readContentCache(key);
		if (json == null || json.equals("")) {
			// no cache
			return;
		}
		// have cache
		JsonParser<Article> parser = new JsonParser<Article>();
		Article article = parser.parseJson(json, Article.class);
		mContentList.addAll(article.getData());
		DebugLog.e(TAG, "[id:%d name:%s] read cache size:%d", mItemType.getId(), mItemType.getName(), mContentList.size());
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
		actualListView.setSelector(R.drawable.item_background_selector);
		actualListView.setOnItemClickListener(this);
	}
	
	/**
	 * is need auto request
	 */
	private void isNeedRequest() {
		if(!NetMonitor.isNetworkConnected(getActivity())){
			needRequest = false;
			return;
		}
		long time = Config.getInstance().getCacheLastModified(getActivity(), mItemType.getId() + mItemType.getName());
		long currentTime = System.currentTimeMillis();
		// judge cache last mofified time,in wifi state,if >four hours,need request data
		if (currentTime - time > 4 * 60 * 1000 && NetMonitor.isWifiState(getActivity())) {
			needRequest = true;
		} else if (currentTime - time > 24 * 60 * 1000 && (!NetMonitor.isWifiState(getActivity()))) {
			// judge cache last mofified time,not in wifi state,if >24 hours,need request data
			needRequest = true;
		} else {
			//other didn't need request
			needRequest = false;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isNeedRequest();
		if (needRequest) {
			mPullRefreshListView.setRefreshing(true);
			needRequest = false;
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
		String label = DateUtil.formatDateToString(System.currentTimeMillis());
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		try {
			JsonParser<ContentType> parser = new JsonParser<ContentType>();
			ContentType contentType = new ContentType();
			String json = parser.toJson(contentType);
			StringEntity enity = new StringEntity(json, HTTP.UTF_8);
			Task task = new Task(enity, HttpRequestURL.HOST, HttpRequestURL.CONTENT_LIST);
			Manager.getInstance().post(this.getActivity(), this, HttpRequestId.CONTENT_LIST_REFRESH, HttpRequestURL.CONTENT_TYPE, task, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtil.formatDateToString(System.currentTimeMillis());
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		try {
			JsonParser<ContentType> parser = new JsonParser<ContentType>();
			ContentType contentType = new ContentType();
			String json = parser.toJson(contentType);
			StringEntity enity = new StringEntity(json, HTTP.UTF_8);
			Task task = new Task(enity, HttpRequestURL.HOST, HttpRequestURL.CONTENT_LIST);
			Manager.getInstance().post(this.getActivity(), this, HttpRequestId.CONTENT_LIST_LOADMORE, HttpRequestURL.CONTENT_TYPE, task, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (CommonUtils.isFastDoubleClick())
			return;
		if (mContentList == null)
			return;

		Intent intent = new Intent(getActivity(), DetailsActivity.class);
		intent.putExtra(DetailsActivity.ARTICLE, mContentList.get(position-1));
		startActivity(intent);
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
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

	@Override
	public void onHttpResponse(int taskId, String data) {
		DebugLog.d(TAG, "onHttpResponse:" + taskId);
		// Call onRefreshComplete when the list has been refreshed.
		mHandler.sendEmptyMessage(REFRESH_COMPLETE);
		if (data == null || data.toString().equals("")) {
			Toaster.showDefToast(getActivity(), R.string.error_network_connection);
		} else {
			DebugLog.d(TAG,data.toString());
			try {
				JsonParser<Article> parser = new JsonParser<Article>();
				Article article = parser.parseJson(data, Article.class);
				if (taskId == HttpRequestId.CONTENT_LIST_REFRESH) {
					long time = Config.getInstance().getCacheLastModified(getActivity(), mItemType.getId() + mItemType.getName());
					long currentTime = System.currentTimeMillis();
					// judge cache last mofified time,if > one day,clear cache data
					if (currentTime - time > 24 * 60 * 60 * 1000) {
						mContentList.clear();
					}
					// refresh add index 0
					mContentList.addAll(0, article.getData());
				} else if (taskId == HttpRequestId.CONTENT_LIST_LOADMORE) {
					// loadmore add index end
					mContentList.addAll(article.getData());
				}
				mAdapter.notifyDataSetChanged();
				saveDataCache();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toaster.showDefToast(getActivity(), R.string.net_data_error);
			}
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		DebugLog.e(TAG, "[id:%d name:%s] onDestroyView()", mItemType.getId(), mItemType.getName());
		super.onDestroyView();
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
		// cache CACHE_SIZE data most
		if (mContentList.size() > CACHE_SIZE) {
			article.setData(mContentList.subList(0, CACHE_SIZE));
		} else {
			article.setData(mContentList);
		}
		DebugLog.e(TAG, "[id:%d name:%s] save cache size:%d", mItemType.getId(), mItemType.getName(), article.getData().size());

		String cacheJson = parser.toJson(article);
		// save cache data
		Config.getInstance().writeContentCache(mItemType.getId() + mItemType.getName(), cacheJson);
	}

}
