package com.strod.yssl.pages.main;

/**
 * 页面
 */
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
import com.strod.yssl.view.SwitchButton;
import com.strod.yssl.view.SwitchButton.OnSwitchChangeListener;

public class PersonalFragment extends AbsFragment implements OnClickListener{
	
	private ScrollView mScrollView;
	/**day night mode*/
	private SwitchButton mSwitchButton;
	
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
		mScrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
		mSwitchButton = (SwitchButton) rootView.findViewById(R.id.switch_button);
		mSwitchButton.setChecked(Config.getInstance().isNightMode);
		mSwitchButton.SetOnSwitchChangeListener(new OnSwitchChangeListener() {
			
			@Override
			public void onSwitchChanged(View view, boolean swtichState) {
				// TODO Auto-generated method stub
				Config.getInstance().setNightMode(swtichState);
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
