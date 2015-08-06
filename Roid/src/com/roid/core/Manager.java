package com.roid.core;

import java.io.File;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.roid.AbsApplication;
import com.roid.net.http.AsyncHttpClient;
import com.roid.net.http.AsyncHttpResponseHandler;
import com.roid.net.http.JsonHttpResponseHandler;
import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.net.http.RequestParams;
import com.roid.net.http.Task;
import com.roid.ui.dialog.LoadingDialog;

import android.content.Context;
import android.util.Log;

public class Manager {

	private static Manager mManager = null;
	private AsyncHttpClient httpClient;
	private LoadingDialog loadingDialog;

	public Manager getInstance() {
		if (mManager == null) {
			synchronized (Manager.class) {
				if (mManager == null) {
					mManager = new Manager();
				}
			}
		}
		return mManager;
	}
	
	public Manager(){
		httpClient = new AsyncHttpClient();
		httpClient.setTimeout(1000 * 10);
	}
	
	private void showDialog(Context context){
		loadingDialog = LoadingDialog.show(context);
	}
	
	private void dissmissDialog(){
		if(loadingDialog!=null){
			loadingDialog.dismiss();
		}
	}

	public void get(Context context,final OnHttpRespondLisenter<JSONObject> call, final int reqNo, Task task, final boolean isShowProgress) {
		if (isShowProgress) {
			showDialog(context);
		}
		if (task != null) {
			RequestParams params = task.getRequestParams();

			httpClient.get(task.getTaskHost() + task.getTaskPath(), params, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, response);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, response);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(e, errorResponse);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}

			});

		}

	}

	public void post(Context context,final OnHttpRespondLisenter<JSONObject> call, final int reqNo, String contentType, Task task, final boolean isShowProgress) {
		if (isShowProgress) {
			showDialog(context);
		}

		if (task != null) {
			StringEntity entity = task.getStringEntity();

			httpClient.post(context, task.getTaskHost() + task.getTaskPath(), entity, contentType, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, response);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, response);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(e, errorResponse);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}
			});

		}

	}

	/**
	 * 
	 * @param call
	 * @param reqNo
	 * @param contentType
	 * @param task
	 * @param isShowProgress
	 *            是否显示进度条
	 * @param progressTips
	 *            进度条提示语
	 */
	public void get(Context context,final OnHttpRespondLisenter<JSONObject> call, final int reqNo, Task task, final boolean isShowProgress, final String progressTips) {
		if (isShowProgress) {
			showDialog(context);
		}
		if (task != null) {
			RequestParams params = task.getRequestParams();

			httpClient.get(task.getTaskHost() + task.getTaskPath(), params, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, response);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, response);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(e, errorResponse);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}
			});

		}

	}

	/**
	 * 
	 * @param call
	 * @param reqNo
	 * @param contentType
	 * @param task
	 * @param isShowProgress
	 *            是否显示进度条
	 * @param progressTips
	 *            进度条提示语
	 */
	public void post(Context context,final OnHttpRespondLisenter<JSONObject> call, final int reqNo, String contentType, Task task, final boolean isShowProgress, final String progressTips) {
		if (isShowProgress) {
			showDialog(context);
		}

		if (task != null) {
			StringEntity entity = task.getStringEntity();

			httpClient.post(context, task.getTaskHost() + task.getTaskPath(), entity, contentType, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, response);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, response);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(e, errorResponse);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}
			});

		}

	}

}
