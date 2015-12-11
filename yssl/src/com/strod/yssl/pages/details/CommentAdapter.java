package com.strod.yssl.pages.details;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.strod.yssl.R;
import com.strod.yssl.bean.details.Comment.CommentType;
import com.strod.yssl.util.DateUtil;

/**
 * listview adapter
 * @author User
 *
 */
public class CommentAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<CommentType> mCommentList;
	
	public CommentAdapter(Context context,ArrayList<CommentType> commentList){
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		this.mCommentList = commentList;
	}
	
	@Override
	public int getCount() {
		return mCommentList==null?0:mCommentList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCommentList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_content_list_left, null);
			holder.mImage = (ImageView) convertView.findViewById(R.id.content_img);
			holder.mTitle = (TextView) convertView.findViewById(R.id.content_title);
			holder.mContent = (TextView) convertView.findViewById(R.id.content_detail);
			holder.mCollect = (TextView) convertView.findViewById(R.id.content_collect);
			holder.mPraise = (TextView) convertView.findViewById(R.id.content_praise);
			holder.mTime = (TextView) convertView.findViewById(R.id.content_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CommentType contentType= mCommentList.get(position);
		if(contentType!=null){
			holder.mTitle.setText(contentType.getTitle());
			holder.mContent.setText(contentType.getContent());
			holder.mCollect.setText(contentType.getCollectNum()+"");
			holder.mPraise.setText(contentType.getPraiseNum()+"");
			holder.mTime.setText(DateUtil.formatDateToString(contentType.getTime()*1000));
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
