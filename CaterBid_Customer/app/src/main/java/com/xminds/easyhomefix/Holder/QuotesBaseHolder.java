	package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class QuotesBaseHolder implements Serializable{
	
	

/**
	 * 
	 */
	private static final long serialVersionUID = -2552078610721825044L;
private boolean success;
private QuotesListHolder data;
public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
public QuotesListHolder getData() {
	return data;
}
public void setData(QuotesListHolder data) {
	this.data = data;
}




}
