package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.activities.LandingPageActivity.LogoutReceiver;


/**
 * 
 * @author sujith <a href="sujith@xminds.in">mail to</a> 
 *
 */

public class PersonalizedCustomerServiceActivity extends Activity{
	
	private static final String TAG = PersonalizedCustomerServiceActivity.class.getName();
	private ImageView close_icon;
	private ImageView avatar_icon;
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalized_customer_service_page);
		initViews();
		initManagers();
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		close_icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//TODO click functionality for close icon
			}
		});
		
	
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	private void initManagers() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		
		avatar_icon = (ImageView)findViewById(R.id.image_avatar);
		close_icon = (ImageView)findViewById(R.id.image_close);
		avatar_icon.setVisibility(View.GONE);
		close_icon.setVisibility(View.VISIBLE);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
