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

import com.roid.ui.AbsFragment;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.pages.personal.SettingActivity;
import com.strod.yssl.view.SwitchButton;

public class PersonalFragment extends AbsFragment implements OnClickListener{
	
	/**day night mode*/
	private SwitchButton mNightModeSwitchButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
		
		initView(rootView);
		
		return rootView;
	}

	private void initView(View rootView){
		mNightModeSwitchButton = (SwitchButton) rootView.findViewById(R.id.night_mode_switch_button);
		mNightModeSwitchButton.setChecked(Config.getInstance().isNightMode);
		mNightModeSwitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Config.getInstance().setNightMode(isChecked);
				if(getActivity()!=null){
					((MainActivity)getActivity()).switchTheme();
				}
			}
		});
		
		rootView.findViewById(R.id.setting_layout).setOnClickListener(this);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setting_layout:
			startActivity(new Intent(getActivity(), SettingActivity.class));
			break;

		default:
			break;
		}
	}

}
