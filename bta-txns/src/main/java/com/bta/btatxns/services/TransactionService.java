package com.bta.btatxns.services;

import java.util.List;

import com.bta.btatxns.entities.Transaction;
import com.bta.btatxns.exceptions.AccountHolderException;
import com.bta.btatxns.exceptions.TransactionException;

public interface TransactionService {

	double getBalance(Long accountHolderId) throws AccountHolderException;
	List<Transaction> getPeriodicTransactionsForAccountHolderId(Long accountHolderId) throws AccountHolderException;
	Transaction getById(Long transactionId);
	Transaction add(Transaction transaction) throws AccountHolderException, TransactionException;
	Transaction update(Transaction transaction) throws AccountHolderException, TransactionException;
	void deleteById(Long transactionId) throws TransactionException;
}

/*
 * When we receive add a transaction request
 * 	-> check if the accountHolder is existing in transactionDB
 *  -> if YES, add the transaction
 *  -> If no, then check if the account exists in profiles - send a GET request to http://localhost:8001/{accountHolderId}/exists
 *  -> If Yes, add accountHolder in transactionDB and then add the transaction
 *  -> if no reject the transaction
 */
