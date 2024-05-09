package com.bta.btastatement.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bta.btastatement.models.Transaction;

@FeignClient(name="transactions", url="http://localhost:8002")
public interface TransactionsClient {

	@GetMapping("/{accountHolderId}/{start}/{end}")
	List<Transaction> getTransactions(@PathVariable("accountHolderId") long accountHolderId,
			@PathVariable("start") @DateTimeFormat(iso=ISO.DATE) LocalDate start,
			@PathVariable("end") @DateTimeFormat(iso=ISO.DATE) LocalDate end
			);
	
	@GetMapping("/{accountHolderId}/balance")
	double getBalance(@PathVariable("accountHolderId") long accountHolderId);
}
