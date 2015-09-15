package com.roid.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.roid.AbsApplication;
import com.roid.R;
import com.roid.config.AbsConfig;
import com.roid.net.http.AsyncHttpClient;
import com.roid.net.http.AsyncHttpResponseHandler;
import com.roid.net.http.BinaryHttpResponseHandler;
import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.net.http.RequestParams;
import com.roid.util.NetMonitor;

/**
 * http request manager
 * @author user
 *
 */
public class HttpManager {

	/**connection time out*/
	public static final int TIME_OUT = 15*1000;
	
	/**singleton Manager instance*/
	private static HttpManager mManager = null;
	/**AsyncHttpClient instance*/
	private AsyncHttpClient httpClient;

	/**
	 * get singleton instance
	 * @return
	 */
	public static HttpManager getInstance() {
		if (mManager == null) {
			synchronized (HttpManager.class) {
				if (mManager == null) {
					mManager = new HttpManager();
				}
			}
		}
		return mManager;
	}

	/**
	 * Initialize Manager
	 */
	public HttpManager() {
		httpClient = new AsyncHttpClient();
		httpClient.setTimeout(TIME_OUT);
	}

	/**
	 * 
	 * @param call
	 * @param taskId
	 * @param params
	 * @param path
	 * @param type
	 */
	public void get(final OnHttpRespondLisenter call, final int taskId,RequestParams params,String path,final Type type) {
		get(null, call, taskId, params, path, type);
	}

	public void get(Context context,final OnHttpRespondLisenter call, final int taskId,RequestParams params,String path,final Type type) {
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		httpClient.get(context,AbsConfig.HOST+path, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				try {
					if (statusCode == 200) {
						Object respon = new Gson().fromJson(content, type);
						if(call!=null){
							call.onHttpSuccess(taskId, respon, content);
						}
					} else {
						if(call!=null){
							call.onHttpFailure(taskId, "statusCode:"+statusCode);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if(call!=null){
						call.onHttpFailure(taskId, e.getMessage());
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				call.onHttpFailure(taskId, error.getMessage());
			}

		});
		
	}

	public void get(Context context,final OnHttpRespondLisenter call, final int taskId,Header[] headers,RequestParams params,String path,final Type type) {
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		httpClient.get(context,AbsConfig.HOST+path, headers,params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				try {
					if (statusCode == 200) {
						Object respon = new Gson().fromJson(content, type);
						if(call!=null){
							call.onHttpSuccess(taskId, respon, content);
						}
					} else {
						if(call!=null){
							call.onHttpFailure(taskId, "statusCode:"+statusCode);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if(call!=null){
						call.onHttpFailure(taskId, e.getMessage());
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				if(call!=null){
					call.onHttpFailure(taskId, error.getMessage());
				}
			}

		});

	}
	
	public void post(final OnHttpRespondLisenter call, final int taskId, String path,RequestParams params,final Type type) {
		post(null, call, taskId, path, params, type);
	}
	
	public void post(Context context,final OnHttpRespondLisenter call, final int taskId, String path,RequestParams params,final Type type) {
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		httpClient.post(context,AbsConfig.HOST+path, params, new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				try {
					if (statusCode == 200) {
						Object respon = new Gson().fromJson(content, type);
						if(call!=null){
							call.onHttpSuccess(taskId, respon, content);
						}
					} else {
						if(call!=null){
							call.onHttpFailure(taskId, "statusCode:"+statusCode);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if(call!=null){
						call.onHttpFailure(taskId, e.getMessage());
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				if(call!=null){
					call.onHttpFailure(taskId, error.getMessage());
				}
			}
			
		});
		
	}

	public void post(Context context,final OnHttpRespondLisenter call, final int taskId, String path,Header[] headers,RequestParams params,final Type type) {
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		httpClient.post(context,AbsConfig.HOST+path,headers, params,AbsConfig.CONTENT_TYPE, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				try {
					if (statusCode == 200) {
						Object respon = new Gson().fromJson(content, type);
						if(call!=null){
							call.onHttpSuccess(taskId, respon, content);
						}
					} else {
						if(call!=null){
							call.onHttpFailure(taskId, "statusCode:"+statusCode);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if(call!=null){
						call.onHttpFailure(taskId, e.getMessage());
					}
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				if(call!=null){
					call.onHttpFailure(taskId, error.getMessage());
				}
			}
			
		});
		
	}
	
	public void post(Context context,final OnHttpRespondLisenter call, final int taskId, String path,String requestJson,final Type type){
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		try {
			StringEntity entity = new StringEntity(requestJson, HTTP.UTF_8);
			httpClient.post(context, AbsConfig.HOST+path, entity, AbsConfig.CONTENT_TYPE, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, content);
					try {
						if (statusCode == 200) {
							Object respon = new Gson().fromJson(content, type);
							if(call!=null){
								call.onHttpSuccess(taskId, respon, content);
							}
						} else {
							if(call!=null){
								call.onHttpFailure(taskId, "statusCode:"+statusCode);
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						if(call!=null){
							call.onHttpFailure(taskId, e1.getMessage());
						}
					}
					
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					if(call!=null){
						call.onHttpFailure(taskId, error.getMessage());
					}
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(call!=null){
				call.onHttpFailure(taskId, e.getMessage());
			}
		}
		
	}
	
	/**
	 * Http post reqeust
	 * @param context
	 * @param call:callback
	 * @param taskId:Request Number
	 * @param path:Request path->no host em:"/test"
	 * @param headers:reqeust headers
	 * @param requestJson:request params json
	 * @param type:json to bean
	 */
	public void post(Context context,final OnHttpRespondLisenter call, final int taskId, String path,Header[] headers,String requestJson,final Type type){
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		try {
			StringEntity entity = new StringEntity(requestJson, HTTP.UTF_8);
			httpClient.post(context, AbsConfig.HOST+path,headers, entity, AbsConfig.CONTENT_TYPE, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, String content) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, content);
					try {
						if (statusCode == 200) {
							Object respon = new Gson().fromJson(content, type);
							if(call!=null){
								call.onHttpSuccess(taskId, respon, content);
							}
						} else {
							if(call!=null){
								call.onHttpFailure(taskId, "statusCode:"+statusCode);
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						if(call!=null){
							call.onHttpFailure(taskId, e1.getMessage());
						}
					}
					
				}

				@Override
				public void onFailure(Throwable error, String content) {
					super.onFailure(error, content);
					if(call!=null){
						call.onHttpFailure(taskId, error.getMessage());
					}
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(call!=null){
				call.onHttpFailure(taskId, e.getMessage());
			}
		}
	}
	
	/**
	 * download
	 * @param call: callback
	 * @param taskId:request Number
	 * @param url:reqeust url
	 * @param filePath:save file path
	 */
	public void download(final OnHttpRespondLisenter call, final int taskId,String url,final String filePath){
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		httpClient.get(url, new BinaryHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, byte[] binaryData) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, binaryData);
				File file = null;
				FileOutputStream oStream=null;
                try {
                	file = new File(filePath);
                    oStream = new FileOutputStream(file);
                    oStream.write(binaryData);

                    oStream.flush();
                    if(call!=null){
						call.onHttpSuccess(taskId, null, null);
					}
                } catch (Exception e) {
                    e.printStackTrace();
                    if(call!=null){
    					call.onHttpFailure(taskId, e.getMessage());
    				}
                }finally{
                	if(oStream!=null){
                		try {
							oStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							if(call!=null){
		    					call.onHttpFailure(taskId, e.getMessage());
		    				}
						}
                	}
                }
			}

			@Override
			public void onFailure(Throwable error, byte[] binaryData) {
				// TODO Auto-generated method stub
				super.onFailure(error, binaryData);
				if(call!=null){
					call.onHttpFailure(taskId, error.getMessage());
				}
			}
			
		});
		
	}
	
	/**
	 * upload file
	 * @param call: callback
	 * @param taskId:request Number
	 * @param url:reqeust url
	 * @param filePath:upload file path
	 */
	public void upload(final OnHttpRespondLisenter call, final int taskId,String url,String filePath){
		if(!NetMonitor.isNetworkConnected(AbsApplication.getApplication())){
			if(call!=null){
				call.onHttpFailure(taskId, AbsApplication.getApplication().getString(R.string.error_network_connection));
			}
			return;
		}
		File file = new File(filePath);
    	RequestParams params = new RequestParams();
    	try {
			params.put("file", file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			call.onHttpFailure(taskId, e.getMessage());
			return;
		}
    	
		httpClient.post(url, params, new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if(call!=null){
					call.onHttpSuccess(taskId, null, null);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
				if(call!=null){
					call.onHttpFailure(taskId, error.getMessage());
				}
			}
			
		});
	}
	
	public void cancelRequest(Context context,boolean mayInterruptIfRunning){
		httpClient.cancelRequests(context, mayInterruptIfRunning);
	}
}
