package com.strod.yssl.pages.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

/**
 * listview adapter
 * @author User
 *
 */
public class ContentListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	private static final  int BIG_IMG = 0;
	private static final  int LEFT_IMG = 1;
	private static final  int THIRD_IMG = 2;
	private static final  int TYPE_COUNT = 3;

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<ContentType> mContentList;

	public ContentListAdapter2(Context context, ArrayList<ContentType> contentList){
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		this.mContentList = contentList;
	}

	@Override
	public int getItemViewType(int position) {
		return mContentList.get(position).getMode();
	}



	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (mOnItemClickListener != null) {
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickListener.onItemClick(holder.itemView, position);
				}
			});
		}

		ContentType contentType= mContentList.get(position);
		if (contentType==null)
			return;

		if (holder instanceof ViewHolderBig){
			ViewHolderBig holderBig = (ViewHolderBig)holder;
			ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holderBig.mImage, Config.getInstance().getDisplayImageOptions());
			holderBig.mTitle.setText(contentType.getTitle());
			holderBig.mCollect.setText("收藏:"+contentType.getCollectNum());
			holderBig.mPraise.setText("点赞:"+contentType.getPraiseNum()+"");
			holderBig.mSource.setText("来源:"+contentType.getSource());
			holderBig.mTime.setText(DateUtil.formatDateToString(contentType.getTime()*1000));
		}else if(holder instanceof ViewHolderLeft){
			ViewHolderLeft holderLeft = (ViewHolderLeft)holder;
			ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holderLeft.mImage,Config.getInstance().getDisplayImageOptions());
			holderLeft.mTitle.setText(contentType.getTitle());
			holderLeft.mContent.setText(contentType.getContent());
			holderLeft.mCollect.setText("收藏:"+contentType.getCollectNum());
			holderLeft.mPraise.setText("点赞:"+contentType.getPraiseNum()+"");
			holderLeft.mSource.setText("来源:"+contentType.getSource());
		}else if(holder instanceof ViewHolderThird){
			ViewHolderThird holderThird = (ViewHolderThird)holder;
			ImageLoader.getInstance().displayImage(contentType.getImgUrl(), holderThird.mImage, Config.getInstance().getDisplayImageOptions());
			ImageLoader.getInstance().displayImage(contentType.getSecondImgUrl(), holderThird.mSecondImage, Config.getInstance().getDisplayImageOptions());
			ImageLoader.getInstance().displayImage(contentType.getThirdImgUrl(), holderThird.mThirdImage, Config.getInstance().getDisplayImageOptions());
			holderThird.mTitle.setText(contentType.getTitle());
			holderThird.mCollect.setText("收藏:"+contentType.getCollectNum());
			holderThird.mPraise.setText("点赞:"+contentType.getPraiseNum());
			holderThird.mSource.setText("来源:"+contentType.getSource());
			holderThird.mTime.setText(DateUtil.formatDateToString(contentType.getTime()*1000));
		}

	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View mView=null;
		RecyclerView.ViewHolder viewHolder=null;

		switch (viewType)
		{
			case BIG_IMG:
				mView = mInflater.inflate(R.layout.item_content_list_big, parent, false);
				viewHolder = new ViewHolderBig(mView);
				break;
			case LEFT_IMG:
				mView = mInflater.inflate(R.layout.item_content_list_left, parent, false);
				viewHolder = new ViewHolderLeft(mView);
				break;
			case THIRD_IMG:
				mView = mInflater.inflate(R.layout.item_content_list_third, parent, false);
				viewHolder = new ViewHolderThird(mView);
				break;
			default:
				break;
		}
		return viewHolder;
	}

	@Override
	public int getItemCount() {
		return mContentList==null?0:mContentList.size();
	}

	public class ViewHolderBig extends RecyclerView.ViewHolder{
		public ImageView mImage;
		public TextView mTitle;
		public TextView mCollect;
		public TextView mPraise;
		public TextView mSource;
		public TextView mTime;

		public ViewHolderBig(View itemView){
			super(itemView);
			mImage = (ImageView) itemView.findViewById(R.id.content_img);
			mTitle = (TextView) itemView.findViewById(R.id.content_title);
			mCollect = (TextView) itemView.findViewById(R.id.content_collect);
			mPraise = (TextView) itemView.findViewById(R.id.content_praise);
			mSource = (TextView)itemView.findViewById(R.id.content_source);
			mTime = (TextView) itemView.findViewById(R.id.content_time);
		}

	}

	public class ViewHolderLeft extends RecyclerView.ViewHolder{
		public ImageView mImage;
		public TextView mTitle;
		public TextView mContent;
		public TextView mCollect;
		public TextView mPraise;
		public TextView mSource;

		public ViewHolderLeft(View itemView){
			super(itemView);
			mImage = (ImageView) itemView.findViewById(R.id.content_img);
			mTitle = (TextView) itemView.findViewById(R.id.content_title);
			mContent = (TextView) itemView.findViewById(R.id.content_detail);
			mCollect = (TextView) itemView.findViewById(R.id.content_collect);
			mPraise = (TextView) itemView.findViewById(R.id.content_praise);
			mSource = (TextView)itemView.findViewById(R.id.content_source);
		}
	}

	public class ViewHolderThird extends RecyclerView.ViewHolder{
		public TextView mTitle;
		public ImageView mImage;
		public ImageView mSecondImage;
		public ImageView mThirdImage;
		public TextView mCollect;
		public TextView mPraise;
		public TextView mSource;
		public TextView mTime;

		public ViewHolderThird(View itemView){
			super(itemView);
			mTitle = (TextView) itemView.findViewById(R.id.content_title);
			mImage = (ImageView) itemView.findViewById(R.id.content_img);
			mSecondImage = (ImageView) itemView.findViewById(R.id.content_second_img);
			mThirdImage = (ImageView) itemView.findViewById(R.id.content_third_img);
			mCollect = (TextView) itemView.findViewById(R.id.content_collect);
			mPraise = (TextView) itemView.findViewById(R.id.content_praise);
			mSource = (TextView)itemView.findViewById(R.id.content_source);
			mTime = (TextView) itemView.findViewById(R.id.content_time);
		}
	}
}
