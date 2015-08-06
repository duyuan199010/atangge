package com.roid.util;

import com.roid.R;

public class AnimationStyle {

	public static final int ANIM_NONE = 0;
	public static final int ANIM_FADING = 1;
	public static final int ANIM_SLIDING_TOP = 2;
	public static final int ANIM_SLIDING_LEFT = 3;
	
	protected int mStyle = ANIM_NONE;
	
	public void setStyle(int style) {
		mStyle = style;
	}
	
	public int getStyle() {
		return mStyle;
	}
	
	public int getStyleResID(int style) {
		switch (style)
		{
			case ANIM_FADING:
				return R.style.dialog_anim_fading;
			case ANIM_SLIDING_TOP:
				return R.style.dialog_anim_sliding_top;
			case ANIM_SLIDING_LEFT:
				return R.style.dialog_anim_sliding_left;
			default:
			case ANIM_NONE:
				return 0;
		}
	}
	
	public int getStyleResID() {
		return getStyleResID(mStyle);
	}
}
