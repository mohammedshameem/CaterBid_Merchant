package com.xminds.easyhomefix.Managers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.preference.PreferenceActivity.Header;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Apiconstants.ApiConstants.UpdateUser;
import com.xminds.easyhomefix.Holder.AddressBaseHolder;
import com.xminds.easyhomefix.Holder.AddressBaseHolderError;
import com.xminds.easyhomefix.Holder.AddressListBaseHolder;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobSubCategories;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.NetChecker;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;
import com.xminds.easyhomefix.webservice.EasyHomeCustomerAppRestClient;

public class JobCreationManager implements ApiConstants {

	private static final String TAG = JobCreationManager.class.getName();
	private static JobCreationManager jobCreationManager;
	private JobCategoriesBaseHolder jobCategoriesBaseHolder;


	/**
	 * 
	 * @return userManager instance
	 */
	public static JobCreationManager getInstance() {

		if (jobCreationManager == null) {

			jobCreationManager = new JobCreationManager();
		}

		return jobCreationManager;
	}


	public void jobCreation(final Activity activity,String postalcode,String jobDetails,String blocknumber,
			String jobTypeId,String jobDate,String floor,String unit,
			final AsyncTaskCallBack jobCreationCallBack,
			final int requestcode) {

		RequestParams params = new RequestParams();
		params.put(JobCreation.JOBDATE,jobDate);
		params.put(JobCreation.JOBDETAILS, jobDetails);
		params.put(JobCreation.JOBTYPEID,jobTypeId);
		params.put(JobCreation.POSTALCODE,postalcode);
		params.put(JobCreation.BLOCKNUMBER, blocknumber);
		params.put(JobCreation.JOBTYPE, "Customer");
		params.put(JobCreation.FLOOR, floor);
		params.put(JobCreation.UNIT, unit);
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e("", "header>>>>>>>>>>"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		Log.e("", "floor>>>>>>>>>>"+floor);
		Log.e("", "unit>>>>>>>>>>"+unit);
		
		EasyHomeCustomerAppRestClient.post(JobCreation.JOB_CREATION_URL,
				params, activity, headers, new AsyncHttpResponseHandler() {
					Gson gson = new Gson();

					@Override
					public void onSuccess(int i, org.apache.http.Header[]  headers,
							byte[] response) {
						Log.e(TAG, "on onSuccess");
						JobCreationBaseHolder jobCreationBaseHolder = new JobCreationBaseHolder();

						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										response));
						Log.e(TAG, "SIGNUP RESPONSE " + responseBody);
						jobCreationBaseHolder = gson.fromJson(responseBody,
								JobCreationBaseHolder.class);
						if (UtilValidate.isNotNull(jobCreationCallBack))

						{
							jobCreationCallBack.onFinish(requestcode,
									jobCreationBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[]  headers,
							byte[] responseBody, Throwable error) {
						if (!(NetChecker.isConnected(activity))) {
							Log.e(TAG, "on failure>>>>");

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								jobCreationCallBack.onFinish(requestcode,
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
							jobCreationCallBack.onFinish(1, onFailureErrorHolder);
						}
					}
				});

	}
	
	
	
	
	public void getSubCategories(final Activity activity,
			final AsyncTaskCallBack jobCategoriesCallBack,
			final int requestcode) {

		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		/*params.put(JobCategories.LIMIT,limit);
		params.put(JobCategories.SESSION_KEY, sessionId);
		params.put(JobCategories.START,start);
		params.put(JobCategories.TZ,timezone);
		params.put(JobCategories.DATE, date);*/
		
		EasyHomeCustomerAppRestClient.get(JobSubCategories.JOB_SUB_CATEGORY_URL,
				params, headers, new AsyncHttpResponseHandler() {
			
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						JobCategoriesBaseHolder jobCategoriesBaseHolder = new JobCategoriesBaseHolder();
						Gson gson = new Gson();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e(TAG, "RESPONSE " + response);
						jobCategoriesBaseHolder = gson.fromJson(response,
								JobCategoriesBaseHolder.class);

						if (UtilValidate.isNotNull(jobCategoriesCallBack)) {
							jobCategoriesCallBack.onFinish(requestcode,
									jobCategoriesBaseHolder);
						
							
						}

					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								jobCategoriesCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							jobCategoriesCallBack.onFinish(requestcode, FAILED);
						}

					}
				});

	}

	public void editFixDetails(final Activity activity,String jobId,String postalCode,String floor,String unit,
			String jobDetails,String jobTypeId,String jobDate,String blocknumber,final AsyncTaskCallBack editFixDetailsCallBack,
			final int requestcode){
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		params.put(EditFixDetails.JOBID, jobId);
		if(UtilValidate.isNotEmpty(floor)){
			params.put(EditFixDetails.FLOOR, floor);
		}
		if(UtilValidate.isNotEmpty(unit)){
			params.put(EditFixDetails.UNIT, unit);
		}
		if(UtilValidate.isNotEmpty(postalCode)){
			params.put(EditFixDetails.POSTAL_CODE, postalCode);
		}
		if(UtilValidate.isNotEmpty(jobDetails)){
			params.put(EditFixDetails.JOB_DETAILS, jobDetails);
		}
		if(UtilValidate.isNotEmpty(jobTypeId)){
			params.put(EditFixDetails.JOB_TYPEID, jobTypeId);
		}
		if(UtilValidate.isNotEmpty(jobDate)){
			params.put(EditFixDetails.JOB_DATE, jobDate);
		}
		params.put(JobCreation.BLOCKNUMBER, blocknumber);
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		EasyHomeCustomerAppRestClient.post(EditFixDetails.EDIT_FIX_DETAILS_URL, params, activity, headers, new AsyncHttpResponseHandler() {
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
				
				Log.e("*******************************************", "Edit Details Response : "+responseBody);
				if(UtilValidate.isNotNull(editFixDetailsCallBack)){
					editFixDetailsCallBack.onFinish(requestcode, jobCreationBaseHolder);
				}
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				Log.e("*******************************************", "Edit Details Response Failure"+statusCode);
				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						editFixDetailsCallBack.onFinish(requestcode,
								NO_INTERNET);
					}

				} else {

					editFixDetailsCallBack.onFinish(requestcode, FAILED);
				}

			}
			
		});
	}
	
	public void completeFix(final Activity activity,String jobId,final AsyncTaskCallBack completeFixCallBack,
			final int requestcode){
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		EasyHomeCustomerAppRestClient.post(CompleteFix.COMPLETE_FIX_URL+jobId+CompleteFix.COMPLETE_FIX_URL_TAIL, params, activity, headers,
				new AsyncHttpResponseHandler() {
					Gson gson = new Gson();
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						EndpointResonseBaseHolder endpointResonseBaseHolder = new EndpointResonseBaseHolder();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e("*******************************************", "Completed : "+response);
						endpointResonseBaseHolder = gson.fromJson(response, EndpointResonseBaseHolder.class);
						if(UtilValidate.isNotNull(completeFixCallBack)){
							completeFixCallBack.onFinish(requestcode, endpointResonseBaseHolder);
						}
					}
					
					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						Log.e("*******************************************", "Campete Failure"+statusCode);
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								completeFixCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							completeFixCallBack.onFinish(requestcode, FAILED);
						}

					}
				});
	}
	
	public void cancelFix(final Activity activity,String jobId,final AsyncTaskCallBack cancelFixCallBack,
			final int requestcode){
		final Map<String, String> headers = new HashMap<String, String>();
		RequestParams params = new RequestParams();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		
		EasyHomeCustomerAppRestClient.post(CancelFix.CANCEL_FIX_URL+jobId+CancelFix.CANCEL_FIX_URL_TAIL, params, activity, headers,
				new AsyncHttpResponseHandler() {
					Gson gson = new Gson();
					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody) {
						EndpointResonseBaseHolder endpointResonseBaseHolder = new EndpointResonseBaseHolder();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e("*******************************************", "Cancelled : "+response);
						endpointResonseBaseHolder = gson.fromJson(response, EndpointResonseBaseHolder.class);
						if(UtilValidate.isNotNull(cancelFixCallBack)){
							cancelFixCallBack.onFinish(requestcode, endpointResonseBaseHolder);
						}
					}
					
					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers,
							byte[] responseBody, Throwable error) {
						Log.e("*******************************************", "Cancelled Failure"+statusCode);
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								cancelFixCallBack.onFinish(requestcode,
										NO_INTERNET);
							}

						} else {

							cancelFixCallBack.onFinish(requestcode, FAILED);
						}

					}
				});
	}
	
	public void getAddressFromServer(final Activity activity,String postalCode,final AsyncTaskCallBack getAddressCallBack,
			final int requestcode){
		RequestParams params = new RequestParams();
		params.put(JobCreation.POSTALCODE,postalCode);
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeCustomerAppRestClient.get(JobCreation.GET_ADDRESS_URL, params, headers, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
				Gson gson = new Gson();
				AddressBaseHolder addressBaseHolder = new AddressBaseHolder();
				String response = UtilValidate
						.getStringFromInputStream(new ByteArrayInputStream(
								responseBody));
				Log.e("*******************************************", "Address : "+statusCode);
				addressBaseHolder = gson.fromJson(response, AddressBaseHolder.class);
				if(getAddressCallBack != null){
					getAddressCallBack.onFinish(requestcode, addressBaseHolder);
				}
			}

			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
					Throwable error) {
				Log.e("*******************************************", "Cancelled Failure Addresss"+statusCode);
				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						getAddressCallBack.onFinish(requestcode,NO_INTERNET);
					}

				} else {
					Gson gson = new Gson();
					AddressBaseHolderError addrresserror = new AddressBaseHolderError();

					String response = UtilValidate
							.getStringFromInputStream(new ByteArrayInputStream(
									responseBody));
					Log.e("*******************************************", "Address : "+statusCode);
					addrresserror = gson.fromJson(response, AddressBaseHolderError.class);
					if(getAddressCallBack != null){
						getAddressCallBack.onFinish(1, addrresserror);
				}
				}

			}				
			
		});
	}
	
	public void getAddressList(final Activity activity,int userId,final AsyncTaskCallBack getAddressListCallBack,
			final int requestcode){
		RequestParams params = new RequestParams();
		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Header.HEADER_TOKEN, CurrentlyLoggedUserDAO.getInstance().getHeader());
		EasyHomeCustomerAppRestClient.get(AddressListInterface.ADDRESS_LIST_URL+userId+AddressListInterface.ADDRESS_LIST_TAIL_URL,
				params, headers, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
						Gson gson = new Gson();
						AddressListBaseHolder addressListBaseHolder = new AddressListBaseHolder();
						String response = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(
										responseBody));
						Log.e("*******************************************", "AddressList : "+response);
						addressListBaseHolder = gson.fromJson(response, AddressListBaseHolder.class);
						if(getAddressListCallBack != null){
							getAddressListCallBack.onFinish(requestcode, addressListBaseHolder);
						}
					}

					@Override
					public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody,
							Throwable error) {

						Log.e("*******************************************", "Cancelled Failure Addresss"+statusCode);
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								getAddressListCallBack.onFinish(requestcode,NO_INTERNET);
							}

						} else {
				
							getAddressListCallBack.onFinish(1, FAILED);
						}

					
					}
			
		});
	}
}
