package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class jobReview implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -327483319786148925L;
	private String id;
	private int star;
	private String review;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	
	

}
