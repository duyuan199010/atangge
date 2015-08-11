package com.roid.net.http;

import org.apache.http.entity.StringEntity;

public class Task {

	private String requestMethod;

	private RequestParams requestParams;
	private StringEntity stringEntity;

	private String taskHost;
	private String taskPath;

	public Task() {
		super();
	}

	public Task(RequestParams requestParams, String taskHost, String taskPath) {
		this.requestParams = requestParams;
		this.taskHost = taskHost;
		this.taskPath = taskPath;
	}

	public Task(StringEntity stringEntity, String taskHost, String taskPath) {
		this.stringEntity = stringEntity;
		this.taskHost = taskHost;
		this.taskPath = taskPath;
	}

	public RequestParams getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(RequestParams requestParams) {
		this.requestParams = requestParams;
	}

	public StringEntity getStringEntity() {
		return stringEntity;
	}

	public void setStringEntity(StringEntity stringEntity) {
		this.stringEntity = stringEntity;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(String taskPath) {
		this.taskPath = taskPath;
	}

	public String getTaskHost() {
		return taskHost;
	}

	public void setTaskHost(String taskHost) {
		this.taskHost = taskHost;
	}

}
