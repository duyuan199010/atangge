package com.strod.yssl.pages.main;

/**
 * 页面
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.roid.ui.AbsFragment;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article;
import com.strod.yssl.bean.main.Collect;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.database.DatabaseHelper;
import com.strod.yssl.pages.details.DetailsActivity;
import com.strod.yssl.view.swipelistview.OnDeleteListioner;
import com.strod.yssl.view.swipelistview.SwipeListView;

public class CollectFragment extends AbsFragment implements OnDeleteListioner,OnItemClickListener{
	
	/**UI listview*/
	private SwipeListView mSwipeListView;
	/** listview datasource */
	private List<Collect> mCollectList;
	/**listview adapter*/
	private CollectAdapter mCollectAdapter;

	/**database*/
	private DatabaseHelper mDatabaseHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collect, container, false);

		initData();
		initView(rootView);
		
		return rootView;
	}

	private void initData(){
		if (mDatabaseHelper==null)
			mDatabaseHelper= new DatabaseHelper<Collect>();

		mCollectList = mDatabaseHelper.queryForAll(Collect.class);
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
		return true;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Collect collect = mCollectList.get(position);

		Article.ContentType contentType = new Article.ContentType();
		contentType.setTitle(collect.getTitle());
		contentType.setArticleId(collect.getArticleId());
		contentType.setImgUrl(collect.getImgUrl());
		contentType.setContent(collect.getContent());
		contentType.setContentUrl(collect.getContentUrl());
		contentType.setTime(collect.getTime());

		Intent intent = new Intent(getActivity(), DetailsActivity.class);
		intent.putExtra(DetailsActivity.ARTICLE, contentType);
		startActivity(intent);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mDatabaseHelper!=null){
			//save data to database
		}
	}

	@Override
	public void onDestroyView() {
		mDatabaseHelper=null;
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
			return mCollectList==null?0:mCollectList.size();
		}

		@Override
		public Object getItem(int position) {
			return mCollectList.get(position);
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
			
			Collect contentType= mCollectList.get(position);
			if(contentType!=null){
				ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holder.mImage,Config.getInstance().getDisplayImageOptions());
				holder.mTitle.setText(contentType.getTitle());
				holder.mContent.setText(contentType.getContent());
				
				holder.mTop.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Collect temp = mCollectList.remove(position);
						mCollectList.add(0, temp);
						mSwipeListView.resetItem();
						notifyDataSetChanged();
					}
				});
				
				holder.mDelete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mCollectList.remove(position);
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
