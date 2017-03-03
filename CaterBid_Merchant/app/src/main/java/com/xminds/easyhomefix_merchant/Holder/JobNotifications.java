package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class JobNotifications implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -8237455576417748831L;
	private String id;
	private Job job;
	private String notifyType;
	private String notifyStatus;
	private String notifyDateTime;
	private String notifyText;
	private UserHolder recieverUser;
	private Quote quote;
	private String swipe;
	
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

	public UserHolder getRecieverUser() {
		return recieverUser;
	}

	public void setRecieverUser(UserHolder recieverUser) {
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
