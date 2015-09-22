package com.strod.yssl.clientcore.httprequest;
/**
 * http请求编号，用于客户端标识不同的请求
 * @author user
 *
 */
public interface HttpRequestId {

	/**内容列表刷新*/
	public static final int CONTENT_LIST_REFRESH = 0x0001;
	/**内容列表加载更多*/
	public static final int CONTENT_LIST_LOADMORE = 0x0002;
}
