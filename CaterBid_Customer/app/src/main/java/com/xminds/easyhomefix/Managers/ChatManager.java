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
import com.xminds.easyhomefix.Holder.ChatBaseHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

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
		EasyHomeCustomerAppRestClient.get(ChatInterface.CHAT_LIST_URL+quoteid+ChatInterface.CHAT_LIST_URL_APPEND,
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
							Log.e("","in on failureeree");
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
			String quoteid,int chatTypeId, final int requestedCode) {
		// TODO Auto-generated method stub
		
		RequestParams params = new RequestParams();
		params.put(ChatStausInterface.CHAT_TEXT, chatText);
		params.put(ChatStausInterface.CHAT_TYPE_ID, chatTypeId);
		
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e("", "header>>>>>>>>>>"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeCustomerAppRestClient.post(ChatStausInterface.CHAT_STATUS_LIST_URL+quoteid+ChatStausInterface.CHAT_STATUS_LIST_URL_APPEND, params, activity, headers, new AsyncHttpResponseHandler(){

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
							Log.e(TAG, "RESPONSE AddChatDetails" + response);
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
