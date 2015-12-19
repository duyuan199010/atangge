package com.strod.yssl.pages.account;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.roid.ui.AbsActivity;
import com.roid.util.DebugLog;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.RippleView;
import com.strod.yssl.view.TitleBar;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * login activity
 *
 * @author user
 */
public class LoginActivity extends AbsActivity implements View.OnClickListener, PlatformActionListener {

    /**
     * UI
     */
    private TitleBar mTitleBar;

    private RippleView mRippleView;

    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Config.getInstance().isNightMode) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_login);

        ShareSDK.initSDK(this);

        initView();
    }

    private void initView() {

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setLeftImageBtnVisibility(View.VISIBLE);
        mTitleBar.setLeftImageBtnBackground(R.drawable.back_selector);
        mTitleBar.setLeftImageBtnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setMiddleText(R.string.login);

        mRippleView = (RippleView) findViewById(R.id.login_layout);

        findViewById(R.id.qq_layout).setOnClickListener(this);
        findViewById(R.id.wechat_layout).setOnClickListener(this);
        findViewById(R.id.sina_layout).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qq_layout:
                mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        Platform qq = ShareSDK.getPlatform(QQ.NAME);
                        authorize(qq);
                    }
                });
                break;
            case R.id.wechat_layout:
                mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                        authorize(wechat);
                    }
                });
                break;
            case R.id.sina_layout:
                mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                        authorize(sina);
                    }
                });
                break;
        }
    }

    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }
        //判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
//                login(plat.getName(), userId, null);
                Toast.makeText(LoginActivity.this, "已经授权过", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
        //获取用户资料
        plat.showUser(null);
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            DebugLog.e("login", res.toString());
            msg.obj = new Object[]{platform.getName(), res};
            handler.sendMessage(msg);
        }
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_AUTH_CANCEL: {
                    //取消授权
                    Toaster.showDefToast(LoginActivity.this,"取消授权");
                }
                break;
                case MSG_AUTH_ERROR: {
                    //授权失败
                    Toaster.showDefToast(LoginActivity.this, "授权失败");
                }
                break;
                case MSG_AUTH_COMPLETE: {
                    //授权成功
                    Toaster.showDefToast(LoginActivity.this, "授权成功");
                    Object[] objs = (Object[]) msg.obj;
                    String platform = (String) objs[0];
                    HashMap<String, Object> res = (HashMap<String, Object>) objs[1];

                    //qq
                    //{is_yellow_vip=0, msg=, vip=0, nickname=月下独醉, figureurl_qq_1=http://q.qlogo.cn/qqapp/1104951098/B96B794D78CE751807A48711DD6F2216/40,
                    // city=赣州, figureurl_1=http://qzapp.qlogo.cn/qzapp/1104951098/B96B794D78CE751807A48711DD6F2216/50,
                    // gender=男, province=江西, is_yellow_year_vip=0, yellow_vip_level=0,
                    // figureurl=http://qzapp.qlogo.cn/qzapp/1104951098/B96B794D78CE751807A48711DD6F2216/30,
                    // figureurl_2=http://qzapp.qlogo.cn/qzapp/1104951098/B96B794D78CE751807A48711DD6F2216/100,
                    // is_lost=0, figureurl_qq_2=http://q.qlogo.cn/qqapp/1104951098/B96B794D78CE751807A48711DD6F2216/100, level=0, ret=0}
                }
                break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
