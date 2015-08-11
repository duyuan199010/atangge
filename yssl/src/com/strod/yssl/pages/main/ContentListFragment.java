package com.strod.yssl.pages.main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

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
import com.roid.util.DebugLog;
import com.roid.util.JsonParser;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.HttpRequestId;
import com.strod.yssl.clientcore.HttpRequestURL;
import com.strod.yssl.pages.main.entity.ContentType;
import com.strod.yssl.pages.main.entity.ItemType;
import com.strod.yssl.util.DateUtil;

public final class ContentListFragment extends AbsFragment implements OnRefreshListener2<ListView>, OnItemClickListener,OnHttpRespondLisenter{
	
	/**debug log tag*/
	private static final String TAG = "ContentListFragment";
	/**refresh complete tag*/
	private static final int REFRESH_COMPLETE = 0;
	
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
//		mPullRefreshListView.setRefreshing(true);

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

	}
	
	Handler mHandler = new Handler(){

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
		DebugLog.d(TAG, "onHttpResponse:"+taskId);
		// Call onRefreshComplete when the list has been refreshed.
		mHandler.sendEmptyMessage(REFRESH_COMPLETE);
		if(data ==null || data.toString().equals("")){
			Toaster.showDefToast(getActivity(), R.string.error_network_connection);
		}else{
			DebugLog.d(TAG, data.toString());
			Toaster.showDefToast(getActivity(), data.toString());
		}
	}
}
