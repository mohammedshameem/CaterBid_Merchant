package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Header;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class ReviewManager implements ApiConstants {

	private static final String TAG = ReviewManager.class.getName();
	private static ReviewManager reviewManager;
	private ProgressDialog pDialog;
	

	public static ReviewManager getInstance() {

		if (reviewManager == null) {

			reviewManager = new ReviewManager();
		}

		return reviewManager;
	}

	public void getAllreviewjoblist(final Activity activity,
			final AsyncTaskCallBack reviewcallback, final String fixerid,final int offset,final int limit,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		Log.e("","header>>>>>>>"+CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();
		params.put(Reviews.OFFSET, offset);
		params.put(Reviews.LIMIT, limit);

		EasyHomeCustomerAppRestClient.get(Reviews.REVIEWJOB_URL + fixerid
				+ Reviews.REVIEW_END_URL, params, headers,
				new AsyncHttpResponseHandler() {
			 Gson gson;
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						EmailSignupBaseHolder reviewlistholder = new EmailSignupBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "REVIEW RESPONSE " + response);
						reviewlistholder = gson.fromJson(response,
								EmailSignupBaseHolder.class);

						if (UtilValidate.isNotNull(reviewcallback))

						{
							reviewcallback.onFinish(requestcode,
									reviewlistholder);
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
						} else {
							Log.e(TAG, "on failure in review");
							/*OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							reviewcallback.onFinish(1, onFailureErrorHolder);*/
						}

					}
				});
	}

	public void addReview(final Activity activity,  String review, int star,
			final AsyncTaskCallBack reviewcallback,String jobid, final int requestcode) {

		RequestParams params = new RequestParams();
		params.put(ReviewAddInterface.STAR,star);
		params.put(ReviewAddInterface.REVIEW,review);

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		EasyHomeCustomerAppRestClient.post(ReviewAddInterface.REVIEW_ADD_URL
				+ jobid + ReviewAddInterface.REVIEW_END_URL, params, activity,
				headers, new AsyncHttpResponseHandler() {
			 Gson gson;
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						Log.e("","in review sucess");
						
						EmailSignupBaseHolder addreviewholder = new EmailSignupBaseHolder();
						gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "ADD REVIEW  RESPONSE " + response);
						addreviewholder = gson.fromJson(response,
								EmailSignupBaseHolder.class);

						if (UtilValidate.isNotNull(reviewcallback))

						{
							reviewcallback.onFinish(requestcode,
									addreviewholder);
						}
						

					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						
						// TODO Auto-generated method stub

						

						if (!(NetChecker.isConnected(activity))) {
							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								reviewcallback.onFinish(requestcode,
										NO_INTERNET);
							}
						}
							else
							{
								OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
								String responseBodyelse = UtilValidate
										.getStringFromInputStream(new ByteArrayInputStream(
												responseBody));
								Log.e("","response"+responseBodyelse);
								
								/*onFailureErrorHolder = gson.fromJson(
										responseBodyelse,
										OnFailureErrorHolder.class);
								reviewcallback.onFinish(1, onFailureErrorHolder);*/
								
								reviewcallback.onFinish(1, responseBodyelse);
								
								
								
								
							}
						}

					

				});

	}

}
