package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;

public class AvailableQuotes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8539259333498835961L;
	private String id;
	private FixedIdObject fixerId;
	private int quoteAmount;
	private String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FixedIdObject getFixerId() {
		return fixerId;
	}
	public void setFixerId(FixedIdObject fixerId) {
		this.fixerId = fixerId;
	}
	public int getQuoteAmount() {
		return quoteAmount;
	}
	public void setQuoteAmount(int quoteAmount) {
		this.quoteAmount = quoteAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
