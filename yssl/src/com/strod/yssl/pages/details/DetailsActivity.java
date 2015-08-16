package com.strod.yssl.pages.details;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.ui.FragmentControlActivity;
import com.roid.util.CommonUtils;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.pages.details.entity.Comment.CommentType;
import com.strod.yssl.pages.main.entity.Article.ContentType;
import com.strod.yssl.view.TitleBar;
/**
 * article details activity
 * @author user
 *
 */
public class DetailsActivity extends FragmentControlActivity implements OnRefreshListener2<ListView>,OnClickListener,OnHttpRespondLisenter{

	/**tag*/
	public static final String ARTICLE="article";
	/**ContentType instance*/
	private ContentType mContentType;
	
	/**UI listview*/
	private PullToRefreshListView mPullRefreshListView;
	/**listview datasource*/
	private ArrayList<CommentType> mCommentList;
	/**listview adapter*/
	private CommentAdapter mAdapter;
	
	private TitleBar mTitleBar;
	private WebView mWebView;
	
	class JavaScriptInterface{
		public void onImageClick(String imageUrl){
			Toaster.showDefToast(getApplicationContext(), imageUrl);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		if(getIntent()!=null){
			mContentType = getIntent().getParcelableExtra(ARTICLE);
		}
		
		if(mContentType==null){
			return;
		}
		
		if(mCommentList == null){
			mCommentList = new ArrayList<CommentType>();
		}
		
		initView();
	}

	private void initView() {
		mTitleBar = (TitleBar) findViewById(R.id.title_bar);
		mTitleBar.setLeftImageBtnVisibility(View.VISIBLE);
		mTitleBar.setLeftImageBtnBackground(R.drawable.back_selector);
		mTitleBar.setLeftImageBtnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mTitleBar.setMiddleText(mContentType.getTitle());
		mTitleBar.setRightButtonText("点赞");
		mTitleBar.setRightBtnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		
		//add webview to listview head
		mWebView = new WebView(this);
		setWebView(mWebView);
		actualListView.addHeaderView(mWebView);
		
		mAdapter = new CommentAdapter(this, mCommentList);
		mPullRefreshListView.setAdapter(mAdapter);
		
		mPullRefreshListView.setOnRefreshListener(this);
	}
	
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	private void setWebView(WebView mWebView){
		mWebView.setBackgroundResource(R.color.white);
		mWebView.getSettings().setJavaScriptEnabled(true);  
	    mWebView.addJavascriptInterface(new JavaScriptInterface(), "js");  
		mWebView.loadUrl(mContentType.getContentUrl());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(CommonUtils.isFastDoubleClick())return;
//		switch (v.getId()) {
//		case value:
//			
//			break;
//
//		default:
//			break;
//		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		//not use
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onHttpResponse(int taskId, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}