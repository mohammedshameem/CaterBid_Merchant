package com.xminds.easyhomefix_merchant.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.PaymentHolder;
import com.xminds.easyhomefix_merchant.Holder.TransactionHistoryBaseHolder;
import com.xminds.easyhomefix_merchant.Manager.TransactionManager;
import com.xminds.easyhomefix_merchant.adapter.TransactionHistoryAdapter;
import com.xminds.easyhomefix_merchant.adapter.TransactionHistoryFirstAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class MerchantTransactionHistoryActivity extends BaseActivity {
	
	private ListView transactionHistoryAll;
	private LinearLayout back;
	private TextView heading;
	private ImageView imageBack;
	private TransactionHistoryCallBack transactionHistoryCallBack;
	private int requestCode = 0;
	private  List<PaymentHolder> paymentHistory = new ArrayList<PaymentHolder>(); 
	private TransactionHistoryFirstAdapter transactionHistoryAdapter;
	private ProgressDialog progressDialog;
	private LogoutReceiver logoutReceiver;
    public class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
                finish();
            }
        }
    }
    
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			TransactionManager.getInstance().getTransactionHistory(MerchantTransactionHistoryActivity.this,transactionHistoryCallBack,requestCode);
		}
	};
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_history_layout);
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
		
		 progressDialog=ProgressDialog.show(MerchantTransactionHistoryActivity.this, null, "Loading transaction...");
		TransactionManager.getInstance().getTransactionHistory(MerchantTransactionHistoryActivity.this,transactionHistoryCallBack,requestCode);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MerchantTransactionHistoryActivity.this.registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		
		if(EasyHomeFixApp.getNotificationcount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getNotificationcount()));
		super.onResume();
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	MerchantTransactionHistoryActivity.this.unregisterReceiver(mBroadcastReceiver);
	unregisterReceiver(logoutReceiver);
	super.onDestroy();
}
	private void initViews() {
		transactionHistoryAll=(ListView)findViewById(R.id.listTransationHistoryAll);
		back=(LinearLayout)findViewById(R.id.back_button_layout);
		heading=(TextView)findViewById(R.id.textview_trackyourfix);
		imageBack=(ImageView)findViewById(R.id.image_back);
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		transactionHistoryCallBack = new TransactionHistoryCallBack();
	}

	private void setVisibilities() {
		imageBack.setVisibility(View.VISIBLE);
		heading.setVisibility(View.VISIBLE);
	}

	private void setTexts() {
		heading.setText(R.string.transaction_history);	
	}
	
	
	private class TransactionHistoryCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(requestCode == responseCode){
				if(UtilValidate.isNotNull(result)){
					TransactionHistoryBaseHolder transactionHistoryBaseHolder = (TransactionHistoryBaseHolder)result;
					if(transactionHistoryBaseHolder.isSuccess()){
						if(UtilValidate.isNotNull(transactionHistoryBaseHolder.getData())){
							if(UtilValidate.isNotNull(transactionHistoryBaseHolder.getData().getPaymentHistory())&&
									UtilValidate.isNotEmpty(transactionHistoryBaseHolder.getData().getPaymentHistory())){
								paymentHistory = transactionHistoryBaseHolder.getData().getPaymentHistory();
								transactionHistoryAdapter = new TransactionHistoryFirstAdapter(MerchantTransactionHistoryActivity.this,
										paymentHistory);
								transactionHistoryAll.setAdapter(transactionHistoryAdapter);
							}
							if(progressDialog != null){
								progressDialog.dismiss();
							}
						}
					}
				}else{
					Toast.makeText(MerchantTransactionHistoryActivity.this, "No Transactions", Toast.LENGTH_LONG).show();
					if(progressDialog != null){
						progressDialog.dismiss();
					}
				}
				
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(progressDialog != null){
				progressDialog.dismiss();
			}
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET)){
				//Toast.makeText(MerchantTransactionHistoryActivity.this, ApiConstants.NO_INTERNET, Toast.LENGTH_LONG).show();
				Utils.showDialog(MerchantTransactionHistoryActivity.this, "Please check your internet connection.", "ERROR");
			}
			if(result.equalsIgnoreCase("Invalid User")){
				//Toast.makeText(MerchantTransactionHistoryActivity.this, result, Toast.LENGTH_LONG).show();
				Utils.showDialog(MerchantTransactionHistoryActivity.this, result, "ERROR");
			}
			}
		}
}
