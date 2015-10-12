package com.strod.yssl.clientcore.httprequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.roid.net.http.RequestParams;
import com.strod.yssl.clientcore.Config;

/**
 * article parameters factory
 * @author Administrator
 *
 */
public class ArticleFactory implements Provider{

	private int itemId;
	private int pageSize = Config.PAGE_SIZE;
	private int refreshType;
	private long time;
	
	

	public ArticleFactory(int itemId, int refreshType, long time) {
		super();
		this.itemId = itemId;
		this.refreshType = refreshType;
		this.time = time;
	}
	
	

	public ArticleFactory(int itemId, int pageSize, int refreshType, long time) {
		super();
		this.itemId = itemId;
		this.pageSize = pageSize;
		this.refreshType = refreshType;
		this.time = time;
	}

	@Override
	public Parameters product() {
		return new ArticleParams();
	}
	
	/**
	 * inner class
	 * @author user
	 *
	 */
	class ArticleParams implements Parameters{
		
		@Override
		public String parameters() {
			JSONObject requestParams = new JSONObject();
			try {
				requestParams.put("itemId", itemId);
				requestParams.put("pageSize", pageSize);
				requestParams.put("refreshType", refreshType);
				requestParams.put("time", time);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return requestParams.toString();
		}

	}
	
}
