package com.bta.profiles.services;

import com.bta.profiles.entities.AccountHolder;
import com.bta.profiles.exceptions.AccountHolderException;

import java.util.List;

public interface AccountHolderService {
	
	AccountHolder add(AccountHolder accountHolder) throws AccountHolderException;
	AccountHolder getById(Long accountHolderId);
	List<AccountHolder> getAll();
	boolean existsById(Long accountHolderId);

}
