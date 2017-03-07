package com.xminds.easyhomefix.activities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.FacebookManager;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.activities.EnterOTPActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class FacebookLoginActivity extends Activity {

	CallbackManager callbackManager;
	ProfileTracker profileTracker;
	UserTokenHolder tokenholder;
	FacebookCallBack fblogincallback;
	int requestcode = 0;
	private LogoutReceiver logoutReceiver;
	private String accessToken;
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
		setContentView(R.layout.splash_screen);
		initManager();

		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		Log.e("", "on createeeeeee");

		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.xminds.easyhomefix", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash>>>:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

		FacebookSdk.sdkInitialize(this.getApplicationContext());

		callbackManager = CallbackManager.Factory.create();
		LoginManager.getInstance().logInWithReadPermissions(
				this,
				Arrays.asList("public_profile", "user_friends", "email"));

		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						// App code

						 accessToken = loginResult.getAccessToken()
								.getToken();
						Log.e("", "accesstoken>>>" + accessToken);

						if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
							if(CurrentlyLoggedUserDAO.getInstance().getUdId() != null &&
									CurrentlyLoggedUserDAO.getInstance().getLoginType().equalsIgnoreCase("udId")){
								String udId=CurrentlyLoggedUserDAO.getInstance().getUdId();
								FacebookManager.getInstance().Login(
										FacebookLoginActivity.this, accessToken,
										"Customer", udId, fblogincallback, requestcode);
								
							}
						}else{
							FacebookManager.getInstance().Login(
									FacebookLoginActivity.this, accessToken,
									"Customer", null, fblogincallback, requestcode);
						}
						
										
						
					}

					@Override
					public void onCancel() {
						Log.e("", "in cancel");
						Utils.showDialog(FacebookLoginActivity.this, "Please check your internet connection.", "ERROR");
						// App code
					}

					@Override
					public void onError(FacebookException exception) {
						// App code
						Log.e("", "in error" + exception);
						Log.e("","accesstoken"+AccessToken.getCurrentAccessToken());
						 if (exception instanceof FacebookException) {
					            if (AccessToken.getCurrentAccessToken() != null) {
					               LoginManager.getInstance().logOut();
					                Log.e("","in error section>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					                

								if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
										if(CurrentlyLoggedUserDAO.getInstance().getUdId() != null &&
												CurrentlyLoggedUserDAO.getInstance().getLoginType().equalsIgnoreCase("udId")){
											String udId=CurrentlyLoggedUserDAO.getInstance().getUdId();
											FacebookManager.getInstance().Login(
													FacebookLoginActivity.this, accessToken,
													"Customer", udId, fblogincallback, requestcode);
											
										}
									}else{
										FacebookManager.getInstance().Login(
												FacebookLoginActivity.this, accessToken,
												"Customer", null, fblogincallback, requestcode);
									}
					                
					            }
						 }
					
					}
				});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	public class FacebookCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder fbHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(fbHolder)) {
					if (fbHolder.isSuccess()) {

						Log.e("@@@@@@@@@@@@@@@@@@@","IS SUCCESS");
						 UserDAO.getInstance().deleteAllUserRows();
						 UserDAO.getInstance().InsertUserDetails(fbHolder);
						 						 
						if(UtilValidate.isNotNull(fbHolder.getData())){
							if(UtilValidate.isNotNull(fbHolder.getData().getUser())){


								if(fbHolder.getData().getUser().getMobileVerified() != null){
									//Check whether mobile verified
									if(fbHolder.getData().getUser().getMobileVerified() == 0){
										Intent otpIntent = new Intent(FacebookLoginActivity.this,
												EnterOTPActivity.class);
										startActivity(otpIntent);
										finish();
									}else{	
										CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
										CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(fbHolder);
										CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
												fbHolder.getData().getUser().getId());
										
										Intent loginIntent = new Intent(
												FacebookLoginActivity.this,
												LandingPageActivity.class);
										startActivity(loginIntent);
										finish();
									}
								}															
							}
						}
					
					
					}

					else {

						Toast.makeText(FacebookLoginActivity.this,
								fbHolder.isSuccess() + "", Toast.LENGTH_LONG)
								.show();

					}
				}
			} else {

				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					if (UtilValidate.isNotNull(onFailureErrorHolder.getData())) {
						
						
						AccessToken.setCurrentAccessToken(null);
						//finish();
						for (ErrorMessageListHolder error : onFailureErrorHolder
								.getData()) {
							Log.e("", ">>>" + error.getMessage());
							Log.e("", ">>>" + error.getField());
							Utils.showDialogFacebook(FacebookLoginActivity.this, ""+error.getMessage(), "ERROR");

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
				Utils.showDialog(FacebookLoginActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(FacebookLoginActivity.this, ""+result, "ERROR");
			}
			
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}

	private void initManager() {
		// TODO Auto-generated method stub
		fblogincallback = new FacebookCallBack();

	}

}
