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
import com.xminds.easyhomefix_merchant.Holder.AcceptCashBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.HasAccountBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.TransactionHistoryBaseHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class TransactionManager implements ApiConstants {
	private static final String TAG = TransactionManager.class.getName();
	private static TransactionManager transactionManager;
	
	public static TransactionManager getInstance(){
		
		if(transactionManager == null){
			
			transactionManager = new TransactionManager();
			
		}
		return transactionManager;
	}
	
	public void getTransactionHistory(final Activity activity,final AsyncTaskCallBack transactionHistoryCallBack,final int requestCode){
		
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		EasyHomeMerchantAppRestClient.get(TransactionHistoryInterface.TRANSACTION_HISTORY_URL, params, headers, 
				new AsyncHttpResponseHandler() {
			Gson gson = new Gson();
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody) {
				TransactionHistoryBaseHolder transactionHistoryBaseHolder = new TransactionHistoryBaseHolder();
				String response = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
				Log.e(TAG, "NEW JOB RESPONSE " + response);
				
				transactionHistoryBaseHolder = gson.fromJson(response, TransactionHistoryBaseHolder.class);
				
				if (UtilValidate.isNotNull(transactionHistoryCallBack))

				{
					transactionHistoryCallBack.onFinish(requestCode, transactionHistoryBaseHolder);
				}
				
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody, Throwable error) {
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure SignUp");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						transactionHistoryCallBack
								.onFinish(requestCode, NO_INTERNET);
					}
				}

				else {
					Log.e(TAG, "on failure");
					transactionHistoryCallBack
					.onFinish(1, "Invalid User");
				}
			}
		});
	}
	
	public void requestPayment(final Activity activity,final AsyncTaskCallBack requestPaymentCallBack,
			String jobId,final int requestCode){
			final Map<String, String> headers = new HashMap<String, String>();
			RequestParams params = new RequestParams();
			headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
			
			EasyHomeMerchantAppRestClient.get(RequestPaymentInterface.REQUEST_PAYMENT_URL+jobId+RequestPaymentInterface.REQUEST_PAYMENT_TAIL_URL,
					params, headers, new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, org.apache.http.Header[] headers,
								byte[] responseBody) {
							// TODO Auto-generated method stub
							Gson gson = new Gson();
							EndpointResonseBaseHolder endpointResonseBaseHolder = new EndpointResonseBaseHolder();
							String response = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "NEW JOB RESPONSE " + response);
							endpointResonseBaseHolder = gson.fromJson(response, EndpointResonseBaseHolder.class);
							if(UtilValidate.isNotNull(requestPaymentCallBack)){
								requestPaymentCallBack.onFinish(requestCode, endpointResonseBaseHolder);
							}
						}
						
						@Override
						public void onFailure(int statusCode, org.apache.http.Header[] headers,
								byte[] responseBody, Throwable error) {

							if (!(NetChecker.isConnected(activity))) {
								Log.e(TAG, "on failure SignUp");

								if (!(NetChecker.isConnectedWifi(activity) && NetChecker
										.isConnectedMobile(activity))) {

									requestPaymentCallBack.onFinish(requestCode, NO_INTERNET);
								}
							}

							else {
								Log.e(TAG, "on failure");
								requestPaymentCallBack.onFinish(1, "Invalid User");
							}
						
						}
					});
			
	}
	
	public void hasAccount(final Activity activity,final AsyncTaskCallBack hasAccountCallBack,final int requestCode){
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeMerchantAppRestClient.get(HasAccountInterface.HAS_ACCOUNT_URL, params, headers, new AsyncHttpResponseHandler() {
						
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
				Gson gson = new Gson();
				String response = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
				HasAccountBaseHolder hasAccountBaseHolder = new HasAccountBaseHolder();
				Log.e(TAG, "Has Account RESPONSE " + response);
				hasAccountBaseHolder = gson.fromJson(response, HasAccountBaseHolder.class);
				if(UtilValidate.isNotNull(hasAccountCallBack)){
					
					hasAccountCallBack.onFinish(requestCode, hasAccountBaseHolder);
				}
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
				
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure SignUp");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						hasAccountCallBack.onFinish(requestCode, NO_INTERNET);
					}
				}

				else {
					Log.e(TAG, "on failure");
					hasAccountCallBack.onFinish(1, "Invalid User");
				}
				
			}
		});
	}
	
	public void acceptCash(final Activity activity,String quoteId,final AsyncTaskCallBack acceptCashCallBack,final int requestCode){
		RequestParams params = new RequestParams();
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeMerchantAppRestClient.post(CashInterface.ACCEPT_CASH_URL+quoteId+CashInterface.ACCEPT_CASH_TAIL_URL,
				params, activity, headers, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						AcceptCashBaseHolder acceptCashBaseHolder = new AcceptCashBaseHolder();
						Log.e(TAG, "Accept cash RESPONSE " + response);
						acceptCashBaseHolder = gson.fromJson(response, AcceptCashBaseHolder.class);
						if(acceptCashCallBack != null){
							acceptCashCallBack.onFinish(requestCode, acceptCashBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure SignUp");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								acceptCashCallBack.onFinish(requestCode, NO_INTERNET);
							}
						}

						else {
							Log.e(TAG, "on failure");
							acceptCashCallBack.onFinish(1, "Failed...");
						}
					}
				});
	}
	
	public void rejectCash(final Activity activity,String quoteId,final AsyncTaskCallBack rejectCashCallBack,final int requestCode){
		RequestParams params = new RequestParams();
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeMerchantAppRestClient.post(CashInterface.REJECT_CASH_URL+quoteId+CashInterface.REJECT_CASH_TAIL_URL,
				params, activity, headers, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						EndpointResonseBaseHolder endpointResonseBaseHolder = new EndpointResonseBaseHolder();
						Log.e(TAG, "Accept cash RESPONSE " + response);
						endpointResonseBaseHolder = gson.fromJson(response, EndpointResonseBaseHolder.class);
						if(rejectCashCallBack != null){
							rejectCashCallBack.onFinish(requestCode, endpointResonseBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure SignUp");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								rejectCashCallBack.onFinish(requestCode, NO_INTERNET);
							}
						}

						else {
							Log.e(TAG, "on failure");
							rejectCashCallBack.onFinish(1, "Failed...");
						}
					}
				});
	}
}
