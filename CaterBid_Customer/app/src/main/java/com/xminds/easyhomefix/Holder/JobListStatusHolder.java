package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class JobListStatusHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7903630558488550985L;
	private boolean success;
	private JobListObject data;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public JobListObject getData() {
		return data;
	}
	public void setData(JobListObject data) {
		this.data = data;
	}
	
}
