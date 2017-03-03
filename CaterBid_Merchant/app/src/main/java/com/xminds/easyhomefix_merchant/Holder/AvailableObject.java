package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;
import java.util.List;

public class AvailableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 998919038118240528L;
	List<AvailableJobs>availableJobs;
	private String message; 
    private Fixerobject fixer;
	List<AvailableQuotes> availableQuotes;
	List<AvailableJobs>jobs;
    

	public List<AvailableJobs> getJobs() {
		return jobs;
	}

	public void setJobs(List<AvailableJobs> jobs) {
		this.jobs = jobs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Fixerobject getFixer() {
		return fixer;
	}

	public void setFixer(Fixerobject fixer) {
		this.fixer = fixer;
	}
	

	public List<AvailableJobs> getAvailableJobs() {
		return availableJobs;
	}

	public void setAvailableJobs(List<AvailableJobs> availableJobs) {
		this.availableJobs = availableJobs;
	}

	public List<AvailableQuotes> getAvailableQuotes() {
		return availableQuotes;
	}

	public void setAvailableQuotes(List<AvailableQuotes> availableQuotes) {
		this.availableQuotes = availableQuotes;
	}

}
