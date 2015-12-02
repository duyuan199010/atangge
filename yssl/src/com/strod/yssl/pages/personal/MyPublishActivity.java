package com.strod.yssl.pages.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.roid.ui.AbsFragmentActivity;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.TitleBar;
import com.strod.yssl.view.pulltorefresh.PullToRefreshListView;

/**
 * my publish activity
 *
 * @author user
 */
public class MyPublishActivity extends AbsFragmentActivity {

    private static final String TAG = "MyPublishActivity";

    /**
     * title bar
     */
    private TitleBar mTitleBar;

    private PullToRefreshListView mPullRefreshListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Config.getInstance().isNightMode) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_my_publish);


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
        mTitleBar.setMiddleText(R.string.mine_publish);
        mTitleBar.setRightBtnVisibility(View.GONE);

        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    class PublishAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
