package com.bta.btatxns.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "transactions")
public class Transaction implements Comparable<Transaction>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	@NotBlank(message = "header is a mandate field")
	private String header;
	
	@NotNull(message = "transactionDate is a mandate field")
	@PastOrPresent(message = "transactionDate cannot be of future")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate transactionDate;
	
	@Enumerated
	@NotNull(message = "type is a mandate field")
	private TransactionType type;
	
	@NotNull(message = "amount is a mandate field")
	@Min(value = 0, message = "amount can not be negative")
	private Double amount;
	
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	private AccountHolder holder;
	
	public Transaction() {
		
	}

	public Transaction(Long transactionId, @NotBlank(message = "header is a mandate field") String header,
			@NotNull(message = "transactionDate is a mandate field") @PastOrPresent(message = "transactionDate can not be of future") LocalDate transactionDate,
			@NotNull(message = "type is a mandate field") TransactionType type,
			@NotNull(message = "amount is a mandate field") @Min(value = 0, message = "amount can not be negative") Double amount,
			AccountHolder holder) {
		super();
		this.transactionId = transactionId;
		this.header = header;
		this.transactionDate = transactionDate;
		this.type = type;
		this.amount = amount;
		this.holder = holder;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
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

	public AccountHolder getHolder() {
		return holder;
	}

	public void setHolder(AccountHolder holder) {
		this.holder = holder;
	}
	
	@Override
	public int compareTo(Transaction t) {
		return transactionId==null?1:transactionId.compareTo(t.transactionId);
	}
	
	
}
