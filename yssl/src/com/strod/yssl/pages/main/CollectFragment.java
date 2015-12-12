package com.strod.yssl.pages.main;

/**
 * 页面
 */
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.roid.util.ScreenUtils;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article;
import com.strod.yssl.bean.main.Collect;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.database.DatabaseHelper;
import com.strod.yssl.pages.details.DetailsActivity;
import com.strod.yssl.view.swipemenulistview.SwipeMenu;
import com.strod.yssl.view.swipemenulistview.SwipeMenuCreator;
import com.strod.yssl.view.swipemenulistview.SwipeMenuItem;
import com.strod.yssl.view.swipemenulistview.SwipeMenuListView;

public class CollectFragment extends AbsFragment implements OnItemClickListener{
	
	/**UI listview*/
	private SwipeMenuListView mSwipeListView;
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
		mSwipeListView = (SwipeMenuListView) rootView.findViewById(R.id.swipe_list_view);
		mCollectAdapter = new CollectAdapter();
		mSwipeListView.setAdapter(mCollectAdapter);
		mSwipeListView.setOnItemClickListener(this);

		SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
				// create "top" item
				SwipeMenuItem topItem = new SwipeMenuItem(getActivity().getApplicationContext());
				// set item background
				topItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xD7, 0x00)));
				// set item width
				topItem.setWidth(ScreenUtils.dip2px(getActivity(),90));
				topItem.setTitle(getString(R.string.item_top));
				topItem.setTitleSize(18);
				topItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(topItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(ScreenUtils.dip2px(getActivity(),90));
				deleteItem.setTitle(getString(R.string.item_delete));
				deleteItem.setTitleSize(18);
				deleteItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(deleteItem);
            }
        };
        // set creator
		mSwipeListView.setMenuCreator(creator);
		mSwipeListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
					case 0:
						Collect temp = mCollectList.remove(position);
						mCollectList.add(0, temp);
						mCollectAdapter.notifyDataSetChanged();
						break;
					case 1:
						mCollectList.remove(position);
						mCollectAdapter.notifyDataSetChanged();
						break;
				}
				// false : close the menu; true : not close the menu
				return false;
			}
		});
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
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			Collect contentType= mCollectList.get(position);
			if(contentType!=null){
				ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holder.mImage, Config.getInstance().getDisplayImageOptions());
				holder.mTitle.setText(contentType.getTitle());
				holder.mContent.setText(contentType.getContent());
			}
			return convertView;
		}
		
		class ViewHolder{
			ImageView mImage;
			TextView mTitle;
			TextView mContent;
			
		}
	}

}
