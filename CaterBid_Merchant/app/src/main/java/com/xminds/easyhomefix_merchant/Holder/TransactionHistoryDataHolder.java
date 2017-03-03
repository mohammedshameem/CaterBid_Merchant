package com.xminds.easyhomefix_merchant.Holder;

import java.util.List;

public class TransactionHistoryDataHolder {
	 private List<PaymentHolder> paymentHistory; 
     private int currentAccountBalance;
	public List<PaymentHolder> getPaymentHistory() {
		return paymentHistory;
	}
	public void setPaymentHistory(List<PaymentHolder> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
	public int getCurrentAccountBalance() {
		return currentAccountBalance;
	}
	public void setCurrentAccountBalance(int currentAccountBalance) {
		this.currentAccountBalance = currentAccountBalance;
	}
     
}

