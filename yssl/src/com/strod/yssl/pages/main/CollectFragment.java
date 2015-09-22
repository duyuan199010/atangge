package com.strod.yssl.pages.main;

/**
 * 页面
 */
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.roid.ui.AbsFragment;
import com.roid.util.JsonParser;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article;
import com.strod.yssl.bean.main.Article.ContentType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.swipelistview.ListViewonSingleTapUpListenner;
import com.strod.yssl.view.swipelistview.OnDeleteListioner;
import com.strod.yssl.view.swipelistview.SwipeListView;

public class CollectFragment extends AbsFragment implements OnDeleteListioner,OnItemClickListener{
	
	/**UI listview*/
	private SwipeListView mSwipeListView;
	/** listview datasource */
	private ArrayList<ContentType> mContentList;
	/**listview adapter*/
	private CollectAdapter mCollectAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collect, container, false);
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
		String key = "0"+"最热";
		String json = Config.getInstance().readContentCache(key);
		if (json == null || json.equals("")) {
			// no cache
			return;
		}
		// have cache
		JsonParser<Article> parser = new JsonParser<Article>();
		Article article = parser.parseJson(json, Article.class);
		mContentList.addAll(article.getData());
	}

	private void initView(View rootView){
		mSwipeListView = (SwipeListView) rootView.findViewById(R.id.swipe_list_view);
		mCollectAdapter = new CollectAdapter();
		mSwipeListView.setAdapter(mCollectAdapter);
		mSwipeListView.setDeleteListioner(this);
		mSwipeListView.setOnItemClickListener(this);
	}
	
	@Override
	public boolean isCandelete(int position) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Toaster.showDefToast(getActivity(), "position"+position);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	/**
	 * adapter
	 * @author Administrator
	 *
	 */
	class CollectAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return mContentList.size();
		}

		@Override
		public Object getItem(int position) {
			return mContentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_collect_list, null);
				
				holder.mImage = (ImageView) convertView.findViewById(R.id.content_img);
				holder.mTitle = (TextView) convertView.findViewById(R.id.content_title);
				holder.mContent = (TextView) convertView.findViewById(R.id.content_detail);
				
				holder.mTop = (TextView) convertView.findViewById(R.id.item_top);
				holder.mDelete = (TextView) convertView.findViewById(R.id.item_delete);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			ContentType contentType= mContentList.get(position);
			if(contentType!=null){
				ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holder.mImage,Config.getInstance().getDisplayImageOptions());
				holder.mTitle.setText(contentType.getTitle());
				holder.mContent.setText(contentType.getContent());
				
				holder.mTop.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ContentType temp = mContentList.remove(position);
						mContentList.add(0, temp);
						mSwipeListView.resetItem();
						notifyDataSetChanged();
					}
				});
				
				holder.mDelete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mContentList.remove(position);
						mSwipeListView.deleteItem();
						notifyDataSetChanged();
					}
				});
			}
			return convertView;
		}
		
		class ViewHolder{
			ImageView mImage;
			TextView mTitle;
			TextView mContent;
			
			TextView mTop;
			TextView mDelete;
			
			
		}
	}

}
