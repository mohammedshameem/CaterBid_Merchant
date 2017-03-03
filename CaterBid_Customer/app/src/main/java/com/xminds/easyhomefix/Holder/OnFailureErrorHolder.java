package com.xminds.easyhomefix.Holder;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OnFailureErrorHolder {

	private boolean success;
	private List<ErrorMessageListHolder> data;


	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<ErrorMessageListHolder> getData() {
		return data;
	}
	public void setData(List<ErrorMessageListHolder> data) {
		this.data = data;
	}

}
