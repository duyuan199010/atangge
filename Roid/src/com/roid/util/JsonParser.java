package com.roid.util;

import java.lang.reflect.Type;
import java.util.LinkedList;

import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonParser<T> {

	public T parseJson(String jsonData, Class<T> clazz) {
		T obj = null;
		if (!TextUtils.isEmpty(jsonData) || clazz != null) {
			Gson gson = new Gson();
			obj = gson.fromJson(jsonData, clazz);
		}

		return obj;
	}

	public LinkedList<T> parseJsonList(String jsonData) {
		Type listType = new TypeToken<LinkedList<T>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<T> list = gson.fromJson(jsonData, listType);
		return list;
	}

	public String toJson(T t) {
		String json = null;
		if (t != null) {
			Gson gson = new Gson();
			json = gson.toJson(t);
		}
		return json;
	}

	public String toJsonNamingPolicy(T t) {
		String json = null;
		if (t != null) {
			// 将java对象的属性转换成指定的json名字
			Gson gson = new GsonBuilder().setFieldNamingPolicy(
					FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			json = gson.toJson(t);
		}

		return json;
	}
	
	public String toJsonExpose(T t) {
		String json = null;
		if (t != null) {
			// 将java对象的属性转换成指定的json名字
			Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
			.create();
			json = gson.toJson(t);
		}

		return json;
	}

}
