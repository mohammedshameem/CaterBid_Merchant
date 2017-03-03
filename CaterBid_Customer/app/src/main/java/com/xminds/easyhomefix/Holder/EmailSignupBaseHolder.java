package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class EmailSignupBaseHolder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4072478685653059484L;
	private boolean success;
	private UserTokenHolder data;
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public UserTokenHolder getData() {
		return data;
	}

	public void setData(UserTokenHolder data) {
		this.data = data;
	}

}
