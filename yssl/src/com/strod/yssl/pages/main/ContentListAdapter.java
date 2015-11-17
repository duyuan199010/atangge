package com.strod.yssl.pages.main;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article.ContentType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.util.DateUtil;

/**
 * listview adapter
 * @author User
 *
 */
public class ContentListAdapter extends BaseAdapter{

	private static final  int BIG_IMG = 0;
	private static final  int LEFT_IMG = 1;
	private static final  int THIRD_IMG = 2;
	private static final  int TYPE_COUNT = 3;

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<ContentType> mContentList;
	
	public ContentListAdapter(Context context,ArrayList<ContentType> contentList){
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		this.mContentList = contentList;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {

		return mContentList.get(position).getMode();
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

		ViewHolderBig holderBig = null;
		ViewHolderLeft holderLeft = null;
		ViewHolderThird holderThird = null;

		int itemType = getItemViewType(position);
		ContentType contentType= mContentList.get(position);

		switch (itemType)
		{
			case BIG_IMG:
				if (convertView == null) {
					holderBig = new ViewHolderBig();
					convertView = mInflater.inflate(R.layout.item_content_list_big, null);
					holderBig.mImage = (ImageView) convertView.findViewById(R.id.content_img);
					holderBig.mTitle = (TextView) convertView.findViewById(R.id.content_title);
					holderBig.mCollect = (TextView) convertView.findViewById(R.id.content_collect);
					holderBig.mPraise = (TextView) convertView.findViewById(R.id.content_praise);
					holderBig.mSource = (TextView)convertView.findViewById(R.id.content_source);
					holderBig.mTime = (TextView) convertView.findViewById(R.id.content_time);
					convertView.setTag(holderBig);
				} else {
					holderBig = (ViewHolderBig) convertView.getTag();
				}

				if(contentType!=null){
					ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holderBig.mImage, Config.getInstance().getDisplayImageOptions());
					holderBig.mTitle.setText(contentType.getTitle());
					holderBig.mCollect.setText("收藏:"+contentType.getCollectNum());
					holderBig.mPraise.setText("点赞:"+contentType.getPraiseNum()+"");
					holderBig.mSource.setText("来源:"+contentType.getSource());
					holderBig.mTime.setText(DateUtil.formatDateToString(contentType.getTime()));
				}
				break;
			case LEFT_IMG:
				if (convertView == null) {
					holderLeft = new ViewHolderLeft();
					convertView = mInflater.inflate(R.layout.item_content_list_left, null);
					holderLeft.mImage = (ImageView) convertView.findViewById(R.id.content_img);
					holderLeft.mTitle = (TextView) convertView.findViewById(R.id.content_title);
					holderLeft.mContent = (TextView) convertView.findViewById(R.id.content_detail);
					holderLeft.mCollect = (TextView) convertView.findViewById(R.id.content_collect);
					holderLeft.mPraise = (TextView) convertView.findViewById(R.id.content_praise);
					holderLeft.mSource = (TextView)convertView.findViewById(R.id.content_source);
					convertView.setTag(holderLeft);
				} else {
					holderLeft = (ViewHolderLeft) convertView.getTag();
				}

				if(contentType!=null){
					ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holderLeft.mImage,Config.getInstance().getDisplayImageOptions());
					holderLeft.mTitle.setText(contentType.getTitle());
					holderLeft.mContent.setText(contentType.getContent());
					holderLeft.mCollect.setText("收藏:"+contentType.getCollectNum());
					holderLeft.mPraise.setText("点赞:"+contentType.getPraiseNum()+"");
					holderLeft.mSource.setText("来源:"+contentType.getSource());
				}
				break;
			case THIRD_IMG:
				if (convertView == null) {
					holderThird = new ViewHolderThird();
					convertView = mInflater.inflate(R.layout.item_content_list_third, null);
					holderThird.mTitle = (TextView) convertView.findViewById(R.id.content_title);
					holderThird.mImage = (ImageView) convertView.findViewById(R.id.content_img);
					holderThird.mSecondImage = (ImageView) convertView.findViewById(R.id.content_second_img);
					holderThird.mThirdImage = (ImageView) convertView.findViewById(R.id.content_third_img);
					holderThird.mCollect = (TextView) convertView.findViewById(R.id.content_collect);
					holderThird.mPraise = (TextView) convertView.findViewById(R.id.content_praise);
					holderThird.mSource = (TextView)convertView.findViewById(R.id.content_source);
					holderThird.mTime = (TextView) convertView.findViewById(R.id.content_time);
					convertView.setTag(holderThird);
				} else {
					holderThird = (ViewHolderThird) convertView.getTag();
				}

				if(contentType!=null){
					ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holderThird.mImage, Config.getInstance().getDisplayImageOptions());
					ImageLoader.getInstance().displayImage(contentType.getSecondImgUrl(), holderThird.mSecondImage, Config.getInstance().getDisplayImageOptions());
					ImageLoader.getInstance().displayImage(contentType.getThirdImgUrl(), holderThird.mThirdImage, Config.getInstance().getDisplayImageOptions());
					holderThird.mTitle.setText(contentType.getTitle());
					holderThird.mCollect.setText("收藏:"+contentType.getCollectNum());
					holderThird.mPraise.setText("点赞:"+contentType.getPraiseNum());
					holderThird.mSource.setText("来源:"+contentType.getSource());
					holderThird.mTime.setText(DateUtil.formatDateToString(contentType.getTime()));
				}
				break;
			default:
				break;
		}




		return convertView;
	}

	static class ViewHolderBig{
		ImageView mImage;
		TextView mTitle;
		TextView mCollect;
		TextView mPraise;
		TextView mSource;
		TextView mTime;
	}

	static class ViewHolderLeft{
		ImageView mImage;
		TextView mTitle;
		TextView mContent;
		TextView mCollect;
		TextView mPraise;
		TextView mSource;
	}

	static class ViewHolderThird{
		TextView mTitle;
		ImageView mImage;
		ImageView mSecondImage;
		ImageView mThirdImage;
		TextView mCollect;
		TextView mPraise;
		TextView mSource;
		TextView mTime;
	}
}
