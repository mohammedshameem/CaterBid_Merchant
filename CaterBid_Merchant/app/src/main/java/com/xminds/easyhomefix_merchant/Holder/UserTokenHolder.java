package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;
import java.util.List;

public class UserTokenHolder implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3107152648771948364L;
	private String token;
	private UserHolder user;
	private String message;
	private int otpCode;
	private Quote quote;
	private List<jobNotifications> jobNotifications;
	private List<FixerReviews>fixerReviews;
	private Job job;

	public List<jobNotifications> getJobNotifications() {
		return jobNotifications;
	}

	public void setJobNotifications(List<jobNotifications> jobNotifications) {
		this.jobNotifications = jobNotifications;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserHolder getUser() {
		return user;
	}

	public void setUser(UserHolder user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(int otpCode) {
		this.otpCode = otpCode;
	}
	public List<FixerReviews> getFixerReviews() {
		return fixerReviews;
	}

	public void setFixerReviews(List<FixerReviews> fixerReviews) {
		this.fixerReviews = fixerReviews;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}


}
