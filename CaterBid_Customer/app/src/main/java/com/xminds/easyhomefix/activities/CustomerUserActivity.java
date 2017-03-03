package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.activities.CustomerSettingsActivity.LogoutReceiver;
import com.xminds.easyhomefix.adapter.customerUserAdapter;

public class CustomerUserActivity extends Activity {
	
private ListView listview_customerdetails;
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
		setContentView(R.layout.customer_users);
		initViews();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		listview_customerdetails.setAdapter(new customerUserAdapter(this));

	}
@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		listview_customerdetails=(ListView) findViewById(R.id.listview_user);
		
	}
	
	

}
