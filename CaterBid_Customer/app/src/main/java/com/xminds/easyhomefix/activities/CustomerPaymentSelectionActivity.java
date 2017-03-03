package com.xminds.easyhomefix.activities;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.PaymentBaseHolder;
import com.xminds.easyhomefix.Managers.PaymentManager;
import com.xminds.easyhomefix.activities.CustomerEditDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class CustomerPaymentSelectionActivity extends Activity {

private Button button_cash;
private Button button_creditcard_or_paypal;
private ImageView close_button;
private ImageView avatar;
private String Ongoingquoteamount;
private String jobId;
private String quoteId;
private Context context=this;
private int requestedCode = 0;

private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
private static final String CONFIG_CLIENT_ID = "AZ_Y2pEJOfvZSf1aWofD9hE4cPkdeGlEzlr74nTGOhfU9-hjfjf_dxsKZw5O_Js9b5cqvSQ1pk7axcYu";
private static final int REQUEST_CODE_PAYMENT = 1;
private static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID).acceptCreditCards(false);
private PaypalPaymentCallback paypalPaymentCallback;
private CashPaymentCallBack cashPaymentCallBack;
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
		getIntentValues();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		Intent intentService = new Intent(CustomerPaymentSelectionActivity.this, PayPalService.class);
		intentService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intentService);
		
		close_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
						PaymentManager.getInstance().cashPayment(CustomerPaymentSelectionActivity.this, 
								cashPaymentCallBack, quoteId, "Completed", requestedCode);
						
					}
				});
				dialog.show();
			}
		});
		
		button_creditcard_or_paypal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 PayPalPayment paymentForFix = new PayPalPayment(new BigDecimal(Ongoingquoteamount), "SGD", "Payment fo Fix: "+jobId,
						 PayPalPayment.PAYMENT_INTENT_SALE);
				 
				 Intent intent = new Intent(CustomerPaymentSelectionActivity.this, PaymentActivity.class);
			        // send the same configuration for restart resiliency
			        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

			        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, paymentForFix);

			        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
			}
		});
		
		Log.e("%%%%%%%%%%%%%%%%","jobId : "+jobId+" Ongoingquoteamount: "+Ongoingquoteamount+" quoteId: "+quoteId);
		Log.e("%%%%%%%%%%%%%%%%","header: "+com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO.getInstance().getHeader());
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void getIntentValues() {
		// TODO Auto-generated method stub
		Ongoingquoteamount = getIntent().getExtras().getString("Ongoingquoteamount");
		jobId = getIntent().getExtras().getString("jobId");
		quoteId = getIntent().getExtras().getString("quoteId");
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
	
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                    	 Log.e("CustomerPaymentSelectionActivity", confirm.getProofOfPayment().toString());
                    	 Log.e("CustomerPaymentSelectionActivity", "Confirmation: "+confirm.describeContents());
                    	 Log.e("CustomerPaymentSelectionActivity", "Confirmation: "+confirm.toString());
                        Log.e("CustomerPaymentSelectionActivity", confirm.toJSONObject().toString(4));
                        Log.e("CustomerPaymentSelectionActivity", confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        
                        String paymentResponse = confirm.toJSONObject().getJSONObject("response").toString();
                        Log.e("CustomerPaymentSelectionActivity", "RESPONSE : "+paymentResponse);
                        Toast.makeText(
                                getApplicationContext(),
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();
                        PaymentManager.getInstance().payPalPayment(CustomerPaymentSelectionActivity.this, paypalPaymentCallback, quoteId, 
                        		"Completed", paymentResponse, "1", requestedCode);
                        

                    } catch (JSONException e) {
                        Log.e("CustomerPaymentSelectionActivity", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("CustomerPaymentSelectionActivity", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("CustomerPaymentSelectionActivity",
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
	}


	private void initManager() {
		// TODO Auto-generated method stub
		paypalPaymentCallback = new PaypalPaymentCallback();
		cashPaymentCallBack = new CashPaymentCallBack();
 	}
	
	public class PaypalPaymentCallback implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (requestedCode == responseCode) {
				PaymentBaseHolder paymentBaseHolder = (PaymentBaseHolder) result;
				if(UtilValidate.isNotNull(paymentBaseHolder)){
					if(paymentBaseHolder.isSuccess()){
						Toast.makeText(CustomerPaymentSelectionActivity.this,
                                "Payment Completed", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(CustomerPaymentSelectionActivity.this, TrackYourFixFragmentTabHostActivity.class);
						EasyHomeFixApp.setCategoryNameDefaultTab(Constants.COMPLETED);
						startActivity(intent);
						finish();
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(CustomerPaymentSelectionActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(CustomerPaymentSelectionActivity.this, ""+result, "ERROR");
			}
			//Toast.makeText(CustomerPaymentSelectionActivity.this,result, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class CashPaymentCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if (requestedCode == responseCode) {
				PaymentBaseHolder paymentBaseHolder = (PaymentBaseHolder) result;
				if(UtilValidate.isNotNull(paymentBaseHolder)){
					if(paymentBaseHolder.isSuccess()){
						Toast.makeText(CustomerPaymentSelectionActivity.this,
                                "Payment Completed", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(CustomerPaymentSelectionActivity.this, TrackYourFixFragmentTabHostActivity.class);
						EasyHomeFixApp.setCategoryNameDefaultTab(Constants.COMPLETED);
						startActivity(intent);
						finish();
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			//Toast.makeText(CustomerPaymentSelectionActivity.this,result, Toast.LENGTH_LONG).show();
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				//Utils.showDialog(CustomerPaymentSelectionActivity.this, "Please check your internet connection.", "ERROR");
				Toast.makeText(CustomerPaymentSelectionActivity.this, "Please check your internet connection.", Toast.LENGTH_LONG).show();
			}
			else
			{
				Utils.showDialog(CustomerPaymentSelectionActivity.this, ""+result, "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
