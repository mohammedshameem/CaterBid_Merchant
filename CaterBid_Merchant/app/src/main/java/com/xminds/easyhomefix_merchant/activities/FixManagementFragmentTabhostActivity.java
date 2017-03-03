package com.xminds.easyhomefix_merchant.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;

public class FixManagementFragmentTabhostActivity extends BaseActivity {

	private Fragment mContent;
	private ImageView image_back;
	private TextView textview_header;
	private LogoutReceiver logoutReceiver;
    public class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
                finish();
            }
        }
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
	
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		/*if (mContent == null)
		    mContent = new TopBottomFragmentTabHostActivity();*/
		
		SlidingMenu sm = getSlidingMenu();
		sm.setSlidingEnabled(false);
		
		initViews();
		initManagers();
		setVisibilities();
		setTexts();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		
		image_back.setVisibility(View.VISIBLE);
		textview_header.setVisibility(View.VISIBLE);
		setContentView(R.layout.first_tab_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.first_tab_frame, mContent).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		
		
		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(EasyHomeFixApp.getNotificationcount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getNotificationcount()));
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	private void setTexts() {
		// TODO Auto-generated method stub
		textview_header.setText("Track Your Fix");
	}
	private void setVisibilities() {
		// TODO Auto-generated method stub
		image_back.setVisibility(View.VISIBLE);
		textview_header.setVisibility(View.VISIBLE);
	}
	private void initManagers() {
		// TODO Auto-generated method stub
		
	}
	private void initViews() {
		// TODO Auto-generated method stub
		image_back = (ImageView) findViewById(R.id.image_back);
		textview_header = (TextView) findViewById(R.id.textview_trackyourfix);
	}	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	
}
