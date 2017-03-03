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
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Header;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Login;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Reviews;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.GoogleBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class GoogleApiManager implements ApiConstants {

	private static final String TAG = GoogleApiManager.class.getName();
	private static GoogleApiManager googleapiManager;

	/**
	 * 
	 * @return
	 */
	public static GoogleApiManager getInstance() {

		if (googleapiManager == null) {

			googleapiManager = new GoogleApiManager();
		}

		return googleapiManager;
	}

	public void getAddressList(final Activity activity,String pincode,
			final AsyncTaskCallBack googleaddresscallback, final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();

		RequestParams params = new RequestParams();
		params.put(GoogleInterface.ADDRESS, pincode);
		EasyHomeCustomerAppRestClient.get(
				GoogleInterface.GOOGLE_ADDRESS_URL, params,
				headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						GoogleBaseHolder googleBaseHolder = new GoogleBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "GOOGLE RESPONSE " + response);
						googleBaseHolder = gson.fromJson(response,
								GoogleBaseHolder.class);

						if (googleaddresscallback!=null)

						{
							googleaddresscallback.onFinish(requestcode,
									googleBaseHolder);

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

								googleaddresscallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							
						}
					}
				});

	}

	
	
	public void getAddressListfromLatlong(final Activity activity,String latitude,String longitude,
			final AsyncTaskCallBack googleaddresscallback, final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
StringBuffer s=new StringBuffer();
s.append(latitude).append(",").append(longitude);
		RequestParams params = new RequestParams();
		params.put(GoogleInterface.LATLONG, s);
		params.put(GoogleInterface.SENSOR, "true");
		EasyHomeCustomerAppRestClient.get(
				GoogleInterface.GOOGLE_ADDRESS_URL, params,
				headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						GoogleBaseHolder googleBaseHolder = new GoogleBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "GOOGLE RESPONSE " + response);
						googleBaseHolder = gson.fromJson(response,
								GoogleBaseHolder.class);

						if (googleaddresscallback!=null)

						{
							googleaddresscallback.onFinish(requestcode,
									googleBaseHolder);

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

								googleaddresscallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							
						}
					}
				});

	}

}
