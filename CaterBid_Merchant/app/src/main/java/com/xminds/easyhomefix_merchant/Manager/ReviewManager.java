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
import com.xminds.easyhomefix_merchant.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class ReviewManager implements ApiConstants {
	
	
	private static final String TAG = ReviewManager.class.getName();
	private static ReviewManager reviewManager;

	public static ReviewManager getInstance() {

		if (reviewManager == null) {

			reviewManager = new ReviewManager();
		}

		return reviewManager;
	}
	
	public void getfixerreview(final Activity activity,
			 final AsyncTaskCallBack reviewcallback, String userid,int offset,int limit,
			final int requestcode) {
		
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		EasyHomeMerchantAppRestClient.get(Reviews.REVIEWJOB_URL+userid+Reviews.REVIEW_END_URL,
				params, headers, new AsyncHttpResponseHandler(){
			Gson gson = new Gson();
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						EmailSignupBaseHolder quoteholder = new EmailSignupBaseHolder();
						String quoteresponseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "REVIEW RESPONSE " + quoteresponseBody);
						quoteholder = gson.fromJson(quoteresponseBody,
								EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(reviewcallback))

						{
							reviewcallback.onFinish(requestcode, quoteholder);
						}
						
					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						
						

						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure SignUp");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								reviewcallback
										.onFinish(requestcode, NO_INTERNET);
							}
						}
						else
						{
							
						
						}

						
					}
			
		});
		
	}

	public void requestReview(final Activity activity,String jobId,final AsyncTaskCallBack requestReviewcallback,
			final int requestcode) {
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		EasyHomeMerchantAppRestClient.get(RequestReviewInterface.REQUEST_REVIEW_URL+jobId+RequestReviewInterface.REQUEST_REVIEW_TAIL_URL,
				params, headers, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						EndpointResonseBaseHolder endpointResonseBaseHolder = new EndpointResonseBaseHolder();
						Log.e(TAG, "Request review RESPONSE " + response);
						endpointResonseBaseHolder = gson.fromJson(response, EndpointResonseBaseHolder.class);
						if(requestReviewcallback != null){
							requestReviewcallback.onFinish(requestcode, endpointResonseBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {

						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure SignUp");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								requestReviewcallback.onFinish(1, NO_INTERNET);
							}
						}

						else {
							Log.e(TAG, "on failure");
							requestReviewcallback.onFinish(1, "Failed...");
						}
					}
			
		});
	}
}
