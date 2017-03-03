package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class AvailableBaseHolder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7984982614776027153L;
	private boolean success; 
    private AvailableObject data;
    
    
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public AvailableObject getData() {
		return data;
	}
	public void setData(AvailableObject data) {
		this.data = data;
	}
    
	
}
