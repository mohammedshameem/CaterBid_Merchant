package com.xminds.easyhomefix_merchant.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhomefix.R;

public class CustomerPaymentSelectionActivity extends Activity {

private Button button_cash;
private Button button_creditcard_or_paypal;
private ImageView close_button;
private ImageView avatar;
Context context=this;
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
		setContentView(R.layout.customer_payment_selection);
		initView();
		initManager();
		setVisibility();

		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		button_cash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.customer_payment_popup);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
				TextView dialog_payment = (TextView) dialog.findViewById(R.id.textview_dialog_payment);
				TextView dialog_how_you = (TextView) dialog.findViewById(R.id.textview_dialog_how_you_made);
				TextView dialog_to_your = (TextView) dialog.findViewById(R.id.textview_dialog_to_your);
				Button button_not_yet=(Button) dialog.findViewById(R.id.button_dialog_not_yet);
				Button button_yes=(Button) dialog.findViewById(R.id.button_dialog_yes);
				
				button_not_yet.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						
					}
				});
				button_yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						
					}
				});
				dialog.show();
			}
		});
	}

	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
			super.onDestroy();
		}

	private void initView() {
		// TODO Auto-generated method stub
		
		button_cash=(Button)findViewById(R.id.button_payment_cash);
		button_creditcard_or_paypal=(Button)findViewById(R.id.button_payment_creditcard_or_paypal);
		close_button = (ImageView) findViewById(R.id.image_close);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		
	}
	private void setVisibility() {
		// TODO Auto-generated method stub
		close_button.setVisibility(View.VISIBLE);
		avatar.setVisibility(View.GONE);
		
	}

	private void initManager() {
		// TODO Auto-generated method stub
		
	}

}
