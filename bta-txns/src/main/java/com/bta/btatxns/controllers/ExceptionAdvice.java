package com.bta.btatxns.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bta.btatxns.exceptions.AccountHolderException;
import com.bta.btatxns.exceptions.TransactionException;

@RestControllerAdvice
public class ExceptionAdvice {

	Logger logger;
	
	public ExceptionAdvice() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@ExceptionHandler(AccountHolderException.class)
	public ResponseEntity<String> handleAccountHolderException(AccountHolderException exception) {
		logger.error(exception.getMessage(), exception);
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<String> handleTransactionException(TransactionException exception) {
		logger.error(exception.getMessage(), exception);
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<String>("Internal Server Issue", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
