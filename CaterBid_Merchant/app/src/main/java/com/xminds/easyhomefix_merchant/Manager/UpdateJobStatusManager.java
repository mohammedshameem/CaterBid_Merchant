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
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;


public class UpdateJobStatusManager implements ApiConstants{
	
	private static final String TAG = UpdateJobStatusManager.class.getName();
	private static UpdateJobStatusManager jobstatusmanager;
	

	public static UpdateJobStatusManager getInstance() {

		if (jobstatusmanager == null) {

			jobstatusmanager = new UpdateJobStatusManager();
		}

		return jobstatusmanager;
	}
	
	public void updateJobStatus(final Activity activity,
			final AsyncTaskCallBack reviewcallback, final String jobid,
			final int requestcode){
		
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();

		EasyHomeMerchantAppRestClient.post(UpadateJobStaus.UPDATE_JOB_STATUS + jobid
				+ UpadateJobStaus.UPDATE_JOB_END_URL, params, activity, headers,
				new AsyncHttpResponseHandler(){
			Gson gson;
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						EmailSignupBaseHolder updatejobstatusholder = new EmailSignupBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "UPDATE JOB STATUS RESPONSE " + response);
						updatejobstatusholder = gson.fromJson(response,
								EmailSignupBaseHolder.class);

						if (UtilValidate.isNotNull(reviewcallback))

						{
							reviewcallback.onFinish(requestcode,
									updatejobstatusholder);
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

								reviewcallback.onFinish(requestcode,
										NO_INTERNET);
							}
						} 
						else {
							
							
							
							
							
						}
						
					}
			
		});
		
	}

}
