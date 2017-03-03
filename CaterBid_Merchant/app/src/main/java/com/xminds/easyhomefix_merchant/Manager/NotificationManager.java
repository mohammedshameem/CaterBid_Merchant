package com.xminds.easyhomefix_merchant.Manager;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;


public class NotificationManager implements ApiConstants {
	
	private static final String TAG = NotificationManager.class.getName();
	private static NotificationManager notificationManager;

	/**
	 * 
	 * @return
	 */
	public static NotificationManager getInstance() {

		if (notificationManager == null) {

			notificationManager = new NotificationManager();
		}

		return notificationManager;
	}
	
	public void getNotificationList(final Activity activity,int limit,int offset,
			final AsyncTaskCallBack notificationcallback, final int requestcode)
	{

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();
		
		params.put(NotificationListInterface.LIMIT, limit);
		params.put(NotificationListInterface.OFFSET, offset);
		
		EasyHomeMerchantAppRestClient.get(
				NotificationListInterface.NOTIFICATION_LIST_URL, params,
				headers, new AsyncHttpResponseHandler() 
				{
					Gson gson = new Gson();
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						EmailSignupBaseHolder notificationListHolder = new EmailSignupBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "NOTIFICATION RESPONSE " + response);
						notificationListHolder = gson.fromJson(response,
								EmailSignupBaseHolder.class);

						if (UtilValidate.isNotNull(notificationcallback))

						{
							notificationcallback.onFinish(requestcode,
									notificationListHolder);

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

								notificationcallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							Log.e(TAG, "on failure in review");
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
						/*	String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));*/
							
							/*onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							notificationcallback.onFinish(1,
									onFailureErrorHolder);*/
						}
					}
				});

		
	}
	
	public void updateNotification(final Activity activity,
			final AsyncTaskCallBack updatenotificationcallback,String notificationid,final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();
		EasyHomeMerchantAppRestClient.post(
				UpdateNotificationInterface.UPDATE_NOTIFICATION_URL+notificationid+UpdateNotificationInterface.UPDATE_NOTIFICATION_END_URL, params,
				activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						EmailSignupBaseHolder notificationListHolder = new EmailSignupBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "UPDATE NOTIFICATION RESPONSE " + response);
						notificationListHolder = gson.fromJson(response,
								EmailSignupBaseHolder.class);

						if (UtilValidate.isNotNull(updatenotificationcallback))
							
						{
							updatenotificationcallback.onFinish(requestcode,
									notificationListHolder);

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

								updatenotificationcallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							Log.e(TAG, "on failure in review");
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							updatenotificationcallback.onFinish(1,
									onFailureErrorHolder);
						}
					}
				});

	}

	public void jobListItemObject(final Activity activity,
			String id,
			final AsyncTaskCallBack notificationListItemCallBack,
			final int requestcode) {
		// TODO Auto-generated method stub
		


		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();
		
		//params.put(NotificationListItemInterface.JOB_ID,id);
		
		EasyHomeMerchantAppRestClient.get(
				jobItemInterface.JOB_ITEM_URL+id, params,
				headers, new AsyncHttpResponseHandler() 
				{
					Gson gson = new Gson();
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						EmailSignupBaseHolder notificationListHolder = new EmailSignupBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "NOTIFICATION LIST ITEM RESPONSE");
						Log.e(TAG, "NOTIFICATION LIST ITEM RESPONSE " + response);
						notificationListHolder = gson.fromJson(response,
								EmailSignupBaseHolder.class);

						if (UtilValidate.isNotNull(notificationListItemCallBack))

						{
							notificationListItemCallBack.onFinish(requestcode,
									notificationListHolder);

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

								notificationListItemCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							Log.e(TAG, "on failure in review");
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
						/*	String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));*/
							
							/*onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							notificationcallback.onFinish(1,
									onFailureErrorHolder);*/
						}
					}
				});

		
	
		
	}
	
	
	

}
