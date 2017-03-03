package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class QuotesListHolder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3224097295411670569L;
	private List<Quotes> quotes;
	private String message;

	public List<Quotes> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quotes> quotes) {
		this.quotes = quotes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
