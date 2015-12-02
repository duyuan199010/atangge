package com.strod.yssl.pages.personal;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.roid.ui.AbsFragmentActivity;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.TitleBar;

/**
 * about us activity
 *
 * @author user
 */
public class AboutUsActivity extends AbsFragmentActivity {

    private static final String TAG = "AboutUsActivity";

    /**
     * title bar
     */
    private TitleBar mTitleBar;

    private ImageView mLogo;
    private TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Config.getInstance().isNightMode) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_about_us);


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
        mTitleBar.setMiddleText(R.string.about_our);
        mTitleBar.setRightBtnVisibility(View.GONE);


        mLogo = (ImageView)findViewById(R.id.logo);
        mVersion = (TextView)findViewById(R.id.version_name);

        String version = String.format(getString(R.string.version_name),Config.getInstance().getAppVersionName(this));

        mVersion.setText(version);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
