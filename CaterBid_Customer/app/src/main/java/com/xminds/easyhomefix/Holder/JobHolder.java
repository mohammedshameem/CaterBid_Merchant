package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class JobHolder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5301915070308911403L;
	private String id;
	private UserHolder userId;
	private String jobDate;
	private String jobDetails;
	private String jobDateTimeSlot;
	private JobCategoryId jobCategoryId;                    
	private JobTypeId jobTypeId;
	private String jobStatus;
	private List<String> images;
	private String readStatus;
    private JobAddress address;
    
	public JobCategoryId getJobCategoryId() {
		return jobCategoryId;
	}
	public JobTypeId getJobTypeId() {
		return jobTypeId;
	}
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	public JobAddress getAddress() {
		return address;
	}
	public void setAddress(JobAddress address) {
		this.address = address;
	}
	public void setJobCategoryId(JobCategoryId jobCategoryId) {
		this.jobCategoryId = jobCategoryId;
	}
	public void setJobTypeId(JobTypeId jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserHolder getUserId() {
		return userId;
	}
	public void setUserId(UserHolder userId) {
		this.userId = userId;
	}
	
	public String getJobDetails() {
		return jobDetails;
	}
	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
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

}
