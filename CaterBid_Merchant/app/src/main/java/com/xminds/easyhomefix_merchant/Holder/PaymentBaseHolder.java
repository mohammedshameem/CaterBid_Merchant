package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class PaymentBaseHolder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8512247870128981146L;
	private String id;
	private PaymentHolder paymentId;
	private String paymentResponse;
	private String paymentResponseCode;
	private String paymentGateway;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PaymentHolder getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(PaymentHolder paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentResponse() {
		return paymentResponse;
	}
	public void setPaymentResponse(String paymentResponse) {
		this.paymentResponse = paymentResponse;
	}
	public String getPaymentResponseCode() {
		return paymentResponseCode;
	}
	public void setPaymentResponseCode(String paymentResponseCode) {
		this.paymentResponseCode = paymentResponseCode;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	
	
	
	
}
