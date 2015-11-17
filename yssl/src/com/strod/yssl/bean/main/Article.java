package com.strod.yssl.bean.main;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.strod.yssl.bean.Result;

public class Article extends Result {

	private List<ContentType> data;

	public Article() {
		super();
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
	}

	/**
	 * list content type bean
	 * @author User
	 *
	 */
	public static class ContentType implements Parcelable {

		/** mode */
		private int mode;
		/** id */
		private int articleId;
		/** img url */
		private String imgUrl;
		/** img url */
		private String secondImgUrl;
		/** img url */
		private String thirdImgUrl;
		/** title */
		private String title;
		/** content */
		private String content;
		/** source*/
		private String source;
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
			mode = in.readInt();
			articleId = in.readInt();
			imgUrl = in.readString();
			secondImgUrl = in.readString();
			thirdImgUrl = in.readString();
			title = in.readString();
			content = in.readString();
			source = in.readString();
			collectNum = in.readInt();
			praiseNum = in.readInt();
			time = in.readLong();
			contentUrl = in.readString();
		}

		public ContentType(int mode,int articleId, String imgUrl, String secondImgUrl, String thirdImgUrl,
						   String title, String content, String source, int collectNum, int praiseNum, long time, String contentUrl) {
			super();
			this.mode = mode;
			this.articleId = articleId;
			this.imgUrl = imgUrl;
			this.secondImgUrl = secondImgUrl;
			this.thirdImgUrl = thirdImgUrl;
			this.title = title;
			this.content = content;
			this.source = source;
			this.collectNum = collectNum;
			this.praiseNum = praiseNum;
			this.time = time;
			this.contentUrl = contentUrl;
		}

		public int getMode() {
			return mode;
		}

		public void setMode(int mode) {
			this.mode = mode;
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

		public String getSecondImgUrl() {
			return secondImgUrl;
		}

		public void setSecondImgUrl(String secondImgUrl) {
			this.secondImgUrl = secondImgUrl;
		}

		public String getThirdImgUrl() {
			return thirdImgUrl;
		}

		public void setThirdImgUrl(String thirdImgUrl) {
			this.thirdImgUrl = thirdImgUrl;
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

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
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
			return "ContentType{" +
					"mode=" + mode +
					", articleId=" + articleId +
					", imgUrl='" + imgUrl + '\'' +
					", secondImgUrl='" + secondImgUrl + '\'' +
					", thirdImgUrl='" + thirdImgUrl + '\'' +
					", title='" + title + '\'' +
					", content='" + content + '\'' +
					", source='" + source + '\'' +
					", collectNum=" + collectNum +
					", praiseNum=" + praiseNum +
					", time=" + time +
					", contentUrl='" + contentUrl + '\'' +
					'}';
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(mode);
			dest.writeInt(articleId);
			dest.writeString(imgUrl);
			dest.writeString(secondImgUrl);
			dest.writeString(thirdImgUrl);
			dest.writeString(title);
			dest.writeString(content);
			dest.writeString(source);
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
