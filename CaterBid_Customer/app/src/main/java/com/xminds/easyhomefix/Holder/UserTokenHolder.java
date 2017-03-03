package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class UserTokenHolder implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3461894400622519335L;
	private String token;
	private UserHolder user;
	private String message;
	private int otpCode;
	private List<JobReviews> fixerReviews;
	private List<JobNotifications> jobNotifications;
	private JobReview jobReview;
	private Job job;

	public List<JobNotifications> getJobNotifications() {
		return jobNotifications;
	}

	public void setJobNotifications(List<JobNotifications> jobNotifications) {
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

	public List<JobReviews> getFixerReviews() {
		return fixerReviews;
	}

	public void setFixerReviews(List<JobReviews> fixerReviews) {
		this.fixerReviews = fixerReviews;
	}

	public JobReview getJobReview() {
		return jobReview;
	}

	public void setJobReview(JobReview jobReview) {
		this.jobReview = jobReview;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}


}
