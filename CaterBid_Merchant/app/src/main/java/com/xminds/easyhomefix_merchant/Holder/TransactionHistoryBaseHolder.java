package com.xminds.easyhomefix_merchant.Holder;

public class TransactionHistoryBaseHolder {
	private boolean success; 
    private TransactionHistoryDataHolder data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public TransactionHistoryDataHolder getData() {
		return data;
	}
	public void setData(TransactionHistoryDataHolder data) {
		this.data = data;
	}
    
}
