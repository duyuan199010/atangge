package com.strod.yssl.pages.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.ui.FragmentControlActivity;
import com.roid.util.CommonUtils;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article.ContentType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.TitleBar;
/**
 * article details activity
 * @author user
 *
 */
public class DetailsActivity extends FragmentControlActivity implements OnClickListener,OnHttpRespondLisenter{

	/**tag*/
	public static final String ARTICLE="article";
	/**ContentType instance*/
	private ContentType mContentType;
	
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
		if (Config.getInstance().isNightMode) {
			setTheme(R.style.NightTheme);
		} else {
			setTheme(R.style.DayTheme);
		}
		setContentView(R.layout.activity_details);
		if(getIntent()!=null){
			mContentType = getIntent().getParcelableExtra(ARTICLE);
		}
		
		if(mContentType==null){
			return;
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
		
		
		
		mWebView = (WebView) findViewById(R.id.webview);
		setWebView(mWebView);
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onHttpSuccess(int taskId, Object obj, String json) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHttpFailure(int taskId, String message) {
		// TODO Auto-generated method stub
		
	}
}
