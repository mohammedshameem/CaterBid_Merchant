package com.xminds.easyhomefix_merchant.activities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix_merchant.Holder.FixerJobCategories;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.Manager.FacebookManager;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class FacebookLoginActivity extends Activity {

	CallbackManager callbackManager;
	ProfileTracker profileTracker;
	UserTokenHolder tokenholder;
	FacebookCallBack fblogincallback;
	int requestcode = 0;
	private List<FixerJobCategories> fixerJobCategoriesList = new ArrayList<FixerJobCategories>();
	private ArrayList<String>categoryID = new ArrayList<String>();
	private String accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		initManager();

		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.xminds.easyhomefix_merchant",
					PackageManager.GET_SIGNATURES);
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
				Arrays.asList("public_profile", "user_friends", "email",
						"basic_info"));

		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						// App code

						 accessToken = loginResult.getAccessToken()
								.getToken();
						Log.e("", "accesstoken>>>" + accessToken);
						FacebookManager.getInstance().Login(
								FacebookLoginActivity.this, accessToken,
								"Fixer", fblogincallback, requestcode);
					
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
						 if (exception instanceof FacebookException) {
					            if (AccessToken.getCurrentAccessToken() != null) {
					                LoginManager.getInstance().logOut();
					                
					                FacebookManager.getInstance().Login(
											FacebookLoginActivity.this, accessToken,
											"Fixer", fblogincallback, requestcode);
					            }
					                
					                
					            }
					}
				});
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
						
						UserDAO.getInstance().deleteAllUserRows();
						UserDAO.getInstance().InsertUserDetails(fbHolder);			

						if (UtilValidate.isNotNull(fbHolder
								.getData().getToken())) {
							EasyHomeFixApp.setUserHeader(null);
							EasyHomeFixApp.setUserHeader(fbHolder
									.getData().getToken());
						}
						
						fixerJobCategoriesList.clear();
						
						if(UtilValidate.isNotNull(fbHolder.getData())){
							if(UtilValidate.isNotNull(fbHolder.getData().getUser())){
								//Getting Job Categories
								if(UtilValidate.isNotNull(fbHolder.getData().getUser().getFixerJobCategories())&&
										(UtilValidate.isNotEmpty(fbHolder.getData().getUser().getFixerJobCategories()))){
									
									fixerJobCategoriesList = fbHolder.getData().getUser().getFixerJobCategories();
									Log.e("", ">>>>"+fixerJobCategoriesList.size());
									UserDAO.getInstance().deleteAllUserRows();
									UserDAO.getInstance().InsertUserDetails(
											fbHolder);
									categoryID.clear();
									//EasyHomeFixApp.setCategoryIds(null);
									for(int i=0;i<fbHolder.getData().getUser().getFixerJobCategories().size();i++){
										
										categoryID.add(fbHolder.getData().getUser().getFixerJobCategories().get(i).getId());
										
									}
																		
								}																
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
										if(categoryID != null && UtilValidate.isNotNull(categoryID)){
											Log.d("", "LOGGING INSIDE AVAILABLE LIST");
											Intent loginIntent = new Intent(
													FacebookLoginActivity.this,
													TopBottomFragmentTabHostActivity.class);
											loginIntent.putExtra("CATEGORY_ID",categoryID );
											startActivity(loginIntent);
										}else{
											Log.d("", "Log in first page ");
											Intent Intent = new Intent(
													FacebookLoginActivity.this,
													FirstLoginPage.class);
											startActivity(Intent);
										}
									}
								
								}
							}
						
						}

					}

					else {

						Toast.makeText(FacebookLoginActivity.this,
								fbHolder.isSuccess() + "", Toast.LENGTH_LONG)
								.show();
						finish();
					}
				}
			} else {

				OnFailureErrorHolder loginonFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(loginonFailureErrorHolder)) {
					if (UtilValidate.isNotNull(loginonFailureErrorHolder
							.getData())) {
						
						AccessToken.setCurrentAccessToken(null);

						for (ErrorMessageListHolder error : loginonFailureErrorHolder
								.getData()) {
							Utils.showDialogFacebook(FacebookLoginActivity.this, ""+error.getMessage(), "EasyHomeFix");
							//finish();
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

	}

	private void initManager() {
		// TODO Auto-generated method stub
		fblogincallback = new FacebookCallBack();

	}

}
