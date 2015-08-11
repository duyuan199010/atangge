package com.roid.net.http;

import org.apache.http.entity.StringEntity;

public class Task {

	private int taskId;
	private int childId;
	private String taskType;
	private String requestMethod;
	private boolean isShowProgress = true;

	private RequestParams requestParams;
	private StringEntity stringEntity;

	private String taskHost;
	private String taskPath;

	public Task() {
		super();
	}

	public Task(int taskId, String requestMethod, RequestParams requestParams) {
		this.taskId = taskId;
		this.requestMethod = requestMethod;
		this.requestParams = requestParams;
	}

	public Task(int taskId, RequestParams requestParams, String taskType, String taskHost, String taskPath) {
		this.taskId = taskId;
		this.requestParams = requestParams;
		this.taskType = taskType;
		this.taskHost = taskHost;
		this.taskPath = taskPath;
	}

	public Task(int taskId, RequestParams requestParams, String taskType, String taskPath) {
		this.taskId = taskId;
		this.requestParams = requestParams;
		this.taskType = taskType;
		this.taskPath = taskPath;
	}

	public Task(int taskId, StringEntity stringEntity, String taskType, String taskPath) {
		this.taskId = taskId;
		this.stringEntity = stringEntity;
		this.taskType = taskType;
		this.taskPath = taskPath;
	}

	public Task(int taskId, StringEntity stringEntity, String taskHost, String taskType, String taskPath) {
		this.taskId = taskId;
		this.stringEntity = stringEntity;
		this.taskHost = taskHost;
		this.taskType = taskType;
		this.taskPath = taskPath;
	}

	public Task(int taskId, RequestParams requestParams, String taskType, String taskHost, String taskPath, boolean isShowProgress) {
		this.taskId = taskId;
		this.requestParams = requestParams;
		this.taskType = taskType;
		this.taskHost = taskHost;
		this.taskPath = taskPath;
		this.isShowProgress = isShowProgress;
	}

	public Task(int taskId, RequestParams requestParams, String taskType, String taskPath, boolean isShowProgress) {
		this.taskId = taskId;
		this.requestParams = requestParams;
		this.taskType = taskType;
		this.taskPath = taskPath;
		this.isShowProgress = isShowProgress;
	}

	public Task(int taskId, int childId, RequestParams requestParams, String taskType, String taskHost, String taskPath) {
		this.taskId = taskId;
		this.childId = childId;
		this.requestParams = requestParams;
		this.taskType = taskType;
		this.taskHost = taskHost;
		this.taskPath = taskPath;
	}

	public Task(int taskId, int childId, RequestParams requestParams, String taskType, String taskHost, String taskPath, boolean isShowProgress) {
		this.taskId = taskId;
		this.childId = childId;
		this.requestParams = requestParams;
		this.taskType = taskType;
		this.taskHost = taskHost;
		this.taskPath = taskPath;
		this.isShowProgress = isShowProgress;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", childId=" + childId + ", taskType=" + taskType + ", requestMethod=" + requestMethod + ", isShowProgress=" + isShowProgress + ", requestParams="
				+ requestParams + ", stringEntity=" + stringEntity + ", taskHost=" + taskHost + ", taskPath=" + taskPath + "]";
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

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
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

	public boolean isShowProgress() {
		return isShowProgress;
	}

	public void setShowProgress(boolean isShowProgress) {
		this.isShowProgress = isShowProgress;
	}

}