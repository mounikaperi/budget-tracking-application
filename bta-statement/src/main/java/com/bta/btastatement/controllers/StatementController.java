package com.bta.btastatement.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bta.btastatement.exceptions.StatementException;
import com.bta.btastatement.models.Statement;
import com.bta.btastatement.services.StatementService;

@RestController
@RequestMapping("/")
public class StatementController {

	@Autowired
	private StatementService statementService;
	
	@GetMapping("/{accountHolderId}/{year}")
	public ResponseEntity<Statement> getAnnualStatement(@PathVariable("accountHolderId")Long accountHolderId,
			@PathVariable("year")int year) throws StatementException {
		LocalDate start = LocalDate.of(year, Month.JANUARY, 1);
		LocalDate end = start.with(TemporalAdjusters.lastDayOfYear());
		return ResponseEntity.ok(statementService.getStatement(accountHolderId, start, end));
	}
	
	@GetMapping("/{accountHolderId}/{year}/{month}")
	public ResponseEntity<Statement> getMonthlyStatement(@PathVariable("accountHolderId")Long accountHolderId,
			@PathVariable("year")int year, @PathVariable("month")int month) throws StatementException {
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.with(TemporalAdjusters.lastDayOfMonth());
		return ResponseEntity.ok(statementService.getStatement(accountHolderId, start, end));
	}
}
