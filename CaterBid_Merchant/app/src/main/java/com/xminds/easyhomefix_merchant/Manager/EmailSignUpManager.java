package com.xminds.easyhomefix_merchant.Manager;


import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.UserBaseHolder;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class EmailSignUpManager implements ApiConstants {

	private static final String TAG = EmailSignUpManager.class.getName();
	private static EmailSignUpManager emailsignupManager;
	private static final String DEVICE_TYPE = "android";
	
	public static EmailSignUpManager getInstance() {

		if (emailsignupManager == null) {

			emailsignupManager = new EmailSignUpManager();
		}

		return emailsignupManager;
	}
	public void SignUp(final Activity activity, String email, String password,
						String firstname, String lastname,String companyName,
						final AsyncTaskCallBack signupcallback, final int requestcode){
					
					final Map<String, String> headers = new HashMap<String, String>();
					RequestParams params = new RequestParams();
					params.put(EmailSignUp.EMAIL, email);
					params.put(EmailSignUp.PASSWORD, password);
					params.put(EmailSignUp.FIRSTNAME, firstname);
					params.put(EmailSignUp.LASTNAME, lastname);
					params.put(EmailSignUp.COMPANYNAME, companyName);
					EasyHomeMerchantAppRestClient.post(EmailSignUp.EMAIL_SIGN_UP_URL, params, activity, 
							headers, new AsyncHttpResponseHandler(){
						Gson gson = new Gson();
						     
						@Override
						public void onSuccess(int statusCode,
								org.apache.http.Header[] headers, byte[] responseBody) {
							// TODO Auto-generated method stub
							
							Log.e(TAG, "on onSuccess");
							EmailSignupBaseHolder emailSignupBaseHolder = new EmailSignupBaseHolder();

							String emailresponseBody = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "SIGNUP RESPONSE " + emailresponseBody);
							emailSignupBaseHolder = gson.fromJson(emailresponseBody,
									EmailSignupBaseHolder.class);
							 
							if (UtilValidate.isNotNull(signupcallback))

							{
								signupcallback.onFinish(requestcode,
										emailSignupBaseHolder);
							}
							
						}
			
						@Override
						public void onFailure(int statusCode,
								org.apache.http.Header[] headers, byte[] responseBody,
								Throwable error) {
							// TODO Auto-generated method stub
							
							if (!(NetChecker.isConnected(activity))) {
								Log.e(TAG, "on failure SignUp");

								if (!(NetChecker.isConnectedWifi(activity) && NetChecker
										.isConnectedMobile(activity))) {

									signupcallback.onFinish(requestcode,
											NO_INTERNET);
								}
							}else{
								Log.e(TAG, "on failure");
								OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
								String responseBodyelse = UtilValidate
										.getStringFromInputStream(new ByteArrayInputStream(
												responseBody));
								onFailureErrorHolder = gson.fromJson(
										responseBodyelse,
										OnFailureErrorHolder.class);
								signupcallback.onFinish(1, onFailureErrorHolder);
							}	
							
						}
						
					});
			 	
					
				}

	

		
	

	
	public void googleSignIn(final Activity activity, String accessToken, String userType,
			final AsyncTaskCallBack googleSignupcallback,final int requestcode){
		
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		
		params.put(GoogleSignUp.ACCESS_TOKEN, accessToken);
		params.put(GoogleSignUp.USERTYPE, userType);
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(Login.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		}
		EasyHomeMerchantAppRestClient.post(GoogleSignUp.GOOGLE_SIGN_UP_URL, params, activity, 
				new AsyncHttpResponseHandler() {
					
				Gson gson = new Gson();
					
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,byte[] responseBody) {
						// TODO Auto-generated method stub
						Log.e(TAG, "on onSuccess login");
						EmailSignupBaseHolder loginemailSignupBaseHolder = new EmailSignupBaseHolder();

						String loginresponse = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "LOGIN RESPONSE " + loginresponse);
						loginemailSignupBaseHolder = gson.fromJson(loginresponse, EmailSignupBaseHolder.class);
						
						//Log.e(TAG, "LOGIN GSON "+UtilValidate.isNotNull(loginemailSignupBaseHolder));
						
						if (UtilValidate.isNotNull(googleSignupcallback))

						{
							googleSignupcallback.onFinish(requestcode,loginemailSignupBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								googleSignupcallback
										.onFinish(requestcode, NO_INTERNET);
							}

						} else {
							
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "on failure Login wlse"+responseBodyelse);
							
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							
							googleSignupcallback.onFinish(1, onFailureErrorHolder);
						}
					}
				});
	}

	
	public void Login(final Activity activity, String email, String password,
			String usertype, final AsyncTaskCallBack logincallback,
			final int requestcode) {
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		Log.e("","heder"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		params.put(Login.EMAIL, email);
		params.put(Login.PASSWORD, password);
		params.put(Login.USERTYPE, usertype);
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(Login.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		}
		EasyHomeMerchantAppRestClient.post(Login.LOGIN_URL, params, activity,
				headers, new AsyncHttpResponseHandler() {
						Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,org.apache.http.Header[]headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						Log.e(TAG, "on onSuccess login");
						EmailSignupBaseHolder loginemailSignupBaseHolder = new EmailSignupBaseHolder();

						String loginresponse = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "LOGIN RESPONSE " + loginresponse);
						loginemailSignupBaseHolder = gson.fromJson(
								loginresponse, EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(logincallback))

						{
							logincallback.onFinish(requestcode,
									loginemailSignupBaseHolder);
						}

					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								logincallback
										.onFinish(requestcode, NO_INTERNET);
							}

						} else {
							
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "on failure Login wlse"+responseBodyelse);
							
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
	
							logincallback.onFinish(1, onFailureErrorHolder);
						}

					}

				});

	}
	

	public void UpdateUser(final Activity activity, String email,
			String firstname, String lastname, String mobile,
			Integer mobilenotification, Integer emailnotification,
			String oldpassword, String newpassword, String repeatpassword,
			String companyname,
			final AsyncTaskCallBack updatecallback, final int requestcode) {
		
		RequestParams params = new RequestParams();
		if(UtilValidate.isNotNull(email))
		{
		params.put(UpdateUser.EMAIL, email);
		}
		if(UtilValidate.isNotNull(firstname))
		{params.put(UpdateUser.FIRSTNAME, firstname);
		
			
		}if(UtilValidate.isNotNull(lastname))
		{params.put(UpdateUser.LASTNAME, lastname);
		
			
		}if(UtilValidate.isNotNull(mobile))
		{
			params.put(UpdateUser.MOBILE, mobile);
			
		}if(UtilValidate.isNotNull(mobilenotification))
		{
			params.put(UpdateUser.MOBILENOTIFICATION, mobilenotification);
			
		}if(UtilValidate.isNotNull(emailnotification))
		{
			params.put(UpdateUser.EMAILNOTIFICATION, emailnotification);
			
		}if(UtilValidate.isNotNull(oldpassword))
		{
			params.put(UpdateUser.OLDPASSWORD, oldpassword);
			
		}if(UtilValidate.isNotNull(newpassword))
		{params.put(UpdateUser.NEWPASSWORD, newpassword);
		
			
		}if(UtilValidate.isNotNull(repeatpassword))
		{
			params.put(UpdateUser.REPEATPASSWORD, repeatpassword);
			
		}if(UtilValidate.isNotNull(companyname))
		{params.put(UpdateUser.COMPANYNAME, companyname);
			
		}
		
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e("", "header>>>>>>>>>>."+CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeMerchantAppRestClient.post(UpdateUser.UPDATE_USER_URL,
				params, activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int i, org.apache.http.Header[]  headers,
							byte[] response) {
						Log.e(TAG, "on onSuccess");
						EmailSignupBaseHolder emailSignupBaseHolder = new EmailSignupBaseHolder();

						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										response));
						Log.e(TAG, "SIGNUP RESPONSE " + responseBody);
						emailSignupBaseHolder = gson.fromJson(responseBody,
								EmailSignupBaseHolder.class);
						Log.e("callll","callllllll"+updatecallback);
						if (UtilValidate.isNotNull(updatecallback))

						{
							updatecallback.onFinish(requestcode,
									emailSignupBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[]  headers,
							byte[] responseBody, Throwable error) {
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								updatecallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
						
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "on failure in update user"+responseBodyelse);
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							updatecallback.onFinish(1, onFailureErrorHolder);
						}
					}
				});
	}
	
	public void getUserDetails(final Activity activity,final AsyncTaskCallBack userDetailscallback, final int requestcode) {
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		if(UserDAO.getInstance().getUserDetails().getToken() != null){
			headers.put(Header.HEADER_TOKEN, UserDAO.getInstance().getUserDetails().getToken());
		}else if(CurrentlyLoggedUserDAO.getInstance().getHeader() != null){
			headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		}
		EasyHomeMerchantAppRestClient.get(UserDetailsInterface.USER_DETAILS_URL, params, headers, 
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						Log.e(TAG, "on onSuccess UserDetails");
						UserBaseHolder userBaseHolder = new UserBaseHolder();

						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "LOGIN RESPONSE " + response);
						userBaseHolder = gson.fromJson(response, UserBaseHolder.class);
						
						if (UtilValidate.isNotNull(userDetailscallback))

						{
							userDetailscallback.onFinish(requestcode,userBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {

						Log.e(TAG, "on failure SignUp");
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								userDetailscallback.onFinish(requestcode,
										NO_INTERNET);
							}

						}else{
							userDetailscallback.onFinish(requestcode,"Failed");
						}
											
					}
				});
	}
	
}
