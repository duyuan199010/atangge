package com.roid.net.http;

public interface OnHttpRespondLisenter {
	/**
	 * http请求响应回调
	 * @param respond
	 */
	public void onHttpResponse(final int taskId, String data);
}
