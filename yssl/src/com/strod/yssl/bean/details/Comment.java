package com.strod.yssl.bean.details;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.strod.yssl.bean.Result;
/**
 * comment
 * @author user
 *
 */
public class Comment extends Result {

	private List<CommentType> data;

	public Comment() {
		super();
	}

	public List<CommentType> getData() {
		return data;
	}

	public void setData(List<CommentType> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Article [data=" + data + "]";
	}

	public Comment(int ret_code, String ret_msg) {
		super(ret_code, ret_msg);
	}

	/**
	 * list content type bean
	 * @author User
	 *
	 */
	public static class CommentType implements Parcelable {

		/** id */
		private int id;
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

		public CommentType() {
			super();
		}

		public CommentType(Parcel in) {
			id = in.readInt();
			imgUrl = in.readString();
			title = in.readString();
			content = in.readString();
			collectNum = in.readInt();
			praiseNum = in.readInt();
			time = in.readLong();
			contentUrl = in.readString();
		}

		public CommentType(int id, String imgUrl, String title, String content, int collectNum, int praiseNum, long time, String contentUrl) {
			super();
			this.id = id;
			this.imgUrl = imgUrl;
			this.title = title;
			this.content = content;
			this.collectNum = collectNum;
			this.praiseNum = praiseNum;
			this.time = time;
			this.contentUrl = contentUrl;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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
			return "ContentType [id=" + id + ", imgUrl=" + imgUrl + ", title=" + title + ", content=" + content + ", collectNum=" + collectNum + ", praiseNum=" + praiseNum + ", time=" + time
					+ ", contentUrl=" + contentUrl + "]";
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(id);
			dest.writeString(imgUrl);
			dest.writeString(title);
			dest.writeString(content);
			dest.writeInt(collectNum);
			dest.writeInt(praiseNum);
			dest.writeLong(time);
			dest.writeString(contentUrl);
		}

		public static final Parcelable.Creator<CommentType> CREATOR = new Creator<CommentType>() {
			@Override
			public CommentType[] newArray(int size) {
				return new CommentType[size];
			}

			@Override
			public CommentType createFromParcel(Parcel in) {
				return new CommentType(in);
			}
		};
	}
}
