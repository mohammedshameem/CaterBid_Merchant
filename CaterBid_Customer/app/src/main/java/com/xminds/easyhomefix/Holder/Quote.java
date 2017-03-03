package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class Quote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9037775847461825501L;
	private String id;
	private Fixer fixerId;
	private int quoteAmount;
	private String status;
	private String paymentType;
	private String paymentStatus;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Fixer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Fixer fixerId) {
		this.fixerId = fixerId;
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

}
