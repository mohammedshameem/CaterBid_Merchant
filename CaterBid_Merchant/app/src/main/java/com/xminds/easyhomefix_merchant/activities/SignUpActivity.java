package com.xminds.easyhomefix_merchant.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.Manager.EmailSignUpManager;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class SignUpActivity extends Activity {
	private EditText edittext_email_address;
	private EditText edittext_first_name;
	private EditText edittext_last_name;
	private EditText password;
	private TextView merchant_underline;
	private TextView help_underline;
	private ImageView close;
	private ImageView avatar;
	private Button next;
	private Button next_button;
	private EditText edittext_companyname;
	boolean firstnameValidationFlag = false;
	boolean lastnameValidationFlag = false;
	boolean passwordValidationFlag = false;
	boolean emailaddressValidationFlag = false;
	int requestcode = 0;
	EmailSignUpCallBack emailsignupcallback;
	private String emailaddress;
	private String firstname;
	private String lastname;
	private String enterpassword;
	private String companyName;
	private UserTokenHolder tokenholder;
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
		setContentView(R.layout.sign_up);
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
		next_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				emailaddress = edittext_email_address.getText().toString();
				firstname = edittext_first_name.getText().toString();
				lastname = edittext_last_name.getText().toString();
				enterpassword = password.getText().toString();
				companyName = edittext_companyname.getText().toString();

				if (!isValidEmail(emailaddress)) {

					emailaddressValidationFlag = false;
					Utils.showDialog(SignUpActivity.this,"Please enter valid email address.", "ERROR");

				} else {

					emailaddressValidationFlag = true;
				}

				if (firstname.length() > 0) {
					firstnameValidationFlag = true;
				} else {
					firstnameValidationFlag = false;
					
					if(emailaddressValidationFlag)
					{
						Utils.showDialog(SignUpActivity.this,"Please enter your first name.", "ERROR");
					}
					
				}

				if (lastname.length() > 0) {
					lastnameValidationFlag = true;
				}

				else {
					lastnameValidationFlag = false;
					if(firstnameValidationFlag){
						Utils.showDialog(SignUpActivity.this,"Please enter your last name.", "ERROR");
					}
					
				}

				if (enterpassword.length() > 0) {
					passwordValidationFlag = true;
				} else {
					passwordValidationFlag = false;
					if(lastnameValidationFlag)
					{
						Utils.showDialog(SignUpActivity.this,"Please enter your password.", "ERROR");
					}
					
				}

				if (emailaddressValidationFlag && firstnameValidationFlag
						&& lastnameValidationFlag && passwordValidationFlag) {

					EmailSignUpManager.getInstance().SignUp(
							SignUpActivity.this, emailaddress, enterpassword,
							firstname, lastname, companyName,
							emailsignupcallback, requestcode);
				}
			}
		});

		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent toHome = new Intent(SignUpActivity.this,
						SignUpandLoginActivity.class);
				startActivity(toHome);
				finish();
			}
		});
		
		merchant_underline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(SignUpActivity.this,MerchantUndertakingDetailsActivity.class);
				startActivity(in);
				
			}
		});
		help_underline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(SignUpActivity.this,MerchantUndertakingDetailsActivity.class);
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
	public class EmailSignUpCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub

			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						if(emailSignupBaseHolder.getData() != null){
							if(emailSignupBaseHolder.getData().getUser() != null){
								UserDAO.getInstance().deleteAllUserRows();
								UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
								Intent otpIntent = new Intent(SignUpActivity.this,EnterOTPActivity.class);
								otpIntent.putExtra("emailaddress", emailaddress);
								otpIntent.putExtra("password", enterpassword);
								startActivity(otpIntent);
								finish();
							}
						}
						Toast.makeText(SignUpActivity.this,"Successful!!!",Toast.LENGTH_LONG).show();

					}

					else {

						Toast.makeText(SignUpActivity.this,
								emailSignupBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();

					}
				}
			} else {
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					if (UtilValidate.isNotNull(onFailureErrorHolder.getData())) {

						for (ErrorMessageListHolder error : onFailureErrorHolder
								.getData()) {
							if (error.getField().equalsIgnoreCase("password")) {
								/*password.setText("");
								password.setTextSize(10);
								password.setHintTextColor(SignUpActivity.this
										.getResources().getColor(R.color.Red));
								password.setHint(error.getMessage());*/
								Utils.showDialog(SignUpActivity.this, ""+error.getMessage(), "ERROR");
							}
							
							if (error.getField().equalsIgnoreCase("email")) 
							{
								Utils.showDialog(SignUpActivity.this, ""+error.getMessage(), "ERROR");
								
							}

						}

					}
				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(SignUpActivity.this, "Please check your internet connection", "ERROR");
			}
			else
			{
				Utils.showDialog(SignUpActivity.this, result, "ERROR");
			}
		}

	}

	private void initViews() {
		// TODO Auto-generated method stub
		edittext_email_address = (EditText) findViewById(R.id.edittext_emailaddress);
		edittext_first_name = (EditText) findViewById(R.id.edittext_firstname);
		edittext_last_name = (EditText) findViewById(R.id.edittext_last_name);
		edittext_companyname = (EditText) findViewById(R.id.edittext_companyname);
		password = (EditText) findViewById(R.id.edittext_password);
		next_button = (Button) findViewById(R.id.button_next);
		close = (ImageView) findViewById(R.id.image_close);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		help_underline = (TextView) findViewById(R.id.help_underline);
		merchant_underline = (TextView) findViewById(R.id.merchant_underline);
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		close.setVisibility(View.VISIBLE);
		avatar.setVisibility(View.GONE);
		merchant_underline.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		help_underline.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	private void initManager() {
		// TODO Auto-generated method stub
		emailsignupcallback = new EmailSignUpCallBack();
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
	
	/*public class LoginCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						UserDAO.getInstance().deleteAllUserRows();
						UserDAO.getInstance().InsertUserDetails(
								emailSignupBaseHolder);
						UserDAO.getInstance().getUserDetails();
						CurrentlyLoggedUserDAO.getInstance()
								.deleteAllUserRows();
						CurrentlyLoggedUserDAO.getInstance()
								.InsertCurrentlyloggedUserDetails(
										emailSignupBaseHolder);
						tokenholder = new UserTokenHolder();
						tokenholder = CurrentlyLoggedUserDAO.getInstance()
								.getUserDetails();
						String token = tokenholder.getToken();

						if (UtilValidate.isNotNull(emailSignupBaseHolder
								.getData().getToken())) {
							EasyHomeFixApp.setUserHeader(null);
							EasyHomeFixApp.setUserHeader(emailSignupBaseHolder
									.getData().getToken());
						}

						Intent otpIntent = new Intent(SignUpActivity.this,
								EnterOTPActivity.class);
						startActivity(otpIntent);
						finish();
					}
					else{
						Toast.makeText(SignUpActivity.this,
								emailSignupBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();
					}
				}
			}
			else{
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {

						
					}
				}
				
			}

					
			
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
		}
		
	}*/
}
