package com.roid.ui.dialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.roid.R;
import com.roid.ui.AbsFragmentActivity;
import com.roid.util.AnimationStyle;
import com.roid.util.DebugLog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActionDialog extends DialogFragment {
	
	public static final int BTN_ID_POSITIVE = 0;
	public static final int BTN_ID_NEGATIVE = 1;
	
	public static abstract class DialogBtnClickListener {
		private Object mArg = null;
		
		public void setArg(Object arg) { mArg = arg; }
		public Object getArg() { return mArg; }
		public abstract void onDialogBtnClick(View btn, int btnID);
	}
	
	public static interface DialogPreDismissListener {
		public void onPreDismiss(ActionDialog dialog);
	}
	
	public static interface DialogDismissedListener {
		public void onDismissed(ActionDialog dialog);
	}

	public static abstract class DialogItemAction {
		public ActionDialog mOwner;
		public int mId;
		public int[] mValues;
		public Object mArg;
		public boolean mIsTriggered;
		public boolean mDismissAfterTriggered;
		
		public boolean isTriggered() {
			return mIsTriggered;
		}
		
		public void setDismissAfterTriggered(boolean dismissAfter) {
			mDismissAfterTriggered = dismissAfter;
		}
		
		public DialogItemAction() {
			mValues = null;
			mIsTriggered = false;
			mDismissAfterTriggered = true;
		}

		public void trigger(int position, long id) {
			mIsTriggered = true;

			int value = (int)id;
			if (mValues != null && mId < mValues.length)
				value = mValues[mId];
			
			if (mDismissAfterTriggered)
			{
				/* Dismiss dialog */
				mOwner.dismiss();
			}
			
			/* Execute the real action */
			onTriggered(position, value);
		}
		
		public void trigger(int position, String value, boolean dismissDialog) {
			mIsTriggered = true;
			
			if (dismissDialog)
			{
				/* Dismiss dialog */
				mOwner.dismiss();
			}
			
			/* Execute the real action */
			onTriggered(position, value);
		}
		
		public void trigger(int position, String value) {
			trigger(position, value, true);
		}		
		
		public void onTriggered(int position, int value) {};
		public void onTriggered(int position, String value) {};
		public void onDismiss(ActionDialog dialog) {};
	}
	
	public static class DialogItemData {
		private String mTitle;
		private int mID;
		
		public String getTitle() {
			return mTitle;
		}

		public void setTitle(String Title) {
			this.mTitle = Title;
		}

		public int getID() {
			return mID;
		}

		public void setID(int ID) {
			this.mID = ID;
		}

		public DialogItemData(String title, int id)
		{
			mTitle = title;
			mID = id;
		}
		
		public String toString()
		{
			return mTitle;
		}
	}
	
	class DialogItemAdapter extends ArrayAdapter<DialogItemData> {

		public DialogItemAdapter(Context context, int resource,
				DialogItemData[] objects) {
			super(context, resource, objects);
		}

		@Override
		public long getItemId(int position) {
			DialogItemData data = getItem(position);
			if (data != null)
				return data.getID();
			return 0;
		}		
	}
	
	protected String mClassName;
	protected int mLayoutResId;
	protected int mCustomTitleViewResId;
	protected int mCustomViewResId;
	protected int mId;
	protected String mSid;
	protected String mTitle;
	protected ListView mList;
	protected ViewStub mCustomViewContainer;
	protected ViewStub mCustomTitleViewContainer;
	protected View mCustomView;
	protected ViewGroup mButtons;
	protected Button mBtnPos;
	protected Button mBtnNeg;
	protected String mBtnPosText;
	protected String mBtnNegText;
	protected String[] mStringArray;
	protected boolean mReusable;
	protected boolean mFullScreen;
	protected boolean mHasList;
	protected boolean mHasButtons;
	protected boolean mHasPosButton;
	protected boolean mHasNegButton;
	protected boolean mAllowMultiInstance = false;
	protected boolean mDismissOnPosBtnClick = false;
	protected boolean mDismissOnNegBtnClick = false;
	protected OnItemClickListener mItemClickListener;
	protected DialogItemAction mAction;
	protected BaseAdapter mAdapter;
	protected AnimationStyle mAnimationStyle;
	protected Object mArg;
	protected Rect mPadding;
	protected DialogBtnClickListener mBtnClickListener;
	protected DialogPreDismissListener mPreDismissListener;
	protected DialogDismissedListener mDimissedListener;
	protected boolean mIsDismissedOnBtnClick = false;
	protected List<DialogItemData> mItemData = new LinkedList<DialogItemData>();

	protected static Map<String, Integer> mShownDialogCounts =
			new HashMap<String, Integer>();
	protected static Object mDialogCountLock = new Object();
	protected static int mUniqueId = 333;
	
	public static void resetAllShownDialogs() {
		synchronized (mDialogCountLock) {
			mShownDialogCounts.clear();
		}
	}
	
	public static void addShownDialog(String dialogClassName) {
		synchronized (mDialogCountLock) {
			Integer cnt = mShownDialogCounts.get(dialogClassName);
			if (cnt == null)
				cnt = Integer.valueOf(1);
			else
				cnt += 1;
			mShownDialogCounts.put(dialogClassName, cnt);			
		}
	}

	public static void removeShownDialog(String dialogClassName) {
		synchronized (mDialogCountLock) {
			Integer cnt = mShownDialogCounts.get(dialogClassName);
			if (cnt != null)
			{
				if (cnt > 0)
					cnt -= 1;
				mShownDialogCounts.put(dialogClassName, cnt);				
			}
		}
	}
	
	public static int getShownDialogCount(String dialogClassName) {
		synchronized (mDialogCountLock) {
			Integer cnt = mShownDialogCounts.get(dialogClassName);
			if (cnt == null)
				return 0;
			return cnt;
		}
	}
	
	public int getDialogId() { return mId; }
	
	public String getDialogSID() {
		return mSid;
	}

	public ActionDialog setDialogId(int id) {
		mId = id;
		return this;
	}
	
	public boolean isAllowMultiInstance() {
		return mAllowMultiInstance;
	}
	
	public ActionDialog setAllowMultiInstance(boolean allow) {
		mAllowMultiInstance = allow;
		return this;
	}
	
	public DialogItemData getItemData(int idx)
	{
		if (idx < 0 || idx >= mItemData.size())
			return null;
		
		return mItemData.get(idx);
	}
	
	public void addItemData(DialogItemData data)
	{
		mItemData.add(data);
	}
	
	public void addItemData(String title, int id)
	{
		DialogItemData data = new DialogItemData(title, id);
		addItemData(data);
	}
	
	public void clearItemData()
	{
		mItemData.clear();
	}
	
	public void removeItemData(int idx)
	{
		if (idx < 0 || idx >= mItemData.size())
			return;
		mItemData.remove(idx);
	}

	public ActionDialog() {
		mClassName = this.getClass().getName();
		mLayoutResId = R.layout.dialog_action;
		mCustomViewContainer = null;
		mList = null;
		mCustomTitleViewContainer = null;
		mCustomTitleViewResId = -1;
		mCustomView = null;
		mCustomViewResId = -1;
		mStringArray = null;
		mItemClickListener = null;
		mBtnClickListener = null;
		mReusable = true;
		mFullScreen = false;
		mHasList = true;
		mHasButtons = false;
		mAdapter =  null;
		mAnimationStyle = new AnimationStyle();
		mArg = null;
		mPadding = null;
		mBtnPos = null;
		mBtnNeg = null;
		mBtnPosText = null;
		mBtnNegText = null;
		mHasPosButton = true;
		mHasNegButton = true;
		mPreDismissListener = null;
		mDimissedListener = null;
		mId = mUniqueId++;
		mSid = null;
	}
	
	public static ActionDialog newInstance(
			int dialogId, String title, String[] stringArray,
			DialogItemAction action) {
		ActionDialog dialog = new ActionDialog();
		dialog.mId = dialogId;
		dialog.mTitle = title;
		dialog.mStringArray = stringArray;
		dialog.setAction(action);
		return dialog;
	}
	
	public Object getArg() {
		return mArg;
	}
	
	public ActionDialog setArg(Object arg) {
		mArg = arg;
		return this;
	}
	
	public ActionDialog setPadding(int left, int top, int right, int bottom) {
		mPadding = new Rect(left, top, right, bottom);
		return this;
	}
	
	public ActionDialog setZeroPadding() {
		mPadding = new Rect(0, 0, 0, 0);
		return this;
	}
	
	public boolean hasList() {
		return mHasList;
	}
	
	public ActionDialog setHasList(boolean hasList) {
		mHasList = hasList;
		return this;
	}
	
	public BaseAdapter getAdapter() {
		return mAdapter;
	}
	
	public ActionDialog setAdapter(BaseAdapter adapter) {
		mAdapter = adapter;
		return this;
	}
	
	public boolean hasButtons() {
		return mHasButtons;
	}
	
	public ActionDialog setHasButtons(boolean hasButtons) {
		mHasButtons = hasButtons;
		return this;
	}
	
	public int getLayoutResId() {
		return mLayoutResId;
	}
	
	public void setLayoutResId(int resId) {
		mLayoutResId = resId;
	}
	
	public int getCustomTitleViewResId() {
		return mCustomTitleViewResId;
	}
	
	public ActionDialog setCustomTitleViewResId(int titleResId) {
		mCustomTitleViewResId = titleResId;
		return this;
	}
	
	public int getCustomViewResId() {
		return mCustomViewResId;
	}
	
	public ActionDialog setCustomViewResId(int resId) {
		mCustomViewResId = resId;
		return this;
	}
	
	public View getCustomView() {
		return mCustomView;
	}
	
	public ViewGroup getButtonBar() {
		return mButtons;
	}
	
	public Button getButton(int btnIdx) {
		if (btnIdx == BTN_ID_POSITIVE)
			return mBtnPos;
		else if (btnIdx == BTN_ID_NEGATIVE)
			return mBtnNeg;
		return null;
	}
	
	public boolean isReusable() {
		return mReusable;
	}
	
	public ActionDialog setReusable(boolean reusable) {
		mReusable = reusable;
		return this;
	}
	
	public boolean isFullScreen() {
		return mFullScreen;
	}
	
	public ActionDialog setFullScreen(boolean fullscreen) {
		mFullScreen = fullscreen;
		return this;
	}
	
	public String[] getStringArray() {
		return mStringArray;
	}
	
	public ActionDialog setStringArray(String[] stringArray) {
		mStringArray = stringArray;
		return this;
	}
		
	public String getTitle() {
		return mTitle;
	}
	
	public ActionDialog setTitle(String title) {
		mTitle = title;
		return this;
	}
	
	public ListView getList() {
		return mList;
	}
	
	public ActionDialog setAction(DialogItemAction action) {
		mAction = action;
		if (mAction != null)
		{
			mAction.mId = mId;
			mAction.mOwner = this;
		}
		return this;
	}
	
	public DialogItemAction getAction() {
		return mAction;
	}

	public void setPositiveBtnText(String text, boolean visible) {
		mBtnPosText = text;
		mHasPosButton = visible;
	}

	public void setNegativeBtnText(String text, boolean visible) {
		mBtnNegText = text;
		mHasNegButton = visible;
	}
	
	public Button getPositiveButton() {
		return mBtnPos;
	}
	
	public Button getNegativeButton() {
		return mBtnNeg;
	}
	
	public void setItemClickListener(OnItemClickListener l) {
		mItemClickListener = l;
	}
	
	public void setBtnClickListener(DialogBtnClickListener l) {
		mBtnClickListener = l;
	}
	
	public void setDismissOnPosBtnClick(boolean dismiss) {
		mDismissOnPosBtnClick = dismiss;
	}
	
	public void setDismissOnNegBtnClick(boolean dismiss) {
		mDismissOnNegBtnClick = dismiss;
	}
	
	public int getAnimationStyle() {
		return mAnimationStyle.getStyle();
	}
	
	public ActionDialog setAnimationStyle(int style) {
		mAnimationStyle.setStyle(style);
		return this;
	}
	
	public ActionDialog setOnDismissedListener(DialogDismissedListener l) {
		mDimissedListener = l;
		return this;
	}
	
	public DialogPreDismissListener getOnPreDismissListener() {
		return mPreDismissListener;
	}
	
	public ActionDialog setOnPreDismissListener(DialogPreDismissListener l) {
		mPreDismissListener = l;
		return this;
	}
	
	public DialogDismissedListener getOnDimissedListener() {
		return mDimissedListener;
	}
	
	public boolean isDismissedOnButtonClick() {
		return mIsDismissedOnBtnClick;
	}
	
	public void notifyDataSetChanged() {
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged();
	}
	
	//-------------------------------------------------------------
	// Custom callback functions overriden by subclass
	//-------------------------------------------------------------
	public void onCustomTitleViewCreated(View customTitleView) {
		/* Must be overriden by subclasses */		
	}
	
	public void onCustomViewCreated(View customView) {
		/* Must be overriden by subclasses */
	}

	public void onDialogViewCreated(View dialogView) {
		/* Must be overriden by subclasses */		
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (mFullScreen)
		{
			if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1)
			{
				setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar);
			}
			else
			{
				setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
			}
		}
		else
		{
			if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1)
			{
				setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
			}
			else
			{
				setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);
			}
		}
	}
	
	protected View onCreateDefaultView(LayoutInflater inflater, ViewGroup container) {
		final ViewGroup rootView = (ViewGroup)inflater.inflate(
				mLayoutResId, container, false);
		if (mPadding != null)
		{
			rootView.setPadding(mPadding.left, mPadding.top, mPadding.right, mPadding.bottom);
		}
		
		mCustomTitleViewContainer = (ViewStub)rootView.findViewById(R.id.action_titlebar_container);
		if (mCustomTitleViewContainer != null)
		{
			if (mCustomTitleViewResId != -1)
			{
				mCustomTitleViewContainer.setLayoutResource(mCustomTitleViewResId);
				View titleContent = mCustomTitleViewContainer.inflate();
				onCustomTitleViewCreated(titleContent);
				
				/* ViewStub should be released after inflate */
				mCustomTitleViewContainer = null;
			}
		}
		
		TextView titlebar = (TextView)rootView.findViewById(R.id.action_titlebar);
		if (titlebar != null)
		{
			if (mTitle == null || mTitle.isEmpty() || mCustomTitleViewResId != -1)
				titlebar.setVisibility(View.GONE);
			else
				titlebar.setText(mTitle);
		}
		
		mCustomViewContainer = (ViewStub)rootView.findViewById(R.id.action_content);
		if (mCustomViewResId == -1 && mHasList)
		{
			if (mCustomViewContainer == null)
			{
				mList = null;
			}
			else
			{
				mCustomViewContainer.setLayoutResource(R.layout.dialog_action_list);
				mList = (ListView)mCustomViewContainer.inflate();

				/* ViewStub should be released after inflate */
				mCustomViewContainer = null;
			}

			if (mList != null)
			{
				if (mItemClickListener == null)
				{
					mList.setOnItemClickListener(mDefaultItemClickAction);
				}
				else
					mList.setOnItemClickListener(mItemClickListener);
			}
		}
		
		/* Inflate the custom view if any */
		if (mCustomViewResId != -1)
		{
			inflateCustomView(mCustomViewResId);
			
			/* Invoke custom view created callback
			 * to notify whom are concerned
			 */
			onCustomViewCreated(mCustomView);
		}
		
		mButtons = (ViewGroup)rootView.findViewById(R.id.action_btn_bar);
		if (!mHasButtons)
		{
			mButtons.setVisibility(View.GONE);
		}
		else
		{
			final OnClickListener btnClickAct = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if (mBtnClickListener != null)
					{
						if (v == mBtnPos)
							mBtnClickListener.onDialogBtnClick(v, BTN_ID_POSITIVE);						
						else if (v == mBtnNeg)
							mBtnClickListener.onDialogBtnClick(v, BTN_ID_NEGATIVE);
					}
					
					if (v == mBtnPos && mDismissOnPosBtnClick)
					{
						/* Ignore the subsequence click */
						mIsDismissedOnBtnClick = true;
						v.setClickable(false);
						v.setEnabled(false);
						dismiss();
					}
					else if (v == mBtnNeg && mDismissOnNegBtnClick)
					{
						/* Ignore the subsequence click */
						mIsDismissedOnBtnClick = true;
						v.setClickable(false);
						v.setEnabled(false);
						dismiss();
					}
				}
			};
			mBtnPos = (Button)mButtons.findViewById(R.id.action_btn_pos);
			if (mHasPosButton)
			{
				mBtnPos.setClickable(true);
				mBtnPos.setEnabled(true);
				mBtnPos.setOnClickListener(btnClickAct);
				if (mBtnPosText != null)
					mBtnPos.setText(mBtnPosText);
			}
			else
			{
				mBtnPos.setVisibility(View.GONE);
			}

			mBtnNeg = (Button)mButtons.findViewById(R.id.action_btn_neg);
			if (mHasNegButton)
			{
				mBtnNeg.setClickable(true);
				mBtnNeg.setEnabled(true);
				mBtnNeg.setOnClickListener(btnClickAct);				
				if (mBtnNegText != null)
					mBtnNeg.setText(mBtnNegText);
			}
			else
			{
				mBtnNeg.setVisibility(View.GONE);
			}
		}
		
		return rootView;
	}
	
	public View inflateCustomView(int resId) {
		mCustomView = null;
		
		if (mCustomViewContainer != null)
		{
			mCustomViewContainer.setLayoutResource(resId);
			mCustomView = mCustomViewContainer.inflate();
			
			/* ViewStub should be released after inflate */
			mCustomViewContainer = null;
		}

		return mCustomView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mFullScreen)
		{
			getDialog().getWindow().setBackgroundDrawableResource(
					R.drawable.bg_black);
			setZeroPadding();
			/* Set Animation */
			getDialog().getWindow().setWindowAnimations(mAnimationStyle.getStyleResID());
		}
		else
		{
			getDialog().getWindow().setBackgroundDrawableResource(
					R.drawable.alert_diaglog_small_bg);
			/* Set Animation */
			getDialog().getWindow().setWindowAnimations(mAnimationStyle.getStyleResID());
		}
		
		final View rootView = onCreateDefaultView(inflater, container);
		final Activity act = getActivity();
		if (act == null)
			return rootView;
		
		/* Set background color */
		//rootView.setBackgroundColor(act.getResources().getColor(R.color.gray21));
		//rootView.setBackgroundResource(R.drawable.alert_diaglog_fail_bg);
		
		if (mList != null)
		{
			if (mAdapter == null)
			{
				if (mStringArray != null)
				{
					mAdapter = new ArrayAdapter<String>(act.getApplicationContext(),
							R.layout.list_item_chart_action, mStringArray);					
				}
				else if (mItemData.size() > 0)
				{
					DialogItemData[] data = new DialogItemData[mItemData.size()];
					mItemData.toArray(data);
					
					mAdapter = new DialogItemAdapter(act.getApplicationContext(),
								R.layout.list_item_chart_action,
								data);
				}
			}
			
			if (mAdapter != null)
			{
				mList.setAdapter(mAdapter);
			}
		}
		
		/* Invoke dialog view created callback
		 * to notify whom are concerned
		 */
		onDialogViewCreated(rootView);
		
//		if (mFullScreen)
//		{
//			rootView.setBackgroundResource(R.drawable.bg_black);
//		}
		
		return rootView;
	}
	
	public boolean isInstantiated()
	{
		/* Check the shown dialog count to determine to show a new dialog */
		final int shownDialogCnt = getShownDialogCount(mClassName);
		return (shownDialogCnt > 0);
	}
	
	public boolean isAllowInstantiation()
	{
		if (!mAllowMultiInstance && isInstantiated())
			return false;
		return true;
	}
	
	protected void onShown()
	{
		/* Must be overridden by subclass */
	}
	
	private void show(FragmentManager manager, int id, String sid) {
		/* Check the shown dialog count to determine to show a new dialog */
		if (!isAllowInstantiation())
		{
			DebugLog.d("Dialog", "Dialog[%s] not allow multi-instance.\n",
					mClassName);
			return;
		}
		addShownDialog(mClassName);
		
		final String converted_id = (sid == null) ? String.valueOf(id) : sid;
		
		FragmentTransaction ft;
		
		try {
			ft = manager.beginTransaction();
			ft.remove(this);
			final Fragment frag = manager.findFragmentByTag(String.valueOf(mId));
			if (frag != null)
				ft.remove(frag);
			ft.commit();			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
//		if (mFullScreen)
//		{
//			try {
//				ft = manager.beginTransaction();
//				ft.add(R.id.main_tab_frame, this, converted_id);
//				ft.commit();
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			}
//		}
//		else
		{
			try {
				show(manager, converted_id);
				
				/* Notify the dialog starts displaying */
				onShown();
			} catch (IllegalStateException e) {
				e.printStackTrace();
				removeShownDialog(mClassName);
			}	
		}
		
	}
	
	private void show(Activity act, FragmentManager manager, int id, String sid) {
		/* Validate where the activity is ready */
		if (act != null)
		{
			/* Check the shown dialog count to determine to show a new dialog */
			if (!isAllowInstantiation())
			{
				DebugLog.d("Dialog", "Dialog[%s] not allow multi-instance.\n",
						mClassName);
				return;
			}
			
			if (act instanceof AbsFragmentActivity)
			{
				final FragmentManager fmanager = manager;
				final int fid = id;
				final String fsid = sid;
				AbsFragmentActivity sctrlAct = (AbsFragmentActivity)act;
				if (!sctrlAct.isFragmentActivityResumed())
				{
					DebugLog.d("ActionDialog", "Dialog cannot show due to activity paused\n");
					sctrlAct.appendAction(new Runnable() {
						final FragmentManager inner_manager = fmanager;
						final int inner_id = fid;
						final String inner_sid = fsid;
						@Override
						public void run() {
							DebugLog.d("ActionDialog", "Dialog is ready to show\n");
							show(inner_manager, inner_id, inner_sid);
						}
					});
				}
				else
				{
					/* Directly show dialog */
					DebugLog.d("ActionDialog", "Dialog id[%d] sid[%s] ready to show\n",
							id, (sid == null) ? "null" : sid);
					show(manager, id, sid);
				}
			}
		}
	}
	
	public void show(Activity act, FragmentManager manager) {
		show(act, manager, mId, null);
	}
	
	public void show(Activity act, FragmentManager manager, int id) {
		show(act, manager, id, null);
	}

	public void show(Activity act, FragmentManager manager, String sid) {
		show(act, manager, -1, sid);
	}
	
	public void dismiss(Activity act) {
		/* Validate where the activity is ready */
		if (act != null)
		{
			if (act instanceof AbsFragmentActivity)
			{
				AbsFragmentActivity sctrlAct = (AbsFragmentActivity)act;
				if (!sctrlAct.isFragmentActivityResumed())
				{
					DebugLog.d("ActionDialog", "Dialog cannot dismiss due to activity paused\n");
					sctrlAct.appendAction(new Runnable() {
						@Override
						public void run() {
							DebugLog.d("ActionDialog", "Dialog is ready to dismiss\n");
							dismiss();
						}
					});
				}
				else
				{
					/* Directly dismiss dialog */
					//DebugLog.log("ActionDialog", "Dialog id[%d] sid[%s] ready to show\n",
					//		id, (sid == null) ? "null" : sid);
					dismiss();
				}
			}
		}
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		try {

			if (mAction != null)
				mAction.onDismiss(this);
			
			if (mPreDismissListener != null)
				mPreDismissListener.onPreDismiss(this);
			
			super.onDismiss(dialog);
			
			if (mDimissedListener != null)
				mDimissedListener.onDismissed(this);
	
		} catch (IllegalStateException e) {
			/* Avoid from owner activity is no exist anymore */
			e.printStackTrace();
		}		
	}

	@Override
	public void onDestroyView() {
	  if (getDialog() != null && getRetainInstance())
	    getDialog().setOnDismissListener(null);
	  
	  removeShownDialog(mClassName);
	  super.onDestroyView();
	}
	
	@Override
	public void dismiss() {
		try
		{
			super.dismiss();
		} catch (NullPointerException e) {
			DebugLog.e("Dialog", "Failure to dismiss dialog. Activity is destroyed\n");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			DebugLog.e("Dialog", "Failure to dismiss dialog. Activity is destroyed\n");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			DebugLog.e("Dialog", "Failure to dismiss dialog. Activity is paused\n");
			e.printStackTrace();	
		}
	}
	
	//-------------------------------------------------------------
	// Functions of OnItemClickListener
	//-------------------------------------------------------------
	protected OnItemClickListener mDefaultItemClickAction = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if  (view != null && parent == mList)
			{
				DialogItemAction action = mAction;
				if (action != null)
				{
					action.trigger(position, id);
				}
			}
		}
	};
}
