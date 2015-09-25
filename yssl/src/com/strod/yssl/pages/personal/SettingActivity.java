package com.strod.yssl.pages.personal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.roid.ui.AbsFragmentActivity;
import com.roid.util.DebugLog;
import com.strod.yssl.R;
import com.strod.yssl.bean.personal.SettingType;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.view.SwitchButton;
import com.strod.yssl.view.TitleBar;

/**
 * setting activity
 * @author user
 *
 */
public class SettingActivity extends AbsFragmentActivity{
	
	private static final String TAG = "SettingActivity";

	/**title bar*/
	private TitleBar mTitleBar;
	
	/**ui listview*/
	private ListView mListView;
	private List<SettingType> mSettingTypeList;
	private SettingAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (Config.getInstance().isNightMode) {
			setTheme(R.style.NightTheme);
		} else {
			setTheme(R.style.DayTheme);
		}
		setContentView(R.layout.activity_setting);
		
		
//		if(mSettingTypeList==null){
//			mSettingTypeList = new ArrayList<SettingType>();
//			mSettingTypeList.add(new SettingType(R.string.unwifi_download, true, null));
//			mSettingTypeList.add(new SettingType(R.string.clear_cache, false, ""));
//			mSettingTypeList.add(new SettingType(R.string.check_update, false, "V"+Config.getInstance().getAppVersionName(SettingActivity.this)));
//			mSettingTypeList.add(new SettingType(R.string.score_atangge, false, ""));
//		}
//		
//		initView();
//		new CaluCache().execute(1);
		
		
		if(mSettingTypeList==null){
			mSettingTypeList = new ArrayList<SettingType>();
			mSettingTypeList.add(new SettingType(R.string.unwifi_download, true, null));
			mSettingTypeList.add(new SettingType(R.string.clear_cache, false, getCacheSize()));
			mSettingTypeList.add(new SettingType(R.string.check_update, false, "V"+Config.getInstance().getAppVersionName(SettingActivity.this)));
			mSettingTypeList.add(new SettingType(R.string.score_atangge, false, ""));
		}
		initView();
	}
	
	class CaluCache extends AsyncTask<Integer,Integer,Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			SettingType clearCacheType = mSettingTypeList.get(1);
			clearCacheType.setRightText(getCacheSize());
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAdapter.notifyDataSetChanged();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private String getCacheSize(){
		long start = System.currentTimeMillis();
		String cacheSize = "";
		long contentCache = Config.getInstance().getContentCacheSize();
		File file = ImageLoader.getInstance().getDiskCache().getDirectory();
		long imageCache = 0;
		File[] subFile = file.listFiles();
		if(subFile != null){
			for(int i = 0;i<subFile.length;i++){
				if(!subFile[i].isDirectory()){
					imageCache += subFile[i].length();
				}
			}
		}
		long size = contentCache+imageCache;
		if(size>1024*1024){//M
			cacheSize = String.format("%.1fM", size/(1024*1024.0));
		}else{
			cacheSize = String.format("%.1fKB", size/1024.0);
		}
		long end = System.currentTimeMillis();
		DebugLog.i(TAG, "getCacheSize used:"+(end-start)+" ms");
		return cacheSize;
	}

	private void initView(){
		mTitleBar = (TitleBar) findViewById(R.id.title_bar);
		mTitleBar.setLeftImageBtnVisibility(View.VISIBLE);
		mTitleBar.setLeftImageBtnBackground(R.drawable.back_selector);
		mTitleBar.setLeftImageBtnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mTitleBar.setMiddleText(R.string.setting);
		mTitleBar.setRightBtnVisibility(View.GONE);
		
		mListView = (ListView) findViewById(R.id.list_view);
		mAdapter = new SettingAdapter();
		mListView.setAdapter(mAdapter);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	class SettingAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mSettingTypeList==null?0:mSettingTypeList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mSettingTypeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.item_setting_list, null);
				
				holder.mLeftText = (TextView) convertView.findViewById(R.id.left_text);
				holder.mSwitchButton = (SwitchButton) convertView.findViewById(R.id.switch_button);
				holder.mRightText = (TextView) convertView.findViewById(R.id.right_text);
				holder.mArrow = (ImageView) convertView.findViewById(R.id.arrow);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			SettingType settingType= mSettingTypeList.get(position);
			holder.mLeftText.setText(settingType.getLeftText());
			
			if(settingType.isShowRightBtn()){
				holder.mSwitchButton.setVisibility(View.VISIBLE);
				holder.mRightText.setVisibility(View.GONE);
				holder.mArrow.setVisibility(View.GONE);
			}else{
				holder.mSwitchButton.setVisibility(View.GONE);
				holder.mRightText.setVisibility(View.VISIBLE);
				holder.mRightText.setText(settingType.getRightText());
				holder.mArrow.setVisibility(View.VISIBLE);
			}
			
			return convertView;
		}
		
		class ViewHolder{
			TextView mLeftText;
			SwitchButton mSwitchButton;
			TextView mRightText;
			ImageView mArrow;
		}
	}
	
}
