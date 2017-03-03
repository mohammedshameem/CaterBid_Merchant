package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class FixerReviews implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3671991791138602239L;
	
	private String id;
	private JobIdHolder jobId;
	private String quoteId;
	private FixerIdHolder fixerId;
	private int star;
	private String review;
	private String reviewDateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public JobIdHolder getJobId() {
		return jobId;
	}
	public void setJobId(JobIdHolder jobId) {
		this.jobId = jobId;
	}
	public String getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}
	public FixerIdHolder getFixerId() {
		return fixerId;
	}
	public void setFixerId(FixerIdHolder fixerId) {
		this.fixerId = fixerId;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getReviewDateTime() {
		return reviewDateTime;
	}
	public void setReviewDateTime(String reviewDateTime) {
		this.reviewDateTime = reviewDateTime;
	}


	
	
}
