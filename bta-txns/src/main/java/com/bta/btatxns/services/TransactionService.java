package com.bta.btatxns.services;

import java.util.List;

import com.bta.btatxns.entities.Transaction;

public interface TransactionService {

	double getBalance(Long accountHolderId) throws AccountHolderException;
	List<Transaction> getPeriodicTransactionsForAccountHolderId(Long accountHolderId) throws AccountHolderException;
	Transaction getById(Long transactionId);
	Transaction add(Transaction transaction) throws AccountHolderException, TransactionException;
	Transaction update(Transaction transaction) throws AccountHolderException, TransactionException;
	void deleteById(Long transactionId) throws TransactionException;
}
