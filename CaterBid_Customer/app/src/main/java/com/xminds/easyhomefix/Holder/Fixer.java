package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class Fixer implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8272702753747089358L;
	
	private String id;
	private User userId;
	private String companyName;
	private int numberOfFixesCompleted;
	private float avrgStarRating;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
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
