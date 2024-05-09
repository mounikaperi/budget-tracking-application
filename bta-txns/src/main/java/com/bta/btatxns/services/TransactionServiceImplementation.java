package com.bta.btatxns.services;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bta.btatxns.entities.AccountHolder;
import com.bta.btatxns.entities.Transaction;
import com.bta.btatxns.entities.TransactionType;
import com.bta.btatxns.exceptions.AccountHolderException;
import com.bta.btatxns.exceptions.TransactionException;
import com.bta.btatxns.repositories.AccountHolderRepository;
import com.bta.btatxns.repositories.TransactionRepository;

@Service
public class TransactionServiceImplementation implements TransactionService {
	
	
	@Autowired
	private ProfilesClient profiles;
	
	@Autowired
	private AccountHolderRepository accountHolderRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	

	@Override
	public double getBalance(Long accountHolderId) throws AccountHolderException {
		AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).orElse(null);
		return accountHolder==null ? 0 : accountHolder.getCurrentBalance();
	}

	@Override
	public List<Transaction> getPeriodicTransactionsForAccountHolderId(Long accountHolderId, LocalDate start, LocalDate end)
			throws AccountHolderException {
		return transactionRepository.getPeriodicTransactionsForAccountHolderId(accountHolderId, start, end);
	}

	@Override
	public Transaction getById(Long transactionId) {
		return transactionRepository.findById(transactionId).orElse(null);
	}

	@Override
	@Transactional
	public Transaction add(Transaction transaction) throws AccountHolderException, TransactionException {
		if (transaction.getHolder() == null) {
			throw new TransactionException("Transaction cannot be added to a non mentioned accountHolder");
		}
		long accountHolderId = transaction.getHolder().getAccountHolderId();
		AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).orElse(null);
		if (accountHolder == null) {
			if (profiles.checkAccountHolderExists(accountHolderId)) {
				accountHolder = new AccountHolder(accountHolderId, 0.0, new TreeSet<>());
			} else {
				throw new TransactionException("Transaction cannot be added to a non existing accountHolder");
			}
		}
		double currentBalance = accountHolder.getCurrentBalance();
		accountHolder.setCurrentBalance(transaction.getType() == TransactionType.CREDIT ? currentBalance + transaction.getAmount() : currentBalance - transaction.getAmount());
		transaction.setHolder(accountHolder);		
		accountHolder.getTransactions().add(transaction);
		accountHolderRepository.save(accountHolder);
		return transactionRepository.save(transaction);
	}

	@Override
	@Transactional
	public Transaction update(Transaction transaction) throws AccountHolderException, TransactionException {
		if (!transactionRepository.existsById(transaction.getTransactionId())) {
			throw new TransactionException("Transaction not found to update");
		}
		Transaction oldTransaction = transactionRepository.findById(transaction.getTransactionId()).orElse(null);
		AccountHolder accountHolder = oldTransaction.getHolder();
		double currentBalance = accountHolder.getCurrentBalance();
		currentBalance = oldTransaction.getType() == TransactionType.CREDIT ? currentBalance - oldTransaction.getAmount():  currentBalance + oldTransaction.getAmount();
		accountHolder.setCurrentBalance(currentBalance);
		accountHolderRepository.save(accountHolder);
		return transaction;
	}

	@Override
	public void deleteById(Long transactionId) throws TransactionException {
		transactionRepository.deleteById(transactionId);
		
	}
	
}
