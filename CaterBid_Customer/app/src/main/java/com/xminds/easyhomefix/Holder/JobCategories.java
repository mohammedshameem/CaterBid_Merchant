package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class JobCategories implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2818441206858831957L;
	private String id;
	private String name;
	private String selectedImage; 
	private String nonSelectedImage; 
	private String description;
	private CategoryId categoryId;
	private boolean isSelected;
	
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public CategoryId getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(CategoryId categoryId) {
		this.categoryId = categoryId;
	}
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
	public String getSelectedImage() {
		return selectedImage;
	}
	public void setSelectedImage(String selectedImage) {
		this.selectedImage = selectedImage;
	}
	public String getNonSelectedImage() {
		return nonSelectedImage;
	}
	public void setNonSelectedImage(String nonSelectedImage) {
		this.nonSelectedImage = nonSelectedImage;
	}
	

}
