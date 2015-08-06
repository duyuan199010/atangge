package com.roid.net.http;

public interface OnHttpRespondLisenter<T> {
	/**
	 * http请求响应回调
	 * @param respond
	 */
	public void onHttpResponse(final int taskId, final T data);
}
