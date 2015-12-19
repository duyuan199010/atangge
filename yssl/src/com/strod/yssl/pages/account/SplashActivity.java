package com.strod.yssl.pages.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.roid.ui.AbsActivity;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.pages.main.MainActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * app first start activity
 * 
 * @author user
 *
 */
public class SplashActivity extends AbsActivity {

	/**the timer task delay*/
	public static final long DELAY = 1500;
	public static final int START_ACTIVITY = 0;

	private Handler mHandler;

	private static class MyHandler extends Handler{

		private final WeakReference<SplashActivity> activityWeakReference;

		public MyHandler(SplashActivity splashActivity){
			this.activityWeakReference = new WeakReference<SplashActivity>(splashActivity);
		}

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case START_ACTIVITY:
					if(Config.getInstance().getGuide()){
						activityWeakReference.get().startActivity(new Intent(activityWeakReference.get(), MainActivity.class));
					}else{
						activityWeakReference.get().startActivity(new Intent(activityWeakReference.get(),GuideActivity.class));
					}
					activityWeakReference.get().finish();
					break;

				default:
					break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		mHandler = new MyHandler(this);

		timer.schedule(task, DELAY);
	}

	Timer timer = new Timer();

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			Message message = new Message();
			message.what = START_ACTIVITY;
			mHandler.sendMessage(message);
		}
	};

	@Override
	protected void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}

}
