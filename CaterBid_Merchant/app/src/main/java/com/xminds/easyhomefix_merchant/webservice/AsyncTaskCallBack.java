package com.xminds.easyhomefix_merchant.webservice;

public interface AsyncTaskCallBack {
	
	public void onFinish(int responseCode, Object result);

	public void onFinish(int responseCode, String result);


}
