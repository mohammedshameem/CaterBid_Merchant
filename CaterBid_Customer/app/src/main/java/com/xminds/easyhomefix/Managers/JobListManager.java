package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.Header;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.PendingJobListInterface;
import com.xminds.easyhomefix.Holder.JobCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.JobListStatusHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class JobListManager implements ApiConstants {

	private static final String TAG = JobListManager.class.getName();
	private static JobListManager jobListManager;
	private JobListStatusHolder jobListStatusHolder;
	private JobListStatusHolder ongoingjobListStatusHolder;

	/**
	 * 
	 * @return jobListManager instance
	 */
	public static JobListManager getInstance() {

		if (jobListManager == null) {

			jobListManager = new JobListManager();
		}

		return jobListManager;
	}

	public void getPendingList(final Activity activity,int offset,int limit,
			final AsyncTaskCallBack pendingCallBack, final int requestedCode) {
		// TODO Auto-generated method stub

		final Map<String, String> headers = new HashMap<String, String>();

		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		Log.e("", "header" + CurrentlyLoggedUserDAO.getInstance().getHeader());

		RequestParams params = new RequestParams();
		
		params.put(PendingJobListInterface.OFFSET, offset);
		params.put(PendingJobListInterface.LIMIT, limit);

		EasyHomeCustomerAppRestClient.get(
				PendingJobListInterface.PENDING_JOB_LIST_URL, params, headers,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						Log.e("","in success");

						jobListStatusHolder = new JobListStatusHolder();

						Gson gson = new Gson();

						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "PENDING JOB LIST RESPONSE " + response);

						jobListStatusHolder = gson.fromJson(response,
								JobListStatusHolder.class);

						if (UtilValidate.isNotNull(pendingCallBack)) {

							pendingCallBack.onFinish(requestedCode,
									jobListStatusHolder);
						}

					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						Log.e("","in failure");
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								pendingCallBack.onFinish(requestedCode,
										NO_INTERNET);
							}

						}
						else{
							
							
							
							
						}
					}

				});

	}

	public void getOngoingList(final Activity activity,int offset,int limit,
			final AsyncTaskCallBack ongoingCallBack, final int requestedCode) {

		final Map<String, String> headers = new HashMap<String, String>();

		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();
		
		params.put(OngoingJobListInterface.OFFSET, offset);
		params.put(OngoingJobListInterface.LIMIT, limit);

		EasyHomeCustomerAppRestClient.get(
				OngoingJobListInterface.ONGOING_JOB_LIST_URL, params, headers,
				new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						ongoingjobListStatusHolder = new JobListStatusHolder();

						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "ONGOING JOB LIST RESPONSE " + response);

						ongoingjobListStatusHolder = gson.fromJson(response,
								JobListStatusHolder.class);
						if (UtilValidate.isNotNull(ongoingCallBack)) {

							ongoingCallBack.onFinish(requestedCode,
									ongoingjobListStatusHolder);

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

								ongoingCallBack.onFinish(requestedCode,
										NO_INTERNET);
							} else {
								Log.e("on failureeeeeeeeeeeeeee",
										"failureeeeeeeeeeeeeeeeee");
								OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
								String responseBodyelse = UtilValidate
										.getStringFromInputStream(new ByteArrayInputStream(
												responseBody));
								Log.e(TAG, "on failure Login wlse"
										+ responseBodyelse);

								onFailureErrorHolder = gson.fromJson(
										responseBodyelse,
										OnFailureErrorHolder.class);

								ongoingCallBack.onFinish(1,
										onFailureErrorHolder);

							}
						}
					}
				});
	}

	public void getCompletedList(final Activity activity,int offset,int limit,
			final AsyncTaskCallBack completedCallBack, final int requestedCode) {

		final Map<String, String> headers = new HashMap<String, String>();

		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());

		RequestParams params = new RequestParams();
		
		params.put(CompletedJobListInterface.OFFSET, offset);
		params.put(CompletedJobListInterface.LIMIT, limit);

		EasyHomeCustomerAppRestClient.get(
				CompletedJobListInterface.COMPLETED_JOB_LIST_URL, params,
				headers, new AsyncHttpResponseHandler() {

					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						ongoingjobListStatusHolder = new JobListStatusHolder();

						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "COMPLETED AND CANCELLED JOB LIST RESPONSE "
								+ response);

						ongoingjobListStatusHolder = gson.fromJson(response,
								JobListStatusHolder.class);
						
						if (UtilValidate.isNotNull(completedCallBack)) {
							

							completedCallBack.onFinish(requestedCode,
									ongoingjobListStatusHolder);
							
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

								completedCallBack.onFinish(requestedCode,
										NO_INTERNET);

							} else {

								OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
								String responseBodyelse = UtilValidate
										.getStringFromInputStream(new ByteArrayInputStream(
												responseBody));
								Log.e(TAG, "on failure Login wlse"
										+ responseBodyelse);

								onFailureErrorHolder = gson.fromJson(
										responseBodyelse,
										OnFailureErrorHolder.class);

								completedCallBack.onFinish(1,
										onFailureErrorHolder);
							}

						}

					}
				});
	}

}
