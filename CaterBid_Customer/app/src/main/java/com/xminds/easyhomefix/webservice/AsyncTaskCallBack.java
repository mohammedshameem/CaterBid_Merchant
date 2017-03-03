package com.xminds.easyhomefix.webservice;

import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;

public interface AsyncTaskCallBack {

	public void onFinish(int responseCode, Object result);

	public void onFinish(int responseCode, String result);

	public void onFinish(int requestcode,
			JobCreationBaseHolder jobCreationBaseHolder, int i);

	

}
