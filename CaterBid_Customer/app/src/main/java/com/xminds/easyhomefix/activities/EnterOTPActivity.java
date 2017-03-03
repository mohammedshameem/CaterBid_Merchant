package com.xminds.easyhomefix.activities;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserBaseHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.LoginManager;
import com.xminds.easyhomefix.Managers.SendOtpManager;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.activities.CustomerCreateAccountActivity.LoginCallBack;
import com.xminds.easyhomefix.activities.CustomerSettingsActivity.UpdateUserCallBack;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class EnterOTPActivity extends Activity {

	private ImageView close;
	private ImageView avatar;
	private LinearLayout enterOTPLayout;
	private TextView textViewTop;
	private TextView textViewMiddle;
	private TextView textViewBottom;
	private EditText edittext_Phoneno;
	private Button submit_button;
	private Button submit;
	private EditText ediText_OTP;
	boolean phonenoValidationFlag = false;
	boolean otpValidationFlag = false;
	private Spinner spinnercountrycode;
	private String[] spinnerValues = new String[] { "+65", "+91", "+1" };
	private int requestcode = 0;
	private String prefix;
	private SendOtpCallBack sendotpcallback;
	private String phoneno;
	private String selected;
	private VerifyOtpCallBack verifyotppcallback;
	private String otp_text;
	private UserTokenHolder tokenholder;
	private TextView textViewCounter;
	private CountDownTimer otpTimer;
	private int timerFlag;
	private String fromConfirmOrder;
	private boolean flag=false;
	private String emailaddress;
	private String password;
	private LoginCallBack loginCallBack;
	private UserDetailsCallBack userDetailsCallBack;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_otp_layout);
		initViews();
		setVisibilities();
		initManager();
		getIntentValues();
		
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		final ArrayAdapter<String> adapter1 = new SpinnerAdapter(
				EnterOTPActivity.this, R.layout.spinner_item, spinnerValues);

		spinnercountrycode.setAdapter(new SpinnerAdapter(this,
				R.layout.spinner_item, spinnerValues));

		spinnercountrycode
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						selected = parent.getItemAtPosition(position)
								.toString();
						Log.e("selected value", "selected" + selected);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});

		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toHome = new Intent(EnterOTPActivity.this,
						SignUpandLoginActivity.class);
				startActivity(toHome);
				finish();
			}
		});

		submit_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				phoneno = edittext_Phoneno.getText().toString();

				if ((phoneno.length() >= 8)
						&& (phoneno.charAt(0) == '8' || phoneno.charAt(0) == '9')) {
					phonenoValidationFlag = true;
				} else {
					phonenoValidationFlag = false;
					/*edittext_Phoneno.setError(
							"please enter Valid Mobile Number", null);*/
					Utils.showDialog(EnterOTPActivity.this, "please enter your valid mobile number", "ERROR");
				}

				if (phonenoValidationFlag) {
					
					if(timerFlag==0){
						SendOtpManager.getInstance().sendotp(EnterOTPActivity.this,selected,false, phoneno, sendotpcallback, requestcode);
						timerFlag++;
					}else{
						SendOtpManager.getInstance().sendotp(EnterOTPActivity.this,selected,true, phoneno, sendotpcallback, requestcode);
						timerFlag++;
					}
					textViewTop.setText(R.string.verify);
					textViewMiddle.setText(R.string.in_case);
					textViewBottom.setText(R.string.needs_to);
					enterOTPLayout.setVisibility(View.VISIBLE);
					submit_button.setText(R.string.submit);
					submit_button.setVisibility(View.GONE);
					submit.setVisibility(View.VISIBLE);
					
					otpTimer.start();
					
				}

			}
		});
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				otp_text = ediText_OTP.getText().toString();
				if (otp_text.length() > 0) {
					otpValidationFlag = true;
				} else {
					otpValidationFlag = false;
					Utils.showDialog(EnterOTPActivity.this, "please enter OTP", "ERROR");
					//ediText_OTP.setError("please enter OTP");
				}

				if (otpValidationFlag) {
					progressDialog=ProgressDialog.show(EnterOTPActivity.this, null, "Verifying OTP ....");
					SendOtpManager.getInstance().verifyotp(
							EnterOTPActivity.this, otp_text,
							verifyotppcallback, requestcode);
					/*Intent toHome = new Intent(EnterOTPActivity.this,
							SignUpandLoginActivity.class);
					startActivity(toHome);
					finish();*/
					
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	public class SpinnerAdapter extends ArrayAdapter<String> {

		private Activity activity;

		public SpinnerAdapter(Activity activity, int resource, String[] objects) {

			// TODO Auto-generated constructor stub
			super(activity, resource, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			Log.e("spinnervalue", "valllllll" + prefix);
			// TODO Auto-generated method stub

			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.spinner_item, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.spinner_item_textview);
			main_text.setText(spinnerValues[position]);
			prefix = spinnerValues[position];
			return mySpinner;

		}

	}

	public class SendOtpCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			Log.e("in sucess", "sucesssssssssssssssssssssss");
			if (responseCode == requestcode) {
				/*Toast.makeText(EnterOTPActivity.this,"",
						Toast.LENGTH_LONG).show();*/
				Utils.showDialog(EnterOTPActivity.this, "OTP sent successfully", "EasyHomeFix");
				EmailSignupBaseHolder sendotpbaseholder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(sendotpbaseholder)) {
					if (sendotpbaseholder.isSuccess()) {
						if (UtilValidate.isNotNull(sendotpbaseholder.getData()
								.getOtpCode())) {
							/*
							 * ediText_OTP.setText(sendotpbaseholder.getData()
							 * .getOtpCode()); otp_text =
							 * ediText_OTP.getText().toString();
							 */
							Log.e("otp", "otp value"
									+ sendotpbaseholder.getData().getOtpCode());
							/*int otpvalue = sendotpbaseholder.getData()
									.getOtpCode();
							String val = String.valueOf(otpvalue);
							ediText_OTP.setText(val);*/

						}

						else {
							Log.e("on not suceess", "on failure");
							Toast.makeText(EnterOTPActivity.this,
									sendotpbaseholder.isSuccess() + "",
									Toast.LENGTH_LONG).show();

						}

					}

				}

				else {
					Log.e("on suceess", "on failure");
					OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
					if (UtilValidate.isNotNull(onFailureErrorHolder)) {
						for (ErrorMessageListHolder error : onFailureErrorHolder
								.getData()) {

							if (error.getField().equalsIgnoreCase("mobile")) {
								edittext_Phoneno.setText("");
								edittext_Phoneno.setTextSize(15);
								edittext_Phoneno
										.setHintTextColor(EnterOTPActivity.this
												.getResources().getColor(
														R.color.Red));
								edittext_Phoneno.setHint(error.getMessage());
							}

						}
					}

				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(EnterOTPActivity.this,
					LandingPageActivity.class);
			startActivity(intent);
			finish();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}

	public class VerifyOtpCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder enterotpbaseholder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(enterotpbaseholder)) {
					if (enterotpbaseholder.isSuccess()) {
						if(fromConfirmOrder != null){
							if(fromConfirmOrder.equalsIgnoreCase("fromConfirmOrder")){
								flag=true;
								finish();
							}
						}else if(emailaddress !=null && password != null){
							LoginManager.getInstance().Login(
									EnterOTPActivity.this, emailaddress,
									password, "Customer", loginCallBack,
									requestcode);
						}else{
							UserManager.getInstance().getUserDetails(EnterOTPActivity.this, userDetailsCallBack, responseCode);
						}
						otpTimer.cancel();
					}

					else {
						Log.e("on not suceess", "on failure");
						Toast.makeText(EnterOTPActivity.this,
								enterotpbaseholder.isSuccess() + "",
								Toast.LENGTH_LONG).show();
						progressDialog.dismiss();
					}
				}

			}

			else {
				progressDialog.dismiss();
				Log.e("on suceess", "on failure");
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {

						if (error.getField().equalsIgnoreCase("otp")) {
							/*ediText_OTP.setText("");
							ediText_OTP.setTextSize(15);
							ediText_OTP.setHintTextColor(EnterOTPActivity.this
									.getResources().getColor(R.color.Red));
							ediText_OTP.setHint(error.getMessage());*/
							Utils.showDialog(EnterOTPActivity.this, ""+error.getMessage(), "ERROR");
						}

					}
				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
		}

	}
	

	private void setVisibilities() {
		// TODO Auto-generated method stub
		avatar.setVisibility(View.GONE);
		close.setVisibility(View.VISIBLE);
		submit_button.setVisibility(View.VISIBLE);
		submit.setVisibility(View.GONE);
	}

	private void initViews() {
		close = (ImageView) findViewById(R.id.image_close);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		enterOTPLayout = (LinearLayout) findViewById(R.id.enterOTPLayout);
		textViewTop = (TextView) findViewById(R.id.textViewTop);
		textViewMiddle = (TextView) findViewById(R.id.textVievMiddle);
		textViewBottom = (TextView) findViewById(R.id.textViewBottom);
		submit_button = (Button) findViewById(R.id.button_submit_OTP);
		submit = (Button) findViewById(R.id.button_submit);
		edittext_Phoneno = (EditText) findViewById(R.id.editTextPhoneno);
		ediText_OTP = (EditText) findViewById(R.id.ediTextOTP);
		spinnercountrycode = (Spinner) findViewById(R.id.spinnerCountryCode);
		textViewCounter=(TextView)findViewById(R.id.textViewCounter);
	}

	private void initManager() {
		// TODO Auto-generated method stub
		sendotpcallback = new SendOtpCallBack();
		verifyotppcallback = new VerifyOtpCallBack();
		timerFlag=0;
		loginCallBack = new LoginCallBack();
		userDetailsCallBack = new UserDetailsCallBack();
		otpTimer=new CountDownTimer(120000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				String hms = String.format("%02d:%02d",  
		                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),  
		                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
				textViewCounter.setText(hms);
			}
			
			@Override
			public void onFinish() {
				retryOTP();
			}
		};
		
	}

	private void getIntentValues() {
		if(getIntent().hasExtra("fromConfirmOrder")){
			fromConfirmOrder = getIntent().getExtras().getString("fromConfirmOrder");
		}
		if(getIntent().hasExtra("emailaddress")){
			emailaddress = getIntent().getExtras().getString("emailaddress");
		}
		if(getIntent().hasExtra("password")){
			password = getIntent().getExtras().getString("password");
		}
	}

	private void retryOTP() {
		// TODO Auto-generated method stub
		textViewTop.setText(R.string.need_number);
		textViewMiddle.setText(R.string.to_give_you);
		textViewBottom.setText(R.string.customer_service);
		enterOTPLayout.setVisibility(View.INVISIBLE);
		submit_button.setVisibility(View.VISIBLE);
		submit.setVisibility(View.GONE);
		Toast.makeText(EnterOTPActivity.this,"Please Request to resend OTP",Toast.LENGTH_LONG).show();
	}
	
	public class LoginCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			Log.e("in login sucesssss", "innnnn loginnnnnnnnnnnn");
			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						UserDAO.getInstance().deleteAllUserRows();
						 UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
						 
						 CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
						 CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailSignupBaseHolder);
						 CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
								 emailSignupBaseHolder.getData().getUser().getId());
						 
						 progressDialog.dismiss();
						 Intent otpIntent = new Intent(EnterOTPActivity.this,
								LandingPageActivity.class);
						 startActivity(otpIntent);
						 finish();
						}else{
							progressDialog.dismiss();
							Toast.makeText(EnterOTPActivity.this, "LogIn Failure Try again", Toast.LENGTH_LONG).show();
						}
					}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			Toast.makeText(EnterOTPActivity.this, "LogIn Failure Try again", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			Toast.makeText(EnterOTPActivity.this, "LogIn Failure Try again", Toast.LENGTH_LONG).show();
		}
		
	}

	private class UserDetailsCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(responseCode == requestcode){
				if(result != null){
					UserBaseHolder userBaseHolder =(UserBaseHolder) result;
					if(userBaseHolder.isSuccess()){
						if(userBaseHolder.getData() != null){
							if(userBaseHolder.getData().getMobileVerified() == 1 ){
								UserDAO.getInstance().updateMobile(userBaseHolder.getData());
								CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
								CurrentlyLoggedUserDAO.getInstance().
								 InsertCurrentlyloggedUserDetails(UserDAO.getInstance().getUserDetails());
								CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
										userBaseHolder.getData().getId());
								progressDialog.dismiss();
								 
									Intent in = new Intent(EnterOTPActivity.this,
											LandingPageActivity.class);
											startActivity(in);
											finish();
							}
						}
					}
				}
			}		
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Toast.makeText(EnterOTPActivity.this,
						"Please check your internet connection.",
						Toast.LENGTH_SHORT).show();
			}

			progressDialog.dismiss();
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
		}
		
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		//Intent intent = new Intent();
		if(fromConfirmOrder != null){
			if(fromConfirmOrder.equalsIgnoreCase("fromConfirmOrder") && flag){
				setResult(RESULT_OK);
			}
		}
		super.finish();
	}
	
	
}
