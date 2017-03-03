package com.xminds.easyhomefix_merchant.activities;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easyhomefix.R;

public class MerchantPaymentsActivity extends Activity {
	TextView accountBalances;
	Button transferToBank;
	Button seeAll;
	ListView transactionHistory;
	LinearLayout back;
	TextView heading;
	ImageView imageBack;
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
		setContentView(R.layout.merchant_payments);
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
	
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		seeAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(MerchantPaymentsActivity.this,MerchantTransactionHistoryActivity.class);
				startActivity(i);
			}
		});
		
		transferToBank.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//noBankDetailsPopup();
				transferToBankPopup();
				
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void setTexts() {
		heading.setText(R.string.payments);		
	}

	private void setVisibilities() {
		imageBack.setVisibility(View.VISIBLE);
		heading.setVisibility(View.VISIBLE);
		
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		
	}

	private void initViews() {
		accountBalances=(TextView)findViewById(R.id.textViewAccountBalances);
		transferToBank=(Button)findViewById(R.id.buttonTransferToBank);
		seeAll=(Button)findViewById(R.id.buttonSeeAll);
		transactionHistory=(ListView)findViewById(R.id.listTransationHistory);
		back=(LinearLayout)findViewById(R.id.back_button_layout);
		heading=(TextView)findViewById(R.id.textview_trackyourfix);
		imageBack=(ImageView)findViewById(R.id.image_back);
	}
	
	private void noBankDetailsPopup(){
		LayoutInflater layoutInflater = (LayoutInflater) MerchantPaymentsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.merchant_payment_popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		
		final TextView ok=(TextView)popupView.findViewById(R.id.textViewPopupOk);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();				
			}
		});
	}
	private void transferToBankPopup(){
		LayoutInflater layoutInflater = (LayoutInflater) MerchantPaymentsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.merchant_payment_transfer_popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		
		final TextView ok=(TextView)popupView.findViewById(R.id.textViewPopupOkTrnsfr);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();				
			}
		});
	}

}
