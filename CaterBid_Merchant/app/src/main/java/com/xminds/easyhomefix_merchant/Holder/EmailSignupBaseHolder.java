package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;


public class EmailSignupBaseHolder implements Serializable {
	
	
	private static final long serialVersionUID = -7423565482256573594L;
	/**
	 * 
	 */
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
