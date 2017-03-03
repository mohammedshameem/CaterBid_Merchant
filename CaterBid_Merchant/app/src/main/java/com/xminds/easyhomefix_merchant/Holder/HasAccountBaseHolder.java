package com.xminds.easyhomefix_merchant.Holder;

public class HasAccountBaseHolder {
    private boolean success; 
    private HasAccountHolder data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public HasAccountHolder getData() {
		return data;
	}
	public void setData(HasAccountHolder data) {
		this.data = data;
	}
    
}
