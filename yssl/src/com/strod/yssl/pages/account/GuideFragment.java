package com.strod.yssl.pages.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.roid.ui.AbsFragment;
import com.strod.yssl.R;
import com.strod.yssl.pages.main.MainActivity;

public class GuideFragment extends AbsFragment implements OnClickListener{

	/**get the position fragment*/
	private int mPosition = 0;

	public static GuideFragment newInsatnce(int position) {
		GuideFragment fragment = new GuideFragment();
		fragment.mPosition = position;

		return fragment;
	}
	
	/**start button*/
	private Button mStartBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view =null;
		switch (mPosition) {
		case 0:
			view = inflater.inflate(R.layout.fragment_guide_one, container, false);
			break;
		case 1:
			view = inflater.inflate(R.layout.fragment_guide_two, container, false);
			break;
		case 2:
			view = inflater.inflate(R.layout.fragment_guide_three, container, false);
			mStartBtn = (Button) view.findViewById(R.id.start);
			mStartBtn.setOnClickListener(this);
			break;

		default:
			break;
		}
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
			getActivity().finish();
			break;

		default:
			break;
		}
	}
	
	
}
