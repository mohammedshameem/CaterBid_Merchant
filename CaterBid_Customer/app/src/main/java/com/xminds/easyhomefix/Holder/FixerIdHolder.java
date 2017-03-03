package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class FixerIdHolder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8485570025432626152L;
	private String id;
	private FixerUserDataHolder userId;
	private String companyName;
	private int numberOfFixesCompleted;
	private String avrgStarRating;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FixerUserDataHolder getUserId() {
		return userId;
	}

	public void setUserId(FixerUserDataHolder userId) {
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

	public String getAvrgStarRating() {
		return avrgStarRating;
	}

	public void setAvrgStarRating(String avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}

}
