package com.xminds.easyhomefix_merchant.Manager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class AvailableManager implements ApiConstants {

	private static AvailableManager availableManager;
	private static final String TAG =  AvailableManager.class.getName(); 
	private AvailableBaseHolder availableBaseHolder;
	
	public static AvailableManager getInstance(){
	
	if(availableManager == null){
		
		availableManager = new AvailableManager();
		
	}
	return availableManager;
	}

	public void getAllNewJobs(final Activity activity, ArrayList<String> categoryId,
			int offset, int limit, final AsyncTaskCallBack newJobCallBack, final int requestCode) {
		// TODO Auto-generated method stub
		 JSONArray categoryArray = new JSONArray(categoryId);
		 Log.e("","categoryid"+categoryId);
		 
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
			Log.e(TAG, "arrray"+categoryArray);
		params.put(AvailableNewJobsInterface.JOBCATEGORIES, categoryArray);
		
		params.put(AvailableNewJobsInterface.OFFSET, offset);
		params.put(AvailableNewJobsInterface.LIMIT, limit);
		
		EasyHomeMerchantAppRestClient.get(AvailableNewJobsInterface.AVAILABLE_NEW_JOBS_URL,params, headers, new AsyncHttpResponseHandler() {

			Gson gson = new Gson();
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				
				 availableBaseHolder = new AvailableBaseHolder();
				 Gson gson = new Gson();
				 String newJobresponseBody = UtilValidate
							.getStringFromInputStream(new ByteArrayInputStream(
									responseBody));
					Log.e(TAG, "NEW JOB RESPONSE " + newJobresponseBody);
					
					availableBaseHolder = gson.fromJson(newJobresponseBody, AvailableBaseHolder.class);
					
					if(UtilValidate.isNotNull(newJobCallBack)){
						
						newJobCallBack.onFinish(requestCode, availableBaseHolder);
					}
				 
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure SignUp");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						newJobCallBack
								.onFinish(requestCode, NO_INTERNET);
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
					newJobCallBack.onFinish(1, onFailureErrorHolder);
				}
			}
		});
	}
		
		
		
		public void getAllQuotedJobs(final Activity activity, ArrayList<String> categoryId,
				int offset, int limit, final AsyncTaskCallBack quotedCallBack, final int requestCode) {
			// TODO Auto-generated method stub
			 JSONArray categoryArray = new JSONArray(categoryId);
			 
			final Map<String, String> headers = new HashMap<String, String>();
			RequestParams params = new RequestParams();
			headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
					.getHeader());
				Log.e(TAG, "arrray"+categoryArray);
			params.put(QuotedJobsInterface.JOBCATEGORIES, categoryArray);
			
			params.put(QuotedJobsInterface.OFFSET, offset);
			params.put(QuotedJobsInterface.LIMIT, limit);
			
			EasyHomeMerchantAppRestClient.get(QuotedJobsInterface.QUOTED_JOBS_URL,params, headers, new AsyncHttpResponseHandler()
			{
				Gson gson = new Gson();

				@Override
				public void onSuccess(int statusCode,
						org.apache.http.Header[] headers, byte[] responseBody) {
					// TODO Auto-generated method stub
					
					 availableBaseHolder = new AvailableBaseHolder();
					 Gson gson = new Gson();
					 String newJobresponseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "QUOTED JOB RESPONSE " + newJobresponseBody);
						
						availableBaseHolder = gson.fromJson(newJobresponseBody, AvailableBaseHolder.class);
						
						if(UtilValidate.isNotNull(quotedCallBack)){
							
							quotedCallBack.onFinish(requestCode, availableBaseHolder);
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

							quotedCallBack
									.onFinish(requestCode, NO_INTERNET);
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
						quotedCallBack.onFinish(1, onFailureErrorHolder);
					}
					
				}
				
			});
		
		
		
		
		
	}
	

	
}
