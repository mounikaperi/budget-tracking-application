package com.bta.btatxns.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bta.btatxns.entities.Transaction;
import com.bta.btatxns.exceptions.AccountHolderException;
import com.bta.btatxns.exceptions.TransactionException;
import com.bta.btatxns.services.TransactionService;

@RestController
@RequestMapping("/")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/{accountHolderId}/balance")
	public ResponseEntity<Double> getBalance(@PathVariable("accountHolderId")Long accountHolderId) throws AccountHolderException {
		return ResponseEntity.ok(transactionService.getBalance(accountHolderId));
	}
	
	@GetMapping("/{accountHolderId}/{start}/{end}")
	public ResponseEntity<List<Transaction>> getTransactions(
			@PathVariable("accountHolderId")Long accountHolderId,
			@PathVariable("start") @DateTimeFormat(iso=ISO.DATE)LocalDate start,
			@PathVariable("end") @DateTimeFormat(iso=ISO.DATE)LocalDate end
			) throws AccountHolderException {
		return ResponseEntity.ok(transactionService.getPeriodicTransactionsForAccountHolderId(accountHolderId, start, end));
	}
	
	@PostMapping
	public ResponseEntity<Transaction> addTransaction(@RequestBody @Valid Transaction transaction, BindingResult result) throws TransactionException, AccountHolderException {
		return saveTransaction(transaction, result, "ADD");
	}
	
	@PutMapping
	public ResponseEntity<Transaction> updateTransaction(@RequestBody @Valid Transaction transaction, BindingResult result) throws TransactionException, AccountHolderException {
		return saveTransaction(transaction, result, "UPDATE");
	}
	
	private ResponseEntity<Transaction> saveTransaction(Transaction transaction, BindingResult results, String operation) throws TransactionException, AccountHolderException {
		if (results.hasErrors()) {
			throw new TransactionException(results.getAllErrors().stream().map(err -> err.getDefaultMessage()).reduce((m1,m2) -> m1 + " " + m2).orElse(null));
		}
		ResponseEntity<Transaction> re = null;
		if (operation.equals("ADD")) {
			re = new ResponseEntity<Transaction>(transactionService.add(transaction), HttpStatus.CREATED);
		} else {
			re = new ResponseEntity<Transaction>(transactionService.update(transaction), HttpStatus.ACCEPTED);
		}
		return re;
	}
}
