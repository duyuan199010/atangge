package com.roid.ui;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class AbsFragment extends Fragment{

	public FragmentManager mFragmentManager = null;
	public FragmentTransaction mFragmentTransaction = null;
	public String fragmentTag = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragmentManager = getChildFragmentManager();

		/***
		 * 避免被系统回收后引起黑屏问题
		 */
        if(null != savedInstanceState){
        	if (savedInstanceState.getBoolean("isHidden")) {
        		mFragmentManager.beginTransaction().hide(this).commit();
            }
		}
	}
	
	@Override
	public void onDetach() {
	    super.onDetach();
	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("isHidden", isHidden());
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
