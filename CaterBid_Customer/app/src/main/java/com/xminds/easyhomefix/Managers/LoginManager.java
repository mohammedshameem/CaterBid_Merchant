package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class LoginManager implements ApiConstants {

	private static final String TAG = LoginManager.class.getName();
	private static LoginManager loginManager;
	private static final String DEVICE_TYPE = "android";
	/**
	 * 
	 * @return
	 */

	public static LoginManager getInstance() {

		if (loginManager == null) {

			loginManager = new LoginManager();
		}

		return loginManager;
	}

	public void Login(final Activity activity, String email, String password,
			String usertype, final AsyncTaskCallBack logincallback,
			final int requestcode) {
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		params.put(Login.EMAIL, email);
		params.put(Login.PASSWORD, password);
		params.put(Login.USERTYPE, usertype);
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(Login.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		}
		EasyHomeCustomerAppRestClient.post(Login.LOGIN_URL, params, activity,
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
							if(responseBody != null){
								OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
								String responseBodyelse = UtilValidate
										.getStringFromInputStream(new ByteArrayInputStream(
												responseBody));
								Log.e(TAG, "on failure Login wlse"+responseBodyelse);
								
								onFailureErrorHolder = gson.fromJson(
										responseBodyelse,
										OnFailureErrorHolder.class);
		
								logincallback.onFinish(1, onFailureErrorHolder);
							}else{
								logincallback
								.onFinish(requestcode, NO_INTERNET);
							}
						}

					}

				});

	}
}
