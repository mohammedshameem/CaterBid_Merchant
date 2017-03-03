package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

import com.xminds.easyhomefix.Holder.UserHolder;

public class Quotes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4605913064671280526L;
	private String id;
	private int quoteAmount;
	private String status;
	private UserIdHolder fixerId;
	private String paymentType;
	private String paymentStatus;
	
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(int quoteAmount) {
		this.quoteAmount = quoteAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserIdHolder getFixerId() {
		return fixerId;
	}

	public void setFixerId(UserIdHolder fixerId) {
		this.fixerId = fixerId;
	}

}
