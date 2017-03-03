package com.xminds.easyhomefix_merchant.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;

public class EmptyFixQuoteActivity extends BaseActivity {
	
	private LinearLayout negotiate_chat_id;
	private ImageView image_back;
	private TextView view_all;
	private TextView textview_trackyourfix;
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
		
		setContentView(R.layout.fix_quotes_pending_tab);
		
		initView();
		initManager();
		setVisibilites();
		setTexts();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		negotiate_chat_id.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pendingFixIntent = new Intent(EmptyFixQuoteActivity.this,TrackFixFragmentTabhostActivity.class);
				startActivity(pendingFixIntent);
			}
		});

		
		view_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pendingFixIntent = new Intent(EmptyFixQuoteActivity.this,ReviewActivity.class);
				pendingFixIntent.putExtra(Constants.PENDING, "pending");
				startActivity(pendingFixIntent);
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
		textview_trackyourfix.setText("Fix Quotes");
	}

	private void setVisibilites() {
		// TODO Auto-generated method stub

		image_back.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

	
	}

	private void initManager() {
		// TODO Auto-generated method stub
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		negotiate_chat_id = (LinearLayout)findViewById(R.id.negotiate_chat_id);
		image_back = (ImageView)findViewById(R.id.image_back);
		textview_trackyourfix = (TextView)findViewById(R.id.textview_trackyourfix);
		view_all=(TextView)findViewById(R.id.view_all);
	}
	
	

}
