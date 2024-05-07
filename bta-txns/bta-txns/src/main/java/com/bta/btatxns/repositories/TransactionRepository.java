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
