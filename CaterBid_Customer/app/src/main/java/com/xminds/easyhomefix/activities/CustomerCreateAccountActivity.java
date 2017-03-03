package com.xminds.easyhomefix.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Managers.LoginManager;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.activities.ConfirmOrderActivity.LogoutReceiver;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class CustomerCreateAccountActivity extends Activity {
	private EditText edittext_email_address;
	private EditText edittext_first_name;
	private EditText edittext_last_name;
	private EditText password;
	private ImageView close;
	private ImageView avatar;
	private Button next;
	private Button next_button;
	boolean firstnameValidationFlag = false;
	boolean lastnameValidationFlag = false;
	boolean passwordValidationFlag = false;
	boolean emailaddressValidationFlag = false;
	private TextView textview_customer_underline;
	private TextView tetview_customer_undertaking;
	private int requestcode = 0;
	private ProgressDialog pDialog;
	private EmailSignUpCallBack emailSignUpCallBack;
	private LoginCallBack loginCallBack;
	private  String emailaddress ;
	private String firstname ;
	private String lastname ;
	private  String enterpassword ;
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
		setContentView(R.layout.customer_create_account);
		initViews();
		initManager();
		setVisibilities();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		Intent signUpIntent = getIntent();
		String signUp_email = signUpIntent.getStringExtra("SignUpEmail");
		edittext_email_address.setText(signUp_email);

		textview_customer_underline.setPaintFlags(textview_customer_underline
				.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

		textview_customer_underline.setText("customer");

		tetview_customer_undertaking.setPaintFlags(tetview_customer_undertaking
				.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

		tetview_customer_undertaking.setText("undertaking");

		next_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			 emailaddress = edittext_email_address.getText()
						.toString();
			 firstname = edittext_first_name.getText()
						.toString();
			 lastname = edittext_last_name.getText().toString();
			 enterpassword = password.getText().toString();

				if (!isValidEmail(emailaddress)) {

					emailaddressValidationFlag = false;
					Utils.showDialog(CustomerCreateAccountActivity.this,"Please enter your valid email address.", "ERROR");
				} else {

					emailaddressValidationFlag = true;
				}

				if (firstname.length() > 0) {
					firstnameValidationFlag = true;
				} else {
					
					firstnameValidationFlag = false;
					if(emailaddressValidationFlag)
					{
						Utils.showDialog(CustomerCreateAccountActivity.this,"Please enter your first name.", "ERROR");
					}

				}

				if (lastname.length() > 0) {
					lastnameValidationFlag = true;
				}

				else {
					lastnameValidationFlag = false;
					if(firstnameValidationFlag)
					{
						Utils.showDialog(CustomerCreateAccountActivity.this,"Please enter your last name.", "ERROR");
					}

				}

				if (enterpassword.length() > 0) {
					passwordValidationFlag = true;
				} else
				{
					passwordValidationFlag = false;
					
					if(lastnameValidationFlag)
					{
						Utils.showDialog(CustomerCreateAccountActivity.this,"Please enter your password.", "ERROR");
					}
					

				}
				
				

				if (emailaddressValidationFlag && firstnameValidationFlag
						&& lastnameValidationFlag && passwordValidationFlag) {

					// Api call for signup
					pDialog = ProgressDialog.show(
							CustomerCreateAccountActivity.this, "",
							"Loading..", true);
					if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
						if(CurrentlyLoggedUserDAO.getInstance().getUdId() != null &&
								CurrentlyLoggedUserDAO.getInstance().getLoginType().equalsIgnoreCase("udId")){
							String udId=CurrentlyLoggedUserDAO.getInstance().getUdId();
							UserManager.getInstance().SignUp(
									CustomerCreateAccountActivity.this, emailaddress,
									enterpassword, firstname, lastname,udId,
									emailSignUpCallBack, requestcode);
						}
					}else{
						UserManager.getInstance().SignUp(
								CustomerCreateAccountActivity.this, emailaddress,
								enterpassword, firstname, lastname,null,
								emailSignUpCallBack, requestcode);
					}
				}
			
			}
		});

		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent toHome = new Intent(CustomerCreateAccountActivity.this,
						SignUpandLoginActivity.class);
				startActivity(toHome);
				finish();
			}
		});
		
		textview_customer_underline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(CustomerCreateAccountActivity.this,CustomerUndertakingDetailsActivity.class);
				startActivity(in);
			}
		});
		tetview_customer_undertaking.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(CustomerCreateAccountActivity.this,CustomerUndertakingDetailsActivity.class);
				startActivity(in);
			}
		});

	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}


	public class LoginCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			Log.e("in login sucesssss", "innnnn loginnnnnnnnnnnn");
			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						UserDAO.getInstance().deleteAllUserRows();
						 UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
						 
						 CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
						 CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailSignupBaseHolder);
						Intent otpIntent = new Intent(
								CustomerCreateAccountActivity.this,
								LandingPageActivity.class);
						startActivity(otpIntent);
						finish();
						}
					}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(CustomerCreateAccountActivity.this, "Please check your internet connection.", "ERROR");
			}


		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}

	public class EmailSignUpCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						if(emailSignupBaseHolder.getData() != null){
							if(emailSignupBaseHolder.getData().getUser() != null){
								if(emailSignupBaseHolder.getData().getUser().getMobileVerified() != null){
									//Check whether mobile verified
									if(emailSignupBaseHolder.getData().getUser().getMobileVerified() == 0){
										UserDAO.getInstance().deleteAllUserRows();
										 UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
										Intent otpIntent = new Intent(CustomerCreateAccountActivity.this,
												EnterOTPActivity.class);
										otpIntent.putExtra("emailaddress", emailaddress);
										otpIntent.putExtra("password", enterpassword);
										startActivity(otpIntent);
										finish();
									}else{										
										 LoginManager.getInstance().Login(
												CustomerCreateAccountActivity.this, emailaddress,
												enterpassword, "Customer", loginCallBack,
												requestcode);
										 finish();
									}
								}
							}
						}
						
						/*Toast.makeText(CustomerCreateAccountActivity.this,
								emailSignupBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();*/

					}

					else {

						Toast.makeText(CustomerCreateAccountActivity.this,
								emailSignupBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();

					}
				}

			} else {

				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					if (UtilValidate.isNotNull(onFailureErrorHolder.getData())) {
						/*Toast.makeText(CustomerCreateAccountActivity.this,
								"?" + onFailureErrorHolder.getData(),
								Toast.LENGTH_LONG).show();*/
						for (ErrorMessageListHolder error : onFailureErrorHolder
								.getData()) {
							Log.e("", ">>>" + error.getMessage());
							Log.e("", ">>>" + error.getField());
							
							final Dialog dialog = new Dialog(CustomerCreateAccountActivity.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.invaliduser_popup);
							dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
							TextView textview_ok = (TextView) dialog
									.findViewById(R.id.ok_button);
							TextView errormessage = (TextView) dialog
									.findViewById(R.id.quote_string);
							errormessage.setText(error.getMessage());
							
							textview_ok.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									dialog.dismiss();
								}
							});
							dialog.show();

							if (error.getField().equalsIgnoreCase("firstName")) {
								edittext_first_name.setText("");
								edittext_first_name.setTextSize(10);
								edittext_first_name
										.setHintTextColor(CustomerCreateAccountActivity.this
												.getResources().getColor(
														R.color.Red));
								edittext_first_name.setHint(error.getMessage());
							}
							if (error.getField().equalsIgnoreCase("lastName")) {
								edittext_last_name.setText("");
								edittext_last_name.setTextSize(10);
								edittext_last_name
										.setHintTextColor(CustomerCreateAccountActivity.this
												.getResources().getColor(
														R.color.Red));
								edittext_last_name.setHint(error.getMessage());
							}
						}
						
					}
					
				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(CustomerCreateAccountActivity.this, "Please check your internet connection.", "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
	}

	private void initViews() {
		// TODO Auto-generated method stub
		edittext_email_address = (EditText) findViewById(R.id.edittext_emailaddress);
		edittext_first_name = (EditText) findViewById(R.id.edittext_firstname);
		edittext_last_name = (EditText) findViewById(R.id.edittext_last_name);
		password = (EditText) findViewById(R.id.edittext_password);
		next_button = (Button) findViewById(R.id.button_next);
		close = (ImageView) findViewById(R.id.image_close);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		textview_customer_underline = (TextView) findViewById(R.id.textview_customer_underline);
		tetview_customer_undertaking = (TextView) findViewById(R.id.tetview_customer_undertaking);

	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		close.setVisibility(View.VISIBLE);
		avatar.setVisibility(View.GONE);

	}

	private void initManager() {
		// TODO Auto-generated method stub
		emailSignUpCallBack = new EmailSignUpCallBack();
		loginCallBack = new LoginCallBack();
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
