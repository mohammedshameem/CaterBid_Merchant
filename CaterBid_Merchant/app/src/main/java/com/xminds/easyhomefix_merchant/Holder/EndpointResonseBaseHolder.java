package com.xminds.easyhomefix_merchant.Holder;

public class EndpointResonseBaseHolder {
	private boolean success;
	private MessageHolder  data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public MessageHolder getData() {
		return data;
	}
	public void setData(MessageHolder data) {
		this.data = data;
	}
	
}
