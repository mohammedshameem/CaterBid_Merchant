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
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.PaymentBaseHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class PaymentManager implements ApiConstants {
	private static final String TAG = PaymentManager.class.getName();
	private static PaymentManager paymentManager;
	
	public static PaymentManager getInstance() {
		if(paymentManager==null){
			paymentManager = new PaymentManager();
		}
		return paymentManager;
	}
	
	public void payPalPayment(final Activity activity,final AsyncTaskCallBack payPalPaymentCallback, String quoteId,String paymentStatus,
			String paymentResponse,String paymentResponseCode,final int requestcode){
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance()
				.getHeader());
		RequestParams params = new RequestParams();
		params.put(PayPalPaymentInterface.QUOTE_ID, quoteId);
		params.put(PayPalPaymentInterface.PAYMENT_STATUS, paymentStatus);
		params.put(PayPalPaymentInterface.PAYMENT_RESPONSE, paymentResponse);
		params.put(PayPalPaymentInterface.PAYMENT_RESPONSE_CODE, paymentResponseCode);
		Log.e(TAG,PayPalPaymentInterface.REQUEST_PAYMENT_URL);
		
		EasyHomeCustomerAppRestClient.post(PayPalPaymentInterface.REQUEST_PAYMENT_URL,params, activity, headers, new AsyncHttpResponseHandler() {
					
			Gson gson = new Gson();
			
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "REQUEST PAYMENT RESPONSE : " + response);
						PaymentBaseHolder paymentBaseHolder = new PaymentBaseHolder();
						paymentBaseHolder = gson.fromJson(response, PaymentBaseHolder.class);
						
						Log.e("4444444444444444444444444444444444", "Check"+UtilValidate.isNotNull(payPalPaymentCallback)+
								UtilValidate.isNotNull(paymentBaseHolder));
						if(UtilValidate.isNotNull(payPalPaymentCallback)){
							payPalPaymentCallback.onFinish(requestcode, paymentBaseHolder);
						}	
					}
					
					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "REQUEST PAYMENT RESPONSE : " + response);
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								payPalPaymentCallback.onFinish(requestcode,
										NO_INTERNET);
							}
						}

						else {

							payPalPaymentCallback.onFinish(requestcode, FAILED);
						}

					}
					
				});
	}
	
	public void cashPayment(final Activity activity,final AsyncTaskCallBack cashPaymentCallback, String quoteId,
			String paymentStatus,final int requestcode){
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		RequestParams params = new RequestParams();
		params.put(CashPaymentInterface.PAYMENT_STATUS, paymentStatus);
		params.put(CashPaymentInterface.QUOTE_ID, quoteId);
		
		EasyHomeCustomerAppRestClient.post(CashPaymentInterface.CASH_PAYMENT_URL, params, activity, headers,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "REQUEST PAYMENT RESPONSE : " + response);
						PaymentBaseHolder paymentBaseHolder = new PaymentBaseHolder();
						paymentBaseHolder = gson.fromJson(response, PaymentBaseHolder.class);
						
						if(UtilValidate.isNotNull(cashPaymentCallback)){
							cashPaymentCallback.onFinish(requestcode, paymentBaseHolder);
						}	
						
					}
					
					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						if(responseBody!=null)
						{
							String response = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "REQUEST PAYMENT RESPONSE : " + response);
						}
						
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								cashPaymentCallback.onFinish(requestcode,
										NO_INTERNET);
							}
						}

						else {

							cashPaymentCallback.onFinish(requestcode, FAILED);
						}
						
					}
				});
		
		
	}
}
