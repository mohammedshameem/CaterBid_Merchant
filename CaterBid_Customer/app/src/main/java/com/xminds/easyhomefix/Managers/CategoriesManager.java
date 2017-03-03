package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.preference.PreferenceActivity.Header;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.JobSubCategories;
import com.xminds.easyhomefix.Holder.JobSubCategoriesBaseHolder;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class CategoriesManager implements ApiConstants {

	private static final String TAG = CategoriesManager.class.getName();
	private static CategoriesManager categoriesManager;
	private JobCategoriesBaseHolder jobCategoriesBaseHolder;
	private JobSubCategoriesBaseHolder jobSubCategoriesBaseHolder;


	/**
	 * 
	 * @return userManager instance
	 */
	public static CategoriesManager getInstance() {

		if (categoriesManager == null) {

			categoriesManager = new CategoriesManager();
		}

		return categoriesManager;
	}


	
	public void getCategories(final Activity activity,
			final AsyncTaskCallBack jobCategoriesCallBack,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		/*params.put(JobCategories.LIMIT,limit);
		params.put(JobCategories.SESSION_KEY, sessionId);
		params.put(JobCategories.START,start);
		params.put(JobCategories.TZ,timezone);
		params.put(JobCategories.DATE, date);*/
		
		EasyHomeCustomerAppRestClient.get(JobCategories.JOB_CATEGORY_URL,
				params, headers, new AsyncHttpResponseHandler() {
			
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						JobCategoriesBaseHolder jobCategoriesBaseHolder = new JobCategoriesBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "RESPONSE " + response);
						jobCategoriesBaseHolder = gson.fromJson(response,
								JobCategoriesBaseHolder.class);

						if (UtilValidate.isNotNull(jobCategoriesCallBack)) {
							jobCategoriesCallBack.onFinish(requestcode,
									jobCategoriesBaseHolder);
						
							
						}

					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								jobCategoriesCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							jobCategoriesCallBack.onFinish(requestcode, FAILED);
						}

					}
				});

	}
	
	
	
	
	public void getSubCategories(final Activity activity,
			final AsyncTaskCallBack jobCategoriesCallBack,String catid,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		/*params.put(JobCategories.LIMIT,limit);
		params.put(JobCategories.SESSION_KEY, sessionId);
		params.put(JobCategories.START,start);
		params.put(JobCategories.TZ,timezone);
		params.put(JobCategories.DATE, date);*/
		
		EasyHomeCustomerAppRestClient.get(JobSubCategories.JOB_SUB_CATEGORY_URL+catid+JobSubCategories.JOB_SUB_CATEGORY_URL_APPEND,
				params, headers, new AsyncHttpResponseHandler() {
			
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						JobSubCategoriesBaseHolder jobSubCategoriesBaseHolder = new JobSubCategoriesBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "RESPONSE " + response);
						jobSubCategoriesBaseHolder = gson.fromJson(response,
								JobSubCategoriesBaseHolder.class);

						if (UtilValidate.isNotNull(jobCategoriesCallBack)) {
							jobCategoriesCallBack.onFinish(requestcode,
									jobSubCategoriesBaseHolder);
						
							
						}

					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								jobCategoriesCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							jobCategoriesCallBack.onFinish(requestcode, FAILED);
						}

					}
				});

	}

}
