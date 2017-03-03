package com.xminds.easyhomefix_merchant.Manager;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyfix_merchant.constants.Quote;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class QuoteManager implements ApiConstants {

	private static final String TAG = QuoteManager.class.getName();
	private static QuoteManager quoteManager;

	public static QuoteManager getInstance() {

		if (quoteManager == null) {

			quoteManager = new QuoteManager();
		}

		return quoteManager;
	}

	public void AddQuote(final Activity activity, String quoteAmount,
			String jobId, final AsyncTaskCallBack quotecallback,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		params.put(Quote.QUOTEAMOUNT, quoteAmount);
		params.put(Quote.JOBID, jobId);
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		EasyHomeMerchantAppRestClient.post(AddQuote.QUOTE_URL,
				params, activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						Log.e(TAG, "on onSuccess");
						EmailSignupBaseHolder quoteholder = new EmailSignupBaseHolder();

						String quoteresponseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "QUOTE RESPONSE " + quoteresponseBody);
						quoteholder = gson.fromJson(quoteresponseBody,
								EmailSignupBaseHolder.class);
						if (UtilValidate.isNotNull(quotecallback))

						{
							quotecallback.onFinish(requestcode, quoteholder);
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

								quotecallback
										.onFinish(requestcode, NO_INTERNET);
							}
						}

						else {
							/*Log.e(TAG, "on failure");
							OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
							String responseBodyelse = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e("","Response"+responseBodyelse);
							onFailureErrorHolder = gson.fromJson(
									responseBodyelse,
									OnFailureErrorHolder.class);
							quotecallback.onFinish(1, onFailureErrorHolder);*/
							
							Toast.makeText(activity, "please try again..", Toast.LENGTH_LONG).show();
						}
					}
				});
	}
}
