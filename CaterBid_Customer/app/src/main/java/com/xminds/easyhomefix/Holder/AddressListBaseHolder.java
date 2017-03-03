package com.xminds.easyhomefix.Holder;

import java.util.List;

public class AddressListBaseHolder {
	private boolean success;
    private List<Address> data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<Address> getData() {
		return data;
	}
	public void setData(List<Address> data) {
		this.data = data;
	}
    
}
