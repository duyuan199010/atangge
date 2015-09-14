package com.roid.net.http;

public interface OnHttpRespondLisenter {
	
	public void onHttpSuccess(final int taskId,Object response, String json);
	
	public void onHttpFailure(final int taskId,String message);
}
