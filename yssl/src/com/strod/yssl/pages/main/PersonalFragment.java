package com.strod.yssl.pages.main;

/**
 * 页面
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.roid.ui.AbsFragment;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.bean.personal.User;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.database.DatabaseHelper;
import com.strod.yssl.pages.account.LoginActivity;
import com.strod.yssl.pages.personal.AboutUsActivity;
import com.strod.yssl.pages.personal.FeedBackActivity;
import com.strod.yssl.pages.personal.MyPublishActivity;
import com.strod.yssl.pages.personal.SettingActivity;
import com.strod.yssl.view.RippleView;
import com.strod.yssl.view.SwitchButton;

import java.util.List;

public class PersonalFragment extends AbsFragment implements OnClickListener {

    private RippleView mPersonalRV;
    private RippleView mPublishRV;
    private RippleView mFeedBackRV;
    private RippleView mAboutRV;
    private RippleView mPSettingRV;
    private RippleView mExitRV;

    private ImageView mHeadImg;
    /**
     * day night mode
     */
    private SwitchButton mNightModeSwitchButton;

    /**database*/
    private DatabaseHelper mDatabaseHelper;
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal, container, false);

        if (mDatabaseHelper==null){
            mDatabaseHelper = new DatabaseHelper<User>();
            List<User> userList = mDatabaseHelper.queryForAll(User.class);
            if (userList != null && userList.size()>0){
                mUser = userList.get(0);
            }else {
                mUser = new User();
            }
        }
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        mPersonalRV = (RippleView) rootView.findViewById(R.id.personal_layout);
        mPersonalRV.setOnClickListener(this);

        mPublishRV = (RippleView) rootView.findViewById(R.id.publish_layout);
        mPublishRV.setOnClickListener(this);

        mFeedBackRV = (RippleView) rootView.findViewById(R.id.feed_back_layout);
        mFeedBackRV.setOnClickListener(this);

        mAboutRV = (RippleView) rootView.findViewById(R.id.about_our_layout);
        mAboutRV.setOnClickListener(this);

        mPSettingRV = (RippleView) rootView.findViewById(R.id.setting_layout);
        mPSettingRV.setOnClickListener(this);

        mExitRV = (RippleView) rootView.findViewById(R.id.exit_layout);
        mExitRV.setOnClickListener(this);

        mExitRV.setVisibility(mUser.isLogin() ? View.VISIBLE : View.GONE);

        mHeadImg = (ImageView) rootView.findViewById(R.id.head_img);
        ImageLoader.getInstance().displayImage("http://c.hiphotos.baidu.com/image/h%3D200/sign=d14b41218501a18befeb154fae2e0761/eaf81a4c510fd9f98db59264272dd42a2834a419.jpg", mHeadImg, Config.getInstance().getDisplayImageOptionsCircle());

        mNightModeSwitchButton = (SwitchButton) rootView.findViewById(R.id.night_mode_switch_button);
        mNightModeSwitchButton.setChecked(Config.getInstance().isNightMode);
        mNightModeSwitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Config.getInstance().setNightMode(isChecked);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).switchTheme();
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_layout:
                mPersonalRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });
                break;

            case R.id.publish_layout:
                mPublishRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        startActivity(new Intent(getActivity(), MyPublishActivity.class));
                    }
                });
                break;

            case R.id.feed_back_layout:
                mFeedBackRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        startActivity(new Intent(getActivity(), FeedBackActivity.class));
                    }
                });
                break;

            case R.id.about_our_layout:
                mAboutRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        startActivity(new Intent(getActivity(), AboutUsActivity.class));
                    }
                });
                break;

            case R.id.setting_layout:
                mPSettingRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        startActivity(new Intent(getActivity(), SettingActivity.class));
                    }
                });
                break;

            case R.id.exit_layout:
                mExitRV.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                @Override
                public void onComplete(RippleView rippleView) {
                    Toaster.showDefToast(getActivity(),"退出");
                }
            });

                break;

            default:
                break;
        }
    }

}
