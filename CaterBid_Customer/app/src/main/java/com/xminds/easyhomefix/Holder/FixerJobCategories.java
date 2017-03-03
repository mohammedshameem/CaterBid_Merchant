package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class FixerJobCategories implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1214340358137460315L;
	private String name;
	private String id;
	private String selectedImage;
	private String nonSelectedImage;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
