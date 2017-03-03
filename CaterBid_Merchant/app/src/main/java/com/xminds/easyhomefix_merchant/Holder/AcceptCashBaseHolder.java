package com.xminds.easyhomefix_merchant.Holder;

public class AcceptCashBaseHolder {
	private boolean success; 
	private CashHolder data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public CashHolder getData() {
		return data;
	}
	public void setData(CashHolder data) {
		this.data = data;
	}
	
}
