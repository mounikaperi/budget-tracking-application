package com.bta.profiles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bta.profiles.entities.AccountHolder;
import com.bta.profiles.exceptions.AccountHolderException;
import com.bta.profiles.services.AccountHolderService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ProfilesController {
	
	@Autowired
	private AccountHolderService accountHolderService;
	
	@GetMapping
	public ResponseEntity<List<AccountHolder>> getAll() {
		return ResponseEntity.ok(accountHolderService.getAll());
	}
	
	@GetMapping("/{accountHolderId}")
	public ResponseEntity<AccountHolder> getById(@PathVariable("accountHolderId") Long accountHolderId) {
		AccountHolder accountHolder = accountHolderService.getById(accountHolderId);
		return accountHolder != null ? ResponseEntity.ok(accountHolder) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{accountHolderId}/exists")
	public ResponseEntity<Boolean> existsById(@PathVariable("accountHolderId") Long accountHolderId) {
		return ResponseEntity.ok(accountHolderService.existsById(accountHolderId));
	}
	
	@PostMapping
	public ResponseEntity<AccountHolder> add(@RequestBody @Valid AccountHolder accountHolder,BindingResult bindingResult) throws AccountHolderException {
		if (bindingResult.hasErrors()) {
			throw new AccountHolderException(bindingResult.getAllErrors().stream().map(
					err -> err.getDefaultMessage()).reduce((m1, m2) -> m1 + "," + m2).orElse(null));
		}
		return new ResponseEntity(accountHolderService.add(accountHolder), HttpStatus.CREATED);
	}
}
