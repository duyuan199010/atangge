package com.strod.yssl.onekeyshare;

import android.content.Context;

import com.strod.yssl.R;

import cn.sharesdk.framework.ShareSDK;

public class ShareUtil {
	
	private static ShareUtil mShareUtil;
	
	public static ShareUtil getInstance(){
		if(mShareUtil == null){
			mShareUtil = new ShareUtil();
		}
		return mShareUtil;
	}

	public void showShare(Context context,String title,String content,String imgUrl,String contentUrl) {
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(imgUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(content);
		oks.setImageUrl(imgUrl);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(contentUrl);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(context.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(contentUrl);

		// 启动分享GUI
		oks.show(context);
	}
}
