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
import com.xminds.easyhomefix_merchant.Holder.JobBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class YourFixesManager implements ApiConstants {
	
	private static final String TAG = YourFixesManager.class.getName();
	private static YourFixesManager yourFixesManager;
	
	public static YourFixesManager getInstance(){
		if(yourFixesManager==null){
			yourFixesManager=new YourFixesManager();
		}
		return yourFixesManager;
		
	}
	
	public void ongoing(final Activity activity,int limit,int offset, final AsyncTaskCallBack ongoingcallback, final int requestcode){

		final Map<String, String> headers = new HashMap<String, String>();
		
		RequestParams params = new RequestParams();
		
		params.put(Ongoing.LIMIT,limit);
		params.put(Ongoing.OFFSET, offset);
		
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		//headers.put(Header.HEADER_TOKEN, "4KDpe8okvIphHCM4ujgpsUPoQGcE7wZb");
		EasyHomeMerchantAppRestClient.get(Ongoing.QUOTE_URL, params, headers, new AsyncHttpResponseHandler() {
			
			Gson gson = new Gson();
			@Override
			public void onSuccess(int statusCode,
					org.apache.http.Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				
				
				Log.e(TAG, "on onSuccess");
				JobBaseHolder baseHolder=new JobBaseHolder();
				
				String response = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
				
				Log.e(TAG, "ONGOING RESPONSE " + response);
				baseHolder = gson.fromJson(response,JobBaseHolder.class);
				Log.e(TAG, "ONGOING RESPONSE " + baseHolder.isSuccess());
				if (UtilValidate.isNotNull(ongoingcallback))

				{
					ongoingcallback.onFinish(requestcode, baseHolder);
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

						ongoingcallback
								.onFinish(requestcode, NO_INTERNET);
					}
				}

				else {
					Log.e(TAG, "on failure");
					OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
					String responseBodyelse = UtilValidate
							.getStringFromInputStream(new ByteArrayInputStream(
									responseBody));
					Log.e("","Response"+responseBodyelse);
					onFailureErrorHolder = gson.fromJson(
							responseBodyelse,
							OnFailureErrorHolder.class);
					ongoingcallback.onFinish(1, onFailureErrorHolder);
				}
			}
			
		});
	}
	
	public void completedCancelled(final Activity activity, int limit,int offset,final AsyncTaskCallBack completedCancelledcallback, final int requestcode){

		final Map<String, String> headers = new HashMap<String, String>();
		
		RequestParams params = new RequestParams();
		
		params.put(CompletedCancelled.LIMIT,limit);
		params.put(CompletedCancelled.OFFSET, offset);
		
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e(TAG, "$$$$$$?"+ CurrentlyLoggedUserDAO.getInstance().getHeader());
		//headers.put(Header.HEADER_TOKEN, "FdfAvIOSXjo7zspbUOHDK6bkM55kYmYH");
		EasyHomeMerchantAppRestClient.get(CompletedCancelled.COMP_CNCL_URL, params, headers, new AsyncHttpResponseHandler() {
			
			Gson gson = new Gson();
			@Override
			public void onSuccess(int statusCode,
					org.apache.http.Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				
				
				Log.e(TAG, "on onSuccess");
				JobBaseHolder baseHolder=new JobBaseHolder();
				
				String response = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
				
				Log.e(TAG, "COMPLETED RESPONSE " + response);
				baseHolder = gson.fromJson(response,JobBaseHolder.class);
				
				if (UtilValidate.isNotNull(completedCancelledcallback))

				{
					completedCancelledcallback.onFinish(requestcode, baseHolder);
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

						completedCancelledcallback
								.onFinish(requestcode, NO_INTERNET);
					}
				}

				else {
					Log.e(TAG, "on failure");
					OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
					String responseBodyelse = UtilValidate
							.getStringFromInputStream(new ByteArrayInputStream(
									responseBody));
					Log.e("","Response"+responseBodyelse);
					onFailureErrorHolder = gson.fromJson(
							responseBodyelse,
							OnFailureErrorHolder.class);
					completedCancelledcallback.onFinish(1, onFailureErrorHolder);
				}
			}
			
		});
	}
}
