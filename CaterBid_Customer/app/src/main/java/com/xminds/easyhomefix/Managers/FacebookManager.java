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
import com.xminds.easyhomefix.Apiconstants.ApiConstants.GoogleSignUp;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Login;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class FacebookManager implements ApiConstants {
	private static final String TAG = LoginManager.class.getName();
	private static FacebookManager facebookManager;
	private static final String DEVICE_TYPE = "android";

	public static FacebookManager getInstance() {

		if (facebookManager == null) {

			facebookManager = new FacebookManager();
		}

		return facebookManager;
	}

	public void Login(final Activity activity, String accessToken,
		String usertype, String udId, final AsyncTaskCallBack fblogincallback,
		final int requestcode) {
		RequestParams params = new RequestParams();
		final Map<String, String> headers = new HashMap<String, String>();
		params.put(Facebooklogin.ACCESSTOKEN, accessToken);
		params.put(Facebooklogin.USERTYPE, usertype);
		if(udId != null){
			params.put(Facebooklogin.UDID, udId);
			Log.e("**********************", "udId : "+udId);
		}
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(Login.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		}
		EasyHomeCustomerAppRestClient.post(Facebooklogin.LOGIN_URL, params,
				activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						Log.e(TAG, "on onSuccess login");
						EmailSignupBaseHolder fbloginholder = new EmailSignupBaseHolder();

						String loginresponse = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "FACEBOOK LOGIN RESPONSE " + loginresponse);
						fbloginholder = gson.fromJson(loginresponse,
								EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(fblogincallback))

						{
							fblogincallback.onFinish(requestcode, fbloginholder);
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

								fblogincallback
										.onFinish(requestcode, NO_INTERNET);

							}

						} else {
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "on failure Login wlse"
									+ responseBodyelse);

							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);

							fblogincallback.onFinish(1, onFailureErrorHolder);
						}
					}

				});
	}

}
