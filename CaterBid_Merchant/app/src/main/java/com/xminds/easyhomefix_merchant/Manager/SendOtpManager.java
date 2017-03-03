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
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;


public class SendOtpManager implements ApiConstants {

	private static final String TAG = SendOtpManager.class.getName();
	private static SendOtpManager enterotpManager;

	public static SendOtpManager getInstance() {

		if (enterotpManager == null) {

			enterotpManager = new SendOtpManager();
		}

		return enterotpManager;
	}

	public void sendotp(final Activity activity, String prefix, Boolean retry, String mobile,
			final AsyncTaskCallBack sendotpcallback, final int requestcode) {
		

		RequestParams params = new RequestParams();
		params.put(SendOtp.PREFIX, prefix);
		params.put(SendOtp.MOBILE, mobile);
		params.put(SendOtp.RETRY, retry);
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, UserDAO.getInstance().getUserDetails().getToken());
		EasyHomeMerchantAppRestClient.post(SendOtp.UPDATE_USER_URL, params,
				activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						Log.e(TAG, "on onSuccess");
						EmailSignupBaseHolder sendotpbaseholder = new EmailSignupBaseHolder();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "SEND OTP RESPONSE " + response);
						sendotpbaseholder = gson.fromJson(response,
								EmailSignupBaseHolder.class);
						 Log.e("before success","before"+sendotpcallback);
						 
						if (UtilValidate.isNotNull(sendotpcallback))

						{
							sendotpcallback.onFinish(requestcode,
									sendotpbaseholder);
							 Log.e("in success","in sucess");
							
						}
						 Log.e("aftrer success","after sucess");
						
					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								sendotpcallback.onFinish(requestcode,
										NO_INTERNET);
							}
						} else {
							sendotpcallback.onFinish(1, "Failed....");
						}

					}

				});

	}

	public void verifyotp(final Activity activity, String otp,
			final AsyncTaskCallBack verifyotppcallback, final int requestcode) {

		RequestParams params = new RequestParams();
		params.put(Verifyotp.OTP, otp);
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, UserDAO.getInstance().getUserDetails().getToken());
		EasyHomeMerchantAppRestClient.post(Verifyotp.VERIFY_URL, params,
				activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						Log.e(TAG, "on onSuccess");
						EmailSignupBaseHolder verifyotpBaseHolder = new EmailSignupBaseHolder();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "VERIFY OTP RESPONSE " + response);
						verifyotpBaseHolder = gson.fromJson(response,
								EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(verifyotppcallback))

						{
							verifyotppcallback.onFinish(requestcode,
									verifyotpBaseHolder);
						}

					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								verifyotppcallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
					
							Log.e(TAG, "on failure in update user");
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							verifyotppcallback.onFinish(1, onFailureErrorHolder);
						}

					}

				});

	}
}
