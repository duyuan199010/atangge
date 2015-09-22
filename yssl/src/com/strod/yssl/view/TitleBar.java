package com.strod.yssl.view;

import com.roid.util.CommonUtils;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.NetworkMonitor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 顶部title自定义控件
 * 
 * @author User
 *
 */
public class TitleBar extends RelativeLayout {

	protected View mContainer;
	/** 左边按钮 */
	protected ImageButton mLefttBtn;
	/** 中间标题 */
	protected TextView mMiddleTitle;
	/** 右边按钮 */
	protected Button mRightBtn; //
	private Context context;

	public TitleBar(Context context) {
		super(context);
		this.context = context;
		initView(context);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView(context);
	}

	public void initView(Context act) {
		View mView = LayoutInflater.from(act).inflate(R.layout.title_bar, this, true);
		mContainer = mView.findViewById(R.id.title_bar);
		mLefttBtn = (ImageButton) mView.findViewById(R.id.title_left_btn);
		mMiddleTitle = (TextView) mView.findViewById(R.id.title_middle_text);
		mRightBtn = (Button) mView.findViewById(R.id.title_right_btn);
	}

	public void enable(boolean enable) {
		if (mContainer != null)
			mContainer.setVisibility(enable ? View.VISIBLE : View.GONE);
	}

	public void setLeftImageBtnVisibility(int visibility) {
		mLefttBtn.setVisibility(visibility);
	}

	public void setLeftImageBtnBackground(int resid) {
		mLefttBtn.setBackgroundResource(resid);
	}
	
	public void setLeftImageBtnClickListener(OnClickListener listener) {
		mLefttBtn.setOnClickListener(listener);
	}

	/**
	 * 设置中间的标题文字
	 * 
	 * @param resId
	 */
	public void setMiddleText(int resId) {
		mMiddleTitle.setText(resId);
	}

	/**
	 * 设置中间的标题文字
	 * 
	 * @param text
	 */
	public void setMiddleText(String text) {
		mMiddleTitle.setText(text);
	}
	
	public void setRightBtnVisibility(int visibility) {
		mRightBtn.setVisibility(visibility);
	}

	/**
	 * 设置右边按钮资源
	 * 
	 * @param resId
	 *            图片资源id
	 */
	public void setRightButtonBackground(int resId) {
		mRightBtn.setBackgroundResource(resId);
	}
	
	public void setRightButtonText(int resId){
		mRightBtn.setText(resId);
	}
	
	public void setRightButtonText(String text){
		mRightBtn.setText(text);
	}

	public void setRightBtnClickListener(OnClickListener listener) {
		mRightBtn.setOnClickListener(listener);
	}


}
