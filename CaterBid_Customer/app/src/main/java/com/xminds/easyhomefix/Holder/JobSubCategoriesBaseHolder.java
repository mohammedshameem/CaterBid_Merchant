package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class JobSubCategoriesBaseHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1397408696400439415L;
	private boolean success;
	private List<JobCategories> data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<JobCategories> getData() {
		return data;
	}

	public void setData(List<JobCategories> data) {
		this.data = data;
	}


}
