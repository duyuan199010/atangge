package com.strod.yssl.pages.account;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.roid.ui.FragmentControlActivity;
import com.strod.yssl.R;
import com.strod.yssl.clientcore.Config;
import com.strod.yssl.pages.main.MainActivity;

/**
 * app first start activity
 * 
 * @author user
 *
 */
public class SplashActivity extends FragmentControlActivity {
	
	/**the timer task delay*/
	public static final long DELAY = 1500;
	public static final int START_ACTIVITY = 0;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case START_ACTIVITY:
				if(Config.getInstance().getGuide()){
					startActivity(new Intent(SplashActivity.this,MainActivity.class));
				}else{
					startActivity(new Intent(SplashActivity.this,GuideActivity.class));
				}
				SplashActivity.this.finish();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		timer.schedule(task, DELAY);
	}

	Timer timer = new Timer();

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			Message message = new Message();
			message.what = START_ACTIVITY;
			handler.sendMessage(message);
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
