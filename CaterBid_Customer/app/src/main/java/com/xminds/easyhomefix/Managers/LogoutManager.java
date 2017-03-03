package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.UserBaseHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

import android.app.Activity;
import android.util.Log;

public class LogoutManager implements ApiConstants {
	private static final String TAG = LogoutManager.class.getName();
	private static LogoutManager logoutManager;
	private static final String DEVICE_TYPE = "android";

	public static LogoutManager getInstance(){
		if(logoutManager == null){
			logoutManager = new LogoutManager();
		}
		return logoutManager;
	}
	
	public void logout(final Activity activity,final AsyncTaskCallBack logoutcallback,
			final int requestcode){
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		RequestParams params = new RequestParams();
		params.put(Login.DEVICE_TOKEN,CurrentlyLoggedUserDAO.getInstance().getDeviceToken());
		params.put(Login.DEVICE_TYPE,DEVICE_TYPE);
		EasyHomeCustomerAppRestClient.post(LogoutInterface.LOGOUT_URL, params, activity, headers, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
				Gson gson = new Gson();
				UserBaseHolder userBaseHolder = new UserBaseHolder();

				String response = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
				Log.e(TAG, "LOGIN RESPONSE " + response);
				userBaseHolder = gson.fromJson(response, UserBaseHolder.class);
				
				if (UtilValidate.isNotNull(logoutcallback))

				{
					logoutcallback.onFinish(requestcode,userBaseHolder);
				}
			}

			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
					Throwable error) {


				Log.e(TAG, "on failure SignUp");
				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						logoutcallback.onFinish(requestcode,
								NO_INTERNET);
					}

				}else{
					logoutcallback.onFinish(requestcode,"Failed");
				}
									
			
			}});
	}
	
}
