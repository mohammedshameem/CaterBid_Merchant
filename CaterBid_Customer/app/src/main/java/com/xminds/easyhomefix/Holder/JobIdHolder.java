package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JobIdHolder implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8704354566891956743L;
	private String id;
	private UserDataHolder userId;
	private String jobDate;
	private String jobDateTimeSlot;
	private String jobDetails;
	private JobCategoryId jobCategoryId;
	private JobTypeId jobTypeId;
	private String jobStatus;
	private List<String> images;
	private String readStatus;
	private Address address;
	private jobReview jobReview;
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

	public jobReview getJobReview() {
		return jobReview;
	}
	public void setJobReview(jobReview jobReview) {
		this.jobReview = jobReview;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserDataHolder getUserId() {
		return userId;
	}
	public void setUserId(UserDataHolder userId) {
		this.userId = userId;
	}
	public String getJobDate() {
		return jobDate;
	}
	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}
	public String getJobDateTimeSlot() {
		return jobDateTimeSlot;
	}
	public void setJobDateTimeSlot(String jobDateTimeSlot) {
		this.jobDateTimeSlot = jobDateTimeSlot;
	}
	public String getJobDetails() {
		return jobDetails;
	}
	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}
	public JobCategoryId getJobCategoryId() {
		return jobCategoryId;
	}
	public void setJobCategoryId(JobCategoryId jobCategoryId) {
		this.jobCategoryId = jobCategoryId;
	}
	public JobTypeId getJobTypeId() {
		return jobTypeId;
	}
	public void setJobTypeId(JobTypeId jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	

}
