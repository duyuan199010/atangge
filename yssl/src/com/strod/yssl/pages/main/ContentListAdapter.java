package com.strod.yssl.pages.main;

import java.util.ArrayList;

import com.strod.yssl.R;
import com.strod.yssl.pages.main.entity.ContentType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * listview adapter
 * @author User
 *
 */
public class ContentListAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<ContentType> mContentList;
	
	public ContentListAdapter(Context context,ArrayList<ContentType> contentList){
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		this.mContentList = contentList;
	}
	
	@Override
	public int getCount() {
		return mContentList==null?0:mContentList.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_content_list, null);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	class ViewHolder{
		ImageView mImage;
		TextView mTitle;
		TextView mContent;
		TextView mCollect;
		TextView mPraise;
		TextView mTime;
	}
}
