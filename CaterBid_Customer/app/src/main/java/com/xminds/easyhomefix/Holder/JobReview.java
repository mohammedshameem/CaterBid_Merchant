package com.xminds.easyhomefix.Holder;

public class JobReview {

	private String id;
	private JobList jobId;
	private String quoteid;
	private FixerIdHolder fixerId;
	private String star;
	private String review;
	private String reviewDateTime;

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

	public String getQuoteid() {
		return quoteid;
	}

	public void setQuoteid(String quoteid) {
		this.quoteid = quoteid;
	}

	public FixerIdHolder getFixerId() {
		return fixerId;
	}

	public void setFixerId(FixerIdHolder fixerId) {
		this.fixerId = fixerId;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
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
