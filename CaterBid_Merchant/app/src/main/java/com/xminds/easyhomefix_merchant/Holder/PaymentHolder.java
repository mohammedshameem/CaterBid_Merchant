package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class PaymentHolder implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -4771017569753737137L;
	private String id; 
     private AvailableJobs jobId; 
     private Quote quoteId;
     private String paymentType;
     private String paymentStatus; 
     private String cashRecievedStatus;
     private String jobPaymentDate;
          
	public String getJobPaymentDate() {
		return jobPaymentDate;
	}
	public void setJobPaymentDate(String jobPaymentDate) {
		this.jobPaymentDate = jobPaymentDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AvailableJobs getJobId() {
		return jobId;
	}
	public void setJobId(AvailableJobs jobId) {
		this.jobId = jobId;
	}
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
