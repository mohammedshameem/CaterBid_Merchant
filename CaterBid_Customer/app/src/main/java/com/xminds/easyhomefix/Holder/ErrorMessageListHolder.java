package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class ErrorMessageListHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -179833733420834514L;
	private String field;
	private String message;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
