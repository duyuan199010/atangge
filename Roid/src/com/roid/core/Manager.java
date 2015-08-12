package com.roid.core;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.roid.net.http.AsyncHttpClient;
import com.roid.net.http.AsyncHttpResponseHandler;
import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.net.http.RequestParams;
import com.roid.net.http.Task;
import com.roid.ui.dialog.LoadingDialog;

/**
 * http request manager
 * @author user
 *
 */
public class Manager {

	/**connection time out*/
	public static final int TIME_OUT = 15*1000;
	
	/**singleton Manager instance*/
	private static Manager mManager = null;
	/**AsyncHttpClient instance*/
	private AsyncHttpClient httpClient;
	/**dialog*/
	private LoadingDialog loadingDialog;

	/**
	 * get singleton instance
	 * @return
	 */
	public static Manager getInstance() {
		if (mManager == null) {
			synchronized (Manager.class) {
				if (mManager == null) {
					mManager = new Manager();
				}
			}
		}
		return mManager;
	}

	/**
	 * Initialize Manager
	 */
	public Manager() {
		httpClient = new AsyncHttpClient();
		httpClient.setTimeout(TIME_OUT);
	}

	/**
	 * show progress dialog
	 * @param context
	 */
	private void showDialog(Context context) {
		loadingDialog = LoadingDialog.show(context);
	}

	/**
	 * dissmiss progress dialog
	 */
	private void dissmissDialog() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog=null;
		}
	}

	/**
	 * http get request
	 * @param context :this can not be Applicaton Context,must be Activity,Service.
	 * @param call : callback interface
	 * @param reqNo : callback request NO,to be distinguish different request in client
	 * @param task : request entity
	 * @param isShowProgress : show progress if true,other not
	 */
	public void get(Context context, final OnHttpRespondLisenter call, final int reqNo, Task task, final boolean isShowProgress) {
		if (isShowProgress) {
			showDialog(context);
		}
		if (task != null) {
			RequestParams params = task.getRequestParams();

			httpClient.get(task.getTaskHost() + task.getTaskPath(), params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, String content) {
					super.onSuccess(statusCode, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, content);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}

			});

		}

	}

	
	/**
	 * http post request
	 * @param context :this can not be Applicaton Context,must be Activity,Service.
	 * @param call : callback interface
	 * @param reqNo : callback request NO,to be distinguish different request in client
	 * @param contentType : request content type,json is "Application/josn"
	 * @param task : request entity
	 * @param isShowProgress : show progress if true,other not
	 */
	public void post(Context context, final OnHttpRespondLisenter call, final int reqNo, String contentType, Task task, final boolean isShowProgress) {
		if (isShowProgress) {
			showDialog(context);
		}

		if (task != null) {
			StringEntity entity = task.getStringEntity();

			httpClient.post(context, task.getTaskHost() + task.getTaskPath(), entity, contentType, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, String content) {
					super.onSuccess(statusCode, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, content);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}

			});

		}

	}

	/**
	 * @deprecated
	 * @param context
	 * @param call
	 * @param reqNo
	 * @param task
	 * @param isShowProgress
	 * @param progressTips
	 */
	public void get(Context context, final OnHttpRespondLisenter call, final int reqNo, Task task, final boolean isShowProgress, final String progressTips) {
		if (isShowProgress) {
			showDialog(context);
		}
		if (task != null) {
			RequestParams params = task.getRequestParams();

			httpClient.get(task.getTaskHost() + task.getTaskPath(), params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, content);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}

			});

		}

	}

	/**
	 * @deprecated
	 * @param context
	 * @param call
	 * @param reqNo
	 * @param contentType
	 * @param task
	 * @param isShowProgress
	 * @param progressTips
	 */
	public void post(Context context, final OnHttpRespondLisenter call, final int reqNo, String contentType, Task task, final boolean isShowProgress, final String progressTips) {
		if (isShowProgress) {
			showDialog(context);
		}

		if (task != null) {
			StringEntity entity = task.getStringEntity();

			httpClient.post(context, task.getTaskHost() + task.getTaskPath(), entity, contentType, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
					super.onSuccess(statusCode, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					if (statusCode == 200) {
						call.onHttpResponse(reqNo, content);
					} else {
						call.onHttpResponse(reqNo, null);
					}
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					if (isShowProgress) {
						dissmissDialog();
					}
					call.onHttpResponse(reqNo, null);
				}

			});

		}

	}

}
