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
import com.xminds.easyhomefix_merchant.Holder.AvailableBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class CategoryManager implements ApiConstants {
	
	private static final String TAG = CategoryManager.class.getName();
	private static CategoryManager categoryManager;
	private AvailableBaseHolder availableBaseHolder;
	
	
	public static CategoryManager getInstance(){
		
		if(categoryManager == null){
			
			categoryManager = new CategoryManager();
		}
		
		return categoryManager;
		
	}


	public void AddCategory(final Activity activity, int pos,
			final AsyncTaskCallBack addCategoryCallBack, final int requestCode) {
		// TODO Auto-generated method stub
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		
		EasyHomeMerchantAppRestClient.post(AddCategoryInterface.ADD_CATEGORY_URL+pos+AddCategoryInterface.ADD_CATEGORY_END_URL, params, activity, headers, new AsyncHttpResponseHandler(){
		Gson gson; 
			
		@Override
		public void onSuccess(int statusCode, org.apache.http.Header[] headers,
				byte[] responseBody) {
			// TODO Auto-generated method stub
			
			availableBaseHolder = new AvailableBaseHolder();
			gson = new Gson();
			
			 String addCategoryresponseBody = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
			 
			 availableBaseHolder = gson.fromJson(addCategoryresponseBody, AvailableBaseHolder.class);
			 
			 if(UtilValidate.isNotNull(addCategoryCallBack)){
				 
				 addCategoryCallBack.onFinish(requestCode,availableBaseHolder );
			 }
			 
			 
		}

		@Override
		public void onFailure(int statusCode, org.apache.http.Header[] headers,
				byte[] responseBody, Throwable error) {
			// TODO Auto-generated method stub
			
			if (!(NetChecker.isConnected(activity))) {
				Log.e(TAG, "on failure Add Category");

				if (!(NetChecker.isConnectedWifi(activity) && NetChecker
						.isConnectedMobile(activity))) {

					addCategoryCallBack
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
				addCategoryCallBack.onFinish(1, onFailureErrorHolder);
			}
		}
		
	});

		
	}


	public void RemoveCategory(final Activity activity, int pos,
			final AsyncTaskCallBack removeCategoryCallBack, final int requestCode) {
		
		// TODO Auto-generated method stub
		
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		EasyHomeMerchantAppRestClient.post(RemoveCategoryInterface.REMOVE_CATEGORY_URL+pos+RemoveCategoryInterface.REMOVE_CATEGORY_END_URL, params, activity, headers, new AsyncHttpResponseHandler() {
			Gson gson;
			
			@Override
			public void onSuccess(int statusCode,
					org.apache.http.Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				availableBaseHolder = new AvailableBaseHolder();
				
				gson = new Gson();
				
				String removeCategoryresponseBody = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
			 
				availableBaseHolder = gson.fromJson(removeCategoryresponseBody, AvailableBaseHolder.class);
				
				if(UtilValidate.isNotNull(removeCategoryCallBack)){
					
					removeCategoryCallBack.onFinish(requestCode, availableBaseHolder);
					
				}
				
			}

			@Override
			public void onFailure(int statusCode,
					org.apache.http.Header[] headers, byte[] responseBody,
					Throwable error) {
				// TODO Auto-generated method stub
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure Remove Category");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						removeCategoryCallBack
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
					removeCategoryCallBack.onFinish(1, onFailureErrorHolder);
				}
			}
			
		});
	}

	
	
}
