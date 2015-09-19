package com.strod.yssl.pages.main.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 栏目类型
 * 
 */
public class ItemType implements Parcelable {


	private int id;
	private String name;

	public ItemType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ItemType(Parcel in) {
		super();
		// TODO Auto-generated constructor stub
		id = in.readInt();
		name = in.readString();
	}

	public ItemType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ItemType [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
	}

	public static final Parcelable.Creator<ItemType> CREATOR = new Creator<ItemType>() {
		@Override
		public ItemType[] newArray(int size) {
			return new ItemType[size];
		}

		@Override
		public ItemType createFromParcel(Parcel in) {
			return new ItemType(in);
		}
	};
}
