package com.roid.ui.dialog;

import com.roid.R;
import com.roid.util.DebugLog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingDialog extends Dialog {

	public static final boolean HAS_FRAME = true;
	private static final int STYLE_HAS_FRAME = R.style.dialog_loading_bar;
	private static final int STYLE_NO_FRAME = R.style.dialog_loading_bar_no_frame;

	private ProgressBar mBar;
	private TextView mMsgBox;
	private static boolean mActive = false;
	private static Object mActiveLock = new Object();

	public static interface OnDialogDismissListener {
		public void onDialogDismiss(LoadingDialog dialog);
	}

	public static LoadingDialog show(Context context) {
		return show(context, null, null);
	}

	public static LoadingDialog show(Context context, CharSequence title, CharSequence message) {
		return show(context, title, message, true);
	}

	public static LoadingDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, true, null);
	}

	public static LoadingDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
		return show(context, title, message, indeterminate, cancelable, null);
	}

	public static LoadingDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
		LoadingDialog dialog = new LoadingDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);

		synchronized (mActiveLock) {
			if (mActive)
				return dialog;
			mActive = true;
		}
		if (!((Activity) context).isFinishing()) {
			dialog.show();
		}

		return dialog;
	}

	public LoadingDialog(Context context) {
		super(context, HAS_FRAME ? STYLE_HAS_FRAME : STYLE_NO_FRAME);

		init();
	}

	public void setMessage(CharSequence message) {
		if (mMsgBox != null) {
			mMsgBox.setVisibility(View.VISIBLE);
			mMsgBox.setText(message);
		}
	}

	public ProgressBar getProgressBar() {
		return mBar;
	}

	private void init() {
		LayoutInflater inflater = getLayoutInflater();
		if (inflater != null) {
			View rootV = inflater.inflate(R.layout.dialog_progress, null, false);
			if (rootV != null) {
				// Resources res = getContext().getResources();
				mBar = (ProgressBar) rootV.findViewById(R.id.loading_progress);
				mMsgBox = (TextView) rootV.findViewById(R.id.loading_message);

				setTitle(null);
				setCancelable(true);
				setOnCancelListener(null);

				/* The next line will add the ProgressBar to the dialog. */
				addContentView(rootV, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		}

		// ProgressBar bar = new ProgressBar(getContext());
		// bar.setIndeterminateDrawable(
		// getContext().getResources().getDrawable(
		// R.drawable.progress_rotate_indeterminate));
		// bar.setPadding(PixelConverter.getPixelFromDip(bar.getResources(),
		// 16),
		// PixelConverter.getPixelFromDip(bar.getResources(), 16),
		// PixelConverter.getPixelFromDip(bar.getResources(), 16),
		// PixelConverter.getPixelFromDip(bar.getResources(), 16));
		//
		// setTitle(null);
		// setCancelable(true);
		// setOnCancelListener(null);
		// /* The next line will add the ProgressBar to the dialog. */
		// addContentView(bar, new LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	@Override
	public void dismiss() {
		synchronized (mActiveLock) {
			mActive = false;
		}

		try {
			super.dismiss();
		} catch (IllegalArgumentException e) {
			DebugLog.e("Dialog", "Failure to dismiss dialog. Activity[%s] is destroyed\n", getContext().toString());
			e.printStackTrace();
		}

	}
}
