package com.bta.btatxns.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "AccountHolders")
public class AccountHolder {

	@Id
	private Long accountHolderId;
	
	private Double currentBalance;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;
	
	public AccountHolder() {
		
	}
	
	public AccountHolder(Long accountHolderId, Double currentBalance, Set<Transaction> transactions) {
		super();
		this.accountHolderId = accountHolderId;
		this.currentBalance = currentBalance;
		this.transactions = transactions;
	}

	public Long getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(Long accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	

}
