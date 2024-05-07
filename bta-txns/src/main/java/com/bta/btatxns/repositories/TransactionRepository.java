package com.bta.btatxns.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bta.btatxns.entities.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	@Query("SELECT t FROM Transaction t where t.holder.accountHolderId=:accountHolderId AND t.transactionDate BETWEEN :start AND :end")
	List<Transaction> getPeriodicTransactionsForAccountHolderId(Long accountHolderId, LocalDate start, LocalDate end);

}

/**
 * When we receive add a transaction request
 * 	-> check if the accountHolder is existing in transactionDB
 *  -> if YES, add the transaction
 *  -> If no, then check if the account exists in profiles - send a GET request to http://localhost:8001/{accountHolderId}/exists
 *  -> If Yes, add accountHolder in transactionDB and then add the transaction
 *  -> if no reject the transaction
 */
*/