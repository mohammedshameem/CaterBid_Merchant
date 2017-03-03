package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.app.EasyHomeFixApp;


public class CollectFloorNoandUnitNoActivity extends Activity{
	
	private EditText edittext_floor_no;
	private EditText edittext_unit_no;
	private Button   subbmit_button;
	private Button   cancel_button;
	private ImageView avatar;
	private Button button_collect_submit;
	private ImageView close_button;
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
		setContentView(R.layout.collect_floor_and_unitnumber);
		
		initViews();
		initManagers();
		setVisibilities();
		
		//logout broadcast receiver
		
		  logoutReceiver = new LogoutReceiver();
		  
	    // Register the logout receiver
		  
	      IntentFilter intentFilter = new IntentFilter();
	      intentFilter.addAction("com.package.ACTION_LOGOUT");
	      registerReceiver(logoutReceiver, intentFilter);
			
		
		close_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		button_collect_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				// TODO Auto-generated method stub
				Intent i=new  Intent(CollectFloorNoandUnitNoActivity.this,CollectAccountDetailsActivity.class);
				startActivity(i);
			}
		});
	}
	
	
	
	@Override
	protected void onDestroy() {
	// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
	super.onDestroy();
	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		edittext_floor_no=(EditText)findViewById(R.id.edittext_collect_floor_no);
		edittext_unit_no=(EditText)findViewById(R.id.edittext_collect_unit_no);
		subbmit_button=(Button)findViewById(R.id.button_collect_submit);
		cancel_button=(Button)findViewById(R.id.button_collect_cancel);
		avatar=(ImageView)findViewById(R.id.image_avatar);
		close_button=(ImageView)findViewById(R.id.image_close);
		button_collect_submit=(Button)findViewById(R.id.button_collect_submit);
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		avatar.setVisibility(View.GONE);
		close_button.setVisibility(View.VISIBLE);
		
	}
	

}
