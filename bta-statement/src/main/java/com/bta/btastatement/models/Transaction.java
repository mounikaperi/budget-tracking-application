package com.bta.btastatement.models;

import java.time.LocalDate;

public class Transaction implements Comparable<Transaction>{
	
	private Long transactionId;
	private String header;
	private LocalDate date;
	private TransactionType type;
	private Double amount;
	
	public Transaction() {
		
	}

	public Transaction(Long transactionId, String header, LocalDate date, TransactionType type, Double amount) {
		super();
		this.transactionId = transactionId;
		this.header = header;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public int compareTo(Transaction o) {
		return transactionId == null ? 1 : transactionId.compareTo(o.transactionId);
	}
	
	
}
