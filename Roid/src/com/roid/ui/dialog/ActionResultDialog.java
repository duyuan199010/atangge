package com.roid.ui.dialog;

import java.util.ArrayList;

import com.roid.R;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActionResultDialog extends ActionDialog {
	public static class ResultDialogAdapter extends BaseAdapter {
		private static class ViewHolder {
			public TextView mTitleV;
			public TextView mTextV;
		}
		
		public static class DataEntry {
			public String mTitle;
			public String mText;
		}
		
		private ArrayList<DataEntry> mList;
		private Object mListLock;
		private Activity mOwnerAct;
		
		public ResultDialogAdapter(Activity act) {
			mOwnerAct = act;
			mList = new ArrayList<DataEntry>();
			mList.clear();
			mListLock = new Object();
		}
		
		private int getNumOfEntries() {
			synchronized (mListLock) {
				return mList.size();
			}
		}
		
		public DataEntry getEntry(int idx) {
			synchronized (mListLock) {
				if (idx >= 0 && idx < mList.size())
					return mList.get(idx);
			}
			return null;
		}
		
		public int addEntry(DataEntry entry) {
			int cnt;
			
			synchronized (mListLock) {
				cnt = mList.size();
				mList.add(entry);
			}
			return cnt;
		}
		
		public int addEntry(String title, String text) {
			DataEntry entry = new DataEntry();
			entry.mTitle = title;
			entry.mText = text;
			
			return addEntry(entry);
		}
		
		public void setEntry(int idx, String text) {
			synchronized (mListLock) {
				if (idx >= mList.size())
					return;
				
				mList.get(idx).mText = text;
			}
		}
		
		@Override
		public int getCount() {
			return getNumOfEntries();
		}

		@Override
		public Object getItem(int position) {
			return getEntry(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
			{
				LayoutInflater inflater = mOwnerAct.getLayoutInflater();
				convertView = inflater.inflate(
						R.layout.list_item_act_result, parent, false);
				
				TextView titleV = (TextView)convertView.findViewById(R.id.list_item_act_res_title);
				TextView textV = (TextView)convertView.findViewById(R.id.list_item_act_res_text);
				ViewHolder newvh = new ViewHolder();
				newvh.mTitleV = titleV;
				newvh.mTextV = textV;
				convertView.setTag(newvh);
			}
			
			ViewHolder vh = (ViewHolder)convertView.getTag();
			if (vh != null)
			{
				DataEntry entry = (DataEntry)getItem(position);
				if (entry != null)
				{
					vh.mTitleV.setText(entry.mTitle);
					vh.mTextV.setText(entry.mText);
				}
			}
			
			return convertView;
		}		
	}

	private Activity mOwnerAct;
	private boolean mHasIconTitle;
	private boolean mSuccess;
	private String mStatus;
	private String mContent;
	private ImageView mStatusIconV;
	private TextView mStatusTitleV;
	private TextView mStatusContentV;
	private ListView mStatusListV;
	private ResultDialogAdapter mResultAdapter;
	private int mCustomContentResId;
	private int mStatusIconResId;
	
	public Activity getOwnerActivity() {
		return mOwnerAct;
	}
	
	public boolean isSuccess() {
		return mSuccess;
	}
	
	public String getStatus() {
		return mStatus;
	}
	
	public void setStatus(String status) {
		mStatus = status;
	}
	
	public String getContent() {
		return mContent;
	}
	
	public void setContent(String content) {
		mContent = content;
	}
	
	public int addListItem(String title, String text) {
		return mResultAdapter.addEntry(title, text);
	}
	
	public void setListItemText(int idx, String text) {
		mResultAdapter.setEntry(idx, text);
	}
	
	public void setHasIconTitle(boolean hasIconTitle) {
		mHasIconTitle = hasIconTitle;
	}
	
	public void setStatusIcon(int resId) {
		mStatusIconResId = resId;
	}

	@Override
	public void onDialogViewCreated(View dialogView) {
		super.onDialogViewCreated(dialogView);

		//View rootView = dialogView.findViewById(R.id.dialog_tr_root);
		mStatusTitleV = (TextView)dialogView.findViewById(R.id.dialog_act_res_status_title);
		mStatusIconV = (ImageView)dialogView.findViewById(R.id.dialog_act_res_status_icon);
		mStatusContentV = (TextView)dialogView.findViewById(R.id.dialog_act_res_status_content);
		mStatusListV = (ListView)dialogView.findViewById(R.id.dialog_act_res_list);

		if (mHasIconTitle)
		{
			int titleResId = 0;
			if (mStatusIconResId != 0)
			{
				mStatusIconV.setImageResource(mStatusIconResId);
			}
			else if (mSuccess)
			{
				titleResId = R.string.new_order_result_success;
				mStatusIconV.setImageResource(R.drawable.iconsuccess);
			}
			else
			{
				titleResId = R.string.new_order_result_failure;
				mStatusIconV.setImageResource(R.drawable.iconfail);
			}
			
			if (mStatus != null)
				mStatusTitleV.setText(mStatus);
			else if (titleResId != 0)
				mStatusTitleV.setText(titleResId);
		}
		else
		{
			View iconTitleBar = dialogView.findViewById(R.id.dialog_act_res_icon_title_bar);
			iconTitleBar.setVisibility(View.GONE);
		}

		if (mCustomContentResId != 0)
		{
			ViewStub customContentContainer = (ViewStub)dialogView.findViewById(
					R.id.dialog_act_res_custom_content);
			customContentContainer.setLayoutResource(mCustomContentResId);
			customContentContainer.inflate();
			mStatusContentV.setVisibility(View.GONE);
			mStatusIconV.setVisibility(View.GONE);
		}
		else if (mResultAdapter != null && mResultAdapter.getCount() > 0)
		{
			mStatusContentV.setVisibility(View.GONE);
			mStatusListV.setVisibility(View.VISIBLE);
			mStatusListV.setAdapter(mResultAdapter);
		}
		else
		{
			mStatusContentV.setVisibility(View.VISIBLE);
			mStatusListV.setVisibility(View.GONE);
			mStatusContentV.setText(mContent);
		}
	}
	
	public void reloadList()
	{
		FragmentActivity act = getActivity();
		if (act != null)
		{
			act.runOnUiThread(new Runnable() {				
				@Override
				public void run() {
					mResultAdapter.notifyDataSetChanged();
				}
			});
		}
	}
	
	public ActionResultDialog() {
		super();
		mHasList = false;
		mHasButtons = true;
		mHasPosButton = true;
		mHasNegButton = false;
		mTitle = null;
		mFullScreen = false;
		mCustomViewResId = R.layout.dialog_action_result;
		mCustomContentResId = 0;
		mStatusIconResId = 0;
		mHasIconTitle = true;
	}
	
	public void setCustomContentResId(int resId)
	{
		mCustomContentResId = resId;
	}
	
	public void initialize(Activity act, boolean success, String status, String content)
	{
		mOwnerAct = act;
		mResultAdapter = new ResultDialogAdapter(act);
		mSuccess = success;
		mStatus = status;
		mContent = content;
	}
	
	public static ActionResultDialog newInstance(Activity act, boolean success,
			String status, String content) {
		ActionResultDialog dialog = new ActionResultDialog();
		dialog.initialize(act, success, status, content);
		return dialog;
	}
}
