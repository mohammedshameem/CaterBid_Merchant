package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class JobCreationHolder implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 2970465401736375791L;
private String message;
private JobHolder job;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public JobHolder getJob() {
	return job;
}
public void setJob(JobHolder job) {
	this.job = job;
}



}
