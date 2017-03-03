package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class Fixerobject implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7798342188854871470L;
	private String id;
    private UserObject userId;
    private String companyName; 
    private int numberOfFixesCompleted; 
    private double avrgStarRating; 
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserObject getUserId() {
		return userId;
	}
	public void setUserId(UserObject userId) {
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
	
	public void setAvrgStarRating(int avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}
	
	public double getAvrgStarRating() {
		return avrgStarRating;
	}
	public void setAvrgStarRating(double avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}

    
    
	
}
