package com.strod.yssl.pages.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.roid.ui.AbsFragmentActivity;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.RippleView;
import com.strod.yssl.view.TitleBar;

/**
 * feedback activity
 *
 * @author user
 */
public class FeedBackActivity extends AbsFragmentActivity implements OnClickListener{

    private static final String TAG = "FeedBackActivity";

    /**
     * title bar
     */
    private TitleBar mTitleBar;
    private EditText mFeedBack;
    private EditText mContact;
    private View mSubmit;

    private RippleView mRippleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Config.getInstance().isNightMode) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_feed_back);


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
        mTitleBar.setMiddleText(R.string.feed_back);
        mTitleBar.setRightBtnVisibility(View.GONE);

        mFeedBack =(EditText)findViewById(R.id.feed_back);
        mContact=(EditText)findViewById(R.id.contact);

        mRippleView = (RippleView)findViewById(R.id.ripple_view);
        mSubmit=findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        submit();
                    }
                });

                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(mFeedBack.getText().toString().trim()))
        {
            Toaster.showDefToast(this,R.string.feed_back_empty);
            mFeedBack.setFocusable(true);
            return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
