package com.strod.yssl.pages.main;

/**
 * 页面
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.roid.ui.AbsFragment;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.SlipSwitch;
import com.strod.yssl.view.SlipSwitch.OnSwitchListener;

public class PersonalFragment extends AbsFragment implements OnClickListener{
	
	/**day night mode*/
	private SlipSwitch mNightModeSwitchButton;
	
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
