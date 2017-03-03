package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class JobListObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2433224256290573048L;
	private List<JobList>jobs;
	

	public List<JobList> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobList> jobs) {
		this.jobs = jobs;
	}
	
}
