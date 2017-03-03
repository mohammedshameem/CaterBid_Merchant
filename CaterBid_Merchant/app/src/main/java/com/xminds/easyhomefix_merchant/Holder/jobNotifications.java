package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class jobNotifications implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3787185585221305301L;
	private String id;
	private Job job;
	private String notifyType;
	private String notifyStatus;
	private String notifyDateTime;
	private String notifyText;
	private ReceiverUser recieverUser;
	private Quote quote;
	private String swipe;
	private PaymentBaseHolder payment;
	
	
	public PaymentBaseHolder getPayment() {
		return payment;
	}
	public void setPayment(PaymentBaseHolder payment) {
		this.payment = payment;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public String getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public String getNotifyDateTime() {
		return notifyDateTime;
	}
	public void setNotifyDateTime(String notifyDateTime) {
		this.notifyDateTime = notifyDateTime;
	}
	public String getNotifyText() {
		return notifyText;
	}
	public void setNotifyText(String notifyText) {
		this.notifyText = notifyText;
	}
	public ReceiverUser getRecieverUser() {
		return recieverUser;
	}
	public void setRecieverUser(ReceiverUser recieverUser) {
		this.recieverUser = recieverUser;
	}
	public Quote getQuote() {
		return quote;
	}
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	public String getSwipe() {
		return swipe;
	}
	public void setSwipe(String swipe) {
		this.swipe = swipe;
	}
	
	
	
 }

