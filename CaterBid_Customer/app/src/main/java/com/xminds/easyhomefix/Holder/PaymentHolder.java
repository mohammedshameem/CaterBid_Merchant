package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class PaymentHolder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6790189084450374995L;
	private String id; 
	//private JobIdHolder jobId; 
    private Quote quoteId; 
    private String paymentType; 
    private String paymentStatus; 
    private String cashRecievedStatus;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*public JobIdHolder getJobId() {
		return jobId;
	}
	public void setJobId(JobIdHolder jobId) {
		this.jobId = jobId;
	}*/
	public Quote getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(Quote quoteId) {
		this.quoteId = quoteId;
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
	public String getCashRecievedStatus() {
		return cashRecievedStatus;
	}
	public void setCashRecievedStatus(String cashRecievedStatus) {
		this.cashRecievedStatus = cashRecievedStatus;
	}
    
}
