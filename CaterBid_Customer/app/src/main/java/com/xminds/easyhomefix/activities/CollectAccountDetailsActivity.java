package com.xminds.easyhomefix.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


public class CollectAccountDetailsActivity extends Activity {

	private EditText edittext_emailaddress;
	private EditText edittext_enterpassword;
	private EditText edittext_confirmpassword;
	private Button   submit_button;
	private ImageView avatar;
	private ImageView close_button;
	boolean emailaddressValidationFlag = false;
	boolean enterpasswordValidationFlag = false;
	boolean confirmpasswordValidationFlag = false;
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
		setContentView(R.layout.collect_account_details);
		
		initViews();
		initManagers();
		setVisibilities();
		
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	    // Register the logout receiver
	      IntentFilter intentFilter = new IntentFilter();
	      intentFilter.addAction("com.package.ACTION_LOGOUT");
	      registerReceiver(logoutReceiver, intentFilter);
			
		
		submit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i=new Intent(CollectAccountDetailsActivity.this,CustomerPaymentSelectionActivity.class);
				 startActivity(i);
			}
		});

		close_button.setOnClickListener(new OnClickListener() {
			
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

	private void setVisibilities() {
		// TODO Auto-generated method stub
		avatar.setVisibility(View.GONE);
		close_button.setVisibility(View.VISIBLE);
		
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		edittext_emailaddress=(EditText) findViewById(R.id.edittext_collect_emailaddress);
		edittext_enterpassword=(EditText) findViewById(R.id.edittext_collect_enterpassword);
		edittext_confirmpassword=(EditText) findViewById(R.id.edittext_collect_confirmpassword);
		submit_button=(Button) findViewById(R.id.collect_submit_button);
		avatar=(ImageView)findViewById(R.id.image_avatar);
		close_button=(ImageView)findViewById(R.id.image_close);		
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */

	 private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
