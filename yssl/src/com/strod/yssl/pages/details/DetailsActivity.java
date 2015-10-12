package com.strod.yssl.pages.details;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roid.net.http.OnHttpRespondLisenter;
import com.roid.ui.AbsFragmentActivity;
import com.roid.util.CommonUtils;
import com.roid.util.DebugLog;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.main.Article.ContentType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.TitleBar;

/**
 * article details activity
 * 
 * @author user
 *
 */
public class DetailsActivity extends AbsFragmentActivity implements OnClickListener, OnHttpRespondLisenter {

	/** tag */
	public static final String ARTICLE = "article";
	/** ContentType instance */
	private ContentType mContentType;

	private TitleBar mTitleBar;
	private WebView mWebView;

	final class JavaScriptInterface {
		public void onImageClick(String imageUrl) {
			Toaster.showDefToast(getApplicationContext(), imageUrl);
		}

		public void showSource(String html) {
			DebugLog.i("HTML", html);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Config.getInstance().isNightMode) {
			setTheme(R.style.NightTheme);
		} else {
			setTheme(R.style.DayTheme);
		}
		setContentView(R.layout.activity_details);
		if (getIntent() != null) {
			mContentType = getIntent().getParcelableExtra(ARTICLE);
		}

		if (mContentType == null) {
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
				finish();
			}
		});
		mTitleBar.setMiddleText(mContentType.getTitle());
		mTitleBar.setRightButtonText("点赞");
		mTitleBar.setRightBtnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mWebView = (WebView) findViewById(R.id.webview);
		setWebView(mWebView);
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	private void setWebView(WebView mWebView) {
		mWebView.setBackgroundResource(R.color.white);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new JavaScriptInterface(), "js");
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.loadUrl(mContentType.getContentUrl());
	}

	final class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.loadUrl("javascript:window.js.showSource('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

	}

	@Override
	public void onClick(View v) {
		if (CommonUtils.isFastDoubleClick())
			return;
		// switch (v.getId()) {
		// case value:
		//
		// break;
		//
		// default:
		// break;
		// }
	}

	@Override
	protected void onDestroy() {
		mWebView.destroy();
		super.onDestroy();
	}

	@Override
	public void onHttpSuccess(int taskId, Object obj, String json) {

	}

	@Override
	public void onHttpFailure(int taskId, String message) {

	}
}
