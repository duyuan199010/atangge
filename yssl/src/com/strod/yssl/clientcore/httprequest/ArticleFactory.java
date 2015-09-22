package com.strod.yssl.clientcore.httprequest;

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
		return new ArticleParams(itemId,pageSize,refreshType,time);
	}
	
	public static class ArticleParams implements Parameters{
		
		private int itemId;
		private int pageSize;
		private int refreshType;
		private long time;

		public ArticleParams(int itemId, int pageSize, int refreshType, long time) {
			super();
			this.itemId = itemId;
			this.pageSize = pageSize;
			this.refreshType = refreshType;
			this.time = time;
		}

		@Override
		public RequestParams paramters() {
			RequestParams requestParams = new RequestParams();
			requestParams.put("itemId", String.valueOf(itemId));
			requestParams.put("pageSize", String.valueOf(pageSize));
			requestParams.put("refreshType", String.valueOf(refreshType));
			requestParams.put("time", String.valueOf(time));
			return requestParams;
		}

	}
}
