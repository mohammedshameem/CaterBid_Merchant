package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class ChatBaseHolder implements Serializable {
	

/**
	 * 
	 */
	private static final long serialVersionUID = -7166188616484768254L;
private boolean success;
private ChatData data;

public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
public ChatData getData() {
	return data;
}
public void setData(ChatData data) {
	this.data = data;
}


}
