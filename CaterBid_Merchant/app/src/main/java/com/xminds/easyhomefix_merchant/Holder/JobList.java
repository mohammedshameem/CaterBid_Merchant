package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;
import java.util.List;


public class JobList implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6535783594180865647L;
	private String id;
	private ReceiverUser userId;
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
	private List<Quote> quote;
	private PaymentHolder payment;
	private String paymentType;
	private String paymentStatus;
	
	public PaymentHolder getPayment() {
		return payment;
	}

	public void setPayment(PaymentHolder payment) {
		this.payment = payment;
	}

	public jobReview getJobReview() {
		return jobReview;
	}

	public void setJobReview(jobReview jobReview) {
		this.jobReview = jobReview;
	}

	public String getJobDateTimeSlot() {
		return jobDateTimeSlot;
	}

	public void setJobDateTimeSlot(String jobDateTimeSlot) {
		this.jobDateTimeSlot = jobDateTimeSlot;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
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

	public List<Quote> getQuote() {
		return quote;
	}

	public void setQuote(List<Quote> quote) {
		this.quote = quote;
	}

	public ReceiverUser getUserId() {
		return userId;
	}

	public void setUserId(ReceiverUser userId) {
		this.userId = userId;
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
