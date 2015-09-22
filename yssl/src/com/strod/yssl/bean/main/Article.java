package com.strod.yssl.bean.main;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.strod.yssl.bean.Result;

public class Article extends Result {

	private List<ContentType> data;

	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ContentType> getData() {
		return data;
	}

	public void setData(List<ContentType> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Article [data=" + data + "]";
	}

	public Article(int ret_code, String ret_msg) {
		super(ret_code, ret_msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * list content type bean
	 * @author User
	 *
	 */
	public static class ContentType implements Parcelable {

		/** id */
		private int articleId;
		/** img url */
		private String imgUrl;
		/** title */
		private String title;
		/** content */
		private String content;
		/** collect number */
		private int collectNum;
		/** praise number */
		private int praiseNum;
		/** publish time */
		private long time;
		/** content url */
		private String contentUrl;

		public ContentType() {
			super();
		}

		public ContentType(Parcel in) {
			articleId = in.readInt();
			imgUrl = in.readString();
			title = in.readString();
			content = in.readString();
			collectNum = in.readInt();
			praiseNum = in.readInt();
			time = in.readLong();
			contentUrl = in.readString();
		}

		public ContentType(int articleId, String imgUrl, String title, String content, int collectNum, int praiseNum, long time, String contentUrl) {
			super();
			this.articleId = articleId;
			this.imgUrl = imgUrl;
			this.title = title;
			this.content = content;
			this.collectNum = collectNum;
			this.praiseNum = praiseNum;
			this.time = time;
			this.contentUrl = contentUrl;
		}

		public int getArticleId() {
			return articleId;
		}

		public void setArticleId(int articleId) {
			this.articleId = articleId;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getCollectNum() {
			return collectNum;
		}

		public void setCollectNum(int collectNum) {
			this.collectNum = collectNum;
		}

		public int getPraiseNum() {
			return praiseNum;
		}

		public void setPraiseNum(int praiseNum) {
			this.praiseNum = praiseNum;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getContentUrl() {
			return contentUrl;
		}

		public void setContentUrl(String contentUrl) {
			this.contentUrl = contentUrl;
		}

		@Override
		public String toString() {
			return "ContentType [articleId=" + articleId + ", imgUrl=" + imgUrl + ", title=" + title + ", content=" + content + ", collectNum=" + collectNum + ", praiseNum=" + praiseNum + ", time=" + time
					+ ", contentUrl=" + contentUrl + "]";
		}

		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(articleId);
			dest.writeString(imgUrl);
			dest.writeString(title);
			dest.writeString(content);
			dest.writeInt(collectNum);
			dest.writeInt(praiseNum);
			dest.writeLong(time);
			dest.writeString(contentUrl);
		}

		public static final Parcelable.Creator<ContentType> CREATOR = new Creator<ContentType>() {
			@Override
			public ContentType[] newArray(int size) {
				return new ContentType[size];
			}

			@Override
			public ContentType createFromParcel(Parcel in) {
				return new ContentType(in);
			}
		};
	}
}
