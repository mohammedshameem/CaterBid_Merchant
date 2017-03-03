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
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants.Login;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;


public class FacebookManager implements ApiConstants {
	private static final String TAG = FacebookManager.class.getName();
	private static FacebookManager facebookManager;
	private static final String DEVICE_TYPE = "android";
	
	public static FacebookManager getInstance() {

		if (facebookManager == null) {

			facebookManager = new FacebookManager();
		}

		return facebookManager;
	}

	public void Login(final Activity activity, String accessToken,
		String usertype, final AsyncTaskCallBack fblogincallback,
		final int requestcode) {
		RequestParams params = new RequestParams();
		final Map<String, String> headers = new HashMap<String, String>();
		params.put(Facebooklogin.ACCESSTOKEN, accessToken);
		params.put(Facebooklogin.USERTYPE, usertype);
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(Login.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		}
		EasyHomeMerchantAppRestClient.post(Facebooklogin.LOGIN_URL, params,
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
							Log.e(TAG, "on failure Login else"
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
