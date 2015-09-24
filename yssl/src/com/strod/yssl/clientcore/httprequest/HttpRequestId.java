package com.strod.yssl.clientcore.httprequest;
/**
 * http request id,use by diff to client's request
 * @author user
 *
 */
public interface HttpRequestId {

	/**article list refresh*/
	public static final int CONTENT_LIST_REFRESH = 0x0001;
	/**article list loadmore*/
	public static final int CONTENT_LIST_LOADMORE = 0x0002;
}
