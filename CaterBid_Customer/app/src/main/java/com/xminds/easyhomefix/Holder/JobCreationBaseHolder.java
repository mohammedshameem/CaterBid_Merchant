package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class JobCreationBaseHolder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8690130858305441360L;
	private boolean success;
	private JobCreationHolder data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public JobCreationHolder getData() {
		return data;
	}
	public void setData(JobCreationHolder data) {
		this.data = data;
	}
	
	

}
