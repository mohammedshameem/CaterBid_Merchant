package com.xminds.easyhomefix_merchant.Manager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.ChatBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.NetChecker;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix_merchant.webservice.EasyHomeMerchantAppRestClient;

public class ImageUploadManager implements ApiConstants {
	private static final String TAG = ImageUploadManager.class.getName();
	private static ImageUploadManager imageUploadManager;
	
	public static ImageUploadManager getInstance(){
		
		if(imageUploadManager==null){
			imageUploadManager=new ImageUploadManager();
		}
		
		return imageUploadManager;
	}
	
	public void uploadImages(final Activity activity, String linkTo,String linkToId,File imageFile,
			final AsyncTaskCallBack uploadImagescallback,final int requestcode,final int i){
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		params.put(UploadImageInterface.LINKTO, linkTo);
		params.put(UploadImageInterface.LINKTO_ID, linkToId);
		try {
			params.put(UploadImageInterface.IMAGE_FILE, imageFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		EasyHomeMerchantAppRestClient.post(UploadImageInterface.UPLOAD_IMAGE_URL, params, activity, new AsyncHttpResponseHandler() {
			Gson gson = new Gson();
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers,
					byte[] response) {
				JobCreationBaseHolder jobCreationBaseHolder = new JobCreationBaseHolder();
				String responseBody = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								response));
				jobCreationBaseHolder = gson.fromJson(responseBody,
						JobCreationBaseHolder.class);
				Log.e(TAG, "UPLOAD IMAGE RESPONSE " + responseBody);
				if (UtilValidate.isNotNull(uploadImagescallback))

				{
					uploadImagescallback.onFinish(requestcode,jobCreationBaseHolder);
				}
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody, Throwable error) {
				
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure>>>>");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						uploadImagescallback.onFinish(requestcode,
								NO_INTERNET);
					}

				} else {
				
					OnFailureErrorHolder onFailureErrorHolder = new OnFailureErrorHolder();
					String responseBodyelse = UtilValidate
							.getStringFromInputStream(new ByteArrayInputStream(
									responseBody));
					Log.e(TAG, "on failure in update user"+responseBodyelse);
					onFailureErrorHolder = gson.fromJson(
							responseBodyelse,
							OnFailureErrorHolder.class);
					uploadImagescallback.onFinish(1, onFailureErrorHolder);
				}
				
			}
		});
	}
	
	public void uploadProfileImage(final Activity activity, String linkTo,File imageFile,
			final AsyncTaskCallBack uploadImagecallback,final int requestcode){
		
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		params.put(UploadImageInterface.LINKTO, linkTo);
		try {
			params.put(UploadImageInterface.IMAGE_FILE, imageFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeMerchantAppRestClient.post(UploadImageInterface.UPLOAD_IMAGE_URL, params, activity, headers,
				new AsyncHttpResponseHandler() {
					
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] response) {
				Gson gson = new Gson();
				EmailSignupBaseHolder emailSignupBaseHolder = new EmailSignupBaseHolder();
				String responseBody = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(response));
				emailSignupBaseHolder = gson.fromJson(responseBody,EmailSignupBaseHolder.class);
				Log.e(TAG, "UPLOAD IMAGE RESPONSE " + responseBody);
				System.out.println(responseBody);
				if (UtilValidate.isNotNull(uploadImagecallback))

				{
					uploadImagecallback.onFinish(requestcode,emailSignupBaseHolder);
				}
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {

				
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure>>>>");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						uploadImagecallback.onFinish(requestcode,
								NO_INTERNET);
					}

				} else {
					uploadImagecallback.onFinish(requestcode,"Upload Failed");
					
				}
				
			
			}
		});
	}

	public void uploadChatImage(final Activity activity, String linkTo,
			String id, File image, final AsyncTaskCallBack uploadImagecallback,
			final int requestCode) {
		// TODO Auto-generated method stub
		
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		RequestParams params = new RequestParams();
		params.put(UploadImageInterface.LINKTO, linkTo);
		params.put(UploadImageInterface.LINKTO_ID, id);
		try {
			params.put(UploadImageInterface.IMAGE_FILE, image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyHomeMerchantAppRestClient.post(UploadImageInterface.UPLOAD_IMAGE_URL, params, activity, headers, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers,
					byte[] response) {
				// TODO Auto-generated method stub
				
				
				Gson gson = new Gson();
				ChatBaseHolder chatBaseHolder = new ChatBaseHolder();
				String responseBody = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(response));
				chatBaseHolder = gson.fromJson(responseBody,ChatBaseHolder.class);
				Log.e(TAG, "UPLOAD IMAGE RESPONSE " + responseBody);
				System.out.println(responseBody);
				if (UtilValidate.isNotNull(uploadImagecallback))

				{
					uploadImagecallback.onFinish(requestCode,chatBaseHolder);
				}
				
				
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				if (!(NetChecker.isConnected(activity))) {
					Log.e(TAG, "on failure>>>>");

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						uploadImagecallback.onFinish(requestCode,
								NO_INTERNET);
					}

				} else {
					uploadImagecallback.onFinish(requestCode,"Upload Failed");
					
				}
			}
		});
	}
}
