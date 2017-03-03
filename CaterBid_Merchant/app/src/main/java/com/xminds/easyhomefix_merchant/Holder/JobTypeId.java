package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class JobTypeId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 269202868271543266L;
	private String id;
	private String name;
	private String description;
    private CategoryId categoryId;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CategoryId getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(CategoryId categoryId) {
		this.categoryId = categoryId;
	}
	
    
    
    
}
