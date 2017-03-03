package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class FixedIdObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 932291874915418372L;
	private String id;
	private UseridObject userId;
	private String companyName;
	private int numberOfFixesCompleted;
	private float avrgStarRating;


	public float getAvrgStarRating() {
		return avrgStarRating;
	}

	public void setAvrgStarRating(float avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UseridObject getUserId() {
		return userId;
	}

	public void setUserId(UseridObject userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getNumberOfFixesCompleted() {
		return numberOfFixesCompleted;
	}

	public void setNumberOfFixesCompleted(int numberOfFixesCompleted) {
		this.numberOfFixesCompleted = numberOfFixesCompleted;
	}

}
