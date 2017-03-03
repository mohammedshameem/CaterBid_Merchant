package com.xminds.easyhomefix.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.LoginManager;
import com.xminds.easyhomefix.activities.CustomerCreateAccountActivity.LoginCallBack;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class SignUpandLoginActivity extends Activity {

	private TextView signup_heading_text;
	private TextView signup_text;
	private TextView login_text;
	private ImageView signup_up_icon;
	private ImageView login_icon;
	// ActionBar
	private ImageView close;
	private ImageView avatar;
	// SignUp Page
	private LinearLayout signup_content_layout;
	private EditText signup_emailaddress;
	private LinearLayout signup_facebook_button;
	private LinearLayout signup_gmail_button;
	private LinearLayout signup_next_button;
	// Login Page
	private LinearLayout login_content_layout;
	private EditText login_emailaddress;
	private EditText login_password;
	private LinearLayout login_facebook_button;
	private LinearLayout login_gmail_button;
	private LinearLayout login_button;
	boolean signupemailaddressValidationFlag = false;
	boolean loginemailaddressValidationFlag = false;
	boolean loginpasswordValidationFlag = false;
	private LoginCallBack loginCallBack;
	private UserTokenHolder tokenholder;
	int requestcode = 0;
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
		setContentView(R.layout.signup_and_login_page);
		initViews();
		initManager();
		setVisibilities();
		setSignUpView();
		
		Log.e("###############", "REGID-SP: "+EasyHomeFixApp.getGcmRegId());
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		signup_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setSignUpView();
			}
		});
		login_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setLoginView();
			}
		});

		login_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String loginemailadddress = login_emailaddress.getText()
						.toString();
				final String loginpassword = login_password.getText()
						.toString();
				
				if (!isValidEmail(loginemailadddress)) {

					loginemailaddressValidationFlag = false;
					Utils.showDialog(SignUpandLoginActivity.this, "please enter your valid email address.", "ERROR");


				} else {

					loginemailaddressValidationFlag = true;
				}

				if (loginpassword.length() > 0) {
					loginpasswordValidationFlag = true;
				} else {
					loginpasswordValidationFlag = false;
					if(loginemailaddressValidationFlag)
					{
						Utils.showDialog(SignUpandLoginActivity.this, "please enter your password.", "ERROR");
					}
					

				}

				if (loginemailaddressValidationFlag
						&& loginpasswordValidationFlag) {
					Toast.makeText(SignUpandLoginActivity.this, "login..",
							Toast.LENGTH_SHORT).show();

					LoginManager.getInstance().Login(
							SignUpandLoginActivity.this, loginemailadddress,
							loginpassword, "Customer", loginCallBack,
							requestcode);
					/*
					 * Intent loginIntent = new Intent(
					 * SignUpandLoginActivity.this, LandingPageActivity.class);
					 * startActivity(loginIntent);
					 */

					// UserDAO.getInstance().getUserId();
					// CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailsignupobject);

				}

			}
		});

		signup_next_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String signupemaladdress = signup_emailaddress.getText()
						.toString();
				// TODO Auto-generated method stub
				
				if (!isValidEmail(signupemaladdress)) {

					signupemailaddressValidationFlag = false;
					Utils.showDialog(SignUpandLoginActivity.this, "please enter your valid email address.", "ERROR");

				} 
				else{

					signupemailaddressValidationFlag = true;
				}

				if (signupemailaddressValidationFlag) {
					String signupEmail = signup_emailaddress.getText()
							.toString();
					Intent createAccntIntent = new Intent(
							SignUpandLoginActivity.this,
							CustomerCreateAccountActivity.class);
					createAccntIntent.putExtra("SignUpEmail", signupEmail);
					startActivity(createAccntIntent);
					finish();
				}
			}
		});
		login_facebook_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignUpandLoginActivity.this,
						FacebookLoginActivity.class);
				startActivity(i);
			}
		});

		login_gmail_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent i = new Intent(SignUpandLoginActivity.this,
						GoogleLoginActivity.class);
				startActivity(i);
				finish();*/
			}
		});

		signup_facebook_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(SignUpandLoginActivity.this,
						FacebookLoginActivity.class);
				startActivity(i);

			}
		});

		signup_gmail_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent i = new Intent(SignUpandLoginActivity.this,
						GoogleLoginActivity.class);
				startActivity(i);
				finish();*/
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

			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						UserDAO.getInstance().deleteAllUserRows();
						UserDAO.getInstance().InsertUserDetails(
								emailSignupBaseHolder);
						
						if(UtilValidate.isNotNull(emailSignupBaseHolder.getData())){
							if(UtilValidate.isNotNull(emailSignupBaseHolder.getData().getUser())){
								if(emailSignupBaseHolder.getData().getUser().getMobileVerified() != null){
									//Check whether mobile verified
									if(emailSignupBaseHolder.getData().getUser().getMobileVerified() == 0){
										Intent otpIntent = new Intent(SignUpandLoginActivity.this,
												EnterOTPActivity.class);
										startActivity(otpIntent);
										finish();
									}else{	
										CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
										CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(
												emailSignupBaseHolder);
										CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
												emailSignupBaseHolder.getData().getUser().getId());
										Intent loginIntent = new Intent(
												SignUpandLoginActivity.this,
												LandingPageActivity.class);
										startActivity(loginIntent);
										finish();
									}
								}								
							}
						}

					}

					else {

						Toast.makeText(SignUpandLoginActivity.this,
								emailSignupBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();

					}
				}

			} else {
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {

						if (error.getField().equalsIgnoreCase("email")) {
							/*
							login_emailaddress.setText("");
							login_emailaddress.setTextSize(15);*/
							Utils.showDialog(SignUpandLoginActivity.this, ""+error.getMessage(), "ERROR");
						}

						else if (error.getField().equalsIgnoreCase("password")) {
						/*	login_password.setText("");
							login_password.setTextSize(15);*/
							Utils.showDialog(SignUpandLoginActivity.this, ""+error.getMessage(), "ERROR");
						}

					}
				}

			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				/*Toast.makeText(SignUpandLoginActivity.this,
						"Please check your internet connection.",
						Toast.LENGTH_SHORT).show();*/
				Utils.showDialog(SignUpandLoginActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(SignUpandLoginActivity.this, ""+result, "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		avatar.setVisibility(View.GONE);
		close.setVisibility(View.VISIBLE);
	}

	private void setSignUpView() {
		// TODO Auto-generated method stub
		signup_heading_text.setText(getResources().getString(
				R.string.signup_text));
		signup_text.setTextColor(getResources().getColor(R.color.green));
		login_text.setTextColor(getResources().getColor(R.color.black));
		signup_up_icon.setVisibility(View.VISIBLE);
		signup_content_layout.setVisibility(View.VISIBLE);
		login_icon.setVisibility(View.INVISIBLE);
		login_content_layout.setVisibility(View.GONE);
	}

	private void setLoginView() {
		// TODO Auto-generated method stub
		signup_heading_text.setText(getResources().getString(
				R.string.login_text));
		login_text.setTextColor(getResources().getColor(R.color.green));
		signup_text.setTextColor(getResources().getColor(R.color.black));
		signup_up_icon.setVisibility(View.INVISIBLE);
		signup_content_layout.setVisibility(View.GONE);
		login_icon.setVisibility(View.VISIBLE);
		login_content_layout.setVisibility(View.VISIBLE);
	}

	private void initViews() {
		signup_heading_text = (TextView) findViewById(R.id.signup_heading_text);
		signup_text = (TextView) findViewById(R.id.signup_text);
		login_text = (TextView) findViewById(R.id.login_text);
		signup_up_icon = (ImageView) findViewById(R.id.signup_up_icon);
		login_icon = (ImageView) findViewById(R.id.login_icon);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		close = (ImageView) findViewById(R.id.image_close);
		// SignUp Page
		signup_content_layout = (LinearLayout) findViewById(R.id.signup_content_layout);
		signup_emailaddress = (EditText) findViewById(R.id.signup_emailaddress);
		signup_facebook_button = (LinearLayout) findViewById(R.id.signup_facebook_button);
		signup_gmail_button = (LinearLayout) findViewById(R.id.signup_gmail_button);
		signup_next_button = (LinearLayout) findViewById(R.id.signup_next_button);
		// Login Page
		login_content_layout = (LinearLayout) findViewById(R.id.login_content_layout);
		login_emailaddress = (EditText) findViewById(R.id.login_emailaddress);
		login_password = (EditText) findViewById(R.id.login_password);
		login_facebook_button = (LinearLayout) findViewById(R.id.login_facebook_button);
		login_gmail_button = (LinearLayout) findViewById(R.id.login_gmail_button);
		login_button = (LinearLayout) findViewById(R.id.login_button);
	}

	private void initManager() {
		// TODO Auto-generated method stub
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
