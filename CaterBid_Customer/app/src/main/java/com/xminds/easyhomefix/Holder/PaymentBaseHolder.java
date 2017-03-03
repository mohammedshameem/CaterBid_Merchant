package com.xminds.easyhomefix.Holder;

public class PaymentBaseHolder {
	private boolean success; 
    private PaymentDataHolder data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public PaymentDataHolder getData() {
		return data;
	}
	public void setData(PaymentDataHolder data) {
		this.data = data;
	}
    
}
