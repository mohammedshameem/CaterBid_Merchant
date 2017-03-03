package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.EmailSignUp;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Login;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

import android.app.Activity;
import android.util.Log;

public class DummyUserManager implements ApiConstants {
	private static final String TAG = DummyUserManager.class.getName();
	private static DummyUserManager dummyUserManager;
	private static final String DEVICE_TYPE = "android";
	public static DummyUserManager getInstance(){
		if(dummyUserManager == null){
			dummyUserManager = new DummyUserManager();
		}
		return dummyUserManager;
	}
	
	public void createDummyUser(final Activity activity, String udId, String firstName,
			String lastName,final AsyncTaskCallBack dummyUserCallBack, final int requestcode){
		RequestParams params = new RequestParams();
		params.put(DummyUserInterface.UDID,udId);
		params.put(DummyUserInterface.FIRSTNAME, firstName);
		params.put(DummyUserInterface.LASTNAME, lastName);
		
		if(EasyHomeFixApp.getGcmRegId() != null){
			params.put(DummyUserInterface.DEVICE_TOKEN,EasyHomeFixApp.getGcmRegId());
			params.put(DummyUserInterface.DEVICE_TYPE,DEVICE_TYPE);
		}
		
		EasyHomeCustomerAppRestClient.post(DummyUserInterface.DUMMY_USER_SIGN_UP_URL, params, activity,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] response) {
						Gson gson = new Gson();
						EmailSignupBaseHolder emailSignupBaseHolder = new EmailSignupBaseHolder();

						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										response));
						Log.e(TAG, "SIGNUP RESPONSE " + responseBody);
						emailSignupBaseHolder = gson.fromJson(responseBody,
								EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(dummyUserCallBack))

						{
							dummyUserCallBack.onFinish(requestcode,
									emailSignupBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {
						Log.e(TAG, "on failure SignUp");
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								dummyUserCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						}else{
							dummyUserCallBack.onFinish(requestcode,"Failed");
						}
					}
			
		});
	}
	
	public void checkDummyUser(final Activity activity, String udId,
			final AsyncTaskCallBack checkDummyUserCallBack, final int requestcode){
		RequestParams params = new RequestParams();
		final Map<String, String> headers = new HashMap<String, String>();
		params.put(DummyUserInterface.UDID,udId);
		EasyHomeCustomerAppRestClient.get(DummyUserInterface.CHECK_DUMMY_USER_URL, params, headers,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] response) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						EndpointResonseBaseHolder endpointResonseBaseHolder = new EndpointResonseBaseHolder();
						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										response));
						Log.e(TAG, "SIGNUP RESPONSE " + responseBody);
						endpointResonseBaseHolder = gson.fromJson(responseBody, EndpointResonseBaseHolder.class);
						if(UtilValidate.isNotNull(checkDummyUserCallBack)){
							checkDummyUserCallBack.onFinish(requestcode, endpointResonseBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								checkDummyUserCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						}else{
							checkDummyUserCallBack.onFinish(requestcode,"Failed");
						}
					}			
		});
	}
	
}
