package com.strod.yssl.pages.main.entity;

import java.io.Serializable;

/**
 * 栏目类型
 * 
 */
public class ItemType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791759957183581485L;

	private int id;
	private String name;

	public ItemType() {
		super();
		// TODO Auto-generated constructor stub
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

}
