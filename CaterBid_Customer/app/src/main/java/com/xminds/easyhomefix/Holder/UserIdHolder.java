package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

import com.xminds.easyhomefix.Holder.UserHolder;

public class UserIdHolder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8929237412235011687L;
	private String id;
	private String companyName;
	private int numberOfFixesCompleted;
	private String avrgStarRating;
	private UserHolder userId;

	public UserHolder getUserId() {
		return userId;
	}

	public void setUserId(UserHolder userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
