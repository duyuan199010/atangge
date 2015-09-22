package com.strod.yssl.bean.main;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 栏目类型
 * 
 */
public class ItemType implements Parcelable {


	private int itemId;
	private String name;

	public ItemType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ItemType(Parcel in) {
		super();
		// TODO Auto-generated constructor stub
		itemId = in.readInt();
		name = in.readString();
	}

	public ItemType(int itemId, String name) {
		super();
		this.itemId = itemId;
		this.name = name;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ItemType [itemId=" + itemId + ", name=" + name + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(itemId);
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
