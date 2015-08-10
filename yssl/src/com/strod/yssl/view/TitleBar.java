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
import android.widget.RelativeLayout;


/**
 * 顶部title自定义控件
 * 
 * @author User
 *
 */
public class TitleBar extends RelativeLayout implements OnClickListener{
	
	private View mContainer;
	public Button mRightBtn; //右边按钮
	private OnRightBtnClickListener rListener;
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
		mRightBtn = (Button) mView.findViewById(R.id.title_right_btn);
		mRightBtn.setOnClickListener(this);
		
	}

	public void enable(boolean enable) {
		if (mContainer != null)
			mContainer.setVisibility(enable ? View.VISIBLE : View.GONE);
	}

	private void setViewVisibility(View v, boolean visible) {
		v.setVisibility(visible ? View.VISIBLE : View.GONE);
	}
	
	public void setLeftButtonContext(){
		
	}
	
	/**
	 * 设置右边按钮资源和显示状态
	 * @param enable 是否显示
	 * @param resId 图片资源id
	 */
	public void setRightButton(boolean enable, int resId) {
		if(resId > 0){
			mRightBtn.setText(context.getString(resId));
		}else{
			mRightBtn.setText(context.getString(R.string.btn_back));
		}
		setViewVisibility(mRightBtn, enable);
	}
	
	
	public void setRightBtnClickListener(OnRightBtnClickListener listener){
		rListener = listener;
	}
	
	@Override
	public void onClick(View v) {
		if (CommonUtils.isFastDoubleClick())
			return;
		
		switch (v.getId()) {
		case R.id.title_right_btn:
			if(null != rListener){
				rListener.onClick(v.getId());
				return;
            }
			// 未连上网络时,气泡提示
//			if (!NetworkMonitor.hasNetWork()) {
//				return;
//			}
			break;
		}
	}
	
	public static interface OnRightBtnClickListener{
		public void onClick(int viewID);
	}
}
