package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.AccessToken;
import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserBaseHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.LogoutManager;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.activities.CustomerPaymentSelectionActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class CustomerSettingsActivity extends Activity {

	private TextView textview_username;
	private TextView textview_mobileno;
	private TextView textview_emailaddress;
	private LinearLayout linear_layout_editdetails;
	private LinearLayout linear_layout_changepassword;
	private TextView textview_mobilenotifications;
	private TextView textview_emailnotifications;
	private Button button_logout;
	private TextView texview_settings;
	private ImageView back_button;
	private ImageView avatar;
	private int remebermecheckMobileNotificationValue;
	private int remebermecheckEmailNotificationValue;
	private boolean remebermecheckemail = false;
	private ToggleButton mobilenotification_toogle_button;
	private ToggleButton emailnotification_toogle_button;
	private UpdateUserCallBack updateUserCallBack;
	private int requestcode = 0;
	private ProgressDialog pDialog;
	private UserTokenHolder tokenholder;
	private String customerfirstname;
	private String customerlastname;
	private String mobilenumber;
	private String email;
	private String countrycode;
	private ImageView imageview_settings_user;
	private LogoutReceiver logoutReceiver;
	private TextView text_edit;
	private LogoutCallback logoutCallback;
	private UserTokenHolder userTokenHolder=new UserTokenHolder();
	private Integer mobilenotification;
	private Integer emailNotification;
	private boolean mobilenotificationvalue;
	private boolean emailnotificationvalue;
	
	
	
	
	public class LogoutReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
	            finish();
	        }
	    }
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tokenholder = new UserTokenHolder();
		tokenholder = UserDAO.getInstance().getUserDetails();
		customerfirstname = tokenholder.getUser().getFirstName();
		customerlastname = tokenholder.getUser().getLastName();
		mobilenumber = tokenholder.getUser().getMobile();
		email = tokenholder.getUser().getEmail();
		countrycode = tokenholder.getUser().getCountryCode();

		if (UtilValidate.isNotNull(customerlastname)) {
			textview_username.setText(customerfirstname + " "
					+ customerlastname);
		} else {
			textview_username.setText(customerfirstname);
		}
		if ((UtilValidate.isNotNull(countrycode))
				&& (UtilValidate.isNotNull(mobilenumber))) {
			textview_mobileno.setText(mobilenumber);
		} else {
			textview_mobileno.setText(mobilenumber);
		}

		textview_emailaddress.setText(email);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.customer_settings);
		initViews();
		initManager();
		setVisibilities();
		
		userTokenHolder=UserDAO.getInstance().getUserDetails();
		mobilenotification=	userTokenHolder.getUser().getMobileNotification();
		emailNotification=userTokenHolder.getUser().getEmailNotification();
		Log.e("", "mobilenotification>>>"+mobilenotification);
		Log.e("", "emailNotification>>>"+emailNotification);
		
		if(mobilenotification==1)
		{
			mobilenotificationvalue=true;
		}else
		{
			mobilenotificationvalue=false;
		}
		if(emailNotification==1)
		{
		emailnotificationvalue=true;	
		}else
		{
			emailnotificationvalue=false;	
		}
		mobilenotification_toogle_button.setChecked(mobilenotificationvalue);
		emailnotification_toogle_button.setChecked(emailnotificationvalue);
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		

		texview_settings.setText("Settings");
		tokenholder = new UserTokenHolder();
		tokenholder = UserDAO.getInstance().getUserDetails();
		customerfirstname = tokenholder.getUser().getFirstName();
		customerlastname = tokenholder.getUser().getLastName();
		mobilenumber = tokenholder.getUser().getMobile();
		email = tokenholder.getUser().getEmail();
		countrycode = tokenholder.getUser().getCountryCode();
		textview_username.setText(customerfirstname + " " + customerlastname);
		textview_mobileno.setText(countrycode + mobilenumber);
		textview_emailaddress.setText(email);
		
		if(UtilValidate.isNotNull(tokenholder.getUser().getProfileImage()))
		{
			/*Picasso.with(CustomerSettingsActivity.this).load(tokenholder.getUser().getProfileImage())
			.into(imageview_settings_user);*/
			
			if(tokenholder.getUser().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=tokenholder.getUser()
						.getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Log.e("", "############"+profileurl);
				Picasso.with(CustomerSettingsActivity.this)
						.load(profileurl)
						.into(imageview_settings_user);
			}
			else
			{
				Picasso.with(CustomerSettingsActivity.this).load(tokenholder.getUser().getProfileImage())
				.into(imageview_settings_user);
				
			}
			
		}
		else
		{
			Picasso.with(CustomerSettingsActivity.this).load(R.drawable.profile_image_null)
			.into(imageview_settings_user);
		}

		back_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(CustomerSettingsActivity.this,
						LandingPageActivity.class);
				startActivity(in);

			}
		});
Log.e("", "LOGIN TYPE"+tokenholder.getUser().getLoginType());
if(tokenholder.getUser().getLoginType().equalsIgnoreCase("facebook")||(tokenholder.getUser().getLoginType().equalsIgnoreCase("google")))
{
	text_edit.setTextColor(this.getResources().getColor(R.color.grey_text));
	linear_layout_changepassword.setClickable(false);
	linear_layout_changepassword.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.edit_hidden));
}
else
{
	linear_layout_changepassword.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(CustomerSettingsActivity.this,
					CustomerChangePasswordActivity.class);
			startActivity(i);
		}
	});
	
	
	
	
}
linear_layout_editdetails.setOnClickListener(new OnClickListener() {

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(CustomerSettingsActivity.this,
				CustomerEditDetailsActivity.class);
		startActivity(i);
	}
});
		button_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				LogoutManager.getInstance().logout(CustomerSettingsActivity.this, logoutCallback, requestcode);

			}
		});

		mobilenotification_toogle_button
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Log.e("", "remember_me_toggle_button is on!");

							remebermecheckMobileNotificationValue = 1;
							pDialog = ProgressDialog.show(
									CustomerSettingsActivity.this, "",
									"Loading..", true);
							UserManager.getInstance().UpdateUser(
									CustomerSettingsActivity.this, null, null,
									null, null,

									remebermecheckMobileNotificationValue,
									null, null, null, null, null,
									updateUserCallBack, requestcode);

						} else {
							Log.e("", "remember_me_toggle_button is off!");

							remebermecheckMobileNotificationValue = 0;
							pDialog = ProgressDialog.show(
									CustomerSettingsActivity.this, "",
									"Loading..", true);
							UserManager.getInstance().UpdateUser(
									CustomerSettingsActivity.this, null, null,
									null, null,

									remebermecheckMobileNotificationValue,
									null, null, null, null, null,
									updateUserCallBack, requestcode);
						}
					}
				});

		emailnotification_toogle_button
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {

							remebermecheckEmailNotificationValue = 1;
							pDialog = ProgressDialog.show(
									CustomerSettingsActivity.this, "",
									"Loading..", true);
							UserManager.getInstance().UpdateUser(
									CustomerSettingsActivity.this, null, null,
									null, null,

									null, remebermecheckEmailNotificationValue,
									null, null, null, null, updateUserCallBack,
									requestcode);

						} else {

							remebermecheckEmailNotificationValue = 0;
							pDialog = ProgressDialog.show(
									CustomerSettingsActivity.this, "",
									"Loading..", true);
							UserManager.getInstance().UpdateUser(
									CustomerSettingsActivity.this, null, null,
									null, null,

									null, remebermecheckEmailNotificationValue,
									null, null, null, null, updateUserCallBack,
									requestcode);
						}
					}
				});

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(CustomerSettingsActivity.this,
				LandingPageActivity.class);
		startActivity(in);
		super.onBackPressed();
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	public class UpdateUserCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {

						if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
							if (emailSignupBaseHolder.isSuccess()) {

								UserDAO.getInstance().deleteAllUserRows();
								UserDAO.getInstance().InsertUserDetails(
										emailSignupBaseHolder);
Log.e("", "updateeeee mobile not"+emailSignupBaseHolder.getData().getUser().getMobileNotification());
Log.e("", "updateeeee email not"+emailSignupBaseHolder.getData().getUser().getEmailNotification());
								CurrentlyLoggedUserDAO.getInstance()
										.deleteAllUserRows();
								CurrentlyLoggedUserDAO.getInstance()
										.InsertCurrentlyloggedUserDetails(
												emailSignupBaseHolder);

								// finish();
							}

							else {

								Toast.makeText(CustomerSettingsActivity.this,
										 "Please try again later",
										Toast.LENGTH_LONG).show();

							}
						}

					}

				}

			}

			else {
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
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(CustomerSettingsActivity.this, "Please check your internet connection.", "ERROR");
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

		textview_username = (TextView) findViewById(R.id.textview_settings_username);
		textview_mobileno = (TextView) findViewById(R.id.textview_settings_mobileno);
		textview_emailaddress = (TextView) findViewById(R.id.textview_settings_emailaddress);
		linear_layout_editdetails = (LinearLayout) findViewById(R.id.linear_layout_settings_editdetails);
		linear_layout_changepassword = (LinearLayout) findViewById(R.id.linearlayout_settings_changepassword);
		textview_mobilenotifications = (TextView) findViewById(R.id.textview_settings_emailnotification);
		textview_emailnotifications = (TextView) findViewById(R.id.textview_settings_emailnotification);
		button_logout = (Button) findViewById(R.id.button_settings_logout);
		back_button = (ImageView) findViewById(R.id.image_back);
		texview_settings = (TextView) findViewById(R.id.textview_trackyourfix);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		mobilenotification_toogle_button = (ToggleButton) findViewById(R.id.mobilenotifications_toggle_button);
		emailnotification_toogle_button = (ToggleButton) findViewById(R.id.emailnotification_toggle_button);
		imageview_settings_user=(ImageView) findViewById(R.id.imageview_settings_user);
		text_edit=(TextView)findViewById(R.id.text_edit);
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		texview_settings.setVisibility(View.VISIBLE);
		avatar.setVisibility(View.GONE);
		back_button.setVisibility(View.VISIBLE);

	}

	private void initManager() {
		updateUserCallBack = new UpdateUserCallBack();
		// TODO Auto-generated method stub
		logoutCallback = new LogoutCallback();
	}
	
	private class LogoutCallback implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if(responseCode == requestcode){
				UserBaseHolder userBaseHolder = (UserBaseHolder) result;
				if(userBaseHolder.isSuccess()){
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("com.package.ACTION_LOGOUT");
					sendBroadcast(broadcastIntent);
					
					
					UserDAO.getInstance().deleteAllUserRows();
						CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
						
						EasyHomeFixApp.setCategoryNameDefaultTab(null);
						EasyHomeFixApp.setDetailsTabStatus(null);
						EasyHomeFixApp.setJobcategories(null);
						EasyHomeFixApp.setJobNotificationReview(null);
						EasyHomeFixApp.setJobNotifications(null);
						EasyHomeFixApp.setJobsubcategories(null);
						EasyHomeFixApp.setListIds(null);
						EasyHomeFixApp.setGcmRegId(null);
						AccessToken.setCurrentAccessToken(null);
						EasyHomeFixApp.setCount(null);
						//LoginManager.getInstance().logOut();
					// TODO Auto-generated method stub
						
					Intent in = new Intent(CustomerSettingsActivity.this,
							SplashScreenActivity.class);
					in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(in);
					finish();
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				/*Toast.makeText(CustomerSettingsActivity.this,
						"Please check your internet connection.",
						Toast.LENGTH_SHORT).show();*/
				Utils.showDialog(CustomerSettingsActivity.this, "Please check your internet connection.","ERROR");
			}

			
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
