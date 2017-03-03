package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class JobCategoriesBaseHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6639835278845022445L;

	public List<JobCategories> getData() {
		return data;
	}

	public void setData(List<JobCategories> data) {
		this.data = data;
	}

	private boolean success;
	private List<JobCategories> data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	

}
