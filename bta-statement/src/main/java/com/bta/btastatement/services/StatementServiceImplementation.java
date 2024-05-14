package com.bta.btastatement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bta.btastatement.exceptions.StatementException;
import com.bta.btastatement.models.AccountHolder;
import com.bta.btastatement.models.Statement;
import com.bta.btastatement.models.Transaction;
import com.bta.btastatement.models.TransactionType;

@Service
public class StatementServiceImplementation implements StatementService {
	
	@Autowired
	private ProfilesClient profilesClient;
	
	@Autowired
	private TransactionsClient transactionsClient;

	private double computeTotal(List<Transaction> transactions,TransactionType type) {
		return transactions.stream().filter(t->t.getType()==type).mapToDouble(Transaction::getAmount).sum();
	}
	
	@Override
	public Statement getStatement(long ahid, LocalDate start, LocalDate end) throws StatementException {
		if(!profilesClient.checkAccountHolderExists(ahid))
			throw new StatementException("Account does not exists");
		
		AccountHolder accountHolder = profilesClient.getAccountHolder(ahid);
		List<Transaction> transactions = transactionsClient.getTransactions(ahid, start, end);
		accountHolder.setCurrentBalance(transactionsClient.getBalance(ahid));
		double totalCredit=computeTotal(transactions, TransactionType.CREDIT);
		double totalDebit=computeTotal(transactions, TransactionType.DEBIT);
		double statementBalance = totalCredit-totalDebit;
		
		return new Statement(accountHolder,new TreeSet<>(transactions), totalCredit, totalDebit, statementBalance, start, end);
	}

}