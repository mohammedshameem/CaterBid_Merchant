package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class FixerIdHolder implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5478947611549979374L;
	private String id;
	private UserHolder userId;
	private String companyName;
	private int numberOfFixesCompleted;
	private float avrgStarRating;
	
	
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
	public float getAvrgStarRating() {
		return avrgStarRating;
	}
	public void setAvrgStarRating(float avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}
	
}
