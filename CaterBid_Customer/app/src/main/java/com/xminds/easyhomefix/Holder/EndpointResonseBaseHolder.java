package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class EndpointResonseBaseHolder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2564995035010821251L;
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
