package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Login;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserBaseHolder;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class UserManager implements ApiConstants {

	private static final String TAG = UserManager.class.getName();
	private static UserManager userManager;
	private static final String DEVICE_TYPE = "android";
	/**
	 * 
	 * @return userManager instance
	 */
	public static UserManager getInstance() {

		if (userManager == null) {

			userManager = new UserManager();
		}

		return userManager;
	}

	public void UpdateUser(final Activity activity, String email,
			String firstname, String lastname, String mobile,
			Integer mobilenotification, Integer emailnotification,
			String oldpassword, String newpassword, String repeatpassword,
			String companyname,
			final AsyncTaskCallBack signupcallback, final int requestcode) {
		
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
		EasyHomeCustomerAppRestClient.post(UpdateUser.UPDATE_USER_URL,
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
						Log.e("callll","callllllll"+signupcallback);
						if (UtilValidate.isNotNull(signupcallback))

						{
							signupcallback.onFinish(requestcode,
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

								signupcallback.onFinish(requestcode,
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
							signupcallback.onFinish(1, onFailureErrorHolder);
						}
					}
				});
	}

	public void SignUp(final Activity activity, String email, String password,
			String firstname, String lastname,String udId,
			final AsyncTaskCallBack signupcallback, final int requestcode) {
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		params.put(EmailSignUp.EMAIL, email);
		params.put(EmailSignUp.PASSWORD, password);
		params.put(EmailSignUp.FIRSTNAME, firstname);
		params.put(EmailSignUp.LASTNAME, lastname);
		if(udId != null){
			params.put(EmailSignUp.UDID, udId);
			Log.e("**********************", "udId : "+udId);
		}
		
		Log.e("", "header>>>>>>>>>>"
				+ CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		EasyHomeCustomerAppRestClient.post(EmailSignUp.EMAIL_SIGN_UP_URL,
				params, activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int i, org.apache.http.Header[] headers,
							byte[] response) {
						Log.e(TAG, "on onSuccess");
						EmailSignupBaseHolder emailSignupBaseHolder = new EmailSignupBaseHolder();

						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										response));
						Log.e(TAG, "SIGNUP RESPONSE " + responseBody);
						emailSignupBaseHolder = gson.fromJson(responseBody,
								EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(signupcallback))

						{
							signupcallback.onFinish(requestcode,
									emailSignupBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[]  headers,
							byte[] responseBody, Throwable error) {
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure SignUp");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								signupcallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							Log.e(TAG, "on failure");
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "on failure"+responseBodyelse);
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							signupcallback.onFinish(1, onFailureErrorHolder);
						}
					}
				});
	}

	public void googleSignIn(final Activity activity, String accessToken, String userType, String udId,
			final AsyncTaskCallBack googleSignupcallback,final int requestcode){
		
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		
		params.put(GoogleSignUp.ACCESS_TOKEN, accessToken);
		params.put(GoogleSignUp.USERTYPE, userType);
		if(udId != null){
			params.put(GoogleSignUp.UDID, udId);
			Log.e("**********************", "udId : "+udId);
		}
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(Login.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		}
		EasyHomeCustomerAppRestClient.post(GoogleSignUp.GOOGLE_SIGN_UP_URL, params, activity, 
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
	
	public void getUserDetails(final Activity activity,final AsyncTaskCallBack userDetailscallback, final int requestcode) {
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, UserDAO.getInstance().getUserDetails().getToken());
		EasyHomeCustomerAppRestClient.get(UserDetailsInterface.USER_DETAILS_URL, params, headers, 
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
