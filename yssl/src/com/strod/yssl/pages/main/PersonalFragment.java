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
import android.widget.ScrollView;

import com.roid.ui.AbsFragment;
import com.roid.util.Toaster;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.SlipSwitch;
import com.strod.yssl.view.SlipSwitch.OnSwitchListener;
import com.strod.yssl.view.SwitchButton;
import com.strod.yssl.view.SwitchButton.OnSwitchChangeListener;

public class PersonalFragment extends AbsFragment implements OnClickListener{
	
	/**day night mode*/
//	private SwitchButton mNightModeSwitchButton;
	private SlipSwitch mNightModeSwitchButton;
	/**translucent_status mode*/
	private SwitchButton mTranslucentStatusSwitchButton;
	
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
//		mNightModeSwitchButton = (SwitchButton) rootView.findViewById(R.id.night_mode_switch_button);
//		mNightModeSwitchButton.setChecked(Config.getInstance().isNightMode);
//		mNightModeSwitchButton.SetOnSwitchChangeListener(new OnSwitchChangeListener() {
//			
//			@Override
//			public void onSwitchChanged(View view, boolean swtichState) {
//				// TODO Auto-generated method stub
//				Config.getInstance().setNightMode(swtichState);
//				if(getActivity()!=null){
//					((MainActivity)getActivity()).switchTheme();
//				}
//			}
//		});
		
		mTranslucentStatusSwitchButton = (SwitchButton) rootView.findViewById(R.id.translucent_status_switch_button);
		mTranslucentStatusSwitchButton.setChecked(Config.mTranslucentStatus);
		mTranslucentStatusSwitchButton.SetOnSwitchChangeListener(new OnSwitchChangeListener() {
			
			@Override
			public void onSwitchChanged(View view, boolean swtichState) {
				// TODO Auto-generated method stub
				Config.getInstance().setTranslucentStatus(swtichState);
				if(getActivity()!=null){
//					((MainActivity)getActivity()).recreate();
					((MainActivity)getActivity()).restartActivity();
					
				}
			}
		});
		
		mNightModeSwitchButton = (SlipSwitch) rootView.findViewById(R.id.night_mode_switch_button);
		mNightModeSwitchButton.setSwitchState(Config.getInstance().isNightMode);
		mNightModeSwitchButton.setOnSwitchListener(new OnSwitchListener() {
			
			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				Config.getInstance().setNightMode(isSwitchOn);
				if(getActivity()!=null){
					((MainActivity)getActivity()).switchTheme();
				}
			}
		});
	}
	
	
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
