package com.bta.btastatement.models;

public class AccountHolder {
	
	private long accountHolderId;
	private String fullName;
	private String mobile;
	private String mailId;
	private double currentBalance;
	
	public AccountHolder() {
		
	}

	public AccountHolder(long accountHolderId, String fullName, String mobile, String mailId, double currentBalance) {
		super();
		this.accountHolderId = accountHolderId;
		this.fullName = fullName;
		this.mobile = mobile;
		this.mailId = mailId;
		this.currentBalance = currentBalance;
	}

	public long getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(long accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	
		

}
