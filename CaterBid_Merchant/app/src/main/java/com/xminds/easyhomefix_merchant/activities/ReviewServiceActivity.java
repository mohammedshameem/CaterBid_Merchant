package com.xminds.easyhomefix_merchant.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhomefix.R;

public class ReviewServiceActivity extends Activity {

	private TextView review;
	private ImageView closeImage;
	private ImageView avatarImage;
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
		setContentView(R.layout.review_service_layout);
		initViews();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		
		review.setText(R.string.review);
		review.setVisibility(View.VISIBLE);
		closeImage.setVisibility(View.VISIBLE);
		avatarImage.setVisibility(View.GONE);
		
		closeImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
	super.onDestroy();
}
	private void initViews(){
		review=(TextView)findViewById(R.id.textview_trackyourfix);
		closeImage=(ImageView)findViewById(R.id.image_close);
		avatarImage=(ImageView)findViewById(R.id.image_avatar);
	}
}
