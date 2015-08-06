package com.roid.ui.dialog;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roid.R;
import com.roid.util.StringFormatter;

public class PopupAboutDialog extends ActionDialog {

	private Activity mOwnerAct;
	
	public static PopupAboutDialog newInstance(Activity act) {
		PopupAboutDialog dialog = new PopupAboutDialog();
		dialog.mTitle = act.getResources().getString(R.string.about_dialog_title);
		dialog.mCustomViewResId = R.layout.dialog_about;
		dialog.mOwnerAct = act;
		dialog.mHasList = true;
		dialog.mHasButtons = true;
		dialog.mFullScreen = true;
		return dialog;
	}

	public static String getAppVersion(Activity act) {
		String app_ver;
		try
		{
		    app_ver = act.getPackageManager().getPackageInfo(
		    		act.getPackageName(), 0).versionName
		    		+ "." + act.getPackageManager().getPackageInfo(
				    		act.getPackageName(), 0).versionCode;
		}
		catch (NameNotFoundException e)
		{
			app_ver = "Unknown version";
		}
		return app_ver;
	}
	
	public static String getAppDate(Activity act) {
		String app_date;
		try {
			ApplicationInfo ai = act.getPackageManager().getApplicationInfo(
					act.getPackageName(), 0);
			ZipFile zf = new ZipFile(ai.sourceDir);
			ZipEntry ze = zf.getEntry("classes.dex");
			long time = ze.getTime();
			app_date = StringFormatter.toDateTime(new java.util.Date(time));
			
		} catch (Exception e) {
			app_date = "Unknown build date";
		}
		return app_date;
	}
	
	@Override
	public void onDialogViewCreated(View dialogView) {
		super.onDialogViewCreated(dialogView);
		
		TextView versionV = (TextView)dialogView.findViewById(R.id.action_about_version);
		if (versionV != null)
		{
			versionV.setText(getAppVersion(mOwnerAct));
		}
		
		TextView dateV = (TextView)dialogView.findViewById(R.id.action_about_date);
		if (dateV != null)
		{
			dateV.setText(getAppDate(mOwnerAct));
		}
		
		ImageView companyIconV = (ImageView)dialogView.findViewById(R.id.action_about_company_icon);
//		if (companyIconV != null)
//		{
//			Drawable comIcon = CompanyInfoMgr.instance().getImage(
//					getResources(),
//					CompanyInfoMgr.RESID_COMPANY_LOGO,
//					CompanyInfoMgr.getDisplayDPI(getResources()));
//			if (comIcon != null)
//			{
//				BitmapDrawable comIconBmp = (BitmapDrawable)comIcon;
//				comIconBmp.setTargetDensity(getResources().getDisplayMetrics());
//				companyIconV.setImageDrawable(comIcon);
//				companyIconV.setVisibility(View.VISIBLE);
//			}			
//		}
		
		TextView copyrightV = (TextView)dialogView.findViewById(R.id.action_about_copyright);
//		if (copyrightV != null)
//		{
//			String copyrightStr = CompanyInfoMgr.instance().getText(
//					CompanyInfoMgr.RESID_COMPANY_NAME,
//					GTConfig.instance().getLanguage());
//			if (copyrightStr != null)
//			{				
//				/* Direct use the bundle cached company name */
//				// MTF feature: Only show copyright if specified company name
//				//              is found in whitelabel bundle
//				View copyrightContainer = (View)dialogView.findViewById(R.id.action_about_copyright_container);
//				copyrightContainer.setVisibility(View.VISIBLE);
//			}
//			else
//			{
//				copyrightStr = mOwnerAct.getString(R.string.about_dialog_copyright);
//			}
//			
//			copyrightStr = copyrightStr.replaceAll("\\\\n", System.getProperty("line.separator"));			
//			copyrightV.setText(copyrightStr);
//		}
		
//		if (GTConfig.ENABLE_SCREEN_DEBUG_INFO)
//		{
//			TextView infoV = (TextView)dialogView.findViewById(R.id.action_about_extend_info);
//			if (infoV != null)
//			{
//				DisplayMetrics metrics = getResources().getDisplayMetrics();
//				String infoStr = String.format("dpi[%d] x[%d] y[%d]\ndensity[%.2f] xdpi[%.2f] ydpi[%.2f]",
//						metrics.densityDpi, metrics.widthPixels, metrics.heightPixels,
//						metrics.density, metrics.xdpi, metrics.ydpi);
//				infoV.setText(infoStr);
//				infoV.setVisibility(View.VISIBLE);
//			}
//		}
		
		if (mButtons != null)
		{
			Button posBtn = (Button)mButtons.findViewById(R.id.action_btn_pos);
			if (posBtn != null)
			{
				posBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
			}
			
			Button negBtn = (Button)mButtons.findViewById(R.id.action_btn_neg);
			if (negBtn != null)
			{
				negBtn.setVisibility(View.GONE);
			}
		}
	}
}
