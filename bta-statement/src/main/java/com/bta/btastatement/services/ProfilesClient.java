package com.bta.btastatement.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bta.btastatement.models.AccountHolder;

@FeignClient(name="profiles", url="http://localhost:8001")
public interface ProfilesClient {
	
	@GetMapping("/{accountHolderId}/exists")
	public Boolean checkAccountHolderExists(@PathVariable("accountHolderId") Long accountHolderId);
	
	@GetMapping("/{accountHolderId}")
	public AccountHolder getAccountHolder(@PathVariable("accountHolderId") Long accountHolderId);
}
