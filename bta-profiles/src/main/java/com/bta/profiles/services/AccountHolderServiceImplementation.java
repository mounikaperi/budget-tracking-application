package com.bta.profiles.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bta.profiles.entities.AccountHolder;
import com.bta.profiles.exceptions.AccountHolderException;
import com.bta.profiles.repositories.AccountHolderRepository;

@Service
public class AccountHolderServiceImplementation implements AccountHolderService {
	
	@Autowired
	private AccountHolderRepository accountHolderRepository;

	@Override
	public AccountHolder add(AccountHolder accountHolder) throws AccountHolderException {
		if (accountHolder.getAccountHolderId() != null && accountHolderRepository.existsById(accountHolder.getAccountHolderId())) {
			throw new AccountHolderException("A record with the given id already found");
		}
		return accountHolderRepository.save(accountHolder);
	}

	@Override
	public AccountHolder getById(Long accountHolderId) {
		return accountHolderRepository.findById(accountHolderId).orElse(null);
	}

	@Override
	public List<AccountHolder> getAll() {
		return accountHolderRepository.findAll();
	}

	@Override
	public boolean existsById(Long accountHolderId) {
		return accountHolderRepository.existsById(accountHolderId);
	}

	
}
