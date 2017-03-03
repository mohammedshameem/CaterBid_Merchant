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
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.QuotesBaseHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class QuotesManager implements ApiConstants {

	private static final String TAG = QuotesManager.class.getName();
	private static QuotesManager quotesManager;

	/**
	 * 
	 * @return userManager instance
	 */
	public static QuotesManager getInstance() {

		if (quotesManager == null) {

			quotesManager = new QuotesManager();
		}

		return quotesManager;
	}

	public void getAllQuotes(final Activity activity,
			final AsyncTaskCallBack quotesCallback, String jobId,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();

		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		Log.e("", "header>>>>>>>>>>"
				+ CurrentlyLoggedUserDAO.getInstance().getHeader());
		RequestParams params = new RequestParams();

		EasyHomeCustomerAppRestClient.get(Quotes.QUOTES_LIST_URL + jobId
				+ Quotes.QUOTES_END, params, headers,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						QuotesBaseHolder quotesBaseHolder = new QuotesBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "RESPONSE " + response);
						quotesBaseHolder = gson.fromJson(response,
								QuotesBaseHolder.class);

						if (UtilValidate.isNotNull(quotesCallback)) {
							quotesCallback.onFinish(requestcode,
									quotesBaseHolder);

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

								quotesCallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							quotesCallback.onFinish(requestcode, FAILED);
						}

					}
				});

	}

	public void AcceptQuote(final Activity activity,
			final AsyncTaskCallBack AcceptquotesCallback, String quoteId,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		// Log.e("",
		// "header>>>>>>>>>>"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		RequestParams params = new RequestParams();

		EasyHomeCustomerAppRestClient.post(AcceptQuote.QUOTES_ACCEPT_URL
				+ quoteId + AcceptQuote.QUOTES_ACCEPT_END, params, activity,
				headers, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub

						QuotesBaseHolder acceptquoteholder = new QuotesBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "ACCEPTQUOTE RESPONSE " + response);
						acceptquoteholder = gson.fromJson(response,
								QuotesBaseHolder.class);
						

						if (UtilValidate.isNotNull(AcceptquotesCallback)) {
							AcceptquotesCallback.onFinish(requestcode,
									acceptquoteholder);
						

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

								AcceptquotesCallback.onFinish(requestcode,
										NO_INTERNET);
							}
						}

						else {

							AcceptquotesCallback.onFinish(requestcode, FAILED);
						}

					}

				});

	}

	public void RejectQuote(final Activity activity,String rejectCategory,
			final AsyncTaskCallBack rejectquotesCallback, String quoteId,
			final int requestcode) {
		

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		
		// Log.e("",
		// "header>>>>>>>>>>"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		RequestParams params = new RequestParams();
		
		params.put(RejectQuoteInterface.REJECTCATEGORY, rejectCategory);

		EasyHomeCustomerAppRestClient.post(
				RejectQuoteInterface.QUOTES_REJECT_URL + quoteId
						+ RejectQuoteInterface.QUOTES_REJECT_END, params,
				activity, headers, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						Log.e("in reject Quote","rejecttttttttt");

						QuotesBaseHolder acceptquoteholder = new QuotesBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "REJECTQUOTE RESPONSE " + response);
						acceptquoteholder = gson.fromJson(response,
								QuotesBaseHolder.class);

						if (UtilValidate.isNotNull(rejectquotesCallback)) {
							rejectquotesCallback.onFinish(requestcode,
									acceptquoteholder);

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

								rejectquotesCallback.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {
							rejectquotesCallback.onFinish(requestcode, FAILED);
						}
					}

				});

	}
}
