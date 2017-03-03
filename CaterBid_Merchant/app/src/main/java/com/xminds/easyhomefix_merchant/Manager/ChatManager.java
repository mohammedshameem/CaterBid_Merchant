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
import com.xminds.easyhomefix_merchant.Holder.ChatBaseHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;


public class ChatManager implements ApiConstants {

	private static final String TAG = ChatManager.class.getName();
	private static ChatManager chatManager;
	private ChatBaseHolder chatBaseHolder;


	/**
	 * 
	 * @return userManager instance
	 */
	public static ChatManager getInstance() {

		if (chatManager == null) {

			chatManager = new ChatManager();
		}

		return chatManager;
	}


	
	public void getAllchats(final Activity activity,
			final AsyncTaskCallBack chatCallBack,String quoteid,
			final int requestcode) {

		RequestParams params = new RequestParams();
	
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e("", "header>>>>>>>>>>"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		params.put("limit", 200);
		params.put("offset", 0);
		EasyHomeMerchantAppRestClient.get(ChatInterface.CHAT_LIST_URL+quoteid+ChatInterface.CHAT_LIST_URL_APPEND,
				params, headers, new AsyncHttpResponseHandler() {
			
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						 chatBaseHolder = new ChatBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "RESPONSE " + response);
						chatBaseHolder = gson.fromJson(response,
								ChatBaseHolder.class);

						if (UtilValidate.isNotNull(chatCallBack)) {
							chatCallBack.onFinish(requestcode,
									chatBaseHolder);
						
							
						}

					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								chatCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							chatCallBack.onFinish(requestcode, FAILED);
						}

					}
				});

	}



	public void AddChatDetails(final Activity activity,
			final AsyncTaskCallBack addChatDetailsCallBack, String chatText,
			int chatType,String quoteid,  final int requestedCode) {
		// TODO Auto-generated method stub
		
		RequestParams params = new RequestParams();
		params.put(ChatStausInterface.CHAT_TEXT, chatText);
		params.put(ChatStausInterface.CHAT_TYPE, chatType);
		
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e("", "header>>>>>>>>>>"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeMerchantAppRestClient.post(ChatStausInterface.CHAT_STATUS_LIST_URL+quoteid+ChatStausInterface.CHAT_STATUS_LIST_URL_APPEND, params, activity, headers, new AsyncHttpResponseHandler(){

					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						
						 chatBaseHolder = new ChatBaseHolder();
							Gson gson = new Gson();
							String response = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											responseBody));
							Log.e(TAG, "RESPONSE " + response);
							chatBaseHolder = gson.fromJson(response,
									ChatBaseHolder.class);

							if (UtilValidate.isNotNull(addChatDetailsCallBack)) {
								addChatDetailsCallBack.onFinish(requestedCode,
										chatBaseHolder);
						
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

								addChatDetailsCallBack.onFinish(requestedCode,
										NO_INTERNET);
							}

						} else {

							addChatDetailsCallBack.onFinish(requestedCode, FAILED);
						}
						
					}
		
	});
	
	}
	

}
